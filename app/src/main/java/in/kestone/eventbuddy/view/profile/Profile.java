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
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import in.kestone.eventbuddy.Altdialog.Progress;
import in.kestone.eventbuddy.MvpApp;
import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.data.DataManager;
import in.kestone.eventbuddy.view.base.MvpView;
import in.kestone.eventbuddy.widgets.ToolbarTextView;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

public class Profile extends AppCompatActivity implements MvpView {
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
    private String str = "";


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
        ToolbarTextView toolbarTitle = toolbar.findViewById( R.id.mTitleTv );
        setSupportActionBar( toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        toolbarTitle.setText( "Update Profile" );
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
//        byte[] decodedString = Base64.decode( dataManager.getImagePath(), Base64.DEFAULT );
//        Bitmap decodedByte = BitmapFactory.decodeByteArray( decodedString, 0, decodedString.length );

        if (dataManager.getImagePath() != null && !dataManager.getImagePath().isEmpty()) {
            Picasso.with( this ).load( dataManager.getImagePath() )
                    .resize( 80, 80 )
                    .placeholder( R.drawable.default_user_grey )
                    .into( profileIv );
        }
//        profileIv.setImageBitmap( decodedByte );

        profileIv.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionHelper permissionHelper = new PermissionHelper( Profile.this, new String[]{Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE}, 100 );
                permissionHelper.request( new PermissionHelper.PermissionCallback() {
                    @Override
                    public void onPermissionGranted() {

                        AlertDialog.Builder builder = new AlertDialog.Builder( Profile.this );
                        builder.setMessage( "Select Source" );
                        builder.setPositiveButton( "Gallery", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Intent photoPickerIntent = new Intent( Intent.ACTION_PICK );
                                photoPickerIntent.setType( "def_image/*" );
                                startActivityForResult( photoPickerIntent, SELECT_PHOTO );
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
//                if(ConnectionCheck.connectionStatus(EditProfile.this)){
//                    Progress.showProgress(Profile.this);
                JSONObject jsonObject = new JSONObject();

                try {
                    jsonObject.put( "Name", nameTv.getText().toString() );
                    jsonObject.put( "Designation", designationTv.getText().toString() );
                    jsonObject.put( "Organization", organizationTv.getText().toString() );
                    jsonObject.put( "EmailID", dataManager.getEmailId() );
                    jsonObject.put( "Mobile", mobileTv.getText().toString() );
                    if (str.length() > 0) {
                        jsonObject.put( "ProfilePic", str );
                    } else {
                        jsonObject.put( "ProfilePic", "" );
                    }

//                        Log.d("UpdateProParams", jsonObject.toString());
                    dataManager.saveDetail( dataManager.getUserId(), nameTv.getText().toString(),
                            dataManager.getEmailId(), designationTv.getText().toString(), str,
                            organizationTv.getText().toString(), mobileTv.getText().toString() );

                    Toast.makeText( context, "Profile Updated", Toast.LENGTH_SHORT ).show();
                    finish();
//                        new UpdateProfile(ApiUrl.UpdateProfile,jsonObject.toString());

                } catch (JSONException e) {
                    e.printStackTrace();
                    Progress.closeProgress();
                }


//                }else{
//                    Toast.makeText(EditProfile.this, "No Internet", Toast.LENGTH_SHORT).show();
//                }
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

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (resultCode == RESULT_OK && requestCode == REQUEST_IMAGE_CAPTURE) {

            Bitmap photo = (Bitmap) data.getExtras().get( "data" );
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            photo.compress( Bitmap.CompressFormat.PNG, 10, baos ); //bm is the bitmap object
            byte[] b = baos.toByteArray();
            Bitmap compressedImage = BitmapFactory.decodeByteArray( b, 0, b.length );

            str = Base64.encodeToString( b, Base64.DEFAULT );
            profileIv.setImageBitmap( compressedImage );

        }
    }

//    private class UpdateProfile {
//
//        private final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
//
//        private UpdateProfile(String url, String postBody) {
//
//            try {
//                postRequest(url, postBody);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//
//        private void postRequest(String postUrl, String postBody) throws IOException {
//
//            OkHttpClient client = new OkHttpClient();
//
//            RequestBody body = RequestBody.create(JSON, postBody);
//
//            Request request = new Request.Builder()
//                    .url(postUrl)
//                    .post(body)
//                    .build();
//
//            client.newCall(request).enqueue(new Callback() {
//                @Override
//                public void onFailure(@NonNull Call call, @NonNull IOException e) {
//                    call.cancel();
//                }
//
//                @Override
//                public void onResponse(@NonNull Call call, @NonNull final Response response) throws IOException {
//
//                    Progress.closeProgress();
//
//                    if (response.isSuccessful()) {
//
//                        String myReponse = response.body().string();
//
//                        Log.d("EditProfileRes", myReponse);
//
//                        try {
//                            JSONObject jsonObj = new JSONObject(myReponse);
//                            if(jsonObj.getString("retval").equals("Profile updated successfully.")){
//
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        Toast.makeText(EditProfile.this, "Profile Updated", Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//
//                                JSONArray jsonArray = new JSONArray();
//                                JSONObject jsonObject = new JSONObject();
//                                jsonObject.put("ID",UserDetails.getID());
//                                jsonObject.put("Name",nameTv.getText().toString());
//                                jsonObject.put("Designation",designationTv.getText().toString());
//                                jsonObject.put("Organization",organizationTv.getText().toString());
//                                jsonObject.put("EmailID",UserDetails.getEmailID());
//                                jsonObject.put("Mobile",mobileTv.getText().toString());
//                                jsonObject.put("PassportNo",UserDetails.getPassportNo());
//                                jsonObject.put("RegistrationType",UserDetails.getRegistrationType());
//
//                                UserDetails.setName(nameTv.getText().toString());
//                                UserDetails.setMobile(mobileTv.getText().toString());
//                                UserDetails.setDesignation(designationTv.getText().toString());
//                                UserDetails.setOrganization(organizationTv.getText().toString());
//                                if(jsonObj.getString("code").length()>4){
//                                    UserDetails.setImageURL(jsonObj.getString("code"));
//                                    jsonObject.put("ImageURL",jsonObj.getString("code"));
//                                }else {
//                                    jsonObject.put("ImageURL",UserDetails.getImageURL());
//                                }
//
//                                jsonObject.put("IsCheckedIn",UserDetails.getIsCheckedIn());
//                                jsonObject.put("CheckinStartDatetime",UserDetails.getCheckinStartDatetime());
//                                jsonObject.put("UserType",UserDetails.getUserType());
//                                jsonObject.put("UniqueID",UserDetails.getUniqueID());
//                                jsonObject.put("CheckinEndDatetime",UserDetails.getCheckinEndDatetime());
//                                jsonObject.put("IsVenue500Applicable",UserDetails.getIsVenue500Applicable());
//                                jsonObject.put("VenueLatitude",UserDetails.getVenueLatitude());
//                                jsonObject.put("VenueLongitude",UserDetails.getVenueLongitude());
//                                jsonObject.put("VenueName",UserDetails.getVenueName());
//                                jsonObject.put("ActivationDays",UserDetails.getActivationDays());
//                                jsonObject.put("IsTrackApplicable",UserDetails.getIsTrackApplicable());
//                                jsonObject.put("IsChekinRequired",UserDetails.getIsChekinRequired());
//                                jsonObject.put("IsPriorityCheckin",UserDetails.getIsPriorityCheckin());
//                                jsonObject.put("PCStart",UserDetails.getPCStart());
//                                jsonObject.put("PCEnd",UserDetails.getPCEnd());
//                                jsonObject.put("PriorityMsg",UserDetails.getPriorityMsg());
//                                jsonObject.put("Welcomemsg",UserDetails.getWelcomemsg());
//
//                                jsonArray.put(jsonObject);
//
//                                SharedPreferences sharedPrefrence = getSharedPreferences("User", MODE_PRIVATE);
//                                SharedPreferences.Editor editor = sharedPrefrence.edit();
//                                editor.putString("UserDetails", jsonArray.toString());
//                                editor.apply();
//
//                                Intent intent = new Intent(EditProfile.this,MainActivity.class);
//                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                startActivity(intent);
//                                finish();
//
//                            }else {
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        Toast.makeText(EditProfile.this, "Profile Not Updated", Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//                            }
//
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//
//                    } else {
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                Toast.makeText(EditProfile.this, response.message(), Toast.LENGTH_SHORT).show();
//                            }
//                        });
//
//                    }
//
//                }
//            });
//        }
//
//    }

}
