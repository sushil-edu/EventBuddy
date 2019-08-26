package in.kestone.eventbuddy.view.social;


import android.os.Bundle;
import androidx.annotation.NonNull;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.kestone.eventbuddy.Altdialog.CustomDialog;
import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.common.CONSTANTS;
import in.kestone.eventbuddy.common.LocalStorage;
import in.kestone.eventbuddy.http.APIClient;
import in.kestone.eventbuddy.http.APIInterface;
import in.kestone.eventbuddy.model.social_model.MSocial;
import in.kestone.eventbuddy.view.adapter.SingleViewPagerAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SocialFragment extends Fragment {
    View view;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.viewpager)
    ViewPager pager;
    MSocial socialDetailsList;
    ArrayList<String> tabList = new ArrayList<>();
    int tabCount = 0;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate( R.layout.fragment_social, container, false );
        ButterKnife.bind( this, view );

        social();
//        notification();
        return view;
    }

    private void getSocials(MSocial mSocial) {
        socialDetailsList = mSocial;
        tabCount = socialDetailsList.getMData().size();
        for (int i = 0; i < tabCount; i++) {
            tabList.add( socialDetailsList.getMData().get( i ).getTitle() );
        }

        setupViewPager( pager );
        tabs.setupWithViewPager( pager );
    }

    private void setupViewPager(ViewPager viewPager) {
        SingleViewPagerAdapter adapter = new SingleViewPagerAdapter( getFragmentManager() );
        for (int i = 0; i < tabList.size(); i++) {
            SocialListFragment partner = new SocialListFragment();
            Bundle bundle = new Bundle();
            bundle.putString( "url", socialDetailsList.getMData().get( i ).getURL() );
            partner.setArguments( bundle );
            adapter.addFrag( partner, tabList.get( i ) );
        }
        viewPager.setAdapter( adapter );
        adapter.notifyDataSetChanged();

    }

    public void social() {
        APIInterface apiInterface = APIClient.getClient().create( APIInterface.class );
        Call<MSocial> call = apiInterface.social( LocalStorage.getEventID( getActivity() ) );
        call.enqueue( new Callback<MSocial>() {
            @Override
            public void onResponse(Call<MSocial> call, Response<MSocial> response) {
                if (response.code() == 200) {
                    if (response.body().getStatusCode() == 200 && !response.body().getMData().isEmpty()) {

                        getSocials( response.body() );
                    } else {
                        CustomDialog.showInvalidPopUp( getActivity(), CONSTANTS.ERROR, response.body().getMessage() );
                    }
                } else {
                    CustomDialog.showInvalidPopUp( getActivity(), CONSTANTS.ERROR, response.message() );
                }
            }

            @Override
            public void onFailure(Call<MSocial> call, Throwable t) {
                CustomDialog.showInvalidPopUp( getActivity(), CONSTANTS.ERROR, CONSTANTS.CONNECTIONERROR );
            }
        } );
    }

    private void notification(){
        Log.d("Refreshed token: " ,""+ FirebaseInstanceId.getInstance().getToken());
        FirebaseMessaging.getInstance().subscribeToTopic("FCM")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "subscribed";
                        if (!task.isSuccessful()) {
                            msg = "Subscription failed";
                        }
                        Log.d("FCM", msg);
                        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                    }
                });

    }

}
