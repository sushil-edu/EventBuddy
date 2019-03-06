package in.kestone.eventbuddy.view.agenda;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.silencedut.expandablelayout.ExpandableLayout;

import java.util.ArrayList;

import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.model.agenda_model.AgendaList;
import in.kestone.eventbuddy.model.agenda_model.Detail;
import in.kestone.eventbuddy.model.agenda_model.Track;

/**
 * A simple {@link Fragment} subclass.
 */
public class AgendaTrackFragmentNew extends Fragment {
    RecyclerView recyclerViewTrack;
    View view;
    int pos, size;
    ArrayList<Track> tracklArrayList = new ArrayList<>();
    TrackAdapter trackAdapter;
    ArrayList<Detail> detailArrayList = new ArrayList<>();


    public AgendaTrackFragmentNew() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public AgendaTrackFragmentNew(int i, int size) {
        this.pos = i;
        this.size = size;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate( R.layout.fragment_agenda_track_new, container, false );

        recyclerViewTrack = view.findViewById( R.id.recyclerView_track );

        if (pos < size - 1) {
            tracklArrayList.clear();
            tracklArrayList.addAll( AgendaList.getAgenda().getAgenda().get( pos ).getTrack() );

            trackAdapter = new TrackAdapter( getActivity(), tracklArrayList );

            LinearLayoutManager mLayoutManager = new LinearLayoutManager( getContext() );
            mLayoutManager.setOrientation( LinearLayoutManager.VERTICAL );
            recyclerViewTrack.setLayoutManager( mLayoutManager );
            recyclerViewTrack.setHasFixedSize( true );
            recyclerViewTrack.setAdapter( trackAdapter );
            trackAdapter.notifyDataSetChanged();


        }

        return view;
    }

    private class TrackAdapter extends RecyclerView.Adapter<TrackAdapter.ViewHolder> {

        private Context context;
        private Activity activity;
        private ArrayList<Track> trackList;

        TrackAdapter(Activity context, ArrayList<Track> trackList) {
            this.context = context;
            this.trackList = trackList;
            this.activity = context;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.track_cell, parent, false );

            return new ViewHolder( view );
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.title.setText( trackList.get( position ).getTrackName() );
            detailArrayList = (ArrayList<Detail>) tracklArrayList.get( position ).getDetails();
            AgendaAdapter adapter = new AgendaAdapter( getActivity(), detailArrayList );
            holder.recyclerViewTrackDetails.setAdapter( adapter );
            adapter.notifyDataSetChanged();


        }

        @Override
        public int getItemCount() {
            return trackList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            ExpandableLayout expandableLayout;
            RecyclerView recyclerViewTrackDetails;
            private TextView title;

            private ViewHolder(View itemView) {
                super( itemView );

                title = itemView.findViewById( R.id.tv_agenda_title );
                title.setTypeface( null, Typeface.BOLD );
                expandableLayout = itemView.findViewById( R.id.expandedLl );

                recyclerViewTrackDetails = itemView.findViewById( R.id.recyclerViewTrackDetails );

                LinearLayoutManager mLayoutManager = new LinearLayoutManager( getContext() );
                mLayoutManager.setOrientation( LinearLayoutManager.VERTICAL );
                recyclerViewTrackDetails.setLayoutManager( mLayoutManager );
                recyclerViewTrackDetails.setHasFixedSize( true );


            }
        }

    }
}
