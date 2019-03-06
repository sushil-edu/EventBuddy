package in.kestone.eventbuddy.view.networking;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormatSymbols;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.data.SharedPrefsHelper;
import in.kestone.eventbuddy.model.agenda_model.AgendaList;
import in.kestone.eventbuddy.model.agenda_model.Speaker;

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
    @BindView(R.id.speakerRLl)
    RelativeLayout speakerRLl;
    @BindView(R.id.nameRLl)
    RelativeLayout nameRLl;
    @BindView( R.id.meetingRequestBtn )
    Button meetingRequestBtn;
    String dayStr = "", monthStr = "", hoursStr = "", minutesStr = "";
    long speakerId=999;
    private ArrayList<Speaker> speakerList = new ArrayList<>();
    public NetworkScheduleFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_schedule_meeting, container, false );
        initialiseView( view );

        return view;
    }

    private void initialiseView(View view) {
        ButterKnife.bind( this, view );
        dayTv.setOnClickListener( this );
        monthTv.setOnClickListener( this );
        hourTv.setOnClickListener( this );
        minuteTv.setOnClickListener( this );

        nameRLl.setOnClickListener( this );
        speakerRLl.setOnClickListener( this );
        meetingRequestBtn.setOnClickListener( this );
    }


    public void populateCalendar() {

        final Dialog dialog = new Dialog( getContext() );
        dialog.requestWindowFeature( Window.FEATURE_NO_TITLE );
        dialog.setContentView( R.layout.alert_date_time );
        dialog.setCancelable( true );
        DatePickerTimeline datePicker =  dialog.findViewById( R.id.datePicker );
        Log.e("Year ",""+datePicker.getSelectedMonth());
        datePicker.setFirstVisibleDate( 2014,10, 10 );
        datePicker.setLastVisibleDate( 2014, 10, 15 );
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

        final Spinner hourSpinner =  dialog.findViewById( R.id.hourSpinner );
        final Spinner minuteSpinner =  dialog.findViewById( R.id.minuteSpinner );

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

    public void populateSpeaker() {
        Dialog dialog = new Dialog( getContext() );
        dialog.requestWindowFeature( Window.FEATURE_NO_TITLE );
        dialog.setContentView( R.layout.alert_user_type );
        TextView headTv =  dialog.findViewById( R.id.headTv );
        headTv.setText( "Select " + typeTv.getText().toString() );

//        try {
//            JSONArray jsonArray = new JSONArray(myResponse);
//            for (int i = 0; i < speakerList; i++) {
//
//                JSONObject jsonObject = jsonArray.getJSONObject(i);
//
//                UserData speakerData = new UserData();
//                speakerData.setID(jsonObject.getString("ID"));
//                speakerData.setName(jsonObject.getString("Name"));
//                speakerData.setDesignation(jsonObject.getString("Designation"));
//                speakerData.setOrganization(jsonObject.getString("Organization"));
//                speakerData.setEmailID(jsonObject.getString("EmailID"));
//                speakerData.setMobile(jsonObject.getString("Mobile"));
//                speakerData.setPassportNo(jsonObject.getString("PassportNo"));
//                speakerData.setRegistrationType(jsonObject.getString("RegistrationType"));
//                speakerData.setImageURL(jsonObject.getString("ImageURL"));
//
//                speakerList.add(speakerData);
//
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
        speakerList.clear();
        speakerList.addAll( AgendaList.getAgenda().getAgenda().get( 1 ).getTrack().get( 0 ).getDetails().get( 0 ).getSpeaker() );
        RecyclerView recyclerView =  dialog.findViewById( R.id.recyclerView );
        recyclerView.setLayoutManager( new LinearLayoutManager( getContext() ) );
        recyclerView.setAdapter( new UserSelectAdapter( this, speakerList, dialog ) );
        dialog.show();
    }

    public void selectType() {
        final Dialog dialog = new Dialog( getContext() );
        dialog.requestWindowFeature( Window.FEATURE_NO_TITLE );
        dialog.setContentView( R.layout.speaker_delegate_select );
        dialog.setCancelable( true );

        TextView speakerTv =  dialog.findViewById( R.id.speakerTV );
        TextView delegatesTv =  dialog.findViewById( R.id.delegateTv );

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
            case R.id.dayTv:
            case R.id.monthTv:
                populateCalendar();
                break;

            case R.id.minuteTv:
            case R.id.hourTv:
                populateTimeAlert();
                break;

            case R.id.nameRLl:
                populateSpeaker();
                break;
            case R.id.speakerRLl:
                selectType();
                break;
            case R.id.meetingRequestBtn:
                JSONObject object = new JSONObject();
                try {
                    object.put( "scheduleFrom",new SharedPrefsHelper( getContext() ).getUserId());
                    object.put( "scheduleTo",speakerId);
                    object.put( "scheduleDate",dayTv.getText().toString()+" "+monthTv.getText().toString());
                    object.put( "scheduleTime",hourTv.getText().toString()+":"+minuteTv.getText().toString());
                    object.put( "scheduleBy",typeTv.getText().toString());

                    Log.e("Meeting request ", String.valueOf( object ));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

        }
    }

    @Override
    public void onSelect(String speakerName, long sid) {
        speakerId = sid;
        nameTv.setText( speakerName );
    }
}
