package in.kestone.eventbuddy.view.splash;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.kestone.eventbuddy.Altdialog.Progress;
import in.kestone.eventbuddy.MvpApp;
import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.common.CommonUtils;
import in.kestone.eventbuddy.common.ReadJson;
import in.kestone.eventbuddy.data.DataManager;
import in.kestone.eventbuddy.model.app_config_model.AppConf;
import in.kestone.eventbuddy.model.app_config_model.ListEvent;
import in.kestone.eventbuddy.view.login.ActivityLogin;
import in.kestone.eventbuddy.view.main.MainActivity;
import in.kestone.eventbuddy.widgets.CustomTextView;


public class ActivitySplash extends Activity implements SplashMvpView {

    @BindView(R.id.tv_eventName)
    CustomTextView tv_eventName;
    @BindView(R.id.tv_error)
    CustomTextView tv_error;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    SharedPreferences.Editor editor;
    SharedPreferences preferences;
    AppConf ac;
    SplashPresenter mSplashPresenter;
    String appConfiguration = "";


    public static Intent getStartIntent(Context context) {
        return new Intent( context, ActivitySplash.class );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        //remove title
        requestWindowFeature( Window.FEATURE_NO_TITLE );
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN );
        setContentView( R.layout.activity_splash );

        initialiseView();

    }

    private void initialiseView() {
        ButterKnife.bind( this );
        //set configuration
        Progress.showProgress( this );
        new Handler().postDelayed( new Runnable() {
            @Override
            public void run() {
                appConfiguration = ReadJson.loadJSONFromAsset( ActivitySplash.this, "app_conf.json" );

                Log.d( "Conf ", appConfiguration );
                if (preferences == null ) {
                    editor = getSharedPreferences( CommonUtils.AppConfigurationPrev, MODE_PRIVATE ).edit();
                    editor.putString( "AppConfiguration", appConfiguration );
                    editor.apply();
                    preferences = getSharedPreferences( CommonUtils.AppConfigurationPrev, MODE_PRIVATE );
                    ac = new Gson().fromJson( preferences.getString( "AppConfiguration", "" ), AppConf.class );
                } else {
                    preferences = getSharedPreferences( CommonUtils.AppConfigurationPrev, MODE_PRIVATE );
                    ac = new Gson().fromJson( preferences.getString( "AppConfiguration", "" ), AppConf.class );

                }
                setAppConf( ac );
            }
        }, 2000 );

    }

    private void setAppConf(AppConf acf ) {
        if (acf.getStatusCode() == 200) {
            ListEvent.setAppConf( acf );
            DataManager dataManager = ((MvpApp) getApplication()).getDataManager();
            mSplashPresenter = new SplashPresenter( dataManager );
            mSplashPresenter.onAttach( this );
            mSplashPresenter.decideNextActivity();
        } else {
            Log.e( "Status", String.valueOf( acf.getStatusCode() ) );
            tv_error.setVisibility( View.VISIBLE );
        }
        Progress.closeProgress();
    }

    @Override
    public void openMainActivity() {
        Intent intent = MainActivity.getStartIntent( this );
//        Intent intent = ActivityLogin.getStartIntent( this );
        startActivity( intent );
        finish();
    }

    @Override
    public void openLoginActivity() {
        Intent intent = ActivityLogin.getStartIntent( this );
        startActivity( intent );
        finish();
    }
}
