package in.kestone.eventbuddy.view.agenda;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.model.agenda_model.Agenda;
import in.kestone.eventbuddy.model.agenda_model.Track;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class AgendaTrackFragment extends Fragment {

    View view;
    int pos;
    List<Track> trackArrayList = new ArrayList<>();
    TabLayout tabTrack;
    ViewPager pagerTrack;
    Agenda modelAgenda;
    int pTabPos;


    public AgendaTrackFragment(int pos, Agenda modelAgenda) {
        this.pos = pos;
        this.modelAgenda = modelAgenda;
    }

    public AgendaTrackFragment() {

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate( R.layout.fragment_agenda_track, container, false );
        super.onCreate( savedInstanceState );
//        ButterKnife.bind( this, view );
        tabTrack = view.findViewById( R.id.tabTrack );
        pagerTrack = view.findViewById( R.id.viewpagerTrack );
        trackArrayList.clear();
        if (modelAgenda.getTrack().size() > 0) {
            trackArrayList = (modelAgenda.getTrack());

            setupViewPager( pagerTrack );
            tabTrack.setupWithViewPager( pagerTrack );
        } else {
            tabTrack.setVisibility( View.GONE );
        }
        tabTrack.addOnTabSelectedListener( new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pagerTrack.setCurrentItem( tab.getPosition() );
                Log.e( "Child Tab", "" + tab.getPosition() );
//                new AgendaListFragment( pTabPos, tab.getPosition() );
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        } );

        pagerTrack.addOnPageChangeListener( new TabLayout.TabLayoutOnPageChangeListener( tabTrack ) );


//        pagerTrack.addOnPageChangeListener( new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int i, float v, int i1) {
//
//            }
//
//            @Override
//            public void onPageSelected(int cTabPos) {
//               new AgendaListFragment(pTabPos, cTabPos);
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int i) {
//            }
//        } );
        return view;
    }


    private void setupViewPager(ViewPager viewPager) {
        ChildViewPagerAdapter adapter = new ChildViewPagerAdapter( getChildFragmentManager(), modelAgenda.getTrack() );
        viewPager.setAdapter( adapter );
        adapter.notifyDataSetChanged();

    }


    private class ChildViewPagerAdapter extends FragmentStatePagerAdapter {

        List<Track> tracks;

        public ChildViewPagerAdapter(FragmentManager fragmentManager, List<Track> track) {
            super( fragmentManager );
            this.tracks = track;
        }


        @Override
        public Fragment getItem(int position) {
            return new AgendaListFragment( position, tracks.get( position ) );
        }

        @Override
        public int getCount() {
            return tracks.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tracks.get( position ).getTrackName();
        }
    }
}
