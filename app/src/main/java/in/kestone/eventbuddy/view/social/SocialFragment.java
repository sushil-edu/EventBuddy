package in.kestone.eventbuddy.view.social;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.common.ReadJson;
import in.kestone.eventbuddy.model.social_model.Social;
import in.kestone.eventbuddy.view.agenda.ViewPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class SocialFragment extends Fragment {
    View view;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewpager)
    ViewPager pager;
    Social socialDetailsList;
    ArrayList<String> pageList = new ArrayList<>();
    int tabCount = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate( R.layout.fragment_social, container, false );
        ButterKnife.bind( this, view );
        getSocials();
        return view;
    }

    private void getSocials() {
        socialDetailsList = new Gson().fromJson( ReadJson.loadJSONFromAsset( getActivity(), "social.json" ),
                Social.class );
        if (socialDetailsList.getStatusCode().equalsIgnoreCase( "200" )) {
            tabCount = socialDetailsList.getSocial().get( 0 ).getPages().size();
            for (int i = 0; i < tabCount; i++) {
                if(socialDetailsList.getSocial().get( 0 ).getPages().get( i ).getRequired().equalsIgnoreCase( "yes" )) {
                    pageList.add( socialDetailsList.getSocial().get( 0 ).getPages().get( i ).getTitle() );
                }
            }
        } else {
            Log.e( "Status", String.valueOf( socialDetailsList.getStatusCode() ) );
        }
        setupViewPager( pager );
        if (pageList.size() <= 4)
            tabs.setTabMode( TabLayout.MODE_FIXED );
        else
            tabs.setTabMode( TabLayout.MODE_SCROLLABLE );

        tabs.setupWithViewPager( pager );
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter( getFragmentManager() );
        for (int i = 0; i < pageList.size(); i++) {
            SocialListFragment partner = new SocialListFragment( socialDetailsList.getSocial().get( 0 ).getPages().get( i ).getUrl() );
            adapter.addFrag( partner, pageList.get( i ) );
        }
        viewPager.setAdapter( adapter );
        adapter.notifyDataSetChanged();

    }

}
