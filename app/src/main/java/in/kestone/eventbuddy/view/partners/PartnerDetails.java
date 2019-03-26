package in.kestone.eventbuddy.view.partners;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.model.partners_model.Detail;


/**
 * A simple {@link Fragment} subclass.
 */
public class PartnerDetails extends AppCompatActivity {

    Detail partnersList;
    TextView mTitleTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.fragment_partner_details );
        Bundle bundle = getIntent().getExtras();
        partnersList = (Detail) bundle.getSerializable( "details" );

        Toolbar toolbar = findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        mTitleTv= toolbar.findViewById( R.id.mTitleTv );
        mTitleTv.setText(  "Profile Details" );

        TextView nameTv = findViewById(R.id.nameTv);
        nameTv.setText(partnersList.getName());
        TextView partnerTv = findViewById(R.id.partnerTv);
        partnerTv.setText(partnersList.getBiography());
        TextView bioTv = findViewById(R.id.bioTv);
        bioTv.setText(partnersList.getBiography());
        TextView callTv = findViewById(R.id.callTv);
        callTv.setText(partnersList.getPhone());
        TextView mailTv = findViewById(R.id.mailTv);
        mailTv.setText(partnersList.getEmail());
        ImageView image = findViewById(R.id.image);

        Picasso.with( this )
                .load(partnersList.getLogo())
                .placeholder(R.drawable.user)
                .error(R.mipmap.ic_launcher)
                .into(image);


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
