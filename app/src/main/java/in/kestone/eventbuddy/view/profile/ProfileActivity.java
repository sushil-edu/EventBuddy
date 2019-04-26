package in.kestone.eventbuddy.view.profile;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.URLSpan;
import android.util.Base64;
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

import com.master.permissionhelper.PermissionHelper;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import in.kestone.eventbuddy.Altdialog.CustomDialog;
import in.kestone.eventbuddy.Altdialog.Progress;
import in.kestone.eventbuddy.MvpApp;
import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.common.CONSTANTS;
import in.kestone.eventbuddy.common.CommonUtils;
import in.kestone.eventbuddy.common.ImageFilePath;
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
    private static final int SELECT_PHOTO = 0x01;
    private static final int REQUEST_IMAGE_CAPTURE = 101;
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
    ActionBar mActionBar;
    APIInterface apiInterface;
    in.kestone.eventbuddy.model.user_model.Profile profileModel;
    private String str = "", uploadedImagePath;
    File profileImagePath;
    Uri imageUri;

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
        window.setStatusBarColor( ContextCompat.getColor( this, R.color.actionbar_color ) );

        setContentView( R.layout.activity_profile );
        ButterKnife.bind( this );

        Toolbar toolbar = findViewById( R.id.toolbar );
        TextView toolbarTitle = toolbar.findViewById( R.id.mTitleTv );
        TextView subTitleTv = toolbar.findViewById( R.id.subTitleTv );
        setSupportActionBar( toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        toolbarTitle.setText( "Update Profile" );
        subTitleTv.setVisibility( View.GONE );
        apiInterface = APIClient.getClient().create( APIInterface.class );

        mPassIv.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        } );
        // this is the text we'll be operating on
        SpannableString text = new SpannableString( "I agree to Terms & Conditions and Privacy Policy" );

        text.setSpan( new ForegroundColorSpan( Color.RED ), 11, 29, 17 );
        text.setSpan( new ForegroundColorSpan( Color.RED ), 34, text.length(), 18 );

        final Context context = this;
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                Toast.makeText( context, "dolor", Toast.LENGTH_LONG ).show();
            }
        };

        text.setSpan( new URLSpan( "http://marketing.kestoneapps.com/Events/DellPartnerSummite2017/PP/index.html" ), 11, 29, 17 );
        text.setSpan( new URLSpan( "http://india.emc.com/legal/emc-corporation-privacy-statement.htm" ), 34, text.length(), 18 );

        // make our ClickableSpans and URLSpans work
        tv.setMovementMethod( LinkMovementMethod.getInstance() );

        // shove our styled text into the TextView
        tv.setText( text, TextView.BufferType.SPANNABLE );

        //set user details
        final DataManager dataManager = ((MvpApp) getApplication()).getDataManager();

        nameTv.setText( dataManager.getName() );
        emailTv.setText( dataManager.getEmailId() );
        mobileTv.setText( dataManager.getMobile() );
        organizationTv.setText( dataManager.getOrganization() );
        designationTv.setText( dataManager.getDesignation() );

        if (CommonUtils.isValidUrl( dataManager.getImagePath())) {
            Picasso.with( this ).load( dataManager.getImagePath() )
                    .resize( 80, 80 )
                    .placeholder( R.drawable.default_user_grey )
                    .into( profileIv );
        }else {
            Picasso.with( this ).load( LocalStorage.getImagePath( ProfileActivity.this).concat( dataManager.getImagePath() ))
                    .resize( 80, 80 )
                    .placeholder( R.drawable.default_user_grey )
                    .into( profileIv );
        }
//        profileIv.setImageBitmap( decodedByte );

        profileIv.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String filename = Environment.getExternalStorageDirectory().getPath() + "/profile/profile.jpg";
                imageUri = Uri.fromFile(new File(filename));
                StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
                StrictMode.setVmPolicy(builder.build());

                PermissionHelper permissionHelper = new PermissionHelper( ProfileActivity.this, new String[]{Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE}, 100 );
                permissionHelper.request( new PermissionHelper.PermissionCallback() {
                    @Override
                    public void onPermissionGranted() {

                        AlertDialog.Builder builder = new AlertDialog.Builder( ProfileActivity.this );
                        builder.setMessage( "Select Source" );
                        builder.setPositiveButton( "Gallery", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Intent photoPickerIntent = new Intent(  );
                                photoPickerIntent.setType( "image/*" );
                                photoPickerIntent.setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(Intent.createChooser(photoPickerIntent, "Select Picture"), SELECT_PHOTO);

                            }
                        } );

                        builder.setNegativeButton( "Camera", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Intent takePictureIntent = new Intent( MediaStore.ACTION_IMAGE_CAPTURE );
                                if (takePictureIntent.resolveActivity( getPackageManager() ) != null) {
                                    startActivityForResult( takePictureIntent, REQUEST_IMAGE_CAPTURE );
                                }

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

        updateBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Progress.showProgress( ProfileActivity.this );
//                        Log.d("UpdateProParams", jsonObject.toString());
                    dataManager.saveDetail( dataManager.getUserId(), nameTv.getText().toString(),
                            dataManager.getEmailId(), designationTv.getText().toString(), str,
                            organizationTv.getText().toString(), mobileTv.getText().toString(), new SharedPrefsHelper( ProfileActivity.this ).getPassword() );

                    Toast.makeText( context, "Profile Updated", Toast.LENGTH_SHORT ).show();
                    finish();
            }
        } );

        //
        // sequence example
        ShowcaseConfig conf = new ShowcaseConfig();
        conf.setDelay( 500 ); // half second between each showcase view

        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence( this, "123" );

        sequence.setConfig( conf );

        sequence.addSequenceItem( updateBtn, "Update your profile", "GOT IT" );

//	sequence.addSequenceItem(mButtonTwo,
//            "This is button two", "GOT IT");
//
//	sequence.addSequenceItem(mButtonThree,
//            "This is button three", "GOT IT");

        sequence.start();

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

        if (resultCode == RESULT_OK && requestCode == SELECT_PHOTO) {
            final Uri resultUri = data.getData();
            try {

                Bitmap photo = MediaStore.Images.Media.getBitmap( this.getContentResolver(), resultUri );

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                photo.compress( Bitmap.CompressFormat.PNG, 10, baos ); //bm is the bitmap object
                byte[] b = baos.toByteArray();
                Bitmap compressedImage = BitmapFactory.decodeByteArray( b, 0, b.length );

                str = Base64.encodeToString( b, Base64.DEFAULT );
                profileIv.setImageBitmap( compressedImage );
                profileImagePath = new File( ImageFilePath.getPath( ProfileActivity.this,data.getData() ) ) ;

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (resultCode == RESULT_OK && requestCode == REQUEST_IMAGE_CAPTURE) {

            profileIv.setImageURI( imageUri);
            profileImagePath = new File( imageUri.getPath() ) ;

        }
    }

    public void updateProfile(in.kestone.eventbuddy.model.user_model.Profile profileData) {
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

    public void postImage(File file) {
        // create multipart
        RequestBody requestFile = RequestBody.create( MediaType.parse( "text/plain" ), file );
        MultipartBody.Part body = MultipartBody.Part.createFormData( "image", file.getName(), requestFile );

        Call<PostImageResponse> call = apiInterface.postImageStream( body );
        call.enqueue( new Callback<PostImageResponse>() {
            @Override
            public void onResponse(Call<PostImageResponse> call, retrofit2.Response<PostImageResponse> response) {

                if (response.code() == 200) {
                    Log.e( "Response ", response.body().getData() );
                     uploadedImagePath = response.body().getData();
                }
                Progress.closeProgress();
            }

            @Override
            public void onFailure(Call<PostImageResponse> call, Throwable t) {
                CustomDialog.showInvalidPopUp( ProfileActivity.this, CONSTANTS.ERROR, CONSTANTS.CONNECTIONERROR );
                Progress.closeProgress();
            }
        } );


    }

}
