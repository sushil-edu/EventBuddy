package in.kestone.eventbuddy.view.agenda;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

import in.kestone.eventbuddy.model.agenda_model.Agenda;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private static List<Agenda> mAgenda;


    ViewPagerAdapter(FragmentManager fragmentManager, List<Agenda> mAgenda) {
        super(fragmentManager);
        ViewPagerAdapter.mAgenda = mAgenda;

    }


    @Override
    public Fragment getItem(int position) {
        return new AgendaWithTrackFragment(position, mAgenda.get(position));

    }

    @Override
    public int getCount() {
        return mAgenda.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mAgenda.get(position).getDisplayLabel();
    }
}
