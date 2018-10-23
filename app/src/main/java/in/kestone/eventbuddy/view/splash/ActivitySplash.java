package in.kestone.eventbuddy.view.splash;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;

import com.google.gson.Gson;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.kestone.eventbuddy.Altdialog.Progress;
import in.kestone.eventbuddy.MvpApp;
import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.common.ReadJson;
import in.kestone.eventbuddy.data.DataManager;
import in.kestone.eventbuddy.model.app_config.AppConf;
import in.kestone.eventbuddy.model.app_config.ListEvent;
import in.kestone.eventbuddy.view.login.ActivityLogin;
import in.kestone.eventbuddy.view.main.MainActivity;
import in.kestone.eventbuddy.widgets.CustomTextView;


public class ActivitySplash extends Activity implements SplashMvpView {

    @BindView(R.id.tv_eventName)
    CustomTextView tv_eventName;
    @BindView(R.id.tv_error)
    CustomTextView tv_error;
    AppConf ac;
    SplashPresenter mSplashPresenter;
    @BindView( R.id.progressBar )
    ProgressBar progressBar;


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

//        Progress.showProgress( this );
        new Handler().postDelayed( new Runnable() {
            @Override
            public void run() {
                setAppConf();
//                Progress.closeProgress();
            }
        }, 2000 );

    }

    private void setAppConf() {
        ac = new Gson().fromJson( new ReadJson().loadJSONFromAsset( ActivitySplash.this,
                 "app_conf.json" ), AppConf.class );
        if (ac.getStatusCode() == 200) {
            ListEvent.setAppConf( ac );
            //start activity
            DataManager dataManager = ((MvpApp) getApplication()).getDataManager();
            mSplashPresenter = new SplashPresenter( dataManager );
            mSplashPresenter.onAttach( this );
            mSplashPresenter.decideNextActivity();
        } else {
            Log.e( "Status", String.valueOf( ac.getStatusCode() ) );
            tv_error.setVisibility( View.VISIBLE );
        }
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
