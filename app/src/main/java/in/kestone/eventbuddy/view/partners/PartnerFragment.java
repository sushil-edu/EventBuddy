package in.kestone.eventbuddy.view.partners;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.common.ReadJson;
import in.kestone.eventbuddy.model.partners_model.CategoryDetail;
import in.kestone.eventbuddy.model.partners_model.Detail;
import in.kestone.eventbuddy.model.partners_model.MPartnersSponsors;
import in.kestone.eventbuddy.view.agenda.ViewPagerAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class PartnerFragment extends Fragment {

    View view;
    @BindView(R.id.text_title)
    TextView title;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewpager)
    ViewPager pager;
    MPartnersSponsors partnersSponsors;
    int tabCount;
    ArrayList<MPartnersSponsors> partnersSponsorsArrayList = new ArrayList<>();
    ArrayList<String> catDetailArrayList = new ArrayList<String>();
    Detail detailClass;
    CategoryDetail categoryDetailClass;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate( R.layout.fragment_partner, container, false );
        ButterKnife.bind( this, view );
        getParents();
        return view;
    }

    private void getParents() {
        partnersSponsors = new Gson().fromJson( ReadJson.loadJSONFromAsset( getActivity(), "partner.json" ),
                MPartnersSponsors.class );
        if (partnersSponsors.getStatusCode().equalsIgnoreCase( "200" )) {
            title.setText( partnersSponsors.getDetails().get( 0 ).getHeader() );

//            detailClass = (Detail) partnersSponsors.getDetails();
            tabCount = partnersSponsors.getDetails().get( 0 ).getCategoryDetails().size();
            for (int i = 0; i < tabCount; i++) {
                catDetailArrayList.add( partnersSponsors.getDetails().get( 0 ).getCategoryDetails().get( i ).getName() );
            }
        } else {
            Log.e( "Status", String.valueOf( partnersSponsors.getStatusCode() ) );
        }
        setupViewPager( pager );
        if (tabCount <= 4)
            tabs.setTabMode( TabLayout.MODE_FIXED );
        else
            tabs.setTabMode( TabLayout.MODE_SCROLLABLE );
        tabs.setupWithViewPager( pager );
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter( getFragmentManager() );
        for (int i = 0; i < tabCount; i++) {
            PartnerListFragment partner = new PartnerListFragment( i, partnersSponsors.getDetails().get( 0 ).getCategoryDetails().get( i ).getList() );
            adapter.addFrag( partner, catDetailArrayList.get( i ) );
        }
        viewPager.setAdapter( adapter );
        adapter.notifyDataSetChanged();

    }

}
