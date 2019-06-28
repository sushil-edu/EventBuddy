package in.kestone.eventbuddy.view.knowledgeBase;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.kestone.eventbuddy.Altdialog.CustomDialog;
import in.kestone.eventbuddy.Altdialog.Progress;
import in.kestone.eventbuddy.Eventlistener.PartnerDetailsCallback;
import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.common.CONSTANTS;
import in.kestone.eventbuddy.common.LocalStorage;
import in.kestone.eventbuddy.http.APIClient;
import in.kestone.eventbuddy.http.APIInterface;
import in.kestone.eventbuddy.model.knowledgebase_model.Datum;
import in.kestone.eventbuddy.model.knowledgebase_model.Knowledge;
import in.kestone.eventbuddy.model.knowledgebase_model.Knowledgebase;
import in.kestone.eventbuddy.model.speaker_model.SpeakerDetail;
import in.kestone.eventbuddy.view.WebviewActivity;
import in.kestone.eventbuddy.view.adapter.KnowledgeAdapter;
import in.kestone.eventbuddy.view.splash.ActivitySplash;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static in.kestone.eventbuddy.R.id;
import static in.kestone.eventbuddy.R.layout;

public class KnowlegdeBaseFragment extends Fragment implements KnowledgeSpeakerAdapter.Selected, KnowledgeAdapter.Selected {

    View view;
    String type;
    ArrayList<Datum> lisKnowledge = new ArrayList<>();
    ArrayList<SpeakerDetail> listSpeaker = new ArrayList<>();
    ArrayList<in.kestone.eventbuddy.model.qanda_model.Session> listQASession = new ArrayList<>();
    APIInterface apiInterface;

    @BindView(id.knowledgeBaseRecycler)
    RecyclerView knowledgeBaseRecycler;
    KnowledgeAssetsAdapter assetsAdapter;

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

        knowledgeBaseRecycler.setLayoutManager( new GridLayoutManager( getActivity(), 2 ) );
//        recyclerView.addItemDecoration(new SpacesItemDecoration(2,20, true));
        knowledgeBaseRecycler.setHasFixedSize( true );

        layoutTrack.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (lisKnowledge.size() > 0 && type.equalsIgnoreCase( CONSTANTS.KNOWLEDGEBASE )) {
                    populateTrack( lisKnowledge );
                } else {
                    CustomDialog.showInvalidPopUp( getActivity(), "", "No track" );
                }
            }
        } );
//        layoutSpeaker.setOnClickListener( new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                listSpeaker.clear();
//                if (lisKnowledge.size() > 0 && type.equalsIgnoreCase( CONSTANTS.KNOWLEDGEBASE )) {
//                    for (int i = 0; i < lisKnowledge.size(); i++) {
//                        if (trackTv.getText().toString().equals( lisKnowledge.get( i ).getTrackName() )) {
//                            listSpeaker.addAll( lisKnowledge.get( i ).getSpeakerList() );
//                        }
//                    }
//                    populateSpeaker( listSpeaker );
//                }
//            }
//        } );

    }


    public void getKnowledgeDetail() {
        Call<Knowledge> call = apiInterface.getKnowledgeBase( LocalStorage.getEventID( getActivity() ) );
        call.enqueue( new Callback<Knowledge>() {
            @Override
            public void onResponse(Call<Knowledge> call, Response<Knowledge> response) {
                if (response.code() == 200) {
                    if (response.body().getStatusCode() == 200 && response.body().getData().size() > 0) {
                        lisKnowledge = (ArrayList<Datum>) response.body().getData();
                    } else {
                        CustomDialog.showInvalidPopUp( getActivity(), CONSTANTS.ERROR, response.body().getMessage() );
                    }
                } else {
                    CustomDialog.showInvalidPopUp( getActivity(), CONSTANTS.ERROR, response.message() );
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

//    public void populateSpeaker(ArrayList<SpeakerDetail> details) {
//        if (details.size() > 0) {
//            Dialog dialog = new Dialog( getContext() );
//            dialog.requestWindowFeature( Window.FEATURE_NO_TITLE );
//            dialog.setContentView( layout.alert_user_type );
//            TextView headTv = dialog.findViewById( id.headTv );
//            headTv.setText( "Select Track" );
//            RecyclerView recyclerView = dialog.findViewById( id.recyclerView );
//            recyclerView.setLayoutManager( new LinearLayoutManager( getContext() ) );
//            recyclerView.setAdapter( new KnowledgeSpeakerAdapter( this, details, dialog, type ) );
//            dialog.show();
//        } else {
//            speakerTv.setText( "Select Speaker" );
//            CustomDialog.showInvalidPopUp( getActivity(), "", "Speaker not available in this track" );
//        }
//    }


    @Override
    public void onSelect(String trackName, long id, String title) {

        Log.e( "Track ID", "" + id );
        if (title.equalsIgnoreCase( "track" )) {
            trackTv.setText( trackName );
        } else {
            speakerTv.setText( trackName );
        }
        for (int i = 0; i < lisKnowledge.size(); i++) {
            if (lisKnowledge.get( i ).getEBTMID().equals( id )) {
                assetsAdapter = new KnowledgeAssetsAdapter( getActivity(), lisKnowledge.get( i ).getKnowledgebase() );
            }
        }
        knowledgeBaseRecycler.setAdapter( assetsAdapter );
        assetsAdapter.notifyDataSetChanged();

    }


    private class KnowledgeAssetsAdapter extends RecyclerView.Adapter<KnowledgeAssetsAdapter.ViewHolder> {

        Activity activity;
        ArrayList<Knowledgebase> detailArrayList;
        PartnerDetailsCallback detailsCallback;
        long id;


        public KnowledgeAssetsAdapter(FragmentActivity activity, List<Knowledgebase> knowledgebase) {
            this.activity = activity;
            this.detailArrayList = (ArrayList<Knowledgebase>) knowledgebase;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.assets_cell, parent, false );

            return new ViewHolder( view );
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            Knowledgebase detail = detailArrayList.get( position );
            Glide.with( activity ).load( LocalStorage.getImagePath( activity ) .concat( detail.getAssetIcon() ))
//                    .resize( 80, 80 )
                    .placeholder( R.drawable.default_user_grey )
                    .into( holder.partnerLogo );
            holder.description.setText( detail.getAssetDescription() );

            holder.rootLayout.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!detail.getAssetLink().isEmpty()) {
                        Intent intent = new Intent( getActivity(), WebviewActivity.class );
                        intent.putExtra( "url", detail.getAssetLink() );
                        activity.startActivity( intent );
                    }else {
                        CustomDialog.showInvalidPopUp( getActivity(), CONSTANTS.ERROR,"Invalid path" );
                    }
                }
            } );
        }

        @Override
        public int getItemCount() {
            return detailArrayList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ImageView partnerLogo;
            TextView description;
            CardView rootLayout;

            public ViewHolder(View view) {
                super( view );

                partnerLogo = view.findViewById( R.id.partnerIv );
                description = view.findViewById( R.id.description );

                rootLayout = view.findViewById( R.id.layout_root );
            }
        }
    }
}
