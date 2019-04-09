package in.kestone.eventbuddy.view.agenda;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.model.agenda_model.Agenda;
import in.kestone.eventbuddy.model.agenda_model.Detail;

/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class AgendaListFragment extends Fragment {
    RecyclerView recyclerView;
    AgendaAdapter adapter;
    View view;
    int pos, size;
    List<Detail> detailArrayList = new ArrayList<>();
    Agenda modelAgenda;


    public AgendaListFragment(int pos, int size, Agenda modelAgenda) {
        this.pos = pos;
        this.size = size;
        this.modelAgenda = modelAgenda;
        Log.e("Size ", ""+modelAgenda.getTrack().size());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate( R.layout.fragment_agenda_list, container, false );

        detailArrayList.clear();
//        if ( !modelAgenda.getTrack().get( pos ).getDetails().isEmpty() ) {
            detailArrayList.addAll( modelAgenda.getTrack().get( pos ).getDetails() );

            adapter = new AgendaAdapter( getActivity(), detailArrayList );

            recyclerView = view.findViewById( R.id.recyclerView );
            LinearLayoutManager mLayoutManager = new LinearLayoutManager( getContext() );
            mLayoutManager.setOrientation( LinearLayoutManager.VERTICAL );
            recyclerView.setLayoutManager( mLayoutManager );
            recyclerView.setHasFixedSize( true );
            recyclerView.setAdapter( adapter );
            adapter.notifyDataSetChanged();
//        }


        return view;
    }
}