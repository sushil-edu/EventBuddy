package in.kestone.eventbuddy.view.partners;


import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.common.LocalStorage;
import in.kestone.eventbuddy.model.partners_model.Detail;
import in.kestone.eventbuddy.model.partners_model.PartnerDetail;


/**
 * A simple {@link Fragment} subclass.
 */
public class PartnerDetails extends AppCompatActivity {

    Detail partnersList;
    TextView mTitleTv, subTitle;
    LinearLayout getConnectTv;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        Window window = getWindow();

        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags( WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS );

        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags( WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS );

        // finally change the color
        window.setStatusBarColor( ContextCompat.getColor( this, R.color.actionbar_color ) );

        setContentView( R.layout.fragment_partner_details );
        Bundle bundle = getIntent().getExtras();
        partnersList = (Detail) bundle.getSerializable( "details" );

        Toolbar toolbar = findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        mTitleTv= toolbar.findViewById( R.id.mTitleTv );
        subTitle= toolbar.findViewById( R.id.subTitleTv );
        mTitleTv.setText( bundle.getString( "type" ));
        subTitle.setText( "Description" );

        TextView nameTv = findViewById(R.id.nameTv);
        nameTv.setText(partnersList.getName());
        TextView partnerTv = findViewById(R.id.partnerTv);
        partnerTv.setText(partnersList.getCategory());
        TextView bioTv = findViewById(R.id.bioTv);
        bioTv.setText(partnersList.getBiography());
        TextView callTv = findViewById(R.id.callTv);
        callTv.setText(partnersList.getPhone());
        TextView mailTv = findViewById(R.id.mailTv);
        mailTv.setText(partnersList.getEmail());
        ImageView image = findViewById(R.id.image);

        Picasso.with( this )
                .load( LocalStorage.getImagePath( PartnerDetails.this )+""+partnersList.getLogo())
                .placeholder(R.drawable.default_user_grey)
                .error(R.drawable.default_user_grey)
                .into(image);


        getConnectTv = findViewById( R.id.getConnectTv );
        if (partnersList.getIsDisplayContactNo().equalsIgnoreCase( "True" )){
            getConnectTv.setVisibility( View.VISIBLE );
        }else {
            getConnectTv.setVisibility( View.GONE );
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
