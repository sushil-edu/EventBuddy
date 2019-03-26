package in.kestone.eventbuddy.view.networking;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
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
    ArrayList<NetworkingList> networkingLists;
    MyScheduled myScheduled ;
    Bundle bundle = new Bundle(  );

    public NetworkMeetingFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_my_meeting, container, false );
        ButterKnife.bind( this, view );

        mScheduledTv.setOnClickListener( this );
        mApproveTv.setOnClickListener( this );
        mPendingTv.setOnClickListener( this );




        return view;
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.mScheduledTv:
                mScheduledTv.setTextColor( getResources().getColor( R.color.colorPrimary ) );
                mApproveTv.setTextColor( getResources().getColor( R.color.grey ) );
                mPendingTv.setTextColor( getResources().getColor( R.color.grey ) );

                myScheduled = new MyScheduled();
                bundle.putString( "type","scheduled" );
                bundle.putSerializable( "myMeeting",  networkingLists );
                myScheduled.setArguments( bundle );
                getChildFragmentManager().beginTransaction()
                        .replace( R.id.container, myScheduled )
                        .commit();
                myMeeting();
                break;

            case R.id.mApproveTv:
                mApproveTv.setTextColor( getResources().getColor( R.color.colorPrimary ) );
                mPendingTv.setTextColor( getResources().getColor( R.color.grey ) );
                mScheduledTv.setTextColor( getResources().getColor( R.color.grey ) );

                myScheduled = new MyScheduled();
                bundle.putString( "type","approved" );
                bundle.putSerializable( "myMeeting",  networkingLists );
                myScheduled.setArguments( bundle );
                getChildFragmentManager().beginTransaction()
                        .replace( R.id.container, myScheduled )
                        .addToBackStack( null )
                        .commit();
                myMeeting();
                break;

            case R.id.mPendingTv:
                mPendingTv.setTextColor( getResources().getColor( R.color.colorPrimary ) );
                mScheduledTv.setTextColor( getResources().getColor( R.color.grey ) );
                mApproveTv.setTextColor( getResources().getColor( R.color.grey ) );

                myScheduled = new MyScheduled();
                bundle.putString( "type","Pending" );
                bundle.putSerializable( "myMeeting",  networkingLists );
                myScheduled.setArguments( bundle );
                getChildFragmentManager().beginTransaction()
                        .replace( R.id.container, myScheduled )
                        .commit();
                myMeeting();

                break;

        }

    }
    public void myMeeting(){
        APIInterface apiInterface = APIClient.getClient().create( APIInterface.class );
        Call<MNetworking> call = apiInterface.networkingStatus( new SharedPrefsHelper( getContext() ).getUserId() );
        call.enqueue( new Callback<MNetworking>() {
            @Override
            public void onResponse(Call<MNetworking> call, Response<MNetworking> response) {
                if(response.code()==200) {
                    if (response.body().getStatusCode() == 200 && response.body().getNetworkingList().size() > 0) {
                        networkingLists = (ArrayList<NetworkingList>) response.body().getNetworkingList();
//                    openFragment();
                    } else {
                        CustomDialog.showInvalidPopUp( getActivity(), CONSTANTS.ERROR, response.body().getMessage() );

                    }
                }
            }

            @Override
            public void onFailure(Call<MNetworking> call, Throwable t) {
                CustomDialog.showInvalidPopUp( getActivity(), CONSTANTS.ERROR, CONSTANTS.CONNECTIONERROR );
            }
        } );

    }
    public void openFragment() //default fragment selected
    {
        mScheduledTv.setTextColor( getResources().getColor( R.color.colorPrimary ) );
        mApproveTv.setTextColor( getResources().getColor( R.color.grey ) );
        mPendingTv.setTextColor( getResources().getColor( R.color.grey ) );

        myScheduled = new MyScheduled();
        bundle.putString( "type","scheduled" );
        bundle.putSerializable( "myMeeting",  networkingLists );
        myScheduled.setArguments( bundle );
        getChildFragmentManager().beginTransaction()
                .replace( R.id.container, myScheduled )
                .commit();
    }

    @Override
    public void onResume() {
        super.onResume();
        myMeeting();
        openFragment();
    }
}
