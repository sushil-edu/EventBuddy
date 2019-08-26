package in.kestone.eventbuddy.view.stream;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.kestone.eventbuddy.Altdialog.CustomDialog;
import in.kestone.eventbuddy.Altdialog.Progress;
import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.common.CONSTANTS;
import in.kestone.eventbuddy.common.CommonUtils;
import in.kestone.eventbuddy.common.ImagePickerActivity;
import in.kestone.eventbuddy.common.LocalStorage;
import in.kestone.eventbuddy.data.SharedPrefsHelper;
import in.kestone.eventbuddy.http.APIClient;
import in.kestone.eventbuddy.http.APIInterface;
import in.kestone.eventbuddy.model.activity_stream_model.PostImageResponse;
import in.kestone.eventbuddy.model.activity_stream_model.Stream;
import in.kestone.eventbuddy.model.activity_stream_model.StreamDatum;
import in.kestone.eventbuddy.widgets.CustomEditText;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class AddActivity extends AppCompatActivity {

    private static final int SELECT_PHOTO = 0x01;
    private static final int SELECT_QR_CODE = 102;
    private static final int REQUEST_IMAGE_CAPTURE = 101;
    private static final String TAG = AddActivity.class.getSimpleName() ;
    private static final int REQUEST_IMAGE = 100;
    public static String status;
    @BindView(R.id.cameraImage)
    ImageView addImageView;
    @BindView(R.id.checkinIcon)
    ImageView checkinIcon;
    private TextView checkInIv, mTitleTv;
    private CustomEditText txtFeedback;
    private String str, uploadedImagePath="testurl";
    APIInterface apiInterface;
    Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_add );
        ButterKnife.bind( this );
        status = "";

        Toolbar toolbar = findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        mTitleTv = toolbar.findViewById( R.id.mTitleTv );
        mTitleTv.setText( getIntent().getStringExtra( "title" ) );
        apiInterface = APIClient.getClient().create( APIInterface.class );
        toolbar.findViewById( R.id.subTitleTv ).setVisibility( View.GONE );

        final CardView textCard = (CardView) findViewById( R.id.textCard );
        final RelativeLayout imageCard = (RelativeLayout) findViewById( R.id.imageCard );
        checkInIv = (TextView) findViewById( R.id.checkInIv );
        txtFeedback = (CustomEditText) findViewById( R.id.txtFeedback );
        checkinIcon = (ImageView) findViewById( R.id.checkinIcon );

        // Clearing older images from cache directory
        // don't call this line if you want to choose multiple images in the same activity
        // call this once the bitmap(s) usage is over
        ImagePickerActivity.clearCache(this);


        addImageView = (ImageView) findViewById( R.id.cameraImage );
        addImageView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                browseImage();
            }
        } );



        findViewById( R.id.btnSubmit ).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss aa" );

                String insertedDate = String.valueOf(dateFormat.format(  Calendar.getInstance().getTime()));

                if (txtFeedback.getText().length() > 0) {
                    StreamDatum streamDatum = new StreamDatum();
                    streamDatum.setComment( txtFeedback.getText().toString() );
                    streamDatum.setEventID( (long) LocalStorage.getEventID( AddActivity.this ) );
                    streamDatum.setImageURL( uploadedImagePath );
                    streamDatum.setInsertedOn( insertedDate );
                    streamDatum.setUserID( (long) new SharedPrefsHelper( AddActivity.this ).getUserId() );
                    if (CommonUtils.isNetworkConnected( AddActivity.this )) {
                        postStream( streamDatum );
                        Progress.showProgress( AddActivity.this );
                    } else {
                        CustomDialog.showInvalidPopUp( AddActivity.this, CONSTANTS.ERROR, "Check your internet connection" );
                    }
                }else {
                    CustomDialog.showInvalidPopUp( AddActivity.this, CONSTANTS.ERROR, "Write your comment." );
                }
            }
        } );

    }

    private void loadProfile(String url) {
        Log.d(TAG, "Image cache path: " + url);

        postImage( new File( url) );
        Glide.with(this).load(url)
                .into(addImageView);
        addImageView.setColorFilter( ContextCompat.getColor(this, android.R.color.transparent));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected( item );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getParcelableExtra("path");
                try {
                    // You can update this bitmap to your server
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);

                    // loading profile image from local cache
                    loadProfile(uri.getPath());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }




    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        status = "No";
    }

    @Override
    public void finish() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra( "Status", status );
        setResult( RESULT_OK, returnIntent );
        super.finish();
    }

    public void postStream(StreamDatum streamData) {

        retrofit2.Call<Stream> call = apiInterface.postStream( LocalStorage.getEventID(AddActivity.this ), streamData );
        call.enqueue( new retrofit2.Callback<Stream>() {
            @Override
            public void onResponse(retrofit2.Call<Stream> call, retrofit2.Response<Stream> response) {
                if (response.code() == 200) {
                    if (response.body().getStatusCode() == 200 && response.body().getStreamData().size() == 0) {
                        CustomDialog.showValidPopUp( AddActivity.this, "", response.body().getMessage() );
                        status="yes";
                        finish();
                    } else {
                        CustomDialog.showInvalidPopUp( AddActivity.this, CONSTANTS.ERROR, response.body().getMessage() );
                    }
                } else {
                    CustomDialog.showInvalidPopUp( AddActivity.this, CONSTANTS.ERROR, response.message() );
                }
                Progress.closeProgress();

            }

            @Override
            public void onFailure(retrofit2.Call<Stream> call, Throwable t) {
                CustomDialog.showInvalidPopUp( AddActivity.this, CONSTANTS.ERROR, CONSTANTS.CONNECTIONERROR );
                Progress.closeProgress();
            }

        } );
    }

    public void postImage(File file){
        // create multipart
        RequestBody requestFile = RequestBody.create(MediaType.parse("text/plain"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", file.getName(), requestFile);

        Call<PostImageResponse> call = apiInterface.postImageStream( body );
        call.enqueue( new Callback<PostImageResponse>() {
            @Override
            public void onResponse(Call<PostImageResponse> call, retrofit2.Response<PostImageResponse> response) {

                if (response.code()==200) {
                    Log.e( "Response ", response.body().getData() );
                    uploadedImagePath= response.body().getData();
                }
                Progress.closeProgress();
            }

            @Override
            public void onFailure(Call<PostImageResponse> call, Throwable t) {
                CustomDialog.showInvalidPopUp(AddActivity.this, CONSTANTS.ERROR, CONSTANTS.CONNECTIONERROR );
                Progress.closeProgress();
            }
        } );


    }
    private void browseImage(){
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
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
                }).check();
    }
    private void showImagePickerOptions() {
        ImagePickerActivity.showImagePickerOptions(this, new ImagePickerActivity.PickerOptionListener() {
            @Override
            public void onTakeCameraSelected() {
                launchCameraIntent();
            }

            @Override
            public void onChooseGallerySelected() {
                launchGalleryIntent();
            }
        });
    }
    private void launchCameraIntent() {
        Intent intent = new Intent(AddActivity.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);

        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);

        startActivityForResult(intent, REQUEST_IMAGE);
    }

    private void launchGalleryIntent() {
        Intent intent = new Intent(AddActivity.this, ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
        startActivityForResult(intent, REQUEST_IMAGE);
    }
    /**
     * Showing Alert Dialog with Settings option
     * Navigates user to app settings
     * NOTE: Keep proper title and message depending on your app
     */
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AddActivity.this);
        builder.setTitle(getString(R.string.dialog_permission_title));
        builder.setMessage(getString(R.string.dialog_permission_message));
        builder.setPositiveButton(getString(R.string.go_to_settings), (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });
        builder.setNegativeButton(getString(android.R.string.cancel), (dialog, which) -> dialog.cancel());
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent( Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

}
