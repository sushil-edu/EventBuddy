package in.kestone.eventbuddy.view.networking;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.viewpager.widget.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.common.CONSTANTS;
import in.kestone.eventbuddy.model.agenda_model.ModelAgenda;
import in.kestone.eventbuddy.model.networking_model.MNetworking;
import in.kestone.eventbuddy.model.speaker_model.SpeakerDetail;

/**
 * A simple {@link Fragment} subclass.
 */
public class Networking extends Fragment {

    ModelAgenda modelAgenda;
    private ViewPager viewPager;
    String name="", type =""; int id;
    SpeakerDetail speakerDetail;
    MNetworking networkingResponse;


    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

//            name = intent.getStringExtra( "name" );
            Log.d( "receiver", "Got message: " + name );
            new Handler().postDelayed(
                    new Runnable() {
                        @Override
                        public void run() {
                            viewPager.setCurrentItem( 0 );
                        }
                    }, 50 );
        }
    };

    private BroadcastReceiver requestReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            new Handler().postDelayed(
                    new Runnable() {
                        @Override
                        public void run() {
                            if(intent.getStringExtra("message").equalsIgnoreCase(CONSTANTS.REQUESTSENT)){
                                viewPager.setCurrentItem( 1 );
                            }
                        }
                    }, 50 );
        }
    };

    public Networking() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_networking, container, false );
        viewPager = (ViewPager) view.findViewById( R.id.viewpager );

        TabLayout tabLayout = (TabLayout) view.findViewById( R.id.tabs );
        tabLayout.setupWithViewPager( viewPager );


//        setAgenda( getActivity() );

        if(getArguments()!=null) {
            speakerDetail = (SpeakerDetail) getArguments().getSerializable( "data" );
//            name = speakerDetail.getFirstName().concat( " " ).concat( speakerDetail.getLastName() );
//            type = speakerDetail.getUserType();
//            id = speakerDetail.getUserID();
        }

        setupViewPager( viewPager );
        LocalBroadcastManager.getInstance( getActivity() ).registerReceiver( mMessageReceiver,
                new IntentFilter( CONSTANTS.SCHEDULE ) );

        LocalBroadcastManager.getInstance( getActivity() ).registerReceiver( requestReceiver,
                new IntentFilter( "myMeeting") );
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
        viewPager.addOnPageChangeListener( new TabLayout.TabLayoutOnPageChangeListener( tabLayout ) );

        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter( getChildFragmentManager() );
        NetworkScheduleFragment scheduleFragment = new NetworkScheduleFragment(  );
        NetworkMeetingFragment myMeetingFragment = new NetworkMeetingFragment(  );
        Bundle bundel=new Bundle(  );
        bundel.putSerializable( "data", speakerDetail );
        scheduleFragment.setArguments( bundel );

        adapter.addFragment( scheduleFragment, "Schedule Meeting" );
        adapter.addFragment( myMeetingFragment, "My Meetings" );
        viewPager.setAdapter( adapter );
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super( manager );
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get( position );
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add( fragment );
            mFragmentTitleList.add( title );
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get( position );
        }
    }

    @Override
    public void onDestroy() {
        // Unregister since the activity is about to be closed.
        LocalBroadcastManager.getInstance( getActivity() ).unregisterReceiver( mMessageReceiver );
        LocalBroadcastManager.getInstance( getActivity() ).unregisterReceiver( requestReceiver );
        super.onDestroy();
    }

}
