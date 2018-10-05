package in.kestone.eventbuddy.view.speaker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.pkmmte.view.CircularImageView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.widgets.CustomTextView;

public class ActivitySpeaterDetails extends AppCompatActivity {

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
    @BindView(R.id.profileIv)
    CircularImageView profileIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_speater_details );

        Toolbar toolbar = findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        getSupportActionBar().setTitle( "Profile Details" );

        ButterKnife.bind( this );

        detailsTv.setText( getIntent().getStringExtra( "details" ) );
        // mTitleTv.setText(getIntent().getStringExtra("Type") + " Details");

        organizationTv.setText( getIntent().getStringExtra( "Organization" ) );
        designationTv.setText( getIntent().getStringExtra( "Designation" ) );
        nameTv.setText( getIntent().getStringExtra( "Name" ) );

        if (getIntent().getStringExtra( "Image" ).length() > 0) {
            Picasso.with( ActivitySpeaterDetails.this ).load( getIntent().getStringExtra( "Image" ) )
                    .resize( 100, 100 )
                    .placeholder( R.drawable.ic_user ).into( profileIv );
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected( item );
    }
}
