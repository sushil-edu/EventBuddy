package in.kestone.eventbuddy.view.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.kestone.eventbuddy.Altdialog.CustomDialog;
import in.kestone.eventbuddy.MvpApp;
import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.common.CommonUtils;
import in.kestone.eventbuddy.data.DataManager;
import in.kestone.eventbuddy.model.app_config.Button;
import in.kestone.eventbuddy.model.app_config.ForgotButton;
import in.kestone.eventbuddy.model.app_config.ListEvent;
import in.kestone.eventbuddy.model.app_config.Password;
import in.kestone.eventbuddy.model.app_config.UserName;
import in.kestone.eventbuddy.view.main.MainActivity;
import in.kestone.eventbuddy.view.verify.ActivityVerify;
import in.kestone.eventbuddy.widgets.CustomButton;
import in.kestone.eventbuddy.widgets.CustomEditText;
import in.kestone.eventbuddy.widgets.CustomTextView;

public class ActivityLogin extends Activity implements View.OnClickListener, LoginMvpView {

    public final String TAG = "ActivityLogin";
    @BindView(R.id.tv_event_title)
    CustomTextView tv_event_title;
    @BindView(R.id.et_email)
    CustomEditText et_mail;
    @BindView(R.id.et_password)
    CustomEditText et_password;
    @BindView(R.id.tv_LogIn)
    CustomButton tv_LogIn;
    @BindView(R.id.layout_email)
    LinearLayout layout_email;
    @BindView(R.id.layout_password)
    LinearLayout layout_password;
    String er_email, er_password;

    CustomDialog customDialog;
    LoginPresenter loginPresenter;

    public static Intent getStartIntent(Context context) {

        return new Intent( context, ActivityLogin.class );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_login );

        initialiseView();
    }

    private void initialiseView() {
        ButterKnife.bind( this );

        DataManager dataManager = ((MvpApp) getApplication()).getDataManager();
        loginPresenter = new LoginPresenter( dataManager );

        loginPresenter.onAttach( this );


        tv_event_title.setText( ListEvent.getAppConf().getEvent().getLogin().getWelcomeText() );
        et_mail.setHint( ListEvent.getAppConf().getEvent().getLogin().getUserName().getHint() );

        customDialog = new CustomDialog();
        //configure user name field
        setEmailConf( ListEvent.getAppConf().getEvent().getLogin().getUserName() );
        setPasswordConf( ListEvent.getAppConf().getEvent().getLogin().getPassword() );
        setButtonConf( ListEvent.getAppConf().getEvent().getLogin().getButton() );

    }

    private void setButtonConf(Button button) {
        tv_LogIn.setText( button.getLabel() );
        tv_LogIn.setOnClickListener( this );
    }

    private void setPasswordConf(Password password) {
        if (password.getVisibility().equalsIgnoreCase( "yes" )) {
            et_password.setHint( password.getHint() );
            er_password = password.getErrorMessage();
            if (password.getType().equalsIgnoreCase( "password" )) {
                et_password.setInputType( InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT );
            } else {
                et_password.setInputType( InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT );
            }
        } else {
            layout_password.setVisibility( View.GONE );
        }
    }

    private void setEmailConf(UserName userName) {
        et_mail.setHint( userName.getHint() );
        er_email = userName.getErrorMessage();
        if (userName.getType().equalsIgnoreCase( "email" )) {
            et_mail.setInputType( InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS | InputType.TYPE_CLASS_TEXT );
        } else {
            et_mail.setInputType( InputType.TYPE_CLASS_TEXT );
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_LogIn:
                onLoginButtonClick();
                break;
        }
    }

    @Override
    public void openMainActivity() {
        Intent intent;
        if (ListEvent.getAppConf().getEvent().getOTP().getVisibility().equalsIgnoreCase( "yes" )) {
            intent = ActivityVerify.getStartIntent( this );
            intent.putExtra( "type", "otp" );
        } else if (ListEvent.getAppConf().getEvent().getGeoTag().getVisibility().equalsIgnoreCase( "yes" )) {
            intent = ActivityVerify.getStartIntent( this );
            intent.putExtra( "type", "checkIn" );
        } else {
            intent = MainActivity.getStartIntent( this );
        }
        startActivity( intent );
        finish();
    }

    @Override
    public void onLoginButtonClick() {
        String email = et_mail.getText().toString();
        String password = et_password.getText().toString();

        if (email.isEmpty() && password.isEmpty()) {
            customDialog.showInvalidPopUp( ActivityLogin.this, "Invalid Credentials", "Please enter valid credentials" );
            et_mail.requestFocus();
        } else if (!CommonUtils.isEmailValid( email ) || email.isEmpty()) {
            customDialog.showInvalidPopUp( ActivityLogin.this, "Invalid Credentials", er_email );
            et_mail.requestFocus();
        } else if (layout_password.getVisibility() == View.VISIBLE) {
            if (password.isEmpty() || password == null) {
                customDialog.showInvalidPopUp( ActivityLogin.this, "Invalid Credentials", er_password );
                et_password.requestFocus();
            } else {
                int id = 1001;
                String name = "Sushil Kumar";
//                        String email="sushil.k@kestone.in";
                String path = "/image/image.png";
                String designation = "Software Engineer";
                String organization = "Kestone Pvt. Ltd.";
                String mobile = "7210094970";
                loginPresenter.startLogin( email, id, name, designation, path, organization, mobile );
            }
        }

    }
}
