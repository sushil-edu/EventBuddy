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

import java.util.ArrayList;

import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.common.ReadJson;
import in.kestone.eventbuddy.model.agenda_model.AgendaList;
import in.kestone.eventbuddy.model.agenda_model.ModelAgenda;
import in.kestone.eventbuddy.model.agenda_model.Track;


/**
 * A simple {@link Fragment} subclass.
 */
public class AgendaFragment extends Fragment {

    View view;
    TabLayout tabLayout;
    ViewPager viewPager;
    int tab_pos = 0;
    ArrayList<String> list = new ArrayList<>();
    ArrayList<String> listTrack = new ArrayList<>();
    ArrayList<Track> listTrackDetails = new ArrayList<>(  );
    int tabCount;
    ModelAgenda modelAgenda;

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
                            if(list.size()>0) {
                                tabLayout.getTabAt( list.size() - 1 ).select();
                            }else {
                                tabLayout.getTabAt( listTrack.size() - 1 ).select();
                            }
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
            listTrack.clear();
            for (int i = 0; i < tabCount; i++) {
                if(AgendaList.getAgenda().getAgenda().get( 0 ).getTrack().get( 0 ).getTrackName().equalsIgnoreCase( "" )){
                    list.add( AgendaList.getAgenda().getAgenda().get( i ).getDisplayLabel() );
                }else {
                    listTrack.add( AgendaList.getAgenda().getAgenda().get( i ).getDisplayLabel() );
                }
            }

        }
        tabLayout = view.findViewById( R.id.tabs );
        viewPager = view.findViewById( R.id.viewpager );
        setupViewPager( viewPager );
        if (list.size() <= 4 || listTrack.size()<= 4)
            tabLayout.setTabMode( TabLayout.MODE_FIXED );
        else
            tabLayout.setTabMode( TabLayout.MODE_SCROLLABLE );

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
//        if(list.size()>0){
//            for (int i = 0; i < list.size(); i++) {
//                AgendaListFragment fView = new AgendaListFragment( i, list.size() );
//                adapter.addFrag( fView, list.get( i ) );
//           }
//        }else {
            for (int i = 0; i < listTrack.size(); i++) {
//                AgendaTrackFragmentNew fView = new AgendaTrackFragmentNew( i, listTrack.size() );
                AgendaTrackFragment fView = new AgendaTrackFragment( i, AgendaList.getAgenda().getAgenda().get( i ).getTrack().size() );
                adapter.addFrag( fView, listTrack.get( i ) );
            }
//        }
        viewPager.setAdapter( adapter );
        adapter.notifyDataSetChanged();

    }

    public void setAgenda(Activity activity) {
        modelAgenda = new Gson().fromJson(  ReadJson.loadJSONFromAsset( activity, "agenda.json" ),
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
