package in.kestone.eventbuddy.view.speaker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.pkmmte.view.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.common.CONSTANTS;
import in.kestone.eventbuddy.model.speaker_model.SpeakerDetail;
import in.kestone.eventbuddy.view.main.MainActivity;
import in.kestone.eventbuddy.widgets.CustomTextView;
import in.kestone.eventbuddy.widgets.ToolbarTextView;

public class ActivitySpeaterDetails extends AppCompatActivity implements View.OnClickListener {

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
    @BindView(R.id.mTitleTv)
    ToolbarTextView mTitleTv;
    private ArrayList<SpeakerDetail> speakerList;
    SpeakerDetail speakerDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_speater_details );

        Toolbar toolbar = findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        mTitleTv= toolbar.findViewById( R.id.mTitleTv );
        mTitleTv.setText(  "Profile Details" );

        Bundle bundle = getIntent().getExtras();
        speakerDetail = (SpeakerDetail) bundle.getSerializable( "data" );

        ButterKnife.bind( this );

//        detailsTv.setText( speakerDetail.getProfileDescription());
        type= speakerDetail.getUserType();
        // mTitleTv.setText(getIntent().getStringExtra("Type") + " Details");

        organizationTv.setText(speakerDetail.getOrganization() );
        designationTv.setText(speakerDetail.getDesignation());
        nameTv.setText( speakerDetail.getFirstName()+" "+speakerDetail.getLastName());
        tag = speakerDetail.getUserType();//getIntent().getStringExtra( "Tag" );

//        if (getIntent().getStringExtra( "Image" )) {
            Picasso.with( ActivitySpeaterDetails.this ).load( speakerDetail.getImage())
                    .resize( 100, 100 )
                    .placeholder( R.drawable.user ).into( profileIv );
//        }

        mScheduleBtn.setOnClickListener( this );
        tvReschedule.setOnClickListener( this );

    }

    @Override
    public void onBackPressed() {
//        Intent intent = new Intent( this, MainActivity.class );
//        if (tag.equalsIgnoreCase( "Speaker" )) {
//            intent.putExtra( "Tag", "Speaker" );
//        } else {
//            intent.putExtra( "Tag", "Agenda" );
//        }
//        startActivity( intent );
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected( item );
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent( this, MainActivity.class );
        Bundle bundle = new Bundle(  );
        switch (view.getId()) {
            case R.id.mScheduleBtn:
//                callSchedule( "test", type );
                intent.putExtra("Name", nameTv.getText().toString());
//                intent.putExtra("Designation", getIntent().getStringExtra("Designation"));
//                intent.putExtra("Organization", getIntent().getStringExtra("Organization"));
                intent.putExtra("Type", type);
//                intent.putExtra("Email", getIntent().getStringExtra("Email"));
                bundle.putSerializable( "Data", speakerDetail );
                intent.putExtra( "Tag", "Schedule" );
                intent.putExtras( bundle );
                break;
            case R.id.tvReschedule:
                intent.putExtra("Name",nameTv.getText().toString());
//                intent.putExtra("Designation", getIntent().getStringExtra("Designation"));
//                intent.putExtra("Organization", getIntent().getStringExtra("Organization"));
                intent.putExtra("Type", type);
//                intent.putExtra("Email", getIntent().getStringExtra("Email"));
                intent.putExtra( "Tag", "Reschedule" );
                bundle.putSerializable( "Data", speakerDetail );
                break;
        }
        startActivity( intent );
//        finish();
    }
    private void callSchedule(String name, String type) {
        Intent intent = new Intent( CONSTANTS.RESCHEDULE );
        // You can also include some extra data.
        intent.putExtra( "message", CONSTANTS.RESCHEDULE );
        intent.putExtra( "name", name );
        intent.putExtra( "type", type );
        LocalBroadcastManager.getInstance( ActivitySpeaterDetails.this ).sendBroadcast( intent );
    }
}
