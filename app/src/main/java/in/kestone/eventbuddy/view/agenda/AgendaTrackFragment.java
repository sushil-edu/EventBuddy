package in.kestone.eventbuddy.view.agenda;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.model.agenda_model.AgendaList;
import in.kestone.eventbuddy.model.agenda_model.Track;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class AgendaTrackFragment extends Fragment {

    View view;
    int pos, size;
    List<Track> trackArrayList = new ArrayList<>();
    @BindView(R.id.tabTrack)
    TabLayout tabTrack;
    @BindView(R.id.viewpagerTrack)
    ViewPager pagerTrack;
    List<String> listTrack = new ArrayList<>();

    public AgendaTrackFragment() {
    }

    public AgendaTrackFragment(int i, int size) {
        this.pos = i;
        this.size = size;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate( R.layout.fragment_agenda_track, container, false );
        ButterKnife.bind( this, view );
        trackArrayList.clear();

        trackArrayList.addAll( AgendaList.getAgenda().getAgenda().get( pos ).getTrack() );
        setupViewPager( pagerTrack );
        tabTrack.setupWithViewPager( pagerTrack );
        return view;
    }

    private void setupViewPager(ViewPager viewPager) {
        ChildViewPagerAdapter adapter = new ChildViewPagerAdapter( getChildFragmentManager() );
        for (int i = 0; i < trackArrayList.size(); i++) {
            AgendaListFragment partner = new AgendaListFragment( i, trackArrayList.size() );
            adapter.addFrag( partner, trackArrayList.get( i ).getTrackName() );
        }
        viewPager.setAdapter( adapter );
        adapter.notifyDataSetChanged();

    }

    private class ChildViewPagerAdapter extends FragmentStatePagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<Fragment>();
        private final ArrayList<String> mFragmentTitleList = new ArrayList<String>();


        public ChildViewPagerAdapter(FragmentManager fragmentManager) {
            super( fragmentManager );
        }

        @Override
        public Fragment getItem(int position) {

            return mFragmentList.get( position );
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add( fragment );
            mFragmentTitleList.add( title );
        }

        @Override
        public int getCount() {
            return mFragmentTitleList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get( position );
        }
    }
}
