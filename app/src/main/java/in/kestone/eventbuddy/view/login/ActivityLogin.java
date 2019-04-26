package in.kestone.eventbuddy.view.login;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.kestone.eventbuddy.Altdialog.CustomDialog;
import in.kestone.eventbuddy.Altdialog.Progress;
import in.kestone.eventbuddy.MvpApp;
import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.common.CONSTANTS;
import in.kestone.eventbuddy.common.CommonUtils;
import in.kestone.eventbuddy.common.LocalStorage;
import in.kestone.eventbuddy.data.DataManager;
import in.kestone.eventbuddy.http.APIClient;
import in.kestone.eventbuddy.http.APIInterface;
import in.kestone.eventbuddy.http.CallUtils;
import in.kestone.eventbuddy.model.app_config_model.Button;
import in.kestone.eventbuddy.model.app_config_model.ForgotButton;
import in.kestone.eventbuddy.model.app_config_model.ListEvent;
import in.kestone.eventbuddy.model.app_config_model.Login;
import in.kestone.eventbuddy.model.app_config_model.Password;
import in.kestone.eventbuddy.model.app_config_model.UserName;
import in.kestone.eventbuddy.model.user_model.Profile;
import in.kestone.eventbuddy.model.user_model.User;
import in.kestone.eventbuddy.view.main.MainActivity;
import in.kestone.eventbuddy.view.verify.ActivityVerify;
import in.kestone.eventbuddy.widgets.CustomButton;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ActivityLogin extends Activity implements View.OnClickListener, LoginMvpView {

    public final String TAG = "ActivityLogin";
    @BindView(R.id.tv_event_title)
    TextView tv_event_title;
    @BindView(R.id.et_email)
    EditText et_mail;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.tv_forgot_password)
    TextView tv_forgot_password;
    @BindView(R.id.tv_LogIn)
    CustomButton tv_LogIn;
    @BindView(R.id.layout_email)
    LinearLayout layout_email;
    @BindView(R.id.layout_password)
    LinearLayout layout_password;
    ArrayList<Profile> profileDetails = new ArrayList<>();
//    @BindView( R.id.image_show_password )
//    ImageView image_show_password;

    String er_email_message, er_password_message, er_email_header, er_password_header;

    LoginPresenter loginPresenter;
    APIInterface apiInterface;
    Login loginModel;


    public static Intent getStartIntent(Context context) {

        return new Intent( context, ActivityLogin.class );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        //remove title
        requestWindowFeature( Window.FEATURE_NO_TITLE );
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN );
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

        //configure user name field
        loginModel = ListEvent.getAppConf().getEvent().getLogin();
//        if(loginModel.getUserName().getVisibility()!=null) {
        setEmailConf( loginModel.getUserName() );
//        }else if(loginModel.getPassword().getVisibility()!=null) {
        setPasswordConf( loginModel.getPassword() );
//        }else if(loginModel.getButton().getVisibility()!=null) {
        setButtonConf( loginModel.getButton() );
//        }else if(loginModel.getForgotButton().getVisibility()!=null) {
        setForgotPassword( loginModel.getForgotButton() );
//        }else {
//            finish();
//        }

//        image_show_password.setOnClickListener( this );
    }

    private void setForgotPassword(ForgotButton forgotButton) {
        if (forgotButton.getVisibility().equalsIgnoreCase( "true" )) {
            tv_forgot_password.setVisibility( View.VISIBLE );
            tv_forgot_password.setText( forgotButton.getLabel() );
            tv_forgot_password.setOnClickListener( this );
        } else {
            tv_forgot_password.setVisibility( View.GONE );
            tv_forgot_password.setOnClickListener( null );
        }
    }

    private void setButtonConf(Button button) {
        tv_LogIn.setText( button.getLabel() );
        tv_LogIn.setOnClickListener( this );
    }

    private void setPasswordConf(Password password) {
        if (password.getVisibility().equalsIgnoreCase( "true" )) {
            layout_password.setVisibility( View.VISIBLE );
            et_password.setHint( password.getHint() );
            er_password_message = password.getErrorMessage();
            er_password_header = password.getErrorHeader();
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
        if (userName.getVisibility().equalsIgnoreCase( "true" )) {
            layout_email.setVisibility( View.VISIBLE );
            et_mail.setHint( userName.getHint() );
            er_email_message = userName.getErrorMessage();
            er_email_header = userName.getErrorHeader();
            if (userName.getType().equalsIgnoreCase( "email" )) {
                et_mail.setInputType( InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS | InputType.TYPE_CLASS_TEXT );
            } else {
                et_mail.setInputType( InputType.TYPE_CLASS_TEXT );
            }
        }else {
            layout_email.setVisibility( View.GONE );
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_LogIn:
                onLoginButtonClick();
                break;
            case R.id.tv_forgot_password:
                Intent intent = new Intent( ActivityLogin.this, Forgot_Password.class );
                Bundle bundle = new Bundle();
                bundle.putSerializable( CONSTANTS.FORGOTPASSWOTD, loginModel );
                intent.putExtras( bundle );
                startActivity( intent );
                break;
//            case R.id.image_show_password:
//                if (flag==false) {
//                    et_password.setTransformationMethod( HideReturnsTransformationMethod.getInstance() );
//                    flag=true;
//                }else {
//                    et_password.setTransformationMethod( PasswordTransformationMethod.getInstance() );
//                    flag=false;
//                }
//                break;
        }
    }

    @Override
    public void openMainActivity() {
        Intent intent;
        if (ListEvent.getAppConf().getEvent().getOTP().getVisibility().equalsIgnoreCase( "true" )) {
            intent = ActivityVerify.getStartIntent( this );
            intent.putExtra( "type", "otp" );
            intent.putExtra( "EMAIL", et_mail.getText().toString() );

        } else if (ListEvent.getAppConf().getEvent().getGeoTag().getVisibility().equalsIgnoreCase( "true" )) {
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

        if (layout_email.getVisibility() == View.VISIBLE && layout_password.getVisibility() == View.VISIBLE) {
            if (email.isEmpty() && password.isEmpty()) {
                CustomDialog.showInvalidPopUp( ActivityLogin.this, "Invalid Credential", "Please enter valid credential" );
                et_mail.requestFocus();
            } else if (!CommonUtils.isEmailValid( email )) {
                CustomDialog.showInvalidPopUp( ActivityLogin.this, er_email_header, er_email_message );
                et_mail.requestFocus();
            } else if (layout_password.getVisibility() == View.VISIBLE) {
                if (password.isEmpty() || password == null) {
                    CustomDialog.showInvalidPopUp( ActivityLogin.this, er_password_header, er_password_message );
                    et_password.requestFocus();
                } else {
//                    Profile profile = new Profile();
//                    profile.setEmailID( email );
//                    profile.setPassword( password );
//                    profile.setEventID( CONSTANTS.EVENTID );
//                    {"EmailID":"sushil.k@kestone.in","Password":"7210094970", "EventID":14}
                    HashMap<String, String> profile = new HashMap<>();
                    profile.put( "EmailID", email );
                    profile.put( "Password", password );
                    profile.put( "EventID", String.valueOf( CONSTANTS.EVENTID ) );


                    if (CommonUtils.isNetworkConnected( getApplicationContext() )) {
                        login( profile );
                        Progress.showProgress( ActivityLogin.this );
                    } else {
                        CustomDialog.showInvalidPopUp( this, CONSTANTS.ERROR, "Check Internet connection" );
                    }
                }
            } else {
                Profile profile = new Profile();
                profile.setEmailID( email );
                profile.setPassword( "" );
                if (CommonUtils.isNetworkConnected( getApplicationContext() )) {
                    otp( profile );
                    Progress.showProgress( getApplicationContext() );
                } else {
                    CustomDialog.showInvalidPopUp( this, CONSTANTS.ERROR, "Check Internet connection" );
                }
            }

        } else if (layout_email.getVisibility() == View.VISIBLE && layout_password.getVisibility() != View.VISIBLE) {
            if (!CommonUtils.isEmailValid( email ) || email.isEmpty()) {
                CustomDialog.showInvalidPopUp( ActivityLogin.this, er_email_header, er_email_message );
                et_mail.requestFocus();
            } else {
                Profile profile = new Profile();
                profile.setEmailID( email );
                otp( profile );
                Progress.showProgress( ActivityLogin.this );
            }
        }
    }

    private void login(HashMap<String, String> profile) {
        apiInterface = APIClient.getClient().create( APIInterface.class );
        Call<User> call = apiInterface.login( profile );
        CallUtils.enqueueWithRetry( call, 2, new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    if (response.body().getStatusCode() == 200) {

                        profileDetails.addAll( response.body().getData() );

                        loginPresenter.startLogin( profileDetails.get( 0 ).getEmailID(), profileDetails.get( 0 ).getUserID(), profileDetails.get( 0 ).getFirstName()
                                        + " " + profileDetails.get( 0 ).getLastName(), profileDetails.get( 0 ).getDesignation(),
                                profileDetails.get( 0 ).getImage(), profileDetails.get( 0 ).getOrganization(), profileDetails.get( 0 ).getMobile(), profileDetails.get( 0 ).getPassword() );
                        LocalStorage.saveImagePath( response.body().getImagePath(), ActivityLogin.this );
                    } else {
                        CustomDialog.showInvalidPopUp( ActivityLogin.this, CONSTANTS.ERROR, response.body().getMessage() );
                        et_mail.getText().clear();
                        et_password.getText().clear();
                        et_mail.requestFocus();
                    }
                } else {
                    CustomDialog.invalidPopUp( ActivityLogin.this, CONSTANTS.ERROR, response.message() );
                }
                Progress.closeProgress();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                CustomDialog.invalidPopUp( ActivityLogin.this, CONSTANTS.ERROR, CONSTANTS.CONNECTIONERROR );
                Progress.closeProgress();
            }
        } );
    }

    private void otp(Profile profile) {
        apiInterface = APIClient.getClient().create( APIInterface.class );
        Call<User> call = apiInterface.getOtp( profile );
        CallUtils.enqueueWithRetry( call, 2, new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    if (response.body().getStatusCode() == 200) {

                        profileDetails.addAll( response.body().getData() );
                        LocalStorage.saveImagePath( response.body().getImagePath(), ActivityLogin.this );
                        sendOTPDialog( profileDetails.get( 0 ), CONSTANTS.SUCCESS, "OTP send to your mail id" );
//                        CustomDialog.showValidPopUp( ActivityLogin.this, CONSTANTS.SUCCESS, "OTP send to your mail id");
                    } else {
                        CustomDialog.showInvalidPopUp( ActivityLogin.this, CONSTANTS.ERROR, response.body().getMessage() );
                        et_mail.getText().clear();
                        et_password.getText().clear();
                        et_mail.requestFocus();
                    }
                } else {
                    CustomDialog.invalidPopUp( ActivityLogin.this, CONSTANTS.ERROR, response.message() );
                }
                Progress.closeProgress();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                CustomDialog.invalidPopUp( ActivityLogin.this, CONSTANTS.ERROR, CONSTANTS.CONNECTIONERROR );
                Progress.closeProgress();
            }
        } );
    }

    private void sendOTPDialog(Profile profile, String title, String body) {
        final Dialog dialog = new Dialog( ActivityLogin.this );
        dialog.requestWindowFeature( Window.FEATURE_NO_TITLE );
        dialog.setContentView( R.layout.dialog_correct_credentials );
        TextView titleTv = dialog.findViewById( R.id.titleTv );
        titleTv.setText( title );
        TextView bodyTv = dialog.findViewById( R.id.bodyTv );
        bodyTv.setText( body );
        TextView yesTv = dialog.findViewById( R.id.yes );
        yesTv.setText( "Ok" );
        yesTv.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                loginPresenter.startLogin( profile.getEmailID(), profile.getUserID(), profile.getFirstName()
                                .concat( " ".concat( profile.getLastName() ) ), profile.getDesignation(),
                        profile.getImage(), profile.getOrganization(), profile.getMobile(), profile.getPassword() );

            }
        } );


        dialog.getWindow().setLayout( WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT );
        dialog.show();
    }
}
