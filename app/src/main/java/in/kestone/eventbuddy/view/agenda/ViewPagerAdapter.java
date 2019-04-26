package in.kestone.eventbuddy.view.agenda;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

import in.kestone.eventbuddy.model.agenda_model.Agenda;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private static List<Agenda> mAgenda;


    public ViewPagerAdapter(FragmentManager fragmentManager, List<Agenda> mAgenda) {
        super( fragmentManager );
        this.mAgenda = mAgenda;

    }


    @Override
    public Fragment getItem(int position) {
        return new AgendaWithTrackFragment( position, mAgenda.get( position ) );

    }

    @Override
    public int getCount() {
        return mAgenda.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mAgenda.get( position ).getDisplayLabel();
    }
}
