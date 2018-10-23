package in.kestone.eventbuddy.view.networking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.kestone.eventbuddy.R;

public class MyScheduled extends Fragment implements View.OnClickListener {
    @BindView( R.id.btnReschedule )
    TextView btnReschedule;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.my_scheduled, container, false );
        initialiseView(view);
        return view;
    }

    private void initialiseView(View view) {
        ButterKnife.bind( this, view );
        btnReschedule.setOnClickListener( this );
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnReschedule:
                callSchedule();
                break;
        }
    }
    // Send an Intent with an action named "custom-event-name". The Intent sent should
// be received by the ReceiverActivity.
    private void callSchedule() {
        Log.d( "sender", "Broadcasting message" );
        Intent intent = new Intent( "schedule" );
        // You can also include some extra data.
        intent.putExtra( "message", "schedule" );
        LocalBroadcastManager.getInstance( getContext() ).sendBroadcast( intent );
    }
}
