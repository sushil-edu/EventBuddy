package in.kestone.eventbuddy.view.main;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import in.kestone.eventbuddy.Eventlistener.FragmentErrorListener;
import in.kestone.eventbuddy.Eventlistener.ViewClickListener;
import in.kestone.eventbuddy.MvpApp;
import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.common.CONSTANTS;
import in.kestone.eventbuddy.data.DataManager;
import in.kestone.eventbuddy.fragment.AskQuestionFragment;
import in.kestone.eventbuddy.fragment.FAQFragment;
import in.kestone.eventbuddy.fragment.FeedbackFragment;
import in.kestone.eventbuddy.fragment.GalleryFragment;
import in.kestone.eventbuddy.fragment.HelpDeskFragment;
import in.kestone.eventbuddy.fragment.PollFragment;
import in.kestone.eventbuddy.view.social.SocialFragment;
import in.kestone.eventbuddy.fragment.WebViewFragment;
import in.kestone.eventbuddy.model.app_config_model.ListEvent;
import in.kestone.eventbuddy.model.app_config_model.Menu;
import in.kestone.eventbuddy.view.agenda.AgendaFragment;
import in.kestone.eventbuddy.view.networking.Networking;
import in.kestone.eventbuddy.view.partners.PartnerFragment;
import in.kestone.eventbuddy.view.profile.Profile;
import in.kestone.eventbuddy.view.speaker.FragmentSpeaker;
import in.kestone.eventbuddy.view.splash.ActivitySplash;
import in.kestone.eventbuddy.view.stream.ActivityStream;
import in.kestone.eventbuddy.view.venue.FragmentVenue;
import in.kestone.eventbuddy.widgets.CustomTextView;
import in.kestone.eventbuddy.widgets.ToolbarTextView;

public class MainActivity extends AppCompatActivity implements ViewClickListener, MainMvpView, FragmentErrorListener {

    List<Menu> list = new ArrayList<>();
    NavMenuAdapter adapter;
    int count;

    @BindView(R.id.navigation_view)
    NavigationView navigationView;
    @BindView(R.id.menu_list)
    ListView listView;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.container)
    FrameLayout container;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.mTitleTv)
    ToolbarTextView mTitleTv;

    //nav header
    @BindView(R.id.imageEdit)
    ImageView imageEdit;
    @BindView(R.id.tv_version_code)
    CustomTextView tv_version_code;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvEmail)
    TextView tvEmail;
    @BindView(R.id.tvDesignation)
    TextView tvDesignation;
    @BindView(R.id.profile_imageview)
    CircleImageView profileImage;

    ActionBarDrawerToggle drawerLayoutToggle;

    boolean doubleBackToExitPressedOnce = false;
    MainPresenter mainPresenter;
    DataManager dataManager;
    String appTitle;
    Bundle bundle = new Bundle(  );

    public static Intent getStartIntent(Context context) {
        return new Intent( context, MainActivity.class );
    }

    @RequiresApi(api = 28)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        initialiseView();
    }

    @RequiresApi(api = 28)
    private void initialiseView() {
        ButterKnife.bind( this );
        ButterKnife.bind( this, drawerLayout );
        ButterKnife.bind( drawerLayout, navigationView );

//        toolbar = drawerLayout.findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );

        dataManager = ((MvpApp) getApplication()).getDataManager();
        mainPresenter = new MainPresenter( dataManager );
        mainPresenter.onAttach( this );

        drawerLayoutToggle = new ActionBarDrawerToggle( this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close ) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed( view );
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened( drawerView );
                invalidateOptionsMenu();
            }

        };
        drawerLayout.addDrawerListener( drawerLayoutToggle );
        drawerLayoutToggle.setDrawerIndicatorEnabled( false );
        Drawable drawable = ResourcesCompat.getDrawable( getResources(), R.drawable.humber_icon, getTheme() );
        drawerLayoutToggle.setHomeAsUpIndicator( drawable );
        drawerLayoutToggle.setToolbarNavigationClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerVisible( GravityCompat.START )) {
                    drawerLayout.closeDrawer( GravityCompat.START );
                } else {
                    drawerLayout.openDrawer( GravityCompat.START );
                }
            }
        } );

        count = ListEvent.getAppConf().getEvent().getMenu().size();
        for (int i = 0; i < count; i++) {
            if (ListEvent.getAppConf().getEvent().getMenu().get( i ).getStatus() == 1) {
                list.add( ListEvent.getAppConf().getEvent().getMenu().get( i ) );
            }
        }
//        //set user details in nave header
//        tvName.setText( dataManager.getName() );
//        tvEmail.setText( dataManager.getEmailId() );
//        tvDesignation.setText( dataManager.getDesignation() );
//        Picasso.with( this ).load( dataManager.getImagePath() )
//                .resize( 80, 80 )
//                .placeholder( R.mipmap.ic_launcher_round )
//                .into( profileImage );

        //default activity
        openFragment( list.get( 0 ).getDisplayTitle(), list.get( 0 ).getMenutitle() );


        // menu list adapter
        adapter = new NavMenuAdapter( this, list );
        listView.setAdapter( adapter );

        //get version code
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo( getPackageName(), 0 );
            tv_version_code.setText( "App Version V" + pInfo.versionName );
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate( savedInstanceState );
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerLayoutToggle.syncState();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged( newConfig );
        drawerLayoutToggle.onConfigurationChanged( newConfig );
    }

    @Override
    public void onClick(int mId, final String dTitle, String mTitle) {
        Log.e( "Menu ID ", String.valueOf( mId ) +" title "+mTitle);
//        Handler handler = new Handler();
//        handler.postDelayed(new Runnable() {
//            public void run() {
        openFragment( dTitle, mTitle );
//            }
//        }, 50);
        drawerLayout.closeDrawers();
    }

    private void openFragment(String dTitle, String mTitle) {
        appTitle = dTitle;
        if (!dTitle.equals( "Log Out" )) {
            mTitleTv.setText( appTitle );
        }
        switch (mTitle) {
            case "Log Out":
                showPopUpLogOut();
                break;
            case "Activity Stream":
                bundle.putString( "title", dTitle );
                ActivityStream activityStream = new ActivityStream();
                activityStream.setArguments( bundle );
                getSupportFragmentManager().beginTransaction()
                        .replace( R.id.container, activityStream )
                        .commit();
                break;
            case "Agenda":
                new Handler().postDelayed( new Runnable() {
                    @Override
                    public void run() {
                        getSupportFragmentManager().beginTransaction()
                                .replace( R.id.container, new AgendaFragment() )
                                .commit();
                    }
                }, 50 );

                break;
            case "Speakers":
                new Handler().postDelayed( new Runnable() {
                    @Override
                    public void run() {
                        getSupportFragmentManager().beginTransaction()
                                .replace( R.id.container, new FragmentSpeaker() )
                                .commit();
                    }
                }, 50 );

                break;
            case "Delegates":
                getSupportFragmentManager().beginTransaction()
                        .replace( R.id.container, new FragmentSpeaker() )
                        .commit();
                break;
            case "Networking":
                getSupportFragmentManager().beginTransaction()
                        .replace( R.id.container, new Networking() )
                        .commit();
                break;
            case "Polls":
                getSupportFragmentManager().beginTransaction()
                        .replace( R.id.container, new PollFragment() )
                        .commit();
                break;
            case "Ask a Question":
            case "Knowledge Base":
                getSupportFragmentManager().beginTransaction()
                        .replace( R.id.container, new AskQuestionFragment() )
                        .commit();
                break;
            case "Social":
                getSupportFragmentManager().beginTransaction()
                        .replace( R.id.container, new SocialFragment() )
                        .commit();
                break;
            case "Gallery":
                getSupportFragmentManager().beginTransaction()
                        .replace( R.id.container, new GalleryFragment() )
                        .commit();
                break;
            case "Venue":
                getSupportFragmentManager().beginTransaction()
                        .replace( R.id.container, new FragmentVenue() )
                        .commit();
                break;
            case "Feedback":
                getSupportFragmentManager().beginTransaction()
                        .replace( R.id.container, new PollFragment() )
                        .commit();
//                getSupportFragmentManager().beginTransaction()
//                        .replace( R.id.container, new FeedbackFragment() )
//                        .commit();
                break;
            case "Help Desk":
                getSupportFragmentManager().beginTransaction()
                        .replace( R.id.container, new HelpDeskFragment() )
                        .commit();
                break;
            case "Terms And Conditions":
                String module = "Terms And Conditions";
                bundle.putString( "module", module );
                WebViewFragment fragment = new WebViewFragment();
                fragment.setArguments( bundle );

                getSupportFragmentManager().beginTransaction()
                        .replace( R.id.container, fragment )
                        .commit();
                break;

            case "Faq":
                getSupportFragmentManager().beginTransaction()
                        .replace( R.id.container, new FAQFragment() )
//                .addToBackStack( null )
                        .commit();
                break;

            case "Partners":
            case "Sponsors":

                PartnerFragment pfragment = new PartnerFragment();

                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction ft = manager.beginTransaction();
//        ft.setCustomAnimations( R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right );
//        ft.setTransition( FragmentTransaction.TRANSIT_FRAGMENT_OPEN );
                ft.replace( R.id.container, pfragment, CONSTANTS.PARTNERSDETAILS );
                ft.addToBackStack( null );
                ft.commit();
                break;

        }
    }

    //edit profile
    @OnClick(R.id.imageEdit)
    public void editProfile() {
        Intent intent = new Intent( MainActivity.this, Profile.class );
        startActivity( intent );
        drawerLayout.closeDrawers();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //meeting schedule and reschedule
        if (getIntent().getExtras() == null) {
            //default fragment
//            mTitleTv.setText( "Activity Stream" );
//            getSupportFragmentManager().beginTransaction()
//                    .replace( R.id.container, new ActivityStream() )
//                    .commit();
        } else if (getIntent().getStringExtra( "Tag" ).equalsIgnoreCase( "Schedule" ) ||
                getIntent().getStringExtra( "Tag" ).equalsIgnoreCase( "Reschedule" )) {
            mTitleTv.setText( "Networking" );

            getSupportFragmentManager().beginTransaction()
                    .replace( R.id.container, new Networking() )
                    .commit();

        } else if ((getIntent().getStringExtra( "Tag" ).equalsIgnoreCase( "Speaker" ))) {
            mTitleTv.setText( "Speaker" );

            getSupportFragmentManager().beginTransaction()
                    .replace( R.id.container, new FragmentSpeaker() )
                    .commit();

        } else if ((getIntent().getStringExtra( "Tag" ).equalsIgnoreCase( "Agenda" ))) {
            mTitleTv.setText( "Agenda" );

            getSupportFragmentManager().beginTransaction()
                    .replace( R.id.container, new AgendaFragment() )
                    .commit();
        }
        //set user details in nave header
        tvName.setText( dataManager.getName() );
        tvEmail.setText( dataManager.getEmailId() );
        tvDesignation.setText( dataManager.getDesignation() );

        byte[] decodedString = Base64.decode( dataManager.getImagePath(), Base64.DEFAULT );
        Bitmap decodedByte = BitmapFactory.decodeByteArray( decodedString, 0, decodedString.length );
        Picasso.with( this ).load( "ksdlfksdmsdmfsldkfsd" )
                .resize( 80, 80 )
                .placeholder( R.drawable.user )
                .into( profileImage );
//        profileImage.setImageBitmap( decodedByte );
    }

    @Override
    public void onBackPressed() {
        Log.e( "Count ", "" + getFragmentManager().getBackStackEntryCount() );
        if (getFragmentManager().getBackStackEntryCount() > 0)
            getFragmentManager().popBackStack();
        else {
            if (drawerLayout.isDrawerOpen( GravityCompat.START )) {
                drawerLayout.closeDrawer( GravityCompat.START );

            } else {
                if (doubleBackToExitPressedOnce) {
                    showPopUpExitFromApp();
                }
                //call default fragment
                this.doubleBackToExitPressedOnce = true;
                //default activity
                openFragment( list.get( 0 ).getDisplayTitle(), list.get( 0 ).getMenutitle() );
                new Handler().postDelayed( new Runnable() {

                    @Override
                    public void run() {
                        doubleBackToExitPressedOnce = false;
                    }
                }, 3000 );

            }
        }
    }

    //logout from the app
    public void showPopUpLogOut() {
        final Dialog clearAll = new Dialog( this );
        clearAll.requestWindowFeature( Window.FEATURE_NO_TITLE );
        clearAll.setCancelable( true );
        clearAll.setContentView( R.layout.dialog_log_out );
        LinearLayout root = clearAll.findViewById( R.id.layout_root );
        TextView no = clearAll.findViewById( R.id.no );
        TextView yes = clearAll.findViewById( R.id.yes );
        yes.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAll.dismiss();
//                SharedPreferences.Editor sp = getSharedPreferences( CommonUtils.AppConfigurationPrev, MODE_PRIVATE ).edit();
//                sp.clear();
                mainPresenter.setUserLoggedOut();
            }
        } );
        no.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearAll.dismiss();
            }
        } );

        clearAll.getWindow().setLayout( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT );
        clearAll.show();
        Vibrator v = (Vibrator) getSystemService( Context.VIBRATOR_SERVICE );
        // Vibrate for 500 milliseconds
        v.vibrate( 150 );
        Animation shake = AnimationUtils.loadAnimation( this, R.anim.shake );
        root.setAnimation( shake );

    }

    @Override
    public void openSplashActivity() {
        Intent intent = ActivitySplash.getStartIntent( this );
        startActivity( intent );
        finish();
    }

    //exit from the app
    public void showPopUpExitFromApp() {
        final Dialog clearAll = new Dialog( this );
        clearAll.requestWindowFeature( Window.FEATURE_NO_TITLE );
        clearAll.setCancelable( true );
        clearAll.setContentView( R.layout.dialog_log_out );
        LinearLayout root = clearAll.findViewById( R.id.layout_root );
        clearAll.findViewById( R.id.tv_title ).setVisibility( View.GONE );
        TextView message = clearAll.findViewById( R.id.tv_message );
        message.setText( "Are you sure. You want to exit?" );
        TextView no = clearAll.findViewById( R.id.no );
        TextView yes = clearAll.findViewById( R.id.yes );
        yes.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearAll.dismiss();
                finish();
            }
        } );
        no.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearAll.dismiss();
//
            }
        } );
        clearAll.getWindow().setLayout( ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT );
        clearAll.show();

        Vibrator v = (Vibrator) getSystemService( Context.VIBRATOR_SERVICE );
        // Vibrate for 500 milliseconds
        v.vibrate( 150 );
        Animation shake = AnimationUtils.loadAnimation( this, R.anim.shake );
        root.setAnimation( shake );
    }

    @Override
    public void onError(boolean status) {
        openFragment( list.get( 0 ).getDisplayTitle(), list.get( 0 ).getMenutitle() );
    }
}
