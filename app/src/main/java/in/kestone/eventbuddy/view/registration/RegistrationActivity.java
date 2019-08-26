package in.kestone.eventbuddy.view.registration;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import in.kestone.eventbuddy.Altdialog.Progress;
import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.common.CONSTANTS;
import in.kestone.eventbuddy.common.CommonUtils;
import in.kestone.eventbuddy.common.ImagePickerActivity;
import in.kestone.eventbuddy.common.LocalStorage;
import in.kestone.eventbuddy.data.DataManager;
import in.kestone.eventbuddy.http.APIClient;
import in.kestone.eventbuddy.http.APIInterface;
import in.kestone.eventbuddy.http.CallUtils;
import in.kestone.eventbuddy.model.activity_stream_model.PostImageResponse;
import in.kestone.eventbuddy.model.user_model.Profile;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static in.kestone.eventbuddy.Altdialog.CustomDialog.*;
import static in.kestone.eventbuddy.common.CONSTANTS.ERROR;

public class RegistrationActivity extends AppCompatActivity {

    public static final int REQUEST_IMAGE = 100;
    @BindView(R.id.profileIv)
    CircleImageView profileIv;
    @BindView(R.id.emailEt)
    EditText emailTv;
    //    @BindView(R.id.uniqueIdTv)
//    TextView uniqueIdTv;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.nameTv)
    EditText nameTv;
    @BindView(R.id.mobileTv)
    EditText mobileTv;
    @BindView(R.id.organizationTv)
    EditText organizationTv;
    @BindView(R.id.designationTv)
    EditText designationTv;
    @BindView(R.id.passwordET)
    EditText password;
    @BindView(R.id.cnfPasswordET)
    EditText cnfPassword;
    @BindView(R.id.checkbox)
    CheckBox checkbox;
    @BindView(R.id.btnSignUp)
    Button btnSignUp;
    @BindView(R.id.mPassIv)
    ImageView mPassIv;
    @BindView( R.id.image_background )
    ImageView imageBackgound;
    APIInterface apiInterface;
    File profileImagePath;
    DataManager dataManager;
    SpannableString text;
    private String str = "", tandcURL, privacyPolicyURL, filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );

        //remove title
        requestWindowFeature( Window.FEATURE_NO_TITLE );
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN );

        setContentView( R.layout.activity_registration );
        ButterKnife.bind( RegistrationActivity.this );

        if (LocalStorage.getEventID( RegistrationActivity.this ) !=0) {
            Picasso.get()
                    .load( CONSTANTS.betaimagepath.concat( LocalStorage.getBackground( this ) ) )
                    .into( imageBackgound );
        }
        apiInterface = APIClient.getClient().create( APIInterface.class );

        mPassIv.setOnClickListener( v -> {
//                browsePhoto();
                setProfileImage();

        } );
        agreementURL();
        // this is the text we'll be operating on
        text = new SpannableString( "I agree to Terms & Conditions and Privacy Policy" );

        text.setSpan( new ForegroundColorSpan( Color.RED ), 11, 29, 17 );
        text.setSpan( new ForegroundColorSpan( Color.RED ), 34, text.length(), 18 );

        btnSignUp.setOnClickListener( v ->{
                if (!nameTv.getText().toString().isEmpty() && !emailTv.getText().toString().isEmpty() &&
                        !mobileTv.getText().toString().isEmpty() && !organizationTv.getText().toString().isEmpty() &&
                        !designationTv.getText().toString().isEmpty() && !password.getText().toString().isEmpty() &&
                        !cnfPassword.getText().toString().isEmpty()) {
                    if (!CommonUtils.isEmailValid( emailTv.getText().toString().trim() )) {
                        showInvalidPopUp( RegistrationActivity.this, ERROR,
                                "Enter valid email id" );
                    } else if (mobileTv.getText().length() < 10) {
                        showInvalidPopUp( RegistrationActivity.this, ERROR,
                                "Enter valid mobile no" );
                    } else if (!password.getText().toString().equals( cnfPassword.getText().toString() )) {
                        showInvalidPopUp( RegistrationActivity.this, ERROR,
                                "Password not matched" );
                    } else if (!checkbox.isChecked()) {
                        showInvalidPopUp( RegistrationActivity.this, ERROR,
                                "Please accept agreement" );
                    } else {
                        if(filePath==null || filePath.isEmpty() ) {
                            Profile profile = new Profile();
                            profile.setEventID((long) LocalStorage.getEventID(RegistrationActivity.this));
                            profile.setFirstName(nameTv.getText().toString().trim());
                            profile.setLastName("");
                            profile.setEmailID(emailTv.getText().toString().trim());
                            profile.setMobile(mobileTv.getText().toString().trim());
                            profile.setDesignation(designationTv.getText().toString().trim());
                            profile.setOrganization(organizationTv.getText().toString().trim());
                            profile.setPassword(cnfPassword.getText().toString().trim());
                            profile.setImage("");
                            profile.setCityID("999");
                            userRegistration(profile);
                        }else {

                            profileImageUpload(profileImagePath);
                        }
                        Progress.showProgress( RegistrationActivity.this );
                    }

                } else {
                    showInvalidPopUp( RegistrationActivity.this, ERROR, "Please fill all details" );
                }
        } );
    }

    private void setProfileImage() {
        Dexter.withActivity( this )
                .withPermissions( Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE )
                .withListener( new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            showImagePickerOptions();
                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                } ).check();
    }

    private void showImagePickerOptions() {
        ImagePickerActivity.showImagePickerOptions( this, new ImagePickerActivity.PickerOptionListener() {
            @Override
            public void onTakeCameraSelected() {
                launchCameraIntent();
            }

            @Override
            public void onChooseGallerySelected() {
                launchGalleryIntent();
            }
        } );
    }

    private void launchCameraIntent() {
        Intent intent = new Intent( RegistrationActivity.this, ImagePickerActivity.class );
        intent.putExtra( ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE );

        // setting aspect ratio
        intent.putExtra( ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true );
        intent.putExtra( ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1 ); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra( ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1 );

        // setting maximum bitmap width and height
        intent.putExtra( ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true );
        intent.putExtra( ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000 );
        intent.putExtra( ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000 );

        startActivityForResult( intent, REQUEST_IMAGE );
    }

    private void launchGalleryIntent() {
        Intent intent = new Intent( RegistrationActivity.this, ImagePickerActivity.class );
        intent.putExtra( ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE );

        // setting aspect ratio
        intent.putExtra( ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true );
        intent.putExtra( ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1 ); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra( ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1 );
        startActivityForResult( intent, REQUEST_IMAGE );
    }

    /**
     * Showing Alert Dialog with Settings option
     * Navigates user to app settings
     * NOTE: Keep proper title and message depending on your app
     */
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder( RegistrationActivity.this );
        builder.setTitle( getString( R.string.dialog_permission_title ) );
        builder.setMessage( getString( R.string.dialog_permission_message ) );
        builder.setPositiveButton( getString( R.string.go_to_settings ), (dialog, which) -> {
            dialog.cancel();
            openSettings();
        } );
        builder.setNegativeButton( getString( android.R.string.cancel ), (dialog, which) -> dialog.cancel() );
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent( Settings.ACTION_APPLICATION_DETAILS_SETTINGS );
        Uri uri = Uri.fromParts( "package", getPackageName(), null );
        intent.setData( uri );
        startActivityForResult( intent, 101 );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getParcelableExtra( "path" );
                try {
                    // You can update this bitmap to your server
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap( this.getContentResolver(), uri );

                    // loading profile image from local cache
                    loadProfile( uri.getPath() );
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void loadProfile(String url) {
        Log.d( "Path", "Image cache path: " + url );
        profileImagePath = new File( url );
        Glide.with( this ).load( url )
                .into( profileIv );
        profileIv.setColorFilter( ContextCompat.getColor( this, android.R.color.transparent ) );
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

    private void userRegistration(Profile profile) {
        Call<JsonObject> call = apiInterface.registration( profile );
        CallUtils.enqueueWithRetry( call, 2, new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Progress.closeProgress();
                if (response.isSuccessful()) {
                    JsonObject jsonObject = response.body();
                    if (Long.parseLong( String.valueOf( jsonObject.get( "StatusCode" ) ) ) == 200) {
                        showValidPopUp( RegistrationActivity.this, CONSTANTS.SUCCESS,
                                jsonObject.get( "Message" ).toString().replace( "\"", "" ) );
                    } else {
                        showInvalidPopUp( RegistrationActivity.this, ERROR,
                                jsonObject.get( "Message" ).toString().replace( "\"", "" ) );
                    }
                } else {
                    showInvalidPopUp( RegistrationActivity.this, ERROR, response.message() );
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                showInvalidPopUp( RegistrationActivity.this, ERROR, t.getMessage() );
                Progress.closeProgress();
            }

        } );
    }

    private void agreementURL() {
        Call<JsonObject> call = apiInterface.termsAndCondition( LocalStorage.getEventID( RegistrationActivity.this ));
        CallUtils.enqueueWithRetry( call, 2, new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.code() == 200) {
                    JsonObject jsonObject = response.body();
                    if (Long.parseLong( String.valueOf( jsonObject.get( "StatusCode" ) ) ) == 200) {
                        if (jsonObject.get( "Data" ).getAsJsonArray().size() > 0) {
                            tandcURL = jsonObject.get( "Data" ).getAsJsonArray().get( 0 ).getAsJsonObject()
                                    .get( "Webpage_Link" ).toString().replace( "\"", "" );
                            privacyPolicyURL = tandcURL;
                            ClickableSpan clickableSpan = new ClickableSpan() {
                                @Override
                                public void onClick(View view) {
                                    Toast.makeText( RegistrationActivity.this, "dolor", Toast.LENGTH_LONG ).show();
                                }
                            };

                            text.setSpan( new URLSpan( tandcURL ), 11, 29, 17 );
                            text.setSpan( new URLSpan( privacyPolicyURL ), 34, text.length(), 18 );

                            // make our ClickableSpans and URLSpans work
                            tv.setMovementMethod( LinkMovementMethod.getInstance() );

                            // shove our styled text into the TextView
                            tv.setText( text, TextView.BufferType.SPANNABLE );
                        }
                    } else {
                        showInvalidPopUp( RegistrationActivity.this, ERROR,
                                jsonObject.get( "Message" ).toString() );
                    }
                } else {
                    showInvalidPopUp( RegistrationActivity.this, ERROR, response.message() );
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                showInvalidPopUp( RegistrationActivity.this, ERROR, t.getMessage() );
                Progress.closeProgress();
            }
        } );
    }

    private void showValidPopUp(final Context context, String title, String body) {
        final Dialog dialog = new Dialog( context );
        dialog.requestWindowFeature( Window.FEATURE_NO_TITLE );
        dialog.setContentView( R.layout.dialog_correct_credentials );
        TextView titleTv = dialog.findViewById( R.id.titleTv );
        titleTv.setText( title );
        TextView bodyTv = dialog.findViewById( R.id.bodyTv );
        bodyTv.setText( body );
        TextView yesTv = dialog.findViewById( R.id.yes );
        yesTv.setText( "Ok" );
        yesTv.setOnClickListener( view -> {
                dialog.dismiss();
                finish();
        } );


        dialog.getWindow().setLayout( WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT );
        dialog.show();
    }

    private void profileImageUpload(File file) {
        // create multipart
        RequestBody requestFile = RequestBody.create( MediaType.parse( "text/plain" ), file );
        MultipartBody.Part body = MultipartBody.Part.createFormData( "image", file.getName(), requestFile );

        Call<PostImageResponse> call = apiInterface.postProfileImage( body );
        call.enqueue( new Callback<PostImageResponse>() {
            @Override
            public void onResponse(Call<PostImageResponse> call, Response<PostImageResponse> response) {

                if (response.code() == 200) {
                    if (response.body() != null) {
                        filePath = response.body().getData();
                        Profile profile = new Profile();
                        profile.setEventID( (long) LocalStorage.getEventID( RegistrationActivity.this ) );
                        profile.setFirstName( nameTv.getText().toString().trim() );
                        profile.setLastName( "" );
                        profile.setEmailID( emailTv.getText().toString().trim() );
                        profile.setMobile( mobileTv.getText().toString().trim() );
                        profile.setDesignation( designationTv.getText().toString().trim() );
                        profile.setOrganization( organizationTv.getText().toString().trim() );
                        profile.setPassword( cnfPassword.getText().toString().trim() );
                        profile.setImage(filePath );
                        profile.setCityID( "999" );
                        userRegistration( profile );
                    }
                }
            }

            @Override
            public void onFailure(Call<PostImageResponse> call, Throwable t) {
                showInvalidPopUp( RegistrationActivity.this, ERROR, CONSTANTS.CONNECTIONERROR );
                Progress.closeProgress();
            }
        } );

    }
}
