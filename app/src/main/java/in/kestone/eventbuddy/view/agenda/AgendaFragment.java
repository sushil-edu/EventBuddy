package in.kestone.eventbuddy.view.agenda;


import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.common.ReadJson;
import in.kestone.eventbuddy.model.agenda.AgendaList;
import in.kestone.eventbuddy.model.agenda.AgendaListFragment;
import in.kestone.eventbuddy.model.agenda.ModelAgenda;


/**
 * A simple {@link Fragment} subclass.
 */
public class AgendaFragment extends Fragment {

    View view;
    TabLayout tabLayout;
    ViewPager viewPager;
    int tab_pos = 0;
    ArrayList<String> list = new ArrayList<>();
    int tabCount;
    ModelAgenda modelAgenda;
    // Our handler for received Intents. This will be called whenever an Intent
// with an action named "custom-event-name" is broadcasted.
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String message = intent.getStringExtra( "message" );
            Log.d( "receiver", "Got message: " + message );
            new Handler().postDelayed(
                    new Runnable() {
                        @Override
                        public void run() {
                            tabLayout.getTabAt( list.size() - 1 ).select();
                        }
                    }, 50 );

        }
    };

    public AgendaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate( R.layout.fragment_agenda, container, false );

        //get agenda from json
        setAgenda( getActivity() );
        if (AgendaList.getAgenda().getStatusCode().equalsIgnoreCase( "200" )) {
            tabCount = AgendaList.getAgenda().getAgenda().size();
            list.clear();
            for (int i = 0; i < tabCount; i++) {
                list.add( AgendaList.getAgenda().getAgenda().get( i ).getDate() );
            }
            list.add( "My Agenda" );

        }
        tabLayout = view.findViewById( R.id.tabs );
        viewPager = view.findViewById( R.id.viewpager );
        setupViewPager( viewPager );
        tabLayout.setupWithViewPager( viewPager );

        // Register to receive messages.
        // We are registering an observer (mMessageReceiver) to receive Intents
        // with actions named "custom-event-name".
        LocalBroadcastManager.getInstance( getActivity() ).registerReceiver( mMessageReceiver,
                new IntentFilter( "event-buddy" ) );

        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter( getFragmentManager() );
        for (int i = 0; i < list.size(); i++) {
            AgendaListFragment fView = new AgendaListFragment( i, list.size() );
            adapter.addFrag( fView, list.get( i ) );
        }
        viewPager.setAdapter( adapter );
        adapter.notifyDataSetChanged();

    }

    public void setAgenda(Activity activity) {
        modelAgenda = new Gson().fromJson( new ReadJson().loadJSONFromAsset( activity, "agenda.json" ),
                ModelAgenda.class );
        if (modelAgenda.getStatusCode().equalsIgnoreCase( "200" )) {
            AgendaList.setAgenda( modelAgenda );

        } else {
            Log.e( "Status", String.valueOf( modelAgenda.getStatusCode() ) );
        }
    }


    @Override
    public void onDestroy() {
        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance( getActivity() ).unregisterReceiver( mMessageReceiver );
        super.onDestroy();
    }

}
