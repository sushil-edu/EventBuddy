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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.kestone.eventbuddy.Altdialog.CustomDialog;
import in.kestone.eventbuddy.Altdialog.Progress;
import in.kestone.eventbuddy.MvpApp;
import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.common.CONSTANTS;
import in.kestone.eventbuddy.common.LocalStorage;
import in.kestone.eventbuddy.data.DataManager;
import in.kestone.eventbuddy.http.APIClient;
import in.kestone.eventbuddy.http.APIInterface;
import in.kestone.eventbuddy.model.app_config_model.AppConf;
import in.kestone.eventbuddy.model.app_config_model.ListEvent;
import in.kestone.eventbuddy.view.SelectEventActivity;
import in.kestone.eventbuddy.view.login.ActivityLogin;
import in.kestone.eventbuddy.view.main.MainActivity;
import in.kestone.eventbuddy.widgets.CustomTextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ActivitySplash extends Activity implements SplashMvpView {

    @BindView(R.id.tv_eventName)
    TextView tv_eventName;
    @BindView(R.id.tv_error)
    TextView tv_error;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.imageSplashBackground)
    ImageView splashBackground;
    SplashPresenter mSplashPresenter;

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

//        String config = LocalStorage.getConfiguration( ActivitySplash.this );
//                        Log.e( "Config ", config );
//                        AppConf appConf = new Gson().fromJson( config, AppConf.class );
//                        setAppConf( appConf );

        Log.e( "Event ID ", "" + LocalStorage.getEventID( ActivitySplash.this ) );
        if (LocalStorage.getEventID( ActivitySplash.this ) != 0) {

            getConfig( LocalStorage.getEventID( ActivitySplash.this ) );
            Progress.showProgress( this );
            tv_eventName.setVisibility( View.GONE );
            Picasso.get()
                    .load( CONSTANTS.betaimagepath.concat( LocalStorage.getSplashBackground( this ) ) )
                    .into( splashBackground );
        } else {
            startActivity( new Intent( ActivitySplash.this, SelectEventActivity.class ) );
        }
    }

    private void setAppConf(AppConf acf) {

        ListEvent.setAppConf( acf );
        DataManager dataManager = ((MvpApp) getApplication()).getDataManager();
        mSplashPresenter = new SplashPresenter( dataManager );
        mSplashPresenter.onAttach( this );
        tv_eventName.setVisibility( View.VISIBLE );
        tv_eventName.setText( acf.getEvent().getEventName() );

        new Handler().postDelayed(() -> mSplashPresenter.decideNextActivity(), 2000 );

    }

    @Override
    public void openMainActivity() {
        SharedPreferences prefs = getSharedPreferences( CONSTANTS.CHECKIN, MODE_PRIVATE );
        Intent intent;

        if (prefs.getBoolean( "status", false )) {
            intent = MainActivity.getStartIntent( this );
        } else {
            intent = ActivityLogin.getStartIntent( this );
        }
        startActivity( intent );
        finish();
    }

    @Override
    public void openLoginActivity() {

        Intent intent = ActivityLogin.getStartIntent( this );
        startActivity( intent );
        finish();
    }

    public void getConfig(int eventID) {
        APIInterface apiInterface = APIClient.getClient().create( APIInterface.class );
        Call<AppConf> call = apiInterface.getAppConfiguration( eventID );
        call.enqueue( new Callback<AppConf>() {
            @Override
            public void onResponse(Call<AppConf> call, Response<AppConf> response) {
                if (response.code() == 200 || response.code() == 201) {
                    //set config to preferences
//                    if (LocalStorage.getConfigVersion( ActivitySplash.this ) == response.body().getAppVersion()
//                    && LocalStorage.getConfiguration( ActivitySplash.this )!=null) {
//                        //get configuration from prefaces
//                        String config = LocalStorage.getConfiguration( ActivitySplash.this );
//                        Log.e( "Config ", config );
//                        AppConf appConf = new Gson().fromJson( config, AppConf.class );
//                        setAppConf( appConf );
//                    }else {
//                    LocalStorage.saveConfiguration( response.body(), ActivitySplash.this );
                    setAppConf( response.body() );
//                    }
                } else {
                    CustomDialog.invalidPopUp( ActivitySplash.this, CONSTANTS.ERROR, response.message() );
                }
                Progress.closeProgress();
            }

            @Override
            public void onFailure(Call<AppConf> call, Throwable t) {
                CustomDialog.invalidPopUp( ActivitySplash.this, CONSTANTS.ERROR, t.getMessage() );
                Progress.closeProgress();
            }
        } );
    }
}
