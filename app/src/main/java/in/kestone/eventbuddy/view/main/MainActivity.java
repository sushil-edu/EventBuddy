package in.kestone.eventbuddy.view.main;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.kestone.eventbuddy.Altdialog.CustomDialog;
import in.kestone.eventbuddy.Eventlistener.ViewClickListener;
import in.kestone.eventbuddy.MvpApp;
import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.adapter.NavMenuAdapter;
import in.kestone.eventbuddy.data.DataManager;
import in.kestone.eventbuddy.model.app_config.ListEvent;
import in.kestone.eventbuddy.model.app_config.Menu;
import in.kestone.eventbuddy.view.agenda.AgendaFragment;
import in.kestone.eventbuddy.view.login.ActivityLogin;
import in.kestone.eventbuddy.view.speaker.FragmentSpeaker;
import in.kestone.eventbuddy.view.splash.ActivitySplash;
import in.kestone.eventbuddy.view.stream.ActivityStream;
import in.kestone.eventbuddy.view.venue.FragmentVenue;
import in.kestone.eventbuddy.widgets.CustomTextView;

public class
MainActivity extends AppCompatActivity implements ViewClickListener, MainMvpView {

    List<Menu> list = new ArrayList<>();
    NavMenuAdapter adapter;
    int count;

    @BindView(R.id.navigation_view)
    NavigationView navigationView;

    ListView listView;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.container)
    FrameLayout container;

    CustomTextView tv_version_code;

    ActionBarDrawerToggle drawerLayoutToggle;
    View parentView;
    Toolbar toolbar;

    boolean doubleBackToExitPressedOnce = false;
    MainPresenter mainPresenter;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent( context, MainActivity.class );
        return intent;
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

        toolbar = drawerLayout.findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );

        DataManager dataManager = ((MvpApp) getApplication()).getDataManager();
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
        listView = navigationView.findViewById( R.id.menu_list );
        adapter = new NavMenuAdapter( this, list );
        listView.setAdapter( adapter );

        //default fragment
        {
            toolbar.setTitle( "Activity Stream" );
            getSupportFragmentManager().beginTransaction()
                    .replace( R.id.container, new ActivityStream() )
                    .commit();

        }


        tv_version_code = navigationView.findViewById( R.id.tv_version_code );
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
    public void onClick(Long mId, String title) {
        Log.e( "Menu ID ", String.valueOf( mId ) );

        if (!title.equals( "Log Out" )) {
            toolbar.setTitle( title );
        }
        openFragment( title );
        drawerLayout.closeDrawers();
    }

    private void openFragment(String title) {
        switch (title) {
            case "Log Out":
                showPopUpLogOut();
                break;
            case "Activity Stream":
                getSupportFragmentManager().beginTransaction()
                        .replace( R.id.container, new ActivityStream() )
                        .commit();
                break;
            case "Agenda":
                getSupportFragmentManager().beginTransaction()
                        .replace( R.id.container, new AgendaFragment() )
                        .commit();
                break;
            case "Speakers":
                getSupportFragmentManager().beginTransaction()
                        .replace( R.id.container, new FragmentSpeaker() )
                        .commit();
                break;
            case "Delegates":
                getSupportFragmentManager().beginTransaction()
                        .replace( R.id.container, new ActivityStream() )
                        .commit();
                break;
            case "Networking":
                getSupportFragmentManager().beginTransaction()
                        .replace( R.id.container, new ActivityStream() )
                        .commit();
                break;
            case "Polls":
                getSupportFragmentManager().beginTransaction()
                        .replace( R.id.container, new ActivityStream() )
                        .commit();
                break;
            case "Ask a Question":
                getSupportFragmentManager().beginTransaction()
                        .replace( R.id.container, new ActivityStream() )
                        .commit();
                break;
            case "Social":
                getSupportFragmentManager().beginTransaction()
                        .replace( R.id.container, new ActivityStream() )
                        .commit();
            case "Gallery":
                getSupportFragmentManager().beginTransaction()
                        .replace( R.id.container, new ActivityStream() )
                        .commit();
                break;
            case "Venue":
                getSupportFragmentManager().beginTransaction()
                        .replace( R.id.container, new FragmentVenue() )
                        .commit();
//                startActivity( new Intent( this, MapsActivity.class ) );
                break;
            case "Feedback":
                getSupportFragmentManager().beginTransaction()
                        .replace( R.id.container, new ActivityStream() )
                        .commit();
                break;
            case "Help Desk":
                getSupportFragmentManager().beginTransaction()
                        .replace( R.id.container, new ActivityStream() )
                        .commit();
                break;
        }
    }

    @Override
    public void onBackPressed() {

        if (drawerLayout.isDrawerOpen( GravityCompat.START )) {
            drawerLayout.closeDrawer( GravityCompat.START );
        } else {
            showPopUpExitFromApp();
        }

        new Handler().postDelayed( new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000 );
    }


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
                mainPresenter.setUserLoggedOut();
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
        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        v.vibrate(150);
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        root.setAnimation(shake);

    }

    @Override
    public void openSplashActivity() {
        Intent intent = ActivitySplash.getStartIntent( this );
        startActivity( intent );
        finish();
    }

    public void showPopUpExitFromApp() {
        final Dialog clearAll = new Dialog( this );
        clearAll.requestWindowFeature( Window.FEATURE_NO_TITLE );
        clearAll.setCancelable( true );
        clearAll.setContentView( R.layout.dialog_log_out );
        LinearLayout root = clearAll.findViewById( R.id.layout_root );
        clearAll.findViewById( R.id.tv_title ).setVisibility( View.GONE );
        TextView message = clearAll.findViewById( R.id.tv_message );
        message.setText( "Are you sure you want to exit?" );
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

        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        v.vibrate(150);
        Animation shake = AnimationUtils.loadAnimation(this, R.anim.shake);
        root.setAnimation(shake);
    }
}
