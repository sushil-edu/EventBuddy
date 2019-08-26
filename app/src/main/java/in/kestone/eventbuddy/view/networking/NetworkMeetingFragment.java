package in.kestone.eventbuddy.view.networking;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
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
import in.kestone.eventbuddy.data.SharedPrefsHelper;
import in.kestone.eventbuddy.http.APIClient;
import in.kestone.eventbuddy.http.APIInterface;
import in.kestone.eventbuddy.model.networking_model.MNetworking;
import in.kestone.eventbuddy.model.networking_model.NetworkingList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkMeetingFragment extends Fragment implements View.OnClickListener {
    @BindView(R.id.mScheduledTv)
    TextView mScheduledTv;
    @BindView(R.id.mApproveTv)
    TextView mApproveTv;
    @BindView(R.id.mPendingTv)
    TextView mPendingTv;
    ArrayList<NetworkingList> networkingLists = new ArrayList<>();
    ArrayList<NetworkingList> filterNetworkingLists = new ArrayList<>();
    MyScheduled myScheduled;
    Bundle bundle = new Bundle();
    int userID;
    MNetworking networkingResponse;

    public NetworkMeetingFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_my_meeting, container, false );
        ButterKnife.bind( this, view );

        userID = new SharedPrefsHelper( getActivity() ).getUserId();

        mScheduledTv.setOnClickListener( this );
        mApproveTv.setOnClickListener( this );
        mPendingTv.setOnClickListener( this );


//        myMeeting();
        openFragment();
        return view;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.mScheduledTv:
                myMeeting( CONSTANTS.SCHEDULE );
                Progress.showProgress( getActivity() );
                mScheduledTv.setTextColor( getResources().getColor( R.color.colorPrimary ) );
                mApproveTv.setTextColor( getResources().getColor( R.color.grey ) );
                mPendingTv.setTextColor( getResources().getColor( R.color.grey ) );

                break;

            case R.id.mApproveTv:
                myMeeting( CONSTANTS.APPROVE );
                Progress.showProgress( getActivity() );
                mApproveTv.setTextColor( getResources().getColor( R.color.colorPrimary ) );
                mPendingTv.setTextColor( getResources().getColor( R.color.grey ) );
                mScheduledTv.setTextColor( getResources().getColor( R.color.grey ) );

                break;

            case R.id.mPendingTv:
                myMeeting( CONSTANTS.PENDING );
                Progress.showProgress( getActivity() );
                mPendingTv.setTextColor( getResources().getColor( R.color.colorPrimary ) );
                mScheduledTv.setTextColor( getResources().getColor( R.color.grey ) );
                mApproveTv.setTextColor( getResources().getColor( R.color.grey ) );

                break;

        }

    }


    public void openFragment() //default fragment selected
    {
        mScheduledTv.setTextColor( getResources().getColor( R.color.colorPrimary ) );
        mApproveTv.setTextColor( getResources().getColor( R.color.grey ) );
        mPendingTv.setTextColor( getResources().getColor( R.color.grey ) );
        myMeeting( CONSTANTS.SCHEDULE );
//        Progress.showProgress( getActivity() );
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        openFragment();
//        myMeeting();
//    }

    public void myMeeting(String page) {
        networkingLists.clear();
        APIInterface apiInterface = APIClient.getClient().create( APIInterface.class );
        Call<MNetworking> call = apiInterface.networkingStatus( new SharedPrefsHelper( getContext() ).getUserId() );
        call.enqueue( new Callback<MNetworking>() {
            @Override
            public void onResponse(Call<MNetworking> call, Response<MNetworking> response) {
                Progress.closeProgress();
                if (response.code() == 200) {

                    if (response.body().getStatusCode() == 200 && response.body().getNetworkingList().size() > 0) {
                        networkingResponse = response.body();
                        networkingLists.addAll( networkingResponse.getNetworkingList() );
//                    openFragment();
                        if (page.equalsIgnoreCase( CONSTANTS.SCHEDULE )) {
                            filterNetworkingLists.clear();
                            //list filter for approve page
                            for (int i = 0; i < networkingLists.size(); i++) {
                                if (CONSTANTS.APPROVED.equalsIgnoreCase( networkingLists.get( i ).getIsApproved() ) &&
                                        userID == Integer.parseInt( networkingLists.get( i ).getRequestToID() ) ||
                                        userID == Integer.parseInt( networkingLists.get( i ).getRequestFromID() )) {
                                    filterNetworkingLists.add( networkingLists.get( i ) );
                                }
                            }
                            myScheduled = new MyScheduled();
                            bundle.putString( "type", CONSTANTS.APPROVED );
                            bundle.putString( "page", CONSTANTS.SCHEDULE );
                            bundle.putSerializable( "myMeeting", filterNetworkingLists );
                            myScheduled.setArguments( bundle );
                            getChildFragmentManager().beginTransaction()
                                    .replace( R.id.container, myScheduled )
//                        .addToBackStack( null )
                                    .commit();
                        } else if (page.equalsIgnoreCase( CONSTANTS.APPROVE )) {
                            filterNetworkingLists.clear();
                            //list filter for approve page
                            for (int i = 0; i < networkingLists.size(); i++) {
                                if (userID == Integer.parseInt( networkingLists.get( i ).getRequestToID()) ) {
                                    filterNetworkingLists.add( networkingLists.get( i ) );
                                }
                            }
                            myScheduled = new MyScheduled();
                            bundle.putString( "type", CONSTANTS.PENDING );
                            bundle.putString( "page", CONSTANTS.APPROVE );
                            bundle.putSerializable( "myMeeting", filterNetworkingLists );
                            myScheduled.setArguments( bundle );
                            getChildFragmentManager().beginTransaction()
                                    .replace( R.id.container, myScheduled )
//                        .addToBackStack( null )
                                    .commit();

                        } else if (page.equalsIgnoreCase( CONSTANTS.PENDING )) {
                            filterNetworkingLists.clear();
                            //list filter for pending page
                            for (int i = 0; i < networkingLists.size(); i++) {
                                if (userID == Integer.parseInt( networkingLists.get( i ).getRequestFromID() )) {
                                    filterNetworkingLists.add( networkingLists.get( i ) );
                                }
                            }

                            myScheduled = new MyScheduled();
                            bundle.putString( "type", CONSTANTS.PENDING );
                            bundle.putString( "page", CONSTANTS.PENDING );
                            bundle.putSerializable( "myMeeting", filterNetworkingLists );
                            myScheduled.setArguments( bundle );
                            getChildFragmentManager().beginTransaction()
                                    .replace( R.id.container, myScheduled )
//                        .addToBackStack( null )
                                    .commit();

                        }
                    } else {
//                        CustomDialog.showInvalidPopUp( getActivity(), CONSTANTS.ERROR, response.body().getMessage() );

                    }
                } else {
                    CustomDialog.showInvalidPopUp( getActivity(), CONSTANTS.ERROR, response.message() );
                }

            }

            @Override
            public void onFailure(Call<MNetworking> call, Throwable t) {
                CustomDialog.showInvalidPopUp( getActivity(), CONSTANTS.ERROR, CONSTANTS.CONNECTIONERROR );
                Progress.closeProgress();
            }
        } );

    }
}
