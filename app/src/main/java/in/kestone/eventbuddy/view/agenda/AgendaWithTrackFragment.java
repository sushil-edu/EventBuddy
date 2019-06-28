package in.kestone.eventbuddy.view.agenda;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.model.agenda_model.Agenda;
import in.kestone.eventbuddy.model.agenda_model.Detail;
import in.kestone.eventbuddy.model.agenda_model.Track;

public class AgendaWithTrackFragment extends Fragment implements ChildTabAdapter.TabClick {

    View view;
    ArrayList<Track> trackArrayList = new ArrayList<>();
    ArrayList<Detail> trackDetailListWithTrack = new ArrayList<>();
    ArrayList<Detail> trackDetailList = new ArrayList<>();
    RecyclerView recycler_child, recycler_details, recycler_details_without_track;
    LinearLayoutManager mLayoutManager, mLayoutManagerV, mLayoutManagerVV;
    TextView noSession;
    ChildTabAdapter childTabAdapter;
    AgendaAdapter agendaAdapter;
    AgendaAdapterWithoutTrack agendaAdapterWithoutTrack;
    Agenda modelAgenda;
    RelativeLayout layout_with_track;
    int pTabPos;


    @SuppressLint("ValidFragment")
    public AgendaWithTrackFragment(int pos, Agenda modelAgenda) {
        this.modelAgenda = modelAgenda;
    }

    public AgendaWithTrackFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        if (getArguments() != null) {
            pTabPos = getArguments().getInt( "pos" );

        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate( R.layout.fragment_agenda_with_track, container, false );
        super.onCreate( savedInstanceState );

        initializeView();
        loadAgenda();

        return view;
    }

    private void initializeView() {
        layout_with_track = view.findViewById( R.id.layout_with_track );
        recycler_child = view.findViewById( R.id.recycler_child_tab );
        mLayoutManager = new LinearLayoutManager( getContext() );
        mLayoutManager.setOrientation( LinearLayoutManager.HORIZONTAL );
        recycler_child.setLayoutManager( mLayoutManager );
        recycler_child.setHasFixedSize( true );
        noSession = view.findViewById( R.id.tvNoSession );

        recycler_details = view.findViewById( R.id.recycler_details );
        mLayoutManagerV = new LinearLayoutManager( getContext() );
        mLayoutManagerV.setOrientation( LinearLayoutManager.VERTICAL );
        recycler_details.setLayoutManager( mLayoutManagerV );
        recycler_details.setHasFixedSize( true );

        recycler_details_without_track = view.findViewById( R.id.recycler_details_without_track );
        mLayoutManagerVV = new LinearLayoutManager( getContext() );
        mLayoutManagerVV.setOrientation( LinearLayoutManager.VERTICAL );
        recycler_details_without_track.setLayoutManager( mLayoutManagerVV );
        recycler_details_without_track.setHasFixedSize( true );
    }

    @Override
    public void onTabClick(int childPos) {
        loadTrackDetail( childPos );
        recycler_details_without_track.setVisibility( View.GONE );
        recycler_details.setVisibility( View.VISIBLE );
    }

    public void loadTrackDetail(int childPos) {
        trackDetailListWithTrack.clear();
        try {
            if (modelAgenda.getTrack().size() > 0) {

                trackDetailListWithTrack.addAll( modelAgenda.getTrack().get( childPos ).getDetails() );
                agendaAdapter = new AgendaAdapter( getActivity(), trackDetailListWithTrack, modelAgenda.getDisplayLabel(),
                        modelAgenda.getID() );
                recycler_details.setAdapter( agendaAdapter );
                agendaAdapter.notifyDataSetChanged();

            }
        } catch (Exception er) {
            Log.e( "Exception ", er.getMessage() );
        }

    }

    private void loadAgenda() {
        trackDetailList.clear();
        trackArrayList.clear();
        try {
            //only for common agenda
            if (modelAgenda.getID() == -1) {//.getCommonAgendaModel().size() > 0 && modelAgenda.getTrack() == null) {
                layout_with_track.setVisibility( View.GONE );
                recycler_details.setVisibility( View.GONE );
                trackDetailList.addAll( modelAgenda.getCommonAgendaModel() );
                agendaAdapterWithoutTrack = new AgendaAdapterWithoutTrack( getActivity(), trackDetailList, modelAgenda.getDisplayLabel(),
                        modelAgenda.getID() );

                recycler_details_without_track.setAdapter( agendaAdapterWithoutTrack );
                agendaAdapterWithoutTrack.notifyDataSetChanged();

            } else if (modelAgenda.getTrack().size() > 0 && modelAgenda.getCommonAgendaModel().size() > 0) {
                //only for track and without track agenda
                layout_with_track.setVisibility( View.VISIBLE );
                recycler_details.setVisibility( View.GONE );
                //session with out track

                trackDetailList.addAll( modelAgenda.getCommonAgendaModel() );
                agendaAdapterWithoutTrack = new AgendaAdapterWithoutTrack( getActivity(), trackDetailList, modelAgenda.getDisplayLabel(),
                        modelAgenda.getID() );

                recycler_details_without_track.setAdapter( agendaAdapterWithoutTrack );
                agendaAdapterWithoutTrack.notifyDataSetChanged();
                //session with track
                trackArrayList.addAll( modelAgenda.getTrack() );
                childTabAdapter = new ChildTabAdapter( getActivity(), trackArrayList, this, false );

                recycler_child.setAdapter( childTabAdapter );
                childTabAdapter.notifyDataSetChanged();

            } else if (modelAgenda.getTrack().size() > 0 && modelAgenda.getCommonAgendaModel().size() == 0 ) {
                //only for track wise agenda
                recycler_details_without_track.setVisibility( View.GONE );
                layout_with_track.setVisibility( View.VISIBLE );
                recycler_details.setVisibility( View.VISIBLE );

                trackArrayList.addAll( modelAgenda.getTrack() );
                childTabAdapter = new ChildTabAdapter( getActivity(), trackArrayList, this, true );
                recycler_child.setAdapter( childTabAdapter );
                childTabAdapter.notifyDataSetChanged();

                loadTrackDetail( 0 );
            }else if (modelAgenda.getTrack().size() == 0 && modelAgenda.getCommonAgendaModel().size()> 0 ||
                        ! modelAgenda.getCommonAgendaModel().equals(null)) {
                //only for track wise agenda
                layout_with_track.setVisibility( View.GONE );
                recycler_details.setVisibility( View.GONE );
                trackDetailList.addAll( modelAgenda.getCommonAgendaModel() );
                agendaAdapterWithoutTrack = new AgendaAdapterWithoutTrack( getActivity(), trackDetailList, modelAgenda.getDisplayLabel(),
                        modelAgenda.getID() );

                recycler_details_without_track.setAdapter( agendaAdapterWithoutTrack );
                agendaAdapterWithoutTrack.notifyDataSetChanged();
            } else {
                //if no track and no session available
                recycler_details_without_track.setVisibility( View.GONE );
                layout_with_track.setVisibility( View.GONE );
                recycler_details.setVisibility( View.GONE );
                noSession.setVisibility( View.VISIBLE );
            }


        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
    }

}
