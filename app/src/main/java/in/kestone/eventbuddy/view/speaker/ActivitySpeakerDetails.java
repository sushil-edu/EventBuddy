package in.kestone.eventbuddy.view.speaker;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.pkmmte.view.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.kestone.eventbuddy.Altdialog.CustomDialog;
import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.common.CONSTANTS;
import in.kestone.eventbuddy.data.SharedPrefsHelper;
import in.kestone.eventbuddy.model.speaker_model.SpeakerDetail;
import in.kestone.eventbuddy.view.main.MainActivity;
import in.kestone.eventbuddy.widgets.CustomTextView;

public class ActivitySpeakerDetails extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.nameTv)
    CustomTextView nameTv;
    @BindView(R.id.organizationTv)
    CustomTextView organizationTv;
    @BindView(R.id.designationTv)
    CustomTextView designationTv;
    //    @BindView(R.id.mTitleTv)
//    CustomTextView mTitleTv;
    @BindView(R.id.detailsTv)
    CustomTextView detailsTv;
    @BindView(R.id.mScheduleBtn)
    CustomTextView mScheduleBtn;
    @BindView(R.id.tvCancel)
    CustomTextView tvCancel;
    @BindView(R.id.tvReschedule)
    CustomTextView tvReschedule;
    @BindView(R.id.profileIv)
    CircularImageView profileIv;
    String tag, type;

    TextView mTitleTv;
    TextView subTitleTv;
    SpeakerDetail speakerDetail;
    private ArrayList<SpeakerDetail> speakerList;

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

        setContentView( R.layout.activity_speater_details );

        Toolbar toolbar = findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        mTitleTv = toolbar.findViewById( R.id.mTitleTv );
        subTitleTv = toolbar.findViewById( R.id.subTitleTv );
        mTitleTv.setText( "Profile Details" );
        subTitleTv.setVisibility( View.GONE );

        Bundle bundle = getIntent().getExtras();
        speakerDetail = (SpeakerDetail) bundle.getSerializable( "data" );

        ButterKnife.bind( this );

        type = speakerDetail.getUserType();
        organizationTv.setText( speakerDetail.getOrganization() );
        designationTv.setText( speakerDetail.getDesignation() );
        nameTv.setText( speakerDetail.getFirstName().concat( " " ).concat( speakerDetail.getLastName() ) );
        tag = speakerDetail.getUserType();

        Picasso.with( ActivitySpeakerDetails.this ).load( speakerDetail.getImage() )
                .resize( 100, 100 )
                .placeholder( R.drawable.default_user_grey ).into( profileIv );

        mScheduleBtn.setOnClickListener( this );
        tvReschedule.setOnClickListener( this );

    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected( item );
    }

    @Override
    public void onClick(View view) {
        if (speakerDetail.getUserID() != new SharedPrefsHelper( getApplicationContext() ).getUserId()) {
            Intent intent = new Intent( this, MainActivity.class );
            Bundle bundle = new Bundle();
            switch (view.getId()) {
                case R.id.mScheduleBtn:
                    intent.putExtra( "Name", nameTv.getText().toString() );
                    intent.putExtra( "Type", type );
                    bundle.putSerializable( "Data", speakerDetail );
                    intent.putExtra( "Tag", CONSTANTS.SCHEDULE );
                    intent.putExtras( bundle );
                    startActivity( intent );
                    break;
                case R.id.tvReschedule:
                    intent.putExtra( "Name", nameTv.getText().toString() );
                    intent.putExtra( "Type", type );
                    intent.putExtra( "Tag", CONSTANTS.RESCHEDULE );
                    bundle.putSerializable( "Data", speakerDetail );
                    startActivity( intent );
                    break;
            }
        } else {
            CustomDialog.showInvalidPopUp( ActivitySpeakerDetails.this, "", "You should not send meeting request yourself" );
        }

//        finish();
    }
}
