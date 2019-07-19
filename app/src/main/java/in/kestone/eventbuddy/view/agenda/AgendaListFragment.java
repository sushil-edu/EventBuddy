package in.kestone.eventbuddy.view.agenda;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.model.agenda_model.AgendaList;
import in.kestone.eventbuddy.model.agenda_model.Detail;
import in.kestone.eventbuddy.model.agenda_model.Track;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class AgendaListFragment extends Fragment {
    int pTabPoss, cTabPoss;
    RecyclerView recyclerView;
    AgendaAdapter adapter;
    View view;
    int pos;
    List<Detail> detailArrayList = new ArrayList<>();
    Track track;


    public AgendaListFragment() {

    }

    public AgendaListFragment(int pos, Track track) {
        this.pos = pos;
        this.track = track;

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pTabPoss = getArguments().getInt("ParentPos", 0);
            cTabPoss = getArguments().getInt("ChildPos", 0);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_agenda_list, container, false);

        detailArrayList.clear();
//        detailArrayList.addAll( track.getDetails() );
        detailArrayList.addAll(AgendaList.getAgenda().getAgenda().get(pTabPoss).getTrack().get(cTabPoss).getDetails());
        adapter =
                new AgendaAdapter(getActivity(), detailArrayList, AgendaList.getAgenda().getAgenda().get(pTabPoss).getDisplayLabel(),
                        AgendaList.getAgenda().getAgenda().get(pTabPoss).getID());
        recyclerView = view.findViewById(R.id.recyclerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return view;
    }
}