package in.kestone.eventbuddy.view.profile;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

import java.io.File;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import in.kestone.eventbuddy.Altdialog.CustomDialog;
import in.kestone.eventbuddy.Altdialog.Progress;
import in.kestone.eventbuddy.MvpApp;
import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.common.CONSTANTS;
import in.kestone.eventbuddy.common.CommonUtils;
import in.kestone.eventbuddy.common.ImagePickerActivity;
import in.kestone.eventbuddy.common.LocalStorage;
import in.kestone.eventbuddy.data.DataManager;
import in.kestone.eventbuddy.data.SharedPrefsHelper;
import in.kestone.eventbuddy.http.APIClient;
import in.kestone.eventbuddy.http.APIInterface;
import in.kestone.eventbuddy.http.CallUtils;
import in.kestone.eventbuddy.model.activity_stream_model.PostImageResponse;
import in.kestone.eventbuddy.model.user_model.Profile;
import in.kestone.eventbuddy.view.base.MvpView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

public class ProfileActivity extends AppCompatActivity implements MvpView {
    public static final int REQUEST_IMAGE = 100;
    @BindView(R.id.profileIv)
    CircleImageView profileIv;
    @BindView(R.id.emailEt)
    TextView emailTv;
    @BindView(R.id.uniqueIdTv)
    TextView uniqueIdTv;
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
    @BindView(R.id.checkbox)
    CheckBox checkbox;
    @BindView(R.id.updateBtn)
    Button updateBtn;
    @BindView(R.id.mPassIv)
    ImageView mPassIv;
    APIInterface apiInterface;
    File profileImagePath;
    DataManager dataManager;
    SpannableString text;
    String filePath = "", tandcURL, privacyPolicyURL;

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        Window window = getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags( WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS );

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags( WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS );

        // finally change the color
        window.setStatusBarColor( ContextCompat.getColor( this, R.color.colorPrimaryDark ) );

        setContentView( R.layout.activity_profile );
        ButterKnife.bind( this );

        Toolbar toolbar = findViewById( R.id.toolbar );
        TextView toolbarTitle = toolbar.findViewById( R.id.mTitleTv );
        TextView subTitleTv = toolbar.findViewById( R.id.subTitleTv );
        setSupportActionBar( toolbar );
        Objects.requireNonNull( getSupportActionBar() ).setDisplayHomeAsUpEnabled( true );
        toolbarTitle.setText( "Update Profile" );
        subTitleTv.setVisibility( View.GONE );
        apiInterface = APIClient.getClient().create( APIInterface.class );

        mPassIv.setOnClickListener( v -> {
//                browsePhoto();
            setProfileImage();
        } );
        // this is the text we'll be operating on
        text = new SpannableString( "I agree to Terms & Conditions and Privacy Policy" );

        text.setSpan( new ForegroundColorSpan( Color.RED ), 11, 29, 17 );
        text.setSpan( new ForegroundColorSpan( Color.RED ), 34, text.length(), 18 );

        final Context context = this;
        new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Toast.makeText( context, "dolor", Toast.LENGTH_LONG ).show();
            }
        };

        agreementURL();

        //set user details
        dataManager = ((MvpApp) getApplication()).getDataManager();

        nameTv.setText( dataManager.getName() );
        emailTv.setText( dataManager.getEmailId() );
        mobileTv.setText( dataManager.getMobile() );
        organizationTv.setText( dataManager.getOrganization() );
        designationTv.setText( dataManager.getDesignation() );

        loadProfileDefault();

        // Clearing older images from cache directory
        // don't call this line if you want to choose multiple images in the same activity
        // call this once the bitmap(s) usage is over
        ImagePickerActivity.clearCache( this );

        profileIv.setOnClickListener( v -> {
//                browsePhoto();
            setProfileImage();
        } );

        updateBtn.setOnClickListener( v -> {
            if (checkbox.isChecked()) {
                Progress.showProgress( ProfileActivity.this );

                Profile profile = new Profile();
                profile.setFirstName( nameTv.getText().toString() );
                profile.setLastName( "" );
                profile.setEmailID( emailTv.getText().toString() );
                profile.setMobile( mobileTv.getText().toString() );
                profile.setOrganization( organizationTv.getText().toString() );
                profile.setDesignation( designationTv.getText().toString() );
                profile.setPassword( dataManager.getPassword() );
                profile.setEventID( Long.valueOf( LocalStorage.getEventID( ProfileActivity.this ) ) );
                profile.setCityID( "1" );
                profile.setUserID( new SharedPrefsHelper( ProfileActivity.this ).getUserId() );
                updateProfile( profile );


            } else {
                Toast.makeText( context, "Please check the term and condition and privacy policy"
                        , Toast.LENGTH_SHORT ).show();
            }
        } );

        // sequence example
        ShowcaseConfig conf = new ShowcaseConfig();
        conf.setDelay( 500 ); // half second between each showcase view

        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence( this, "123" );

        sequence.setConfig( conf );

        sequence.addSequenceItem( updateBtn, "Update your profile", "GOT IT" );

//	    sequence.addSequenceItem(mButtonTwo,
//            "This is button two", "GOT IT");
//
//	    sequence.addSequenceItem(mButtonThree,
//            "This is button three", "GOT IT");

        sequence.start();

    }

    private void loadProfileDefault() {
        if (CommonUtils.isValidUrl( dataManager.getImage() )) {

            Glide.with( this ).load( dataManager.getImage() )
                    .placeholder( R.drawable.default_user_grey )
                    .into( profileIv );
        } else {
            Glide.with( this ).load( LocalStorage.getImagePath( ProfileActivity.this )
                    .concat( dataManager.getImage() ) )
                    .placeholder( R.drawable.default_user_grey )
                    .into( profileIv );
        }
    }

    private void loadProfile(String url) {
        Log.d( "Path", "Image cache path: " + url );
        profileImagePath = new File( url );
        Glide.with( this ).load( url )
                .into( profileIv );
        profileIv.setColorFilter( ContextCompat.getColor( this, android.R.color.transparent ) );
        profileImageUpload( profileImagePath );
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected( item );
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getParcelableExtra( "path" );
                // loading profile image from local cache
                loadProfile( uri.getPath() );

            }
        }
    }

    public void updateProfile(Profile profileData) {
        Call<Profile> call = apiInterface.
                updateProfile( new SharedPrefsHelper( ProfileActivity.this ).getUserId(), profileData );
        CallUtils.enqueueWithRetry( call, 1, new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {

            }

            @Override
            public void onFailure(Call<Profile> call, Throwable t) {
                CustomDialog.showInvalidPopUp( ProfileActivity.this, CONSTANTS.ERROR, CONSTANTS.CONNECTIONERROR );
            }
        } );
    }

    public void profileImageUpload(File file) {
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
                    }
                }
            }

            @Override
            public void onFailure(Call<PostImageResponse> call, Throwable t) {
                CustomDialog.showInvalidPopUp( ProfileActivity.this, CONSTANTS.ERROR, CONSTANTS.CONNECTIONERROR );
                Progress.closeProgress();
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
        Intent intent = new Intent( ProfileActivity.this, ImagePickerActivity.class );
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
        Intent intent = new Intent( ProfileActivity.this, ImagePickerActivity.class );
        intent.putExtra( ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE );

        // setting aspect ratio
        intent.putExtra( ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true );
        intent.putExtra( ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1 ); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra( ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1 );
        startActivityForResult( intent, REQUEST_IMAGE );
    }

    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder( ProfileActivity.this );
        builder.setTitle( getString( R.string.dialog_permission_title ) );
        builder.setMessage( getString( R.string.dialog_permission_message ) );
        builder.setPositiveButton( getString( R.string.go_to_settings ), (dialog, which) -> {
            dialog.cancel();
            openSettings();
        } );
        builder.setNegativeButton( getString( android.R.string.cancel ), (dialog, which) -> dialog.cancel() );
        builder.show();

    }

    private void openSettings() {
        Intent intent = new Intent( Settings.ACTION_APPLICATION_DETAILS_SETTINGS );
        Uri uri = Uri.fromParts( "package", getPackageName(), null );
        intent.setData( uri );
        startActivityForResult( intent, 101 );
    }

    private void agreementURL() {
        Call<JsonObject> call = apiInterface.termsAndCondition( LocalStorage.getEventID( ProfileActivity.this ) );
        CallUtils.enqueueWithRetry( call, 2, new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                if (response.code() == 200) {
                    JsonObject jsonObject = response.body();
                    if (Long.parseLong( String.valueOf( Objects.requireNonNull( jsonObject ).get( "StatusCode" ) ) ) == 200) {
                        if (jsonObject.get( "Data" ).getAsJsonArray().size() > 0) {
                            tandcURL = jsonObject.get( "Data" ).getAsJsonArray().get( 0 ).getAsJsonObject()
                                    .get( "Webpage_Link" ).toString().replace( "\"", "" );
                            privacyPolicyURL = tandcURL;
                            new ClickableSpan() {
                                @Override
                                public void onClick(@NonNull View view) {
                                    Toast.makeText( ProfileActivity.this, "dolor", Toast.LENGTH_LONG ).show();
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
                        CustomDialog.showInvalidPopUp( ProfileActivity.this, CONSTANTS.ERROR,
                                jsonObject.get( "Message" ).toString() );
                    }
                } else {
                    CustomDialog.showInvalidPopUp( ProfileActivity.this, CONSTANTS.ERROR, response.message() );
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
                CustomDialog.showInvalidPopUp( ProfileActivity.this, CONSTANTS.ERROR, t.getMessage() );
                Progress.closeProgress();
            }
        } );
    }

}
