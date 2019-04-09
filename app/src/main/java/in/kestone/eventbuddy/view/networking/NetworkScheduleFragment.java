package in.kestone.eventbuddy.view.networking;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.github.badoualy.datepicker.DatePickerTimeline;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.kestone.eventbuddy.Altdialog.CustomDialog;
import in.kestone.eventbuddy.Altdialog.Progress;
import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.common.CONSTANTS;
import in.kestone.eventbuddy.data.SharedPrefsHelper;
import in.kestone.eventbuddy.http.APIClient;
import in.kestone.eventbuddy.http.APIInterface;
import in.kestone.eventbuddy.model.app_config_model.ListEvent;
import in.kestone.eventbuddy.model.app_config_model.Location;
import in.kestone.eventbuddy.model.networking_model.MNetworking;
import in.kestone.eventbuddy.model.networking_model.NetworkingList;
import in.kestone.eventbuddy.model.speaker_model.Speaker;
import in.kestone.eventbuddy.model.speaker_model.SpeakerDetail;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class NetworkScheduleFragment extends Fragment implements View.OnClickListener, SelectedSpeaker {

    @BindView(R.id.typeTv)
    TextView typeTv;
    @BindView(R.id.nameTv)
    TextView nameTv;
    @BindView(R.id.dayTv)
    TextView dayTv;
    @BindView(R.id.monthTv)
    TextView monthTv;
    @BindView(R.id.hourTv)
    TextView hourTv;
    @BindView(R.id.minuteTv)
    TextView minuteTv;
    @BindView(R.id.labelTv)
    TextView labelTv;
    @BindView(R.id.locationNameTv)
    TextView tvLocation;
    @BindView(R.id.speakerRLl)
    RelativeLayout speakerRLl;
    @BindView(R.id.nameRLl)
    RelativeLayout nameRLl;
    @BindView(R.id.LocationRLl)
    RelativeLayout locationRLl;
    @BindView(R.id.meetingRequestBtn)
    Button meetingRequestBtn;
    String dayStr = "", monthStr = "", hoursStr = "", minutesStr = "", name = "";
    String speakerId = "";
    boolean flag = true;// true for schedule and false for reschedule
    String emb_id;
    SpeakerDetail speakerDetail;
    ArrayList<Location> listLocation = new ArrayList<>();
    private ArrayList<SpeakerDetail> speakerList = new ArrayList<>();
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            String message = intent.getStringExtra( "message" );
            Log.d( "Message ", message );
            name = intent.getStringExtra( "name" );
            nameTv.setText( name );
            typeTv.setText( intent.getStringExtra( "type" ) );
            labelTv.setText( intent.getStringExtra( "type" ) );
            emb_id = intent.getStringExtra( "emb_id" );
            speakerId = intent.getStringExtra( "id" );
            nameRLl.setOnClickListener( null );
            speakerRLl.setOnClickListener( null );
            flag = false;

        }
    };

    public NetworkScheduleFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_schedule_meeting, container, false );
        initialiseView( view );
        LocalBroadcastManager.getInstance( getActivity() ).registerReceiver( mMessageReceiver,
                new IntentFilter( CONSTANTS.SCHEDULE ) );
        LocalBroadcastManager.getInstance( getActivity() ).registerReceiver( mMessageReceiver,
                new IntentFilter( CONSTANTS.RESCHEDULE ) );

        listLocation.addAll( ListEvent.getAppConf().getEvent().getnNetworking().getLocation() );

        if (getArguments() != null) {
            Log.e( "Name type ", getArguments().getString( "name" ) + " and " + getArguments().getString( "type" ) );
            speakerDetail = (SpeakerDetail) getArguments().getSerializable( "data" );
            if (speakerDetail != null) {
                nameTv.setText( speakerDetail.getFirstName() + " " + speakerDetail.getLastName() );
                typeTv.setText( speakerDetail.getUserType() );
                speakerId = String.valueOf( speakerDetail.getUserID() );
            }
        }

        return view;
    }

    private void initialiseView(View view) {
        ButterKnife.bind( this, view );
        dayTv.setOnClickListener( this );
        monthTv.setOnClickListener( this );
        hourTv.setOnClickListener( this );
        minuteTv.setOnClickListener( this );
        locationRLl.setOnClickListener( this );

        nameRLl.setOnClickListener( this );
        speakerRLl.setOnClickListener( this );
        meetingRequestBtn.setOnClickListener( this );

//        Log.e("Data ", getArguments().getString( "type" ));
    }


    public void populateCalendar() {

        final Dialog dialog = new Dialog( getContext() );
        dialog.requestWindowFeature( Window.FEATURE_NO_TITLE );
        dialog.setContentView( R.layout.alert_date_time );
        dialog.setCancelable( true );
        DatePickerTimeline datePicker = dialog.findViewById( R.id.datePicker );
        Log.e( "Year ", "" + datePicker.getSelectedMonth() );
        datePicker.setFirstVisibleDate( 2019, 02, 01 );
        datePicker.setLastVisibleDate( 2019, 02, 10 );
        datePicker.setSelectedDate( 0, 0, 0 );
        datePicker.setOnDateSelectedListener( new DatePickerTimeline.OnDateSelectedListener() {
            @Override
            public void onDateSelected(int year, int month, int day, int index) {

                dayTv.setText( day + "" );

                dayStr = day + "";
                monthStr = month + 1 + "";

                String monthStr = new DateFormatSymbols().getMonths()[month];
                monthTv.setText( monthStr.substring( 0, Math.min( monthStr.length(), 3 ) ) );
                dialog.dismiss();
            }
        } );

        dialog.show();

    }

    public void populateTimeAlert() {
        ArrayList<String> hourList = new ArrayList<>();
        ArrayList<String> minuteList = new ArrayList<>();

        for (int i = 1; i <= 24; i++) {
            hourList.add( i < 10 ? "0" + String.valueOf( i ) : String.valueOf( i ) );
        }

        for (int i = 0; i < 4; i++) {
            minuteList.add( i * 15 < 10 ? "0" + String.valueOf( i * 15 ) : String.valueOf( (i * 15) ) );
        }


        final Dialog dialog = new Dialog( getContext() );
        dialog.requestWindowFeature( Window.FEATURE_NO_TITLE );
        dialog.setContentView( R.layout.alert_time_spinner );
        dialog.setCancelable( true );

        final Spinner hourSpinner = dialog.findViewById( R.id.hourSpinner );
        final Spinner minuteSpinner = dialog.findViewById( R.id.minuteSpinner );

        ArrayAdapter adapter = new ArrayAdapter( getContext(), android.R.layout.simple_spinner_dropdown_item, hourList );
        hourSpinner.setAdapter( adapter );

        ArrayAdapter mAdapter = new ArrayAdapter( getContext(), android.R.layout.simple_spinner_dropdown_item, minuteList );
        minuteSpinner.setAdapter( mAdapter );

        TextView mBtnSet = dialog.findViewById( R.id.mBtnSet );
        mBtnSet.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                hourTv.setText( hourSpinner.getSelectedItem().toString() );
                minuteTv.setText( minuteSpinner.getSelectedItem().toString() );

                hoursStr = hourSpinner.getSelectedItem().toString();
                minutesStr = minuteSpinner.getSelectedItem().toString();

                dialog.dismiss();
            }
        } );

        dialog.show();


    }

    public void populateLocation(ArrayList<Location> locationDetails) {
        Dialog dialog = new Dialog( getContext() );
        dialog.requestWindowFeature( Window.FEATURE_NO_TITLE );
        dialog.setContentView( R.layout.alert_user_type );
        TextView headTv = dialog.findViewById( R.id.headTv );
        headTv.setText( "Select Location" );
        RecyclerView recyclerView = dialog.findViewById( R.id.recyclerView );
        recyclerView.setLayoutManager( new LinearLayoutManager( getContext() ) );
        recyclerView.setAdapter( new LocationAdapter( this, locationDetails, dialog ) );
        dialog.show();
    }

    public void populateSpeaker(ArrayList<SpeakerDetail> speakerDetails) {
        Dialog dialog = new Dialog( getContext() );
        dialog.requestWindowFeature( Window.FEATURE_NO_TITLE );
        dialog.setContentView( R.layout.alert_user_type );
        TextView headTv = dialog.findViewById( R.id.headTv );
        headTv.setText( "Select " + typeTv.getText().toString() );
        RecyclerView recyclerView = dialog.findViewById( R.id.recyclerView );
        recyclerView.setLayoutManager( new LinearLayoutManager( getContext() ) );
        recyclerView.setAdapter( new UserSelectAdapter( this, speakerDetails, dialog ) );
        dialog.show();
    }

    public void selectType() {
        final Dialog dialog = new Dialog( getContext() );
        dialog.requestWindowFeature( Window.FEATURE_NO_TITLE );
        dialog.setContentView( R.layout.speaker_delegate_select );
        dialog.setCancelable( true );

        TextView speakerTv = dialog.findViewById( R.id.speakerTV );
        TextView delegatesTv = dialog.findViewById( R.id.delegateTv );

        speakerTv.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeTv.setText( "Speaker" );
                nameTv.setText( "" );
                labelTv.setText( "Select Speaker" );
                dialog.dismiss();
            }
        } );
        delegatesTv.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                typeTv.setText( "Delegate" );
                nameTv.setText( "" );
                labelTv.setText( "Select Delegate" );
                dialog.dismiss();
            }
        } );

        dialog.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.LocationRLl:
                populateLocation( listLocation );
                break;

            case R.id.dayTv:
            case R.id.monthTv:
                populateCalendar();
                break;

            case R.id.minuteTv:
            case R.id.hourTv:
                populateTimeAlert();
                break;

            case R.id.nameRLl:
                getSpeaker( typeTv.getText().toString() );
                break;
            case R.id.speakerRLl:
                selectType();
                break;
            case R.id.meetingRequestBtn:
                if (nameTv.getText().length() > 5) {
                    if (!dayTv.getText().toString().contains( "Day" ) && !monthTv.getText().toString().contains( "Month" )
                            && !hourTv.getText().toString().contains( "Hrs" ) && !minuteTv.getText().toString().contains( "Min" )) {
                        Progress.showProgress( getActivity() );
                        NetworkingList mNetworking = new NetworkingList();
                        mNetworking.setEventID( CONSTANTS.EVENTID );
                        mNetworking.setNetworkingRequestDate( dayTv.getText().toString() + " " + monthTv.getText().toString() );
                        mNetworking.setNetworingRequestTime( hourTv.getText().toString() + ":" + minuteTv.getText().toString() );
                        mNetworking.setNetworkingLocation( tvLocation.getText().toString() );
                        mNetworking.setEBMRID( (long) 1 );
                        if (flag) {
                            mNetworking.setRequestFromID( String.valueOf( new SharedPrefsHelper( getContext() ).getUserId() ) );
                            mNetworking.setRequestToID( String.valueOf( speakerId ) );
                            mNetworking.setIsApproved( "Pending" );
                            mNetworking.setApprovedOn( "08-03-2019" );
                            schedule( mNetworking );
                        } else {
                            mNetworking.setEBMRID( Long.valueOf( emb_id ) );
                            mNetworking.setRequestToID( String.valueOf( new SharedPrefsHelper( getContext() ).getUserId() ) );
                            mNetworking.setRequestFromID( String.valueOf( speakerId ) );
//                            mNetworking.setIsApproved( "Approved" );
                            mNetworking.setApprovedOn( String.valueOf( Calendar.getInstance().getTime() ) );
                            reSchedule( mNetworking );
                        }
                    } else {
                        CustomDialog.showInvalidPopUp( getActivity(), CONSTANTS.ERROR, "Please select date and time" );
                    }
                } else {
                    CustomDialog.showInvalidPopUp( getActivity(), CONSTANTS.ERROR, "Please select speaker/delegates" );
                }
                break;

        }
    }

    @Override
    public void onSelect(String name, long sid, String typ) {
        Log.e( "Type", typ );
        if (typ.contains( "Location" )) {
            tvLocation.setText( name );
        } else {
            speakerId = String.valueOf( sid );
            nameTv.setText( name );
        }
    }

    public void getSpeaker(String type) {
        APIInterface apiInterface = APIClient.getClient().create( APIInterface.class );
        Call<Speaker> call;
        if (type.equalsIgnoreCase( "Speaker" )) {
            call = apiInterface.getAllSpeaker();
        } else {
            call = apiInterface.getAllDelegates();
        }
        call.enqueue( new Callback<Speaker>() {
            @Override
            public void onResponse(Call<Speaker> call, Response<Speaker> response) {
                if (response.code()==200) {
                    if (response.body().getStatusCode() == 200 && response.body().getData().size() > 0) {
                        speakerList.clear();
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            if (response.body().getData().get( i ).getUserID() != new SharedPrefsHelper( getContext() ).getUserId() &&
                                    type.equalsIgnoreCase( response.body().getData().get( i ).getUserType() )) {
                                speakerList.add( response.body().getData().get( i ) );
                            }
                        }

                        populateSpeaker( speakerList );
                        Log.e( "Size ", "" + speakerList.size() );
                    } else {
                        CustomDialog.showInvalidPopUp( getActivity(), CONSTANTS.ERROR, response.body().getMessage() );
                    }
                } else {
                    CustomDialog.showInvalidPopUp( getActivity(), CONSTANTS.ERROR, response.message() );
                }
                Progress.closeProgress();
            }

            @Override
            public void onFailure(Call<in.kestone.eventbuddy.model.speaker_model.Speaker> call, Throwable t) {
                CustomDialog.showInvalidPopUp( getActivity(), CONSTANTS.ERROR, CONSTANTS.CONNECTIONERROR );
                Progress.closeProgress();
            }

        } );
    }

    public void schedule(NetworkingList mNetworking) {
        APIInterface apiInterface = APIClient.getClient().create( APIInterface.class );
        Call<MNetworking> call = apiInterface.schedule( mNetworking );
        call.enqueue( new Callback<MNetworking>() {
            @Override
            public void onResponse(Call<MNetworking> call, Response<MNetworking> response) {

                if (response.code() == 200) {
                    CustomDialog.showValidPopUp( getActivity(), "", response.body().getMessage() );
                }
                else {
                    CustomDialog.showInvalidPopUp( getActivity(), CONSTANTS.ERROR, response.message() );
                }
                Progress.closeProgress();


            }

            @Override
            public void onFailure(Call<MNetworking> call, Throwable t) {
                CustomDialog.showInvalidPopUp( getActivity(), CONSTANTS.ERROR, CONSTANTS.CONNECTIONERROR );
                Progress.closeProgress();
            }
        } );
    }

    public void reSchedule(NetworkingList mNetworking) {
        APIInterface apiInterface = APIClient.getClient().create( APIInterface.class );
        Call<MNetworking> call = apiInterface.reSchedule( mNetworking );
        call.enqueue( new Callback<MNetworking>() {
            @Override
            public void onResponse(Call<MNetworking> call, Response<MNetworking> response) {

                Progress.closeProgress();
                if (response.code() == 200) {
                    CustomDialog.showValidPopUp( getActivity(), "", response.body().getMessage() );
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
