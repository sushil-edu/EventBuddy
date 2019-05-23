package in.kestone.eventbuddy.view.verify;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import java.util.HashMap;

import in.kestone.eventbuddy.Altdialog.CustomDialog;
import in.kestone.eventbuddy.Altdialog.Progress;
import in.kestone.eventbuddy.Eventlistener.OnVerifiedListener;
import in.kestone.eventbuddy.MvpApp;
import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.common.CONSTANTS;
import in.kestone.eventbuddy.common.LocalStorage;
import in.kestone.eventbuddy.data.DataManager;
import in.kestone.eventbuddy.fragment.Priority;
import in.kestone.eventbuddy.http.APIClient;
import in.kestone.eventbuddy.http.APIInterface;
import in.kestone.eventbuddy.http.CallUtils;
import in.kestone.eventbuddy.model.app_config_model.ListEvent;
import in.kestone.eventbuddy.model.user_model.Profile;
import in.kestone.eventbuddy.model.user_model.User;
import in.kestone.eventbuddy.view.login.ActivityLogin;
import in.kestone.eventbuddy.view.main.MainActivity;
import in.kestone.eventbuddy.view.main.MainMvpView;
import in.kestone.eventbuddy.view.main.MainPresenter;
import in.kestone.eventbuddy.view.splash.ActivitySplash;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityVerify extends AppCompatActivity implements OnVerifiedListener, MainMvpView {
    String type = "";
    MainPresenter mainPresenter;
    DataManager dataManager;
    String EMAIL;
    SharedPreferences.Editor editor;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent( context, ActivityVerify.class );
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        //remove title
        requestWindowFeature( Window.FEATURE_NO_TITLE );
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView( R.layout.activity_verify );

        dataManager = ((MvpApp) getApplication()).getDataManager();
        mainPresenter = new MainPresenter( dataManager );
        mainPresenter.onAttach( this );

        editor = getSharedPreferences( CONSTANTS.CHECKIN, MODE_PRIVATE ).edit();
        editor.putBoolean( "status", false );
        editor.apply();

        if (getIntent() != null) {
            EMAIL = getIntent().getStringExtra( "EMAIL" );
        }

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
    public void onVerified(String status, String otp) {
        
        if (status.equalsIgnoreCase( "verified" ) &&
                ListEvent.getAppConf().getEvent().getGeoTag().getVisibility().equalsIgnoreCase( "true" )) {
            getSupportFragmentManager().beginTransaction()
                    .replace( R.id.container, new FragmentCheckIn() )
                    .commit();
            editor.putBoolean( "status", true );
            editor.apply();
        } else if (status.equalsIgnoreCase( "check-in" ) &&
                ListEvent.getAppConf().getEvent().getPriority().getVisibility().equalsIgnoreCase( "true" )) {
            getSupportFragmentManager().beginTransaction()
                    .replace( R.id.container, new Priority() )
                    .commit();
            editor.putBoolean( "status", true );
            editor.apply();
        } else if (status.equalsIgnoreCase( "check-in" ) &&
                ListEvent.getAppConf().getEvent().getPriority().getVisibility().equalsIgnoreCase( "false" )) {
            Intent intent = MainActivity.getStartIntent( ActivityVerify.this );
            startActivity( intent );
            finish();
            editor.putBoolean( "status", true );
            editor.apply();
        } else if (status.equalsIgnoreCase( "verified" ) && !otp.isEmpty()) {

            //verifying OTP
//            Profile profile = new Profile();
//            profile.setEmailID( EMAIL );
//            profile.setPassword( otp );
//            profile.setEventID( CONSTANTS.EVENTID );
//            login( profile );

            HashMap<String, String > profile = new HashMap<>(  );
            profile.put( "EmailID",EMAIL );
            profile.put( "Password",    otp );
            profile.put( "EventID", String.valueOf( LocalStorage.getEventID( ActivityVerify.this ) ) );
            login( profile );

        }

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

    private void login(HashMap<String, String> profile) {
        APIInterface apiInterface = APIClient.getClient().create( APIInterface.class );
        Call<User> call = apiInterface.login( profile);
        CallUtils.enqueueWithRetry( call, 2, new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    if (response.body().getStatusCode() == 200) {
                        SharedPreferences.Editor editor = getSharedPreferences( CONSTANTS.CHECKIN, MODE_PRIVATE ).edit();
                        Intent intent = MainActivity.getStartIntent( ActivityVerify.this );
                        startActivity( intent );
                        finish();
                        editor.putBoolean( "status", true );
                        editor.apply();
                    } else {
                        CustomDialog.showInvalidPopUp( ActivityVerify.this, CONSTANTS.ERROR, response.body().getMessage() );

                    }
                } else {
                    CustomDialog.invalidPopUp( ActivityVerify.this, CONSTANTS.ERROR, response.message() );
                }
                Progress.closeProgress();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                CustomDialog.invalidPopUp( ActivityVerify.this, CONSTANTS.ERROR, CONSTANTS.CONNECTIONERROR );
                Progress.closeProgress();
            }
        } );
    }

}
