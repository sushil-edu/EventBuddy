package in.kestone.eventbuddy.model.agenda_holder;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.view.agenda.AgendaAdapter;

/**
 * A simple {@link Fragment} subclass.
 */
public class AgendaListFragment extends Fragment {
    RecyclerView recyclerView;
    AgendaAdapter adapter;
    View view;
    int pos, size;
    ArrayList<Detail> detailArrayList = new ArrayList<>(  );
    String strOnReceive=null;

    public AgendaListFragment() {

    }

    @SuppressLint("ValidFragment")
    public AgendaListFragment(int pos, int size) {
        // Required empty public constructor
        this.pos=pos;
        this.size=size;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =inflater.inflate( R.layout.fragment_agenda_list, container, false );

        if(pos<size-1) {
            detailArrayList.clear();
            detailArrayList.addAll( AgendaList.getAgenda().getAgenda().get( pos ).getDetails() );
            adapter = new AgendaAdapter( getActivity(), detailArrayList );
            recyclerView = view.findViewById( R.id.recyclerView );
            LinearLayoutManager mLayoutManager = new LinearLayoutManager( getContext());
            mLayoutManager.setOrientation( LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager( mLayoutManager );
            recyclerView.setHasFixedSize( true );
            recyclerView.setAdapter( adapter );
            adapter.notifyDataSetChanged();
        }

        return view;
    }



}
