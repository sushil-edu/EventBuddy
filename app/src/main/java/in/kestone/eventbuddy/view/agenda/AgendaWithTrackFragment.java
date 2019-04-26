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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.model.agenda_model.Agenda;
import in.kestone.eventbuddy.model.agenda_model.AgendaList;
import in.kestone.eventbuddy.model.agenda_model.Detail;
import in.kestone.eventbuddy.model.agenda_model.Track;

/**
 * A simple {@link Fragment} subclass.
 */

public class AgendaWithTrackFragment extends Fragment implements ChildTabAdapter.TabClick {

    View view;
    int pos;
    ArrayList<Track> trackArrayList = new ArrayList<>();
    ArrayList<Track> trackArrayListCopy = new ArrayList<>();
    Set<Track> trackset;
    ArrayList<Detail> trackDetailList = new ArrayList<>();
    RecyclerView recycler_child, recycler_details;
    ChildTabAdapter childTabAdapter;
    AgendaAdapter agendaAdapter;
    Agenda modelAgenda;
    int pTabPos;


    @SuppressLint("ValidFragment")
    public AgendaWithTrackFragment(int pos, Agenda modelAgenda) {
        this.pos = pos;
        this.modelAgenda = modelAgenda;
        Log.e("PPos ", ""+pos);
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
        recycler_child = view.findViewById( R.id.recycler_child_tab );
        try {
            trackArrayList.addAll( AgendaList.getAgenda().getAgenda().get( pos ).getTrack() );
        }catch (NullPointerException ex){}

        childTabAdapter = new ChildTabAdapter( getActivity(), trackArrayList, this, pos );
        recycler_details = view.findViewById( R.id.recycler_details );
        LinearLayoutManager mLayoutManager = new LinearLayoutManager( getContext() );
        mLayoutManager.setOrientation( LinearLayoutManager.HORIZONTAL );
        recycler_child.setLayoutManager( mLayoutManager );
        recycler_child.setHasFixedSize( true );
        recycler_child.setAdapter( childTabAdapter );
        childTabAdapter.notifyDataSetChanged();

        loadTrackDetail( pos,0 );
        return view;
    }

    @Override
    public void onTabClick(int childpos) {

        loadTrackDetail( pos, childpos );

    }

    public void loadTrackDetail(int parentPos, int childPos){

        trackDetailList.clear();
        try {
            if (AgendaList.getAgenda().getAgenda().get( parentPos ).getTrack().size() > 0) {
                trackDetailList.addAll( AgendaList.getAgenda().getAgenda().get( parentPos ).getTrack().get( childPos ).getDetails() );
                agendaAdapter = new AgendaAdapter( getActivity(), trackDetailList, AgendaList.getAgenda().getAgenda().get( parentPos ).getDisplayLabel(),
                        AgendaList.getAgenda().getAgenda().get( parentPos ).getID());
                LinearLayoutManager mLayoutManager = new LinearLayoutManager( getContext() );
                mLayoutManager.setOrientation( LinearLayoutManager.VERTICAL );
                recycler_details.setLayoutManager( mLayoutManager );
                recycler_details.setHasFixedSize( true );
                recycler_details.setAdapter( agendaAdapter );
                agendaAdapter.notifyDataSetChanged();
            }
        }catch (NullPointerException er){}
    }

}
