package in.kestone.eventbuddy.view.main;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
import in.kestone.eventbuddy.Altdialog.CustomDialog;
import in.kestone.eventbuddy.Eventlistener.FragmentErrorListener;
import in.kestone.eventbuddy.Eventlistener.ViewClickListener;
import in.kestone.eventbuddy.MvpApp;
import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.common.CONSTANTS;
import in.kestone.eventbuddy.common.LocalStorage;
import in.kestone.eventbuddy.data.DataManager;
import in.kestone.eventbuddy.fragment.AskQuestionFragment;
import in.kestone.eventbuddy.fragment.FAQFragment;
import in.kestone.eventbuddy.fragment.FeedbackFragment;
import in.kestone.eventbuddy.fragment.HelpDeskFragment;
import in.kestone.eventbuddy.fragment.PollFragment;
import in.kestone.eventbuddy.fragment.WebViewFragment;
import in.kestone.eventbuddy.model.app_config_model.ListEvent;
import in.kestone.eventbuddy.model.app_config_model.Menu;
import in.kestone.eventbuddy.model.speaker_model.SpeakerDetail;
import in.kestone.eventbuddy.view.agenda.AgendaFragment;
import in.kestone.eventbuddy.view.knowledgeBase.KnowlegdeBaseFragment;
import in.kestone.eventbuddy.view.networking.Networking;
import in.kestone.eventbuddy.view.partners.PartnerFragment;
import in.kestone.eventbuddy.view.profile.ProfileActivity;
import in.kestone.eventbuddy.view.social.SocialFragment;
import in.kestone.eventbuddy.view.speaker.FragmentSpeaker;
import in.kestone.eventbuddy.view.splash.ActivitySplash;
import in.kestone.eventbuddy.view.stream.ActivityStream;
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

    @BindView(R.id.subTitleTv)
    TextView subTitle;
    @BindView(R.id.image_background)
    ImageView imageBackGround;

    //nav header
    @BindView(R.id.imageEdit)
    ImageView imageEdit;
    @BindView(R.id.layout_profile)
    LinearLayout layout_profile;
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
    Bundle bundle = new Bundle();
    int flag = 0;

    public static Intent getStartIntent(Context context) {
        return new Intent( context, MainActivity.class );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );



        initialiseView();
    }

    private void initialiseView() {
        ButterKnife.bind( this );
        ButterKnife.bind( this, drawerLayout );
        ButterKnife.bind( drawerLayout, navigationView );

        setSupportActionBar( toolbar );

        dataManager = ((MvpApp) getApplication()).getDataManager();
        mainPresenter = new MainPresenter( dataManager );
        mainPresenter.onAttach( this );

        drawerLayoutToggle = new ActionBarDrawerToggle( this, drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close ) {
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
            list.add( ListEvent.getAppConf().getEvent().getMenu().get( i ) );
        }
        Menu m = new Menu();
        m.setDisplayTitle( CONSTANTS.LOGOUT );
        m.setMenuicon( "" );
        m.setMenutitle( CONSTANTS.LOGOUT );
        list.add( m );

        //load default fragment
        openFragment( list.get( 0 ).getDisplayTitle(), list.get( 0 ).getMenutitle(),
                list.get( 0 ).getHeader(), list.get( 0 ).getSubheader() );


        // menu list adapter
        adapter = new NavMenuAdapter( MainActivity.this, list );
        listView.setAdapter( adapter );

        //app version
        try {
            PackageInfo pInfo = getPackageManager().getPackageInfo( getPackageName(), 0 );
            tv_version_code.setText( "App Version V".concat( pInfo.versionName ) );
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate( savedInstanceState );
        drawerLayoutToggle.syncState();

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged( newConfig );
        drawerLayoutToggle.onConfigurationChanged( newConfig );
    }

    @Override
    public void onClick(int mId, final String dTitle, String mTitle, String header, String subHeader) {
        Log.d( "Menu ID ", String.valueOf( mId ) + " title " + mTitle );
        openFragment( dTitle, mTitle, header, subHeader );
        drawerLayout.closeDrawers();
    }

    private void openFragment(String dTitle, String mTitle, String header, String subHeader) {
        try {
            if (subHeader.length() > 0) {
                subTitle.setVisibility( View.VISIBLE );
            } else {
                subTitle.setVisibility( View.GONE );
            }
            if (header.length() > 0) {
                appTitle = header;
            } else {
                appTitle = dTitle;
            }
        } catch (NullPointerException ex) {
        }


        Fragment fragment;
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.setCustomAnimations( R.anim.enter_from_right, R.anim.exit_to_right,
                R.anim.enter_from_right, R.anim.exit_to_right );
        ft.setTransition( FragmentTransaction.TRANSIT_FRAGMENT_OPEN );

        switch (mTitle) {
            case CONSTANTS.LOGOUT:
                showPopUpLogOut();
                break;
            case CONSTANTS.ACTIVITYSTREAM:
            case CONSTANTS.ACTIVITYSTREAM2:
                mTitleTv.setText( appTitle );
                subTitle.setText( subHeader );
                bundle.putString( "title", dTitle );
                fragment = new ActivityStream();
                fragment.setArguments( bundle );
                ft.replace( R.id.container, fragment );
                ft.addToBackStack( null );
                ft.commit();
                break;
            case CONSTANTS.AGENDA:
                mTitleTv.setText( appTitle );
                subTitle.setText( subHeader );
                fragment = new AgendaFragment();
                ft.replace( R.id.container, fragment, CONSTANTS.AGENDA );
                ft.addToBackStack( null );
                ft.commit();
                break;
            case CONSTANTS.SPEAKER:
                mTitleTv.setText( appTitle );
                subTitle.setText( subHeader );
                bundle.putString( "module", "speaker" );
                fragment = new FragmentSpeaker();
                fragment.setArguments( bundle );
                ft.replace( R.id.container, fragment, CONSTANTS.SPEAKER );
                ft.addToBackStack( null );
                ft.commit();
                break;
            case CONSTANTS.DELEGATES:
                mTitleTv.setText( appTitle );
                subTitle.setText( subHeader );
                bundle.putString( "module", "delegate" );
                fragment = new FragmentSpeaker();
                fragment.setArguments( bundle );
                ft.replace( R.id.container, fragment, CONSTANTS.SPEAKER );
                ft.addToBackStack( null );
                ft.commit();
                break;
            case CONSTANTS.NETWORKING:
                mTitleTv.setText( appTitle );
                subTitle.setText( subHeader );
                ft.replace( R.id.container, new Networking(), CONSTANTS.NETWORKING );
                ft.addToBackStack( null );
                ft.commit();
                break;
            case CONSTANTS.HELPDESK:
                mTitleTv.setText( appTitle );
                subTitle.setText( subHeader );
                ft.replace( R.id.container, new HelpDeskFragment(), CONSTANTS.SPEAKER );
                ft.addToBackStack( null );
                ft.commit();
                break;
            case CONSTANTS.KNOWLEDGEBASE:
                mTitleTv.setText( appTitle );
                subTitle.setText( subHeader );
                fragment = new KnowlegdeBaseFragment();
                bundle.putString( "type", CONSTANTS.KNOWLEDGEBASE );
                fragment.setArguments( bundle );
                ft.replace( R.id.container, fragment, CONSTANTS.KNOWLEDGEBASE );
                ft.addToBackStack( null );
                ft.commit();
                break;
            //Q&A module
            case CONSTANTS.ASKAQUESTION:
                mTitleTv.setText( appTitle );
                subTitle.setText( subHeader );
                fragment = new AskQuestionFragment();
                ft.replace( R.id.container, fragment, CONSTANTS.ASKAQUESTION );
                ft.addToBackStack( null );
                ft.commit();
                break;
            case CONSTANTS.SOCIAL:
                mTitleTv.setText( appTitle );
                subTitle.setText( subHeader );
                ft.replace( R.id.container, new SocialFragment(), CONSTANTS.SOCIAL );
                ft.addToBackStack( null );
                ft.commit();
                break;
            case CONSTANTS.FEEDBACK:
                mTitleTv.setText( appTitle );
                subTitle.setText( subHeader );
                ft.replace( R.id.container, new FeedbackFragment(), CONSTANTS.FEEDBACK );
                ft.addToBackStack( null );
                ft.commit();
                break;
            case CONSTANTS.TANDC:
                mTitleTv.setText( appTitle );
                subTitle.setText( subHeader );
                bundle.putString( "module", CONSTANTS.TANDC );
                fragment = new WebViewFragment();
                fragment.setArguments( bundle );
                ft.replace( R.id.container, fragment, CONSTANTS.TANDC );
                ft.addToBackStack( null );
                ft.commit();
                break;
            case CONSTANTS.POLLING:
                mTitleTv.setText( appTitle );
                subTitle.setText( subHeader );
                fragment = new PollFragment();
                bundle.putString( "type", CONSTANTS.POLLING );
                fragment.setArguments( bundle );
                ft.replace( R.id.container, fragment, CONSTANTS.POLLING );
                ft.addToBackStack( null );
                ft.commit();
                break;
            case CONSTANTS.NOTIFICATION:
                mTitleTv.setText( appTitle );
                subTitle.setText( subHeader );
                fragment = new NotificationFragment();
                ft.replace( R.id.container, fragment, CONSTANTS.NOTIFICATION );
                ft.addToBackStack( null );
                ft.commit();
                break;
            case CONSTANTS.FAQS:
                mTitleTv.setText( appTitle );
                subTitle.setText( subHeader );
                ft.replace( R.id.container, new FAQFragment(), CONSTANTS.FAQS );
                ft.addToBackStack( null );
                ft.commit();
                break;
            case CONSTANTS.PARTNERS:
                mTitleTv.setText( appTitle );
                subTitle.setText( subHeader );
                fragment = new PartnerFragment();
                bundle.putString( "type", CONSTANTS.PARTNERS );
                fragment.setArguments( bundle );
                ft.replace( R.id.container, fragment, CONSTANTS.PARTNERS );
                ft.addToBackStack( null );
                ft.commit();
                break;
            case CONSTANTS.SPONSORS:
                mTitleTv.setText( appTitle );
                subTitle.setText( subHeader );
                fragment = new PartnerFragment();
                bundle.putString( "type", CONSTANTS.SPONSORS );
                fragment.setArguments( bundle );
                ft.replace( R.id.container, fragment, CONSTANTS.SPONSORS );
                ft.addToBackStack( null );
                ft.commit();
                break;
//            case CONSTANTS.VENUE:
//                mTitleTv.setText( appTitle );
//                subTitle.setText( subHeader );
//                ft.replace( R.id.container, new FragmentVenue(), CONSTANTS.VENUE );
//                ft.addToBackStack( null );
//                ft.commit();
//                break;
            default:
                CustomDialog.showInvalidPopUp( MainActivity.this, CONSTANTS.ERROR, "Module not found" );
                break;

        }
    }

    //edit profile
    @OnClick({R.id.imageEdit, R.id.layout_profile})
    public void editProfile() {
        Intent intent = new Intent( MainActivity.this, ProfileActivity.class );
        overridePendingTransition( R.anim.enter_from_right, R.anim.exit_to_right );
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
        } else if (getIntent().getStringExtra( "Tag" ).equalsIgnoreCase( CONSTANTS.SCHEDULE ) ||
                getIntent().getStringExtra( "Tag" ).equalsIgnoreCase( CONSTANTS.RESCHEDULE )) {
            mTitleTv.setText( CONSTANTS.NETWORKING );
            Networking networking = new Networking();
            SpeakerDetail speakerDetail = (SpeakerDetail) getIntent().getExtras().getSerializable( "Data" );
            Bundle bundle = new Bundle();
            bundle.putSerializable( "data", speakerDetail );
            networking.setArguments( bundle );
            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations( R.anim.enter_from_right, R.anim.exit_to_right,
                            R.anim.enter_from_right, R.anim.exit_to_right )
                    .setTransition( FragmentTransaction.TRANSIT_FRAGMENT_OPEN )
                    .replace( R.id.container, networking )
                    .commit();

        } else if ((getIntent().getStringExtra( "Tag" ).equalsIgnoreCase( "Speaker" ))) {
            mTitleTv.setText( CONSTANTS.SPEAKER );

            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations( R.anim.enter_from_right, R.anim.exit_to_right,
                            R.anim.enter_from_right, R.anim.exit_to_right )
                    .setTransition( FragmentTransaction.TRANSIT_FRAGMENT_OPEN )
                    .replace( R.id.container, new FragmentSpeaker() )
                    .commit();

        } else if ((getIntent().getStringExtra( "Tag" ).equalsIgnoreCase( CONSTANTS.AGENDA ))) {
            mTitleTv.setText( CONSTANTS.AGENDA );

            getSupportFragmentManager().beginTransaction()
                    .setCustomAnimations( R.anim.enter_from_right, R.anim.exit_to_right,
                            R.anim.enter_from_right, R.anim.exit_to_right )
                    .setTransition( FragmentTransaction.TRANSIT_FRAGMENT_OPEN )
                    .replace( R.id.container, new AgendaFragment() )
                    .commit();
        }
        //set user details in nave header
        tvName.setText( dataManager.getName() );
        tvEmail.setText( dataManager.getEmailId() );
        tvDesignation.setText( dataManager.getDesignation() );
        if (LocalStorage.getEventID( MainActivity.this ) != 0) {
            Picasso.with( MainActivity.this ).load( "http://eventsbuddy.in/beta/".concat( LocalStorage.getMasterHead( MainActivity.this ) ) )
                    .fit().into( imageBackGround );
        }

        if (dataManager.getImage().contains( LocalStorage.getImagePath( MainActivity.this ) )) {

            Picasso.with( MainActivity.this ).load( (dataManager.getImage()) )
                    .resize( 80, 80 )
                    .placeholder( R.drawable.default_user_grey )
                    .into( profileImage );
        } else {
            Picasso.with( MainActivity.this ).load( LocalStorage.getImagePath( MainActivity.this ).concat( dataManager.getImage() ) )
                    .resize( 80, 80 )
                    .placeholder( R.drawable.default_user_grey )
                    .into( profileImage );
        }


    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen( GravityCompat.START )) {
            drawerLayout.closeDrawer( GravityCompat.START );

        } else {
            if (doubleBackToExitPressedOnce) {
                showPopUpExitFromApp();
            }
            //call default fragment
            openFragment( list.get( 0 ).getDisplayTitle(), list.get( 0 ).getMenutitle(),
                    list.get( 0 ).getHeader(), list.get( 0 ).getSubheader() );
            this.doubleBackToExitPressedOnce = true;

            new Handler().postDelayed( new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;

                }
            }, 3000 );

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
//                LocalStorage.clearData( MainActivity.this );
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
        // Vibrate for 50 milliseconds
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
        message.setText( CONSTANTS.LOGOUTSTRING );
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
        openFragment( list.get( 0 ).getDisplayTitle(), list.get( 0 ).getMenutitle(), list.get( 0 ).getHeader(), list.get( 0 ).getSubheader() );
    }


}
