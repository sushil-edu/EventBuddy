package in.kestone.eventbuddy.view.verify;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import in.kestone.eventbuddy.Eventlistener.OnVerifiedListener;
import in.kestone.eventbuddy.MvpApp;
import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.common.CONSTANTS;
import in.kestone.eventbuddy.data.DataManager;
import in.kestone.eventbuddy.fragment.FragmentCheckIn;
import in.kestone.eventbuddy.fragment.OtpFragment;
import in.kestone.eventbuddy.fragment.Priority;
import in.kestone.eventbuddy.model.app_config_model.ListEvent;
import in.kestone.eventbuddy.view.login.ActivityLogin;
import in.kestone.eventbuddy.view.main.MainActivity;
import in.kestone.eventbuddy.view.main.MainMvpView;
import in.kestone.eventbuddy.view.main.MainPresenter;
import in.kestone.eventbuddy.view.splash.ActivitySplash;

public class ActivityVerify extends AppCompatActivity implements OnVerifiedListener, MainMvpView {
    String type = "";
    MainPresenter mainPresenter;
    DataManager dataManager;
    SharedPreferences preferences;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent( context, ActivityVerify.class );
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_verify );

        dataManager = ((MvpApp) getApplication()).getDataManager();
        mainPresenter = new MainPresenter( dataManager );
        mainPresenter.onAttach( this );

        type = getIntent().getStringExtra( "type" );
        if (type.equalsIgnoreCase( "checkIn" )) {
            getSupportFragmentManager().beginTransaction()
                    .replace( R.id.container, new FragmentCheckIn() )
                    .commit();
        } else if (type.equalsIgnoreCase( "otp" )) {
            getSupportFragmentManager().beginTransaction()
                    .replace( R.id.container, new OtpFragment() )
                    .commit();
        }
    }

    @Override
    public void onVerified(String status) {
        SharedPreferences.Editor editor = getSharedPreferences( CONSTANTS.CHECKIN, MODE_PRIVATE).edit();

        if (status.equalsIgnoreCase( "verified" ) &&
                ListEvent.getAppConf().getEvent().getGeoTag().getVisibility().equalsIgnoreCase( "true" )) {
            getSupportFragmentManager().beginTransaction()
                    .replace( R.id.container, new FragmentCheckIn() )
                    .commit();
            editor.putBoolean("status", true);

        } else if (status.equalsIgnoreCase( "check-in" ) &&
                ListEvent.getAppConf().getEvent().getPriority().getVisibility().equalsIgnoreCase( "true" )) {
            getSupportFragmentManager().beginTransaction()
                    .replace( R.id.container, new Priority() )
                    .commit();
            editor.putBoolean("status", false);
        }else {
            Intent intent = MainActivity.getStartIntent( this );
            startActivity( intent );
            finish();
            editor.putBoolean("status", true);
        }
        editor.apply();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mainPresenter.setUserLoggedOut();
        startActivity( new Intent( ActivityVerify.this, ActivityLogin.class ) );
        finish();

    }

    @Override
    public void openSplashActivity() {
        Intent intent = ActivitySplash.getStartIntent( this );
        startActivity( intent );
        finish();
    }

}
