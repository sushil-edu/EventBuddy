package in.kestone.eventbuddy.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.kestone.eventbuddy.Altdialog.CustomDialog;
import in.kestone.eventbuddy.Altdialog.Progress;
import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.common.CONSTANTS;
import in.kestone.eventbuddy.data.SharedPrefsHelper;
import in.kestone.eventbuddy.http.APIClient;
import in.kestone.eventbuddy.http.APIInterface;
import in.kestone.eventbuddy.model.polling_model.MDatum;
import in.kestone.eventbuddy.model.polling_model.Polling;
import in.kestone.eventbuddy.model.polling_model.PostRequest;
import in.kestone.eventbuddy.model.polling_model.Session;
import in.kestone.eventbuddy.view.adapter.KnowledgeAdapter;
import in.kestone.eventbuddy.view.adapter.PollingAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PollFragment extends Fragment implements PollingAdapter.Selected, KnowledgeAdapter.Selected {

    APIInterface apiInterface;

    @BindView(R.id.layoutTrack)
    RelativeLayout layoutTrack;
    @BindView(R.id.layoutSpeaker)
    RelativeLayout layoutSpeaker;
    @BindView(R.id.spinnerTrack)
    Spinner spinnerTrack;
    @BindView(R.id.spinnerSpeaker)
    Spinner spinnerSpeaker;
    @BindView(R.id.trackTv)
    TextView trackTv;
    @BindView(R.id.speakerTv)
    TextView speakerTv;
    @BindView(R.id.layoutDetails)
    LinearLayout layoutDetails;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    PostRequest postRequest;
    @BindView(R.id.layout_poll)
    LinearLayout layout_poll;
    @BindView(R.id.optionA)
    TextView optionA;
    @BindView(R.id.optionB)
    TextView optionB;
    @BindView(R.id.optionC)
    TextView optionC;
    @BindView(R.id.optionD)
    TextView optionD;
    View view;
    String type, option = null;
    ArrayList<MDatum> lisPolling = new ArrayList<>();
    ArrayList<Session> listSession = new ArrayList<>();
    long sessionId, trackId;

    public PollFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate( R.layout.fragment_poll, container, false );
        initialisation( view );
        return view;
    }

    private void initialisation(View view) {
        ButterKnife.bind( this, view );
        apiInterface = APIClient.getClient().create( APIInterface.class );
        if (getArguments() != null) {
            type = getArguments().getString( "type" );
            if (type.equalsIgnoreCase( CONSTANTS.POLLING )) {
                layout_poll.setVisibility( View.VISIBLE );
                speakerTv.setText( "Select Session" );
                getPolling();
                Progress.showProgress( getContext() );
            }
        }
    }

    @OnClick({R.id.layoutTrack, R.id.layoutSpeaker, R.id.btnSubmit, R.id.optionA, R.id.optionB, R.id.optionC, R.id.optionD})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.layoutTrack:
                populatePollingTrack( lisPolling );
                break;
            case R.id.layoutSpeaker:
                if (lisPolling.size() > 0) {
                    for (int i = 0; i < lisPolling.size(); i++) {
                        if (trackTv.getText().toString().equals( lisPolling.get( i ).getTrackName() ) &&
                                lisPolling.get( i ).getSession().size() > 0) {
                            listSession.addAll( lisPolling.get( i ).getSession() );
                            populateSession( listSession );
                        } else {
                            speakerTv.setText( "Select Session" );
                        }
                    }
                }

                break;
            case R.id.btnSubmit:
                if (trackId != 0 && sessionId != 0 && option != null) {
                    PostRequest postRequest = new PostRequest();
                    postRequest.setResponse( option );
                    postRequest.setSessionID( sessionId );
                    postRequest.setDelegateID( (long) new SharedPrefsHelper( getContext() ).getUserId() );
                    postRequest.setEventID( CONSTANTS.EVENTID );
                    postRequest.setTrackID( trackId );
                    postPolling( postRequest );
                    Progress.showProgress( getContext() );
                } else {
                    CustomDialog.showInvalidPopUp( getActivity(), CONSTANTS.ERROR, "Select option" );
                }

                break;

            case R.id.optionA:
                optionA.setBackgroundColor( ContextCompat.getColor( getContext(), R.color.colorPrimary ) );
                optionB.setBackgroundDrawable( getResources().getDrawable( R.drawable.outline ) );
                optionC.setBackgroundDrawable( getResources().getDrawable( R.drawable.outline ) );
                optionD.setBackgroundDrawable( getResources().getDrawable( R.drawable.outline ) );
                optionA.setTextColor( ContextCompat.getColor( getContext(), R.color.white ) );
                optionB.setTextColor( ContextCompat.getColor( getContext(), R.color.black ) );
                optionC.setTextColor( ContextCompat.getColor( getContext(), R.color.black ) );
                optionD.setTextColor( ContextCompat.getColor( getContext(), R.color.black ) );
                option = "A";
                break;
            case R.id.optionB:
                optionB.setBackgroundColor( ContextCompat.getColor( getContext(), R.color.colorPrimary ) );
                optionA.setBackgroundDrawable( getResources().getDrawable( R.drawable.outline ) );
                optionC.setBackgroundDrawable( getResources().getDrawable( R.drawable.outline ) );
                optionD.setBackgroundDrawable( getResources().getDrawable( R.drawable.outline ) );
                optionB.setTextColor( ContextCompat.getColor( getContext(), R.color.white ) );
                optionA.setTextColor( ContextCompat.getColor( getContext(), R.color.black ) );
                optionC.setTextColor( ContextCompat.getColor( getContext(), R.color.black ) );
                optionD.setTextColor( ContextCompat.getColor( getContext(), R.color.black ) );
                option = "B";
                break;
            case R.id.optionC:
                optionC.setBackgroundColor( ContextCompat.getColor( getContext(), R.color.colorPrimary ) );
                optionA.setBackgroundDrawable( getResources().getDrawable( R.drawable.outline ) );
                optionB.setBackgroundDrawable( getResources().getDrawable( R.drawable.outline ) );
                optionD.setBackgroundDrawable( getResources().getDrawable( R.drawable.outline ) );
                optionC.setTextColor( ContextCompat.getColor( getContext(), R.color.white ) );
                optionB.setTextColor( ContextCompat.getColor( getContext(), R.color.black ) );
                optionA.setTextColor( ContextCompat.getColor( getContext(), R.color.black ) );
                optionD.setTextColor( ContextCompat.getColor( getContext(), R.color.black ) );
                option = "C";
                break;
            case R.id.optionD:
                optionD.setBackgroundColor( ContextCompat.getColor( getContext(), R.color.colorPrimary ) );
                optionB.setBackgroundDrawable( getResources().getDrawable( R.drawable.outline ) );
                optionC.setBackgroundDrawable( getResources().getDrawable( R.drawable.outline ) );
                optionA.setBackgroundDrawable( getResources().getDrawable( R.drawable.outline ) );
                optionD.setTextColor( ContextCompat.getColor( getContext(), R.color.white ) );
                optionB.setTextColor( ContextCompat.getColor( getContext(), R.color.black ) );
                optionC.setTextColor( ContextCompat.getColor( getContext(), R.color.black ) );
                optionA.setTextColor( ContextCompat.getColor( getContext(), R.color.black ) );
                option = "D";
                break;
        }
    }

    public void populatePollingTrack(ArrayList<MDatum> details) {
        Dialog dialog = new Dialog( getContext() );
        dialog.requestWindowFeature( Window.FEATURE_NO_TITLE );
        dialog.setContentView( R.layout.alert_user_type );
        TextView headTv = dialog.findViewById( R.id.headTv );
        headTv.setText( "Select Track" );
        RecyclerView recyclerView = dialog.findViewById( R.id.recyclerView );
        recyclerView.setLayoutManager( new LinearLayoutManager( getContext() ) );
        recyclerView.setAdapter( new PollingAdapter( this, details, dialog, type ) );
        dialog.show();
    }

    public void populateSession(ArrayList<Session> details) {
        Dialog dialog = new Dialog( getContext() );
        dialog.requestWindowFeature( Window.FEATURE_NO_TITLE );
        dialog.setContentView( R.layout.alert_user_type );
        TextView headTv = dialog.findViewById( R.id.headTv );
        headTv.setText( "Select Session" );
        RecyclerView recyclerView = dialog.findViewById( R.id.recyclerView );
        recyclerView.setLayoutManager( new LinearLayoutManager( getContext() ) );
        recyclerView.setAdapter( new KnowledgeAdapter( this, details, dialog, type ) );
        dialog.show();
    }

    public void getPolling() {

        Call<Polling> call = apiInterface.getPolling( CONSTANTS.EVENTID );
        call.enqueue( new Callback<Polling>() {
            @Override
            public void onResponse(Call<Polling> call, Response<Polling> response) {

                if (response.body().getStatusCode() == 200 && response.body().getMData().size() > 0 && response.code() == 200) {
                    lisPolling = (ArrayList<MDatum>) response.body().getMData();


                } else {
                    CustomDialog.showInvalidPopUp( getActivity(), CONSTANTS.ERROR, response.body().getMessage() );
                }
                Progress.closeProgress();
            }

            @Override
            public void onFailure(Call<Polling> call, Throwable t) {
                Progress.closeProgress();
                CustomDialog.showInvalidPopUp( getActivity(), CONSTANTS.ERROR, CONSTANTS.CONNECTIONERROR );
            }
        } );

    }

    @Override
    public void onSelect(String trackName, long id, String title) {

        if (title.equalsIgnoreCase( "track" )) {
            trackTv.setText( trackName );
            trackId = id;
        } else {
            speakerTv.setText( trackName );
            sessionId = id;
        }
    }

    private void postPolling(PostRequest postRequest) {
        Call<JSONObject> call = apiInterface.postPolling( postRequest );
        call.enqueue( new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                if (response.code() == 201) {
                    CustomDialog.showValidPopUp( getContext(), "Success", "Request send" );
                    optionD.setBackgroundDrawable( getResources().getDrawable( R.drawable.outline ) );
                    optionB.setBackgroundDrawable( getResources().getDrawable( R.drawable.outline ) );
                    optionC.setBackgroundDrawable( getResources().getDrawable( R.drawable.outline ) );
                    optionA.setBackgroundDrawable( getResources().getDrawable( R.drawable.outline ) );
                    optionD.setTextColor( ContextCompat.getColor( getContext(), R.color.black ) );
                    optionB.setTextColor( ContextCompat.getColor( getContext(), R.color.black ) );
                    optionC.setTextColor( ContextCompat.getColor( getContext(), R.color.black ) );
                    optionA.setTextColor( ContextCompat.getColor( getContext(), R.color.black ) );
                    trackTv.setText( "Select track" );
                    speakerTv.setText( "Select Session" );

                } else {
                    CustomDialog.showInvalidPopUp( getActivity(), CONSTANTS.ERROR, "Request not send" );
                }
                Progress.closeProgress();
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                Progress.closeProgress();
                CustomDialog.showInvalidPopUp( getActivity(), CONSTANTS.ERROR, CONSTANTS.CONNECTIONERROR );
            }
        } );
    }

}
