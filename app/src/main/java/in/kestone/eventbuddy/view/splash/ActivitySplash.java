package in.kestone.eventbuddy.view.splash;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.kestone.eventbuddy.Altdialog.CustomDialog;
import in.kestone.eventbuddy.Altdialog.Progress;
import in.kestone.eventbuddy.MvpApp;
import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.common.CONSTANTS;
import in.kestone.eventbuddy.common.CommonUtils;
import in.kestone.eventbuddy.data.DataManager;
import in.kestone.eventbuddy.http.APIClient;
import in.kestone.eventbuddy.http.APIInterface;
import in.kestone.eventbuddy.model.app_config_model.AppConf;
import in.kestone.eventbuddy.model.app_config_model.ListEvent;
import in.kestone.eventbuddy.view.login.ActivityLogin;
import in.kestone.eventbuddy.view.main.MainActivity;
import in.kestone.eventbuddy.widgets.CustomTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
    String TAG = "ActivitySplash";

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
        if(CommonUtils.isNetworkConnected( getApplicationContext() )) {
            Progress.showProgress( this );
            getConfig();
        }else{
            CustomDialog.showInvalidPopUp( this, CONSTANTS.ERROR,"Check Internet connection" );
        }
    }

    private void setAppConf(AppConf acf) {

            ListEvent.setAppConf( acf );
            tv_eventName.setText( acf.getEvent().getEventName() );
//            Log.e("Event name  ", acf.getEvent().getEventName());
            DataManager dataManager = ((MvpApp) getApplication()).getDataManager();
            mSplashPresenter = new SplashPresenter( dataManager );
            mSplashPresenter.onAttach( this );
            mSplashPresenter.decideNextActivity();

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

    public void getConfig() {

            //get config from server
            APIInterface apiInterface = APIClient.getClient().create( APIInterface.class );
            Call<AppConf> call = apiInterface.getAppConfiguration( (int) CONSTANTS.EVENTID );
            call.enqueue( new Callback<AppConf>() {
                @Override
                public void onResponse(Call<AppConf> call, Response<AppConf> response) {
                    if(response.code()==200) {
                        //get config from preferences
//                    preferences = getSharedPreferences( CommonUtils.AppConfigurationPrev, MODE_PRIVATE );
//                    if (preferences.getFloat( "version",0 ) == response.body().getAppVersion()  || preferences!=null) {
//                        String config = preferences.getString( "AppConfiguration", "" );
//                        Log.e("Config ", config);
//                        AppConf appConf = new Gson().fromJson( config, AppConf.class );
////                        setAppConf( appConf );
//                    } else {
                        editor = getSharedPreferences( CommonUtils.AppConfigurationPrev, MODE_PRIVATE ).edit();
                        editor.putFloat( "version", response.body().getAppVersion() );
                        String gson = new Gson().toJson( response.body() );
                        editor.putString( "AppConfiguration", gson );
                        editor.apply();
                        setAppConf( response.body() );
//                    }
                    }
                    Progress.closeProgress();
                }

                @Override
                public void onFailure(Call<AppConf> call, Throwable t) {
                    CustomDialog.showInvalidPopUp( getApplicationContext(), CONSTANTS.ERROR, CONSTANTS.CONNECTIONERROR );
                    Progress.closeProgress();
                }
            } );

        }

}
