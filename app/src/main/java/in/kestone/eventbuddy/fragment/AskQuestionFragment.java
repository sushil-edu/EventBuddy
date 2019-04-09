package in.kestone.eventbuddy.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONObject;

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
import in.kestone.eventbuddy.model.polling_model.PostRequest;
import in.kestone.eventbuddy.model.polling_model.Session;
import in.kestone.eventbuddy.model.qanda_model.QandA;
import in.kestone.eventbuddy.model.qanda_model.Track;
import in.kestone.eventbuddy.view.adapter.KnowledgeAdapter;
import in.kestone.eventbuddy.view.adapter.PollingAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static in.kestone.eventbuddy.R.id;
import static in.kestone.eventbuddy.R.layout;

public class AskQuestionFragment extends Fragment implements KnowledgeAdapter.Selected, PollingAdapter.Selected, QASessionAdapter.Selected {

    View view;
    String type;
    ArrayList<Session> listSession = new ArrayList<>();
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
    @BindView(id.layoutDetails)
    LinearLayout layoutDetails;
    @BindView(id.btnSubmit)
    Button btnSubmit;
    PostRequest postRequest;
    @BindView(id.txtFeedback)
    EditText txtFeedback;
    @BindView(id.layoutFeedback)
    LinearLayout layoutFeedback;

    int flag = 0;

    public AskQuestionFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate( R.layout.fragment_ask_question, container, false );
        initialiseView( view );
        apiInterface = APIClient.getClient().create( APIInterface.class );
        speakerTv.setText( "Select Session" );
        getAskQuestion();
        Progress.showProgress( getContext() );

        return view;
    }

    private void initialiseView(View view) {
        ButterKnife.bind( this, view );
        postRequest = new PostRequest();
        layoutTrack.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listQA.size() > 0) {
                    populateQandATrack( listQA );
                }
            }
        } );
        layoutSpeaker.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listSession.clear();
                listQASession.clear();
                if (listQA.size() > 0) {
                    for (int i = 0; i < listQA.size(); i++) {
                        if (listQA.get( i ).getSession().size() > 0 &&
                                trackTv.getText().equals( listQA.get( i ).getTrackName() )) {
                            listQASession.addAll( listQA.get( i ).getSession() );
                        }
                    }
                    populateQandASession( listQASession );
                }
            }
        } );
        btnSubmit.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
//                if (type.equalsIgnoreCase( CONSTANTS.POLLING ) && txtFeedback.getText().length() > 0) {
//                    postRequest.setEventID( CONSTANTS.EVENTID );
//                    postRequest.setDelegateID( (long) new SharedPrefsHelper( getContext() ).getUserId() );
//                    postRequest.setResponse( txtFeedback.getText().toString() );
//                    postPollingRequest( postRequest );
//                    Progress.showProgress( getContext() );
//                } else
//                if (type.equalsIgnoreCase( CONSTANTS.ASKAQUESTION )) {
//                    {"Event_ID":1,"User_ID":1,"Comment":"test","ImageURL":"trest","Inserted_On":"12-12-12"}
                    postRequest.setEventID( CONSTANTS.EVENTID );
                    postRequest.setDelegateID( (long) new SharedPrefsHelper( getContext() ).getUserId() );
                    postRequest.setResponse( txtFeedback.getText().toString() );
                    postQA( postRequest );
                    Progress.showProgress( getContext() );
//                }
            }
        } );
    }


    private void populateQandASession(ArrayList<in.kestone.eventbuddy.model.qanda_model.Session> listQASession) {
        if (listQASession.size()>0) {
            Dialog dialog = new Dialog( getContext() );
            dialog.requestWindowFeature( Window.FEATURE_NO_TITLE );
            dialog.setContentView( layout.alert_user_type );
            TextView headTv = dialog.findViewById( id.headTv );
            headTv.setText( "Select Session" );
            RecyclerView recyclerView = dialog.findViewById( id.recyclerView );
            recyclerView.setLayoutManager( new LinearLayoutManager( getContext() ) );
            recyclerView.setAdapter( new QASessionAdapter( this, listQASession, dialog, type ) );
            dialog.show();
        }else {
            CustomDialog.showInvalidPopUp( getContext(),"","Session not available in this track" );
        }
    }

    public void getAskQuestion() {
        Call<QandA> call = apiInterface.getQandA( CONSTANTS.EVENTID );
        call.enqueue( new Callback<QandA>() {
            @Override
            public void onResponse(Call<QandA> call, Response<QandA> response) {
                if(response.code()==200) {
                    if (response.body().getStatusCode() == 200 && response.body().getData().size() > 0 && response.code() == 200) {
                        listQA.addAll( response.body().getData() );
                    } else {
                        CustomDialog.showInvalidPopUp( getActivity(), CONSTANTS.ERROR, response.body().getMessage() );
                    }
                } else {
                    CustomDialog.showInvalidPopUp( getActivity(), CONSTANTS.ERROR, response.message() );
                }
                Progress.closeProgress();
            }

            @Override
            public void onFailure(Call<QandA> call, Throwable t) {
                Progress.closeProgress();
                CustomDialog.showInvalidPopUp( getActivity(), CONSTANTS.ERROR, CONSTANTS.CONNECTIONERROR );
            }
        } );

    }



    private void postQA(PostRequest postRequest) {
        Call<JSONObject> call = apiInterface.postQandA( CONSTANTS.EVENTID, postRequest );
        call.enqueue( new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                if (response.code() == 201) {
                    CustomDialog.showValidPopUp( getContext(), CONSTANTS.SUCCESS, "Request send" );
                    txtFeedback.getText().clear();
                    trackTv.setText( "Select Track" );
                    speakerTv.setText( "Select Session" );
                } else {
                    CustomDialog.showInvalidPopUp( getActivity(), CONSTANTS.ERROR, response.message() );
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


    public void populateQandATrack(ArrayList<Track> details) {
        Dialog dialog = new Dialog( getContext() );
        dialog.requestWindowFeature( Window.FEATURE_NO_TITLE );
        dialog.setContentView( layout.alert_user_type );
        TextView headTv = dialog.findViewById( id.headTv );
        headTv.setText( "Select Track" );
        RecyclerView recyclerView = dialog.findViewById( id.recyclerView );
        recyclerView.setLayoutManager( new LinearLayoutManager( getContext() ) );
        recyclerView.setAdapter( new PollingAdapter( this, details, dialog, CONSTANTS.ASKAQUESTION ) );
        dialog.show();
    }


    @Override
    public void onSelect(String trackName, long id, String title) {

        if (title.equalsIgnoreCase( "track" )) {
            trackTv.setText( trackName );
            postRequest.setTrackID( id );
        } else {
            speakerTv.setText( trackName );
            postRequest.setSessionID( id );
        }
    }


}
