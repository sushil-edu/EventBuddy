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

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.kestone.eventbuddy.Altdialog.CustomDialog;
import in.kestone.eventbuddy.Altdialog.Progress;
import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.common.CONSTANTS;
import in.kestone.eventbuddy.common.CommonUtils;
import in.kestone.eventbuddy.common.CompareDateTime;
import in.kestone.eventbuddy.data.SharedPrefsHelper;
import in.kestone.eventbuddy.http.APIClient;
import in.kestone.eventbuddy.http.APIInterface;
import in.kestone.eventbuddy.model.app_config_model.ConfNetworking;
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
    Long emb_id;
    SpeakerDetail speakerDetail;
    ArrayList<Location> listLocation = new ArrayList<>();
    DateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss aa" );
    String insertedDate = String.valueOf( dateFormat.format( Calendar.getInstance().getTime() ) );
    //for delegate
    String activationDateForDelegateFrom;
    String activationDateForDelegateTo;
    String activationTimeForDelegateFrom;
    String activationTimeForDelegateTo;
    //for speaker
    String activationDateForSpeakerFrom;
    String activationDateForSpeakerTo;
    String activationTimeForSpeakerFrom;
    String activationTimeForSpeakerTo;
    //current date and time
    SimpleDateFormat dateFormatC = new SimpleDateFormat( "yyyy-MM-dd" );
    //    SimpleDateFormat timeFormat = new SimpleDateFormat( "h:mm a" );
    SimpleDateFormat timeFormat = new SimpleDateFormat( "kk:mm" );
    String strCurrentDate = dateFormatC.format( Calendar.getInstance().getTime() );
    String strCurrentTime = timeFormat.format( new Date() );
    String delegateErrorHeader, delegateErrorMsg, speakerErrorHeader, speakerErrorMsg;
    int slot;
    Date currentDate, currentTime;
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
            emb_id = intent.getLongExtra( "emb_id", 0 );
            speakerId = intent.getStringExtra( "id" );
            tvLocation.setText( intent.getStringExtra( "location" ) );
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
            speakerDetail = (SpeakerDetail) getArguments().getSerializable( "data" );
            if(speakerDetail!=null) {
//            Log.e( "Name type ", speakerDetail.getFirstName()+"--"+getArguments().getString( "name" ) + " and " + getArguments().getString( "type" ) );
                nameTv.setText( speakerDetail.getFirstName().concat( " ".concat( speakerDetail.getLastName() ) ) );
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

        ConfNetworking networkingList = ListEvent.getAppConf().getEvent().getnNetworking();
        activationDateForDelegateFrom = networkingList.getDelegateNetworkingDateFrom();
        activationDateForDelegateTo = networkingList.getDelegateNetworkingDateTo();
        activationTimeForDelegateFrom = networkingList.getDelegateNetworkingTimeFrom();
        activationTimeForDelegateTo = networkingList.getDelegateNetworkingTimeTo();
        delegateErrorHeader = networkingList.getNetworkingAlertMsgHeaderWithinDelegates();
        delegateErrorMsg = networkingList.getNetworkingAlertMsgWithinDelegates();

        //for speaker
        activationDateForSpeakerFrom = networkingList.getSpeakerNetworkingDateFrom();
        activationDateForSpeakerTo = networkingList.getSpeakerNetworkingDateTo();
        activationTimeForSpeakerFrom = networkingList.getSpeakerNetworkingTimeFrom();
        activationTimeForSpeakerTo = networkingList.getSpeakerNetworkingTimeTo();
        speakerErrorHeader = networkingList.getNetworkingAlertMsgHeaderWithinSpeaker();
        speakerErrorMsg = networkingList.getNetworkingAlertMsgWithSpeaker();

        slot=30;// = Integer.parseInt( ListEvent.getAppConf().getEvent().getnNetworking().getNetworkingRequestSlotDuration() );
//        Log.e("Data ", getArguments().getString( "type" ));

        try {
            currentDate = dateFormatC.parse( strCurrentDate );
            currentTime = timeFormat.parse( strCurrentTime );
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


    public void populateCalendar() {
        String []dt,dtEnd;
        final Dialog dialog = new Dialog( getContext() );
        dialog.requestWindowFeature( Window.FEATURE_NO_TITLE );
        dialog.setContentView( R.layout.alert_date_time );
        dialog.setCancelable( true );
        DatePickerTimeline datePicker = dialog.findViewById( R.id.datePicker );
        Log.e( "Year ", "" + datePicker.getSelectedMonth() );
        if(typeTv.getText().toString().equalsIgnoreCase( "Speaker" )){
            dt = activationDateForSpeakerFrom.split( "-" );
            dtEnd = activationDateForSpeakerTo.split( "-" );
            datePicker.setFirstVisibleDate( Integer.parseInt( dt[0] ), Integer.parseInt( dt[1] )-1, Integer.parseInt( dt[2] ) );
            datePicker.setLastVisibleDate( Integer.parseInt( dtEnd[0] ), Integer.parseInt( dtEnd[1] )-1, Integer.parseInt( dtEnd[2] ) );
        }else if(typeTv.getText().toString().equalsIgnoreCase( "Delegate" )){
            dt = activationDateForDelegateFrom.split( "-" );
            dtEnd = activationDateForDelegateTo.split( "-" );
            datePicker.setFirstVisibleDate( Integer.parseInt( dt[0] ), Integer.parseInt( dt[1] )-1, Integer.parseInt( dt[2] ) );
            datePicker.setLastVisibleDate( Integer.parseInt( dtEnd[0] ), Integer.parseInt( dtEnd[1] )-1, Integer.parseInt( dtEnd[2] ) );
        }

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


        for (int i = 0; i < 60 / slot; i++) {
            minuteList.add( i * slot < 10 ? "0" + String.valueOf( i * slot ) : String.valueOf( (i * slot) ) );
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
                if(typeTv.getText().toString().equalsIgnoreCase( "Speaker" ) ||
                        typeTv.getText().toString().equalsIgnoreCase( "Delegate" )) {
                    populateCalendar();
                }
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
                if (typeTv.getText().length() > 5) {
                    if (!dayTv.getText().toString().contains( "Day" ) && !monthTv.getText().toString().contains( "Month" )
                            && !hourTv.getText().toString().contains( "Hrs" ) && !minuteTv.getText().toString().contains( "Min" )) {

                        String requestTime = hourTv.getText().toString().concat( ":" ).concat( minuteTv.getText().toString() );
                        String requestDate = dayTv.getText().toString().concat( ":" ).concat( monthTv.getText().toString() );
                        //compare date time for delegate/speaker
                        if (typeTv.getText().toString().equalsIgnoreCase( "Delegate" )) {
                            try {

                                if (CompareDateTime.compareDate( activationDateForDelegateFrom, activationDateForDelegateTo )) {
                                    if (CompareDateTime.compareTime( activationTimeForDelegateFrom, activationTimeForDelegateTo ) &&
                                            CompareDateTime.compareTime( requestTime, requestTime )) {

                                        Progress.showProgress( getActivity() );
                                        NetworkingList mNetworking = new NetworkingList();
                                        mNetworking.setEventID( CONSTANTS.EVENTID );
                                        mNetworking.setNetworkingRequestDate( requestDate );
                                        mNetworking.setNetworingRequestTime(requestTime );
                                        mNetworking.setNetworkingLocation( tvLocation.getText().toString() );
                                        mNetworking.setLocation( tvLocation.getText().toString() );
//                        mNetworking.setEBMRID( (long) 1 );
                                        if (flag) {
                                            mNetworking.setRequestFromID( String.valueOf( new SharedPrefsHelper( getContext() ).getUserId() ) );
                                            mNetworking.setRequestToID( String.valueOf( speakerId ) );
                                            mNetworking.setIsApproved( CONSTANTS.PENDING );
                                            mNetworking.setApprovedOn( insertedDate );
                                            schedule( mNetworking );
                                        } else {
                                            mNetworking.setEBMRID( emb_id );
                                            mNetworking.setRequestToID( String.valueOf( new SharedPrefsHelper( getContext() ).getUserId() ) );
                                            mNetworking.setRequestFromID( String.valueOf( speakerId ) );
                                            mNetworking.setIsApproved( CONSTANTS.PENDING );
                                            mNetworking.setApprovedOn( insertedDate );
                                            reSchedule( mNetworking );
                                        }

                                    } else {
                                        CustomDialog.showInvalidPopUp( getActivity(), delegateErrorHeader, delegateErrorHeader );
                                    }

                                } else {
                                    CustomDialog.showInvalidPopUp( getActivity(), delegateErrorHeader, delegateErrorHeader );
                                }
                            } catch (Exception ex) {
                                Log.e( "Excep ", ex.getMessage() );
                            }
                        } else if (typeTv.getText().toString().equalsIgnoreCase( "Speaker" )) {
                            try {
                                if (CompareDateTime.compareDate( activationDateForSpeakerFrom, activationDateForSpeakerTo )) {
                                    if (CompareDateTime.compareTime( activationTimeForSpeakerFrom, activationTimeForSpeakerTo ) &&
                                            CompareDateTime.compareTime( requestTime, requestTime )) {

                                        Progress.showProgress( getActivity() );
                                        NetworkingList mNetworking = new NetworkingList();
                                        mNetworking.setEventID( CONSTANTS.EVENTID );
                                        mNetworking.setNetworkingRequestDate( requestDate );
                                        mNetworking.setNetworingRequestTime( requestTime );
                                        mNetworking.setNetworkingLocation( tvLocation.getText().toString() );
                                        mNetworking.setLocation( tvLocation.getText().toString() );
                                        //                        mNetworking.setEBMRID( (long) 1 );
                                        if (flag) {
                                            mNetworking.setRequestFromID( String.valueOf( new SharedPrefsHelper( getContext() ).getUserId() ) );
                                            mNetworking.setRequestToID( String.valueOf( speakerId ) );
                                            mNetworking.setIsApproved( CONSTANTS.PENDING );
                                            mNetworking.setApprovedOn( insertedDate );
                                            schedule( mNetworking );
                                        } else {
                                            mNetworking.setEBMRID( emb_id );
                                            mNetworking.setRequestToID( String.valueOf( new SharedPrefsHelper( getContext() ).getUserId() ) );
                                            mNetworking.setRequestFromID( String.valueOf( speakerId ) );
                                            mNetworking.setIsApproved( CONSTANTS.PENDING );
                                            mNetworking.setApprovedOn( insertedDate );
                                            reSchedule( mNetworking );
                                        }
                                    } else {
                                        CustomDialog.showInvalidPopUp( getActivity(), speakerErrorHeader, speakerErrorMsg );
                                    }

                                } else {
                                    CustomDialog.showInvalidPopUp( getActivity(), speakerErrorHeader, speakerErrorMsg );
                                }
                            } catch (Exception ex) {
                                Log.e( "Excep ", ex.getMessage() );
                            }
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
            call = apiInterface.getAllSpeaker((int)CONSTANTS.EVENTID);
        } else {
            call = apiInterface.getAllDelegates((int)CONSTANTS.EVENTID);
        }
        call.enqueue( new Callback<Speaker>() {
            @Override
            public void onResponse(Call<Speaker> call, Response<Speaker> response) {
                if (response.code() == 200) {
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

    public void schedule(NetworkingList mNetworking)  {
        APIInterface apiInterface = APIClient.getClient().create( APIInterface.class );
        Call<MNetworking> call = apiInterface.schedule( mNetworking );
        call.enqueue( new Callback<MNetworking>() {
            @Override
            public void onResponse(Call<MNetworking> call, Response<MNetworking> response) {

                if (response.code() == 200) {
                    CustomDialog.showValidPopUp( getActivity(), "", response.body().getMessage() );
                } else {
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
