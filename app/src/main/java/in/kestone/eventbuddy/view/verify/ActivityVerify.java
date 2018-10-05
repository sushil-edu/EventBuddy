package in.kestone.eventbuddy.view.verify;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import in.kestone.eventbuddy.Eventlistener.OnVerifiedListener;
import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.fragment.FragmentCheckIn;
import in.kestone.eventbuddy.fragment.FragmentOtp;
import in.kestone.eventbuddy.fragment.Priority;
import in.kestone.eventbuddy.view.login.ActivityLogin;

public class ActivityVerify extends AppCompatActivity implements OnVerifiedListener {
   String type="";

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent( context, ActivityVerify.class );

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_verify );

        type = getIntent().getStringExtra( "type" );
        if(type.equalsIgnoreCase( "checkIn" )){
            getSupportFragmentManager().beginTransaction()
                    .replace( R.id.container, new FragmentCheckIn() )
                    .commit();
        }else if(type.equalsIgnoreCase( "otp" )){
            getSupportFragmentManager().beginTransaction()
                    .replace( R.id.container, new FragmentOtp() )
                    .commit();
        }
    }

    @Override
    public void onVerified(String status) {
        if (status.equalsIgnoreCase( "verified" )) {
            getSupportFragmentManager().beginTransaction()
                    .replace( R.id.container, new FragmentCheckIn() )
                    .commit();
        }else if (status.equalsIgnoreCase( "check-in" )) {
            getSupportFragmentManager().beginTransaction()
                    .replace( R.id.container, new Priority() )
                    .commit();
        }
    }
}
