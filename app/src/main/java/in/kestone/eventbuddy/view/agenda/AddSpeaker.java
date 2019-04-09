package in.kestone.eventbuddy.view.agenda;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.view.main.MainActivity;
import in.kestone.eventbuddy.widgets.CustomTextView;
import in.kestone.eventbuddy.widgets.ToolbarTextView;

public class AddSpeaker extends AppCompatActivity {

    Toolbar toolbar;
    ToolbarTextView mTitleTv;
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

        setContentView( R.layout.activity_add_speaker );
        Toolbar toolbar = findViewById( R.id.toolbar );
        setSupportActionBar( toolbar );
        mTitleTv=toolbar.findViewById( R.id.mTitleTv );
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );
        getSupportActionBar().setTitle( "Add Speaker" );

        ButterKnife.bind( this );
        findViewById( R.id.layout_save ).setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        } );
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent( this, MainActivity.class );
        intent.putExtra( "Tag","Agenda" );
        startActivity( intent );
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        onBackPressed();
        return super.onOptionsItemSelected( item );
    }
}
