package in.kestone.eventbuddy.view.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SingleViewPagerAdapter extends FragmentStatePagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<Fragment>();
    private final ArrayList<String> mFragmentTitleList = new ArrayList<String>();

    public SingleViewPagerAdapter(FragmentManager fragmentManager) {
        super( fragmentManager );
    }


    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
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
