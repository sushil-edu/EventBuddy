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

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.kestone.eventbuddy.Altdialog.CustomDialog;
import in.kestone.eventbuddy.Altdialog.Progress;
import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.common.CONSTANTS;
import in.kestone.eventbuddy.common.CommonUtils;
import in.kestone.eventbuddy.common.LocalStorage;
import in.kestone.eventbuddy.http.APIClient;
import in.kestone.eventbuddy.http.APIInterface;
import in.kestone.eventbuddy.model.partners_model.PartnerDetail;
import in.kestone.eventbuddy.view.adapter.SingleViewPagerAdapter;
import in.kestone.eventbuddy.view.agenda.ViewPagerAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class PartnerFragment extends Fragment {

    View view;
//    @BindView(R.id.text_title)
//    TextView title;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewpager)
    ViewPager pager;
    int tabCount;
    ArrayList<String> catDetailArrayList = new ArrayList<String>();
    PartnerDetail partnerDetail;
    String type;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate( R.layout.fragment_partner, container, false );
        ButterKnife.bind( this, view );
            if(getArguments()!=null) {
                type = getArguments().getString( "type" );
                getPartner(getArguments().getString( "type" ));
                Progress.showProgress( getContext() );
            }
        return view;
    }

    private void getParents(PartnerDetail partnerDetail) {
        this.partnerDetail = partnerDetail;

            tabCount = partnerDetail.getData().size();
            for (int i = 0; i < tabCount; i++) {
                catDetailArrayList.add( partnerDetail.getData().get( i ).getCategory());
            }

        setupViewPager( pager );
        if (tabCount <= 4)
            tabs.setTabMode( TabLayout.MODE_FIXED );
        else
            tabs.setTabMode( TabLayout.MODE_SCROLLABLE );
        tabs.setupWithViewPager( pager );
    }

    private void setupViewPager(ViewPager viewPager) {
        SingleViewPagerAdapter adapter = new SingleViewPagerAdapter( getFragmentManager() );
        for (int i = 0; i < tabCount; i++) {
            PartnerListFragment partner = new PartnerListFragment( i, partnerDetail.getData().get( i ).getDetail(), type);
            adapter.addFrag( partner, catDetailArrayList.get( i ) );
        }
        viewPager.setAdapter( adapter );
        adapter.notifyDataSetChanged();

    }

    public void getPartner(String type) {
        APIInterface apiInterface = APIClient.getClient().create( APIInterface.class );
        Call<PartnerDetail> call;
        if(type.contains( CONSTANTS.PARTNERS )) {
            call = apiInterface.getPartners( LocalStorage.getEventID( getActivity() ) );
        }else {
            call = apiInterface.getSponsors( LocalStorage.getEventID( getActivity() ) );
        }
        call.enqueue( new Callback<PartnerDetail>() {
            @Override
            public void onResponse(Call<PartnerDetail> call, Response<PartnerDetail> response) {
                if (response.code()==200) {
                    if (response.body().getStatusCode() == 200 && !response.body().getData().isEmpty()) {
                        getParents( response.body() );
                    } else {
                        CustomDialog.showInvalidPopUp( getContext(), CONSTANTS.ERROR, response.body().getMessage() );
                    }
                } else {
                    CustomDialog.showInvalidPopUp( getActivity(), CONSTANTS.ERROR, response.message() );
                }
                Progress.closeProgress();
            }

            @Override
            public void onFailure(Call<PartnerDetail> call, Throwable t) {
                CustomDialog.showInvalidPopUp( getActivity(), CONSTANTS.ERROR, CONSTANTS.CONNECTIONERROR );
                Progress.closeProgress();
            }
        } );

    }

}
