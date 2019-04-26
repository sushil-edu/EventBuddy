package in.kestone.eventbuddy.view.login;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.JsonObject;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.kestone.eventbuddy.Altdialog.CustomDialog;
import in.kestone.eventbuddy.Altdialog.Progress;
import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.common.CONSTANTS;
import in.kestone.eventbuddy.common.CommonUtils;
import in.kestone.eventbuddy.http.APIClient;
import in.kestone.eventbuddy.http.APIInterface;
import in.kestone.eventbuddy.http.CallUtils;
import in.kestone.eventbuddy.model.app_config_model.Login;
import in.kestone.eventbuddy.model.user_model.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Forgot_Password extends AppCompatActivity {

    @BindView(R.id.tv_event_title)
    TextView title;
    @BindView(R.id.tv_forgot_password_label)
    TextView label;
    @BindView(R.id.tv_forgot_password_message)
    TextView message;
    @BindView(R.id.et_email)
    TextView email;
    @BindView(R.id.btn_submit)
    Button submit;
    @BindView(R.id.layout_reset_password)
    LinearLayout layout_reset_password;
    @BindView(R.id.et_otp)
    EditText et_otp;
    @BindView(R.id.et_password)
    EditText et_password;
    @BindView(R.id.et_confirm_password)
    EditText et_confirm_password;
    long OTP = 999999;
    APIInterface apiInterface;

    Login loginModel;
    HashMap<String, String> userSet = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        //remove title
        requestWindowFeature( Window.FEATURE_NO_TITLE );
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN );

        setContentView( R.layout.activity_forgot__password );

        ButterKnife.bind( this );

        apiInterface = APIClient.getClient().create( APIInterface.class );

        if (getIntent() != null) {
            loginModel = (Login) getIntent().getSerializableExtra( CONSTANTS.FORGOTPASSWOTD );
            title.setText( loginModel.getWelcomeText() );
            label.setText( loginModel.getForgotButton().getLabel() );
            email.setHint( loginModel.getUserName().getHint() );
            if (loginModel.getUserName().getType().equalsIgnoreCase( "email" )) {
                email.setInputType( InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS | InputType.TYPE_CLASS_TEXT );
            } else {
                email.setInputType( InputType.TYPE_CLASS_TEXT );
            }

        }
        email.setEnabled( true );
        layout_reset_password.setVisibility( View.GONE );
    }

    @OnClick(R.id.btn_submit)
    public void onClick() {
        //to send otp
        if (layout_reset_password.getVisibility() != View.VISIBLE) {
            if (CommonUtils.isEmailValid( email.getText().toString() )) {
                //call send otp API
                userSet.put( "EmailID", email.getText().toString() );
                userSet.put( "Event_ID", String.valueOf( CONSTANTS.EVENTID ) );
                sendOTP( userSet );
                Progress.showProgress( Forgot_Password.this );
            } else {
                CustomDialog.showInvalidPopUp( Forgot_Password.this, loginModel.getUserName().getErrorHeader(),
                        loginModel.getUserName().getErrorMessage() );
            }
        } else {
            //to reset password
            if (!et_otp.getText().toString().isEmpty()) {
                if (!et_password.getText().toString().isEmpty() && !et_confirm_password.getText().toString().isEmpty()) {
                    if (OTP == Long.parseLong( et_otp.getText().toString() )) {
                        if (et_password.getText().toString().equals( et_confirm_password.getText().toString() )) {
                            //call reset password API
                            userSet.put( "EmailID", email.getText().toString() );
                            userSet.put( "Event_ID", String.valueOf( CONSTANTS.EVENTID ) );
                            userSet.put( "Password", et_password.getText().toString() );
                            updatePassword( userSet );
                            Progress.showProgress( Forgot_Password.this );
                        } else {
                            CustomDialog.showInvalidPopUp( Forgot_Password.this, "", "Password not match" );
                        }
                    } else {
                        CustomDialog.showInvalidPopUp( Forgot_Password.this, "", "OTP not match" );
                    }

                } else {
                    CustomDialog.showInvalidPopUp( Forgot_Password.this, "", "Password field not be left blank" );
                }
            } else {
                CustomDialog.showInvalidPopUp( Forgot_Password.this, "", "Enter OTP" );
            }

        }
    }

    public void showValidPopUp(final Activity context, String type, String title, String body) {
        final Dialog dialog = new Dialog( context );
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
                if (type.equals( "OTP" )) {
                    layout_reset_password.setVisibility( View.VISIBLE );
                    email.setEnabled( false );
                } else if (type.equals( "RESETPASSWORD" )) {
                    onBackPressed();
                }
                dialog.dismiss();
            }
        } );


        dialog.getWindow().setLayout( WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT );
        dialog.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected( item );
    }

    private void sendOTP(HashMap<String, String> user) {
        Call<User> call = apiInterface.sendOtpToResetPassword( user );
        CallUtils.enqueueWithRetry( call, 2, new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    if (response.body().getStatusCode() == 200) {
                        OTP = Long.parseLong( response.body().getData().get( 0 ).getForgotOTP() );
                        showValidPopUp( Forgot_Password.this, "OTP", CONSTANTS.SUCCESS, "OTP send to your email." );
                    } else {
                        CustomDialog.showInvalidPopUp( Forgot_Password.this, CONSTANTS.ERROR, response.body().getMessage() );
                    }
                } else {
                    CustomDialog.invalidPopUp( Forgot_Password.this, CONSTANTS.ERROR, response.message() );
                }
                Progress.closeProgress();
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                CustomDialog.invalidPopUp( Forgot_Password.this, CONSTANTS.ERROR, CONSTANTS.CONNECTIONERROR );
                Progress.closeProgress();
            }
        } );
    }

    private void updatePassword(HashMap<String, String> user) {
        Call<JsonObject> call = apiInterface.updatePassword( user );
        CallUtils.enqueueWithRetry( call, 2, new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.code() == 200) {
                    showValidPopUp( Forgot_Password.this, "RESETPASSWORD", CONSTANTS.SUCCESS,
                            response.body().get( "Data" ).toString().replace( "\"", "" ) );
                } else {
                    CustomDialog.invalidPopUp( Forgot_Password.this, CONSTANTS.ERROR, response.message() );
                }
                Progress.closeProgress();
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                CustomDialog.invalidPopUp( Forgot_Password.this, CONSTANTS.ERROR, CONSTANTS.CONNECTIONERROR );
                Progress.closeProgress();
            }
        } );
    }

}
