package in.kestone.eventbuddy.view.agenda;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import in.kestone.eventbuddy.model.agenda_model.Agenda;
import in.kestone.eventbuddy.model.agenda_model.ModelAgenda;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

//    private final List<Fragment> mFragmentList = new ArrayList<Fragment>();
//    private final ArrayList<String> mFragmentTitleList = new ArrayList<String>();
//    private int tabCount;
//    ModelAgenda modelAgenda;
    private static List<Agenda> mAgenda;

    public ViewPagerAdapter(FragmentManager fragmentManager) {
        super( fragmentManager );
    }

    public ViewPagerAdapter(FragmentManager fragmentManager,List<Agenda> mAgenda) {
        super( fragmentManager );
        this.mAgenda = mAgenda;

    }


    @Override
    public Fragment getItem(int position) {
//        return new AgendaTrackFragment(position,mAgenda.get( position ));
        return new AgendaWithTrackFragment(position,mAgenda.get( position ));
//        return new AgendaTrackFragmentNew(position,mAgenda.get( position ));
    }

//    public void addFrag(Fragment fragment, String title) {
//        mFragmentList.add( fragment );
//        mFragmentTitleList.add( title );
//    }

    @Override
    public int getCount() {
        return mAgenda.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mAgenda.get( position ).getDisplayLabel();
    }
}
