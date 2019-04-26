package in.kestone.eventbuddy.view.stream;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.master.permissionhelper.PermissionHelper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.kestone.eventbuddy.Altdialog.CustomDialog;
import in.kestone.eventbuddy.Altdialog.Progress;
import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.common.CONSTANTS;
import in.kestone.eventbuddy.common.CommonUtils;
import in.kestone.eventbuddy.common.ImageFilePath;
import in.kestone.eventbuddy.data.SharedPrefsHelper;
import in.kestone.eventbuddy.http.APIClient;
import in.kestone.eventbuddy.http.APIInterface;
import in.kestone.eventbuddy.model.activity_stream_model.PostImageResponse;
import in.kestone.eventbuddy.model.activity_stream_model.Stream;
import in.kestone.eventbuddy.model.activity_stream_model.StreamDatum;
import in.kestone.eventbuddy.widgets.CustomEditText;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;

public class AddActivity extends AppCompatActivity {

    private static final int SELECT_PHOTO = 0x01;
    private static final int SELECT_QR_CODE = 102;
    private static final int REQUEST_IMAGE_CAPTURE = 101;
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

//        RadioButton imgRadioButton = (RadioButton) findViewById(R.id.imgRadioBtn);
//        RadioButton txtRadioButton = (RadioButton) findViewById(R.id.textRadioBtn);
        final CardView textCard = (CardView) findViewById( R.id.textCard );
        final RelativeLayout imageCard = (RelativeLayout) findViewById( R.id.imageCard );
        checkInIv = (TextView) findViewById( R.id.checkInIv );
        txtFeedback = (CustomEditText) findViewById( R.id.txtFeedback );
        checkinIcon = (ImageView) findViewById( R.id.checkinIcon );


        addImageView = (ImageView) findViewById( R.id.cameraImage );
        addImageView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filename = Environment.getExternalStorageDirectory().getPath().concat( "/stream/stream.jpg");
                imageUri = Uri.fromFile(new File(filename));
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());

                PermissionHelper permissionHelper = new PermissionHelper( AddActivity.this, new String[]{Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100 );
                permissionHelper.request( new PermissionHelper.PermissionCallback() {
                    @Override
                    public void onPermissionGranted() {

                        AlertDialog.Builder builder = new AlertDialog.Builder( AddActivity.this );
                        builder.setMessage( "Select Source" );
//                        builder.setPositiveButton( "Gallery", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//                                Intent photoPickerIntent = new Intent(  );
//                                photoPickerIntent.setType( "image/*" );
//                                photoPickerIntent.setAction(Intent.ACTION_GET_CONTENT);
//                                startActivityForResult(Intent.createChooser(photoPickerIntent, "Select Picture"), SELECT_PHOTO);
//                            }
//                        } );

                        builder.setNegativeButton( "Camera", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Intent takePictureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                                takePictureIntent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageUri);
//                                if (takePictureIntent.resolveActivity( getPackageManager() ) != null) {
                                    startActivityForResult( takePictureIntent, REQUEST_IMAGE_CAPTURE );
//                                }

                            }
                        } );
                        builder.show();


                    }

                    @Override
                    public void onPermissionDenied() {
                        Log.d( "Permission", "onPermissionDenied() called" );
                    }

                    @Override
                    public void onPermissionDeniedBySystem() {
                        Log.d( "Permission", "onPermissionDeniedBySystem() called" );
                    }
                } );
            }
        } );

        checkInIv.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(AddActivity.this, CheckIn.class);
//                startActivityForResult(intent, SELECT_QR_CODE);

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
                    streamDatum.setEventID( CONSTANTS.EVENTID );
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected( item );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK && requestCode == SELECT_PHOTO) {
            final Uri resultUri = data.getData();
            try {

                Bitmap photo = MediaStore.Images.Media.getBitmap( this.getContentResolver(), resultUri );
                addImageView.setImageBitmap( photo );
//                addImageView.setImageURI( imageUri);
                String picturePath = ImageFilePath.getPath(AddActivity.this, data.getData());
                Log.d("Picture Path", picturePath);
                postImage( new File( picturePath) );
                Progress.showProgress( AddActivity.this );

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (resultCode == RESULT_OK && requestCode == REQUEST_IMAGE_CAPTURE) {
            addImageView.setImageURI( imageUri);
            // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
//            Uri tempUri = getImageUri(getApplicationContext(), imageUri);
            String picturePath =  imageUri.getPath() ;// getPath( AddActivity.this, imageUri );
            Log.e("Image Path ", String.valueOf( picturePath ) );
            postImage( new File( picturePath ) );
            Progress.showProgress( AddActivity.this );

        } else if (resultCode == RESULT_OK && requestCode == SELECT_QR_CODE) {
            String boothStr = data.getStringExtra( "boothStr" );

            if (boothStr.length() > 0) {
                checkInIv.setText( boothStr );
                checkinIcon.setVisibility( View.GONE );
            } else {
                checkinIcon.setVisibility( View.VISIBLE );
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

        retrofit2.Call<Stream> call = apiInterface.postStream( CONSTANTS.EVENTID, streamData );
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
    public static String getPath(Context context, Uri uri ) {
        String result = null;
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver( ).query( uri, proj, null, null, null );
        if(cursor != null){
            if ( cursor.moveToFirst( ) ) {
                int column_index = cursor.getColumnIndexOrThrow( proj[0] );
                result = cursor.getString( column_index );
            }
            cursor.close( );
        }
        if(result == null) {
            result = "Not found";
        }
        return result;
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

}
