package in.kestone.eventbuddy.view.agenda;


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

import java.util.ArrayList;

import in.kestone.eventbuddy.Altdialog.CustomDialog;
import in.kestone.eventbuddy.Altdialog.Progress;
import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.common.CONSTANTS;
import in.kestone.eventbuddy.http.APIClient;
import in.kestone.eventbuddy.http.APIInterface;
import in.kestone.eventbuddy.http.CallUtils;
import in.kestone.eventbuddy.model.agenda_model.ModelAgenda;
import in.kestone.eventbuddy.model.agenda_model.Track;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
    ArrayList<Track> listTrackDetails = new ArrayList<>();
    int tabCount;
    ModelAgenda modelAgenda;
    APIInterface apiInterface;

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
                            tabLayout.getTabAt( listTrack.size() - 1 ).select();
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

        getAgenda();
        Progress.showProgress( getActivity() );

        tabLayout = view.findViewById( R.id.tabs );
        viewPager = view.findViewById( R.id.viewpager );
        tabLayout.setVisibility( View.GONE );

        if (list.size() <= 4 || listTrack.size() <= 4)
            tabLayout.setTabMode( TabLayout.MODE_FIXED );
        else
            tabLayout.setTabMode( TabLayout.MODE_SCROLLABLE );

        LocalBroadcastManager.getInstance( getActivity() ).registerReceiver( mMessageReceiver,
                new IntentFilter( "event-buddy" ) );

        tabLayout.addOnTabSelectedListener( new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem( tab.getPosition() );
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        } );

        return view;
    }

    private void setupViewPager(ViewPager viewPager, ModelAgenda modelAgenda) {

        ViewPagerAdapter adapter = new ViewPagerAdapter( getFragmentManager() );

        for (int i = 0; i < listTrack.size(); i++) {
            AgendaTrackFragment fView = new AgendaTrackFragment( i, modelAgenda );
//            AgendaTrackFragment fView = new AgendaTrackFragment( i, modelAgenda.getAgenda().get( i ).getTrack().size() );
            adapter.addFrag( fView, listTrack.get( i ) );
        }
        viewPager.setAdapter( adapter );
        adapter.notifyDataSetChanged();
    }

    public void setAgenda(ModelAgenda modelAgenda) {
        new Handler().postDelayed( new Runnable() {
            @Override
            public void run() {
                getActivity().runOnUiThread( new Runnable() {
                    @Override
                    public void run() {
                        tabCount = modelAgenda.getAgenda().size();
                        listTrack.clear();
                        for (int i = 0; i < tabCount; i++)
                            listTrack.add( modelAgenda.getAgenda().get( i ).getDisplayLabel() );

                        setupViewPager( viewPager, modelAgenda );
                        tabLayout.setupWithViewPager( viewPager );
                        tabLayout.setVisibility( View.VISIBLE );
//                        Progress.closeProgress();
                    }
                } );
            }
        }, 500 );

    }


    @Override
    public void onDestroy() {
        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance( getActivity() ).unregisterReceiver( mMessageReceiver );
        super.onDestroy();
    }

    public void getAgenda() {

        apiInterface = APIClient.getClient().create( APIInterface.class );
        Call<ModelAgenda> call = apiInterface.getAgenda( (int) CONSTANTS.EVENTID );
        CallUtils.enqueueWithRetry( call, 3, new Callback<ModelAgenda>() {
            @Override
            public void onResponse(Call<ModelAgenda> call, Response<ModelAgenda> response) {
                if (response.code() == 200) {
                    Log.e( "Response ", "" + response.body().getStatusCode() );
//                AgendaList.setAgenda( response.body() );
                    setAgenda( response.body() );
                } else {
                    CustomDialog.showInvalidPopUp( getActivity(), CONSTANTS.ERROR, response.message() );
                }
                Progress.closeProgress();

            }

            @Override
            public void onFailure(Call<ModelAgenda> call, Throwable t) {
                Log.e( "Error ", t.getMessage() );
                CustomDialog.showInvalidPopUp( getActivity(), CONSTANTS.ERROR, CONSTANTS.CONNECTIONERROR );
                Progress.closeProgress();
            }
        } );
    }
}
