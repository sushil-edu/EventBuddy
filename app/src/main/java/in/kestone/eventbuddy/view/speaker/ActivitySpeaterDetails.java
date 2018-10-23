package in.kestone.eventbuddy.view.speaker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.pkmmte.view.CircularImageView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.kestone.eventbuddy.R;
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
    String tag;
    @BindView(R.id.mTitleTv)
    ToolbarTextView mTitleTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_speater_details );

        Toolbar toolbar = findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        mTitleTv= toolbar.findViewById( R.id.mTitleTv );
        mTitleTv.setText(  "Profile Details" );

        ButterKnife.bind( this );

        detailsTv.setText( getIntent().getStringExtra( "details" ) );
        // mTitleTv.setText(getIntent().getStringExtra("Type") + " Details");

        organizationTv.setText( getIntent().getStringExtra( "Organization" ) );
        designationTv.setText( getIntent().getStringExtra( "Designation" ) );
        nameTv.setText( getIntent().getStringExtra( "Name" ) );
        tag = getIntent().getStringExtra( "Tag" );

        if (getIntent().getStringExtra( "Image" ).length() > 0) {
            Picasso.with( ActivitySpeaterDetails.this ).load( getIntent().getStringExtra( "Image" ) )
                    .resize( 100, 100 )
                    .placeholder( R.drawable.ic_user ).into( profileIv );
        }

        mScheduleBtn.setOnClickListener( this );
        tvReschedule.setOnClickListener( this );

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent( this, MainActivity.class );
        if (tag.equalsIgnoreCase( "Speaker" )) {
            intent.putExtra( "Tag", "Speaker" );
        } else {
            intent.putExtra( "Tag", "Agenda" );
        }
        startActivity( intent );
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
        switch (view.getId()) {
            case R.id.mScheduleBtn:
//                intent.putExtra("Name", getIntent().getStringExtra("Name"));
//                intent.putExtra("Designation", getIntent().getStringExtra("Designation"));
//                intent.putExtra("Organization", getIntent().getStringExtra("Organization"));
//                intent.putExtra("Type", getIntent().getStringExtra("Type"));
//                intent.putExtra("Email", getIntent().getStringExtra("Email"));
                intent.putExtra( "Tag", "Schedule" );
                break;
            case R.id.tvReschedule:
//                intent.putExtra("Name", getIntent().getStringExtra("Name"));
//                intent.putExtra("Designation", getIntent().getStringExtra("Designation"));
//                intent.putExtra("Organization", getIntent().getStringExtra("Organization"));
//                intent.putExtra("Type", getIntent().getStringExtra("Type"));
//                intent.putExtra("Email", getIntent().getStringExtra("Email"));
                intent.putExtra( "Tag", "Reschedule" );
                break;
        }
        startActivity( intent );
        finish();
    }
}
