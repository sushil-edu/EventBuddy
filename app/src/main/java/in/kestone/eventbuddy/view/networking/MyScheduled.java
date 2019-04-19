package in.kestone.eventbuddy.view.networking;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import in.kestone.eventbuddy.Altdialog.CustomDialog;
import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.common.CONSTANTS;
import in.kestone.eventbuddy.data.SharedPrefsHelper;
import in.kestone.eventbuddy.http.APIClient;
import in.kestone.eventbuddy.http.APIInterface;
import in.kestone.eventbuddy.model.ScheduleStatusResponse;
import in.kestone.eventbuddy.model.networking_model.NetworkingList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyScheduled extends Fragment implements View.OnClickListener, MyMeetingAdapter.StatusUpdate {
    //    @BindView( R.id.btnReschedule )
//    TextView btnReschedule;
    ArrayList<NetworkingList> networkingLists = new ArrayList<>(  );
    ArrayList<NetworkingList> networkingListsStatus = new ArrayList<>();
    RecyclerView recyclerViewMyMeeting;
    String type = "", page="";
    int userID;
    private MyMeetingAdapter myMeetingAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.my_scheduled, container, false );
        initialiseView( view );

        userID = new SharedPrefsHelper( getActivity() ).getUserId();

        if (getArguments() != null) {
            networkingLists = (ArrayList<NetworkingList>) getArguments().getSerializable( "myMeeting" );
            Log.e("Length ", ""+networkingLists.size());
            type = getArguments().getString( "type" );
            page = getArguments().getString( "page" );
            networkingListsStatus.clear();
            if (networkingLists != null) {
                for (int i = 0; i < networkingLists.size(); i++) {
                    if (networkingLists.get( i ).getIsApproved().equalsIgnoreCase( type )) {
                        networkingListsStatus.add( networkingLists.get( i ) );
                    }
                }
            }

            recyclerViewMyMeeting = view.findViewById( R.id.recyclerViewMyMeeting );
            recyclerViewMyMeeting.setLayoutManager( new LinearLayoutManager( getContext() ) );
            recyclerViewMyMeeting.setHasFixedSize( true );
            myMeetingAdapter = new MyMeetingAdapter( getContext(), networkingListsStatus, type, this, userID , page);
            recyclerViewMyMeeting.setAdapter( myMeetingAdapter );
            myMeetingAdapter.notifyDataSetChanged();
        }


        return view;
    }

    private void initialiseView(View view) {
//        ButterKnife.bind( this, view );
//        btnReschedule.setOnClickListener( this );
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnReschedule:
                callSchedule();
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        myMeetingAdapter.notifyDataSetChanged();
    }

    // Send an Intent with an action named "custom-event-name". The Intent sent should
// be received by the ReceiverActivity.
    private void callSchedule() {
        Log.d( "sender", "Broadcasting message" );
        Intent intent = new Intent( "schedule" );
        // You can also include some extra data.
        intent.putExtra( "message", "schedule" );
        LocalBroadcastManager.getInstance( getContext() ).sendBroadcast( intent );
    }

    @Override
    public void onStatusUpdate(String type, long id, int pos) {
        if (type.equalsIgnoreCase( CONSTANTS.APPROVE )) {
            approve( id, pos );
        } else {
            rejectRequest( id, pos );
        }

    }

    private void rejectRequest(Long ebmrid, int pos) {

        APIInterface apiInterface = APIClient.getClient().create( APIInterface.class );
        Call<ScheduleStatusResponse> call = apiInterface.reject( ebmrid );
        call.enqueue( new Callback<ScheduleStatusResponse>() {
            @Override
            public void onResponse(Call<ScheduleStatusResponse> call, Response<ScheduleStatusResponse> response) {
                if (response.code() == 200 && response.body().getStatusCode() == 200) {
                    CustomDialog.showValidPopUp( getContext(), CONSTANTS.SUCCESS, "Request has been rejected" );
                    networkingListsStatus.remove( pos );
                    recyclerViewMyMeeting.removeViewAt( pos );
                    myMeetingAdapter.notifyItemRemoved( pos );
                    myMeetingAdapter.notifyItemRangeChanged( pos, networkingListsStatus.size() );
                    myMeetingAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ScheduleStatusResponse> call, Throwable t) {
                CustomDialog.showInvalidPopUp( getContext(), CONSTANTS.ERROR, CONSTANTS.CONNECTIONERROR );
            }
        } );

    }

    private void approve(Long ebmrid, int pos) {
        APIInterface apiInterface = APIClient.getClient().create( APIInterface.class );
        Call<ScheduleStatusResponse> call = apiInterface.approve( ebmrid );
        call.enqueue( new Callback<ScheduleStatusResponse>() {
            @Override
            public void onResponse(Call<ScheduleStatusResponse> call, Response<ScheduleStatusResponse> response) {

                if (response.code() == 200 && response.body().getStatusCode() == 200) {
                    CustomDialog.showValidPopUp( getContext(), CONSTANTS.SUCCESS, response.body().getMessage() );
                    networkingListsStatus.remove( pos );
                    recyclerViewMyMeeting.removeViewAt( pos );
                    myMeetingAdapter.notifyItemRemoved( pos );
                    myMeetingAdapter.notifyItemRangeChanged( pos, networkingListsStatus.size() );
                    myMeetingAdapter.notifyDataSetChanged();
                } else {
                    CustomDialog.showInvalidPopUp( getActivity(), CONSTANTS.ERROR, response.message() );
                }
            }

            @Override
            public void onFailure(Call<ScheduleStatusResponse> call, Throwable t) {
                CustomDialog.showInvalidPopUp( getContext(), CONSTANTS.ERROR, t.getMessage() );
            }
        } );
    }

}
