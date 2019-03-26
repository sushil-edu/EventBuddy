package in.kestone.eventbuddy.fragment;

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
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.kestone.eventbuddy.Altdialog.CustomDialog;
import in.kestone.eventbuddy.Altdialog.Progress;
import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.common.CONSTANTS;
import in.kestone.eventbuddy.http.APIClient;
import in.kestone.eventbuddy.http.APIInterface;
import in.kestone.eventbuddy.model.knowledgebase_model.Datum;
import in.kestone.eventbuddy.model.knowledgebase_model.Knowledge;
import in.kestone.eventbuddy.model.polling_model.MDatum;
import in.kestone.eventbuddy.model.polling_model.Session;
import in.kestone.eventbuddy.model.qanda_model.Track;
import in.kestone.eventbuddy.model.speaker_model.SpeakerDetail;
import in.kestone.eventbuddy.view.adapter.KnowledgeAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static in.kestone.eventbuddy.R.id;
import static in.kestone.eventbuddy.R.layout;

public class KnowlegdeBaseFragment extends Fragment implements KnowledgeSpeakerAdapter.Selected, KnowledgeAdapter.Selected {

    View view;
    String type;
    ArrayList<Datum> lisKnowledge = new ArrayList<>();
    ArrayList<MDatum> lisPolling = new ArrayList<>();
    ArrayList<Session> listSession = new ArrayList<>();
    ArrayList<SpeakerDetail> listSpeaker = new ArrayList<>();
    ArrayList<Track> listQA = new ArrayList<>();
    ArrayList<in.kestone.eventbuddy.model.qanda_model.Session> listQASession = new ArrayList<>();
    APIInterface apiInterface;

    @BindView(id.layoutTrack)
    RelativeLayout layoutTrack;
    @BindView(id.layoutSpeaker)
    RelativeLayout layoutSpeaker;
    @BindView(id.spinnerTrack)
    Spinner spinnerTrack;
    @BindView(id.spinnerSpeaker)
    Spinner spinnerSpeaker;
    @BindView(id.trackTv)
    TextView trackTv;
    @BindView(id.speakerTv)
    TextView speakerTv;
    int flag = 0;

    public KnowlegdeBaseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate( R.layout.fragment_knowlegde_base, container, false );
        initialiseView( view );
        apiInterface = APIClient.getClient().create( APIInterface.class );
        if (getArguments() != null) {
            type = getArguments().getString( "type" );
            if (type.equalsIgnoreCase( CONSTANTS.KNOWLEDGEBASE )) {
                getKnowledgeDetail();
                Progress.showProgress( getContext() );
            }
        }

        return view;
    }

    private void initialiseView(View view) {
        ButterKnife.bind( this, view );
        layoutTrack.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lisKnowledge.size() > 0 && type.equalsIgnoreCase( CONSTANTS.KNOWLEDGEBASE )) {
                    populateTrack( lisKnowledge );
                }
            }
        } );
        layoutSpeaker.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lisKnowledge.size() > 0 && type.equalsIgnoreCase( CONSTANTS.KNOWLEDGEBASE )) {
                    for (int i = 0; i < lisKnowledge.size(); i++) {
                        if (trackTv.getText().toString().equals( lisKnowledge.get( i ).getTrackName() ) &&
                                lisKnowledge.get( i ).getSpeakerList().size() > 0) {
                            listSpeaker.addAll( lisKnowledge.get( i ).getSpeakerList() );
                            populateSpeaker( listSpeaker );
                        }else {
                            speakerTv.setText( "Select Speaker" );
                        }

                    }
                }
            }
        } );

    }


    public void getKnowledgeDetail() {
        Call<Knowledge> call = apiInterface.getKnowledgeBase( CONSTANTS.EVENTID );
        call.enqueue( new Callback<Knowledge>() {
            @Override
            public void onResponse(Call<Knowledge> call, Response<Knowledge> response) {
                Log.e( "Data ", "" + response.body().getData().size() );
                if (response.body().getStatusCode() == 200 && response.body().getData().size() > 0 && response.code() == 200) {
                    lisKnowledge = (ArrayList<Datum>) response.body().getData();
                    for (int i = 0; i < response.body().getData().size(); i++) {
                        if (response.body().getData().get( i ).getSpeakerList().size() > 0) {
                            layoutSpeaker.setVisibility( View.VISIBLE );

                        } else {
                            layoutSpeaker.setVisibility( View.VISIBLE );
                        }
                    }


                } else {
                    CustomDialog.showInvalidPopUp( getActivity(), CONSTANTS.ERROR, response.body().getMessage() );
                }
                Progress.closeProgress();
            }

            @Override
            public void onFailure(Call<Knowledge> call, Throwable t) {
                Progress.closeProgress();
                CustomDialog.showInvalidPopUp( getActivity(), CONSTANTS.ERROR, CONSTANTS.CONNECTIONERROR );
            }
        } );

    }


    public void populateTrack(ArrayList<Datum> details) {
        Dialog dialog = new Dialog( getContext() );
        dialog.requestWindowFeature( Window.FEATURE_NO_TITLE );
        dialog.setContentView( layout.alert_user_type );
        TextView headTv = dialog.findViewById( id.headTv );
        headTv.setText( "Select Track" );
        RecyclerView recyclerView = dialog.findViewById( id.recyclerView );
        recyclerView.setLayoutManager( new LinearLayoutManager( getContext() ) );
        recyclerView.setAdapter( new KnowledgeAdapter( this, details, dialog, type ) );
        dialog.show();
    }

    public void populateSpeaker(ArrayList<SpeakerDetail> details) {
        Dialog dialog = new Dialog( getContext() );
        dialog.requestWindowFeature( Window.FEATURE_NO_TITLE );
        dialog.setContentView( layout.alert_user_type );
        TextView headTv = dialog.findViewById( id.headTv );
        headTv.setText( "Select Track" );
        RecyclerView recyclerView = dialog.findViewById( id.recyclerView );
        recyclerView.setLayoutManager( new LinearLayoutManager( getContext() ) );
        recyclerView.setAdapter( new KnowledgeSpeakerAdapter( this, details, dialog, type ) );
        dialog.show();
    }


    @Override
    public void onSelect(String trackName, long id, String title) {

        if (title.equalsIgnoreCase( "track" )) {
            trackTv.setText( trackName );
        } else {
            speakerTv.setText( trackName );
        }

    }


}
