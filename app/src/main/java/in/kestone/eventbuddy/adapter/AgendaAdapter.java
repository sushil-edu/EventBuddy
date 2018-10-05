package in.kestone.eventbuddy.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import java.util.ArrayList;

import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.model.agenda.Detail;
import in.kestone.eventbuddy.model.agenda.Speaker;
import in.kestone.eventbuddy.widgets.CustomTextView;

public class AgendaAdapter extends RecyclerView.Adapter<AgendaAdapter.ViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList<Detail> agendaList;
    private ArrayList<Speaker> speakerList;

    public AgendaAdapter(Activity activity, ArrayList<Detail> detailArrayList) {
        this.context = activity;
        this.agendaList = detailArrayList;
        this.activity = (Activity) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.agenda_cell, parent, false );

        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final Detail agendaData = agendaList.get( position );
//        if (agendaData.getIsMultitrack().equals( "N" )) {
//            holder.addIv.setVisibility( View.GONE );
//        } else holder.addIv.setVisibility( View.VISIBLE );
//
//        if (agendaData.getIsRateable().equals( "N" )) {
//            holder.avgRatingBar.setVisibility( View.GONE );
//            holder.rateTv.setVisibility( View.GONE );
//        } else {
//            holder.rateTv.setVisibility( View.VISIBLE );
//            holder.avgRatingBar.setVisibility( View.VISIBLE );
//        }


//        holder.titleTv.setText( agendaData.getTitleTrack() );

        holder.timeTv.setText( agendaData.getTime() );
        holder.locationTv.setText( "Location: " + agendaData.getLocation() );
        holder.headingTv.setText( agendaData.getShortTitle() );

        //speaker list
        speakerList = new ArrayList<Speaker>();
        speakerList.clear();
        speakerList.addAll( agendaData.getSpeaker() );
        SpeakerAdapter spkAdapter = new SpeakerAdapter( activity, speakerList );
        holder.nestedReyclerView.setAdapter( spkAdapter );


//        if (agendaData.getRating().length() > 0) {
//            holder.avgRatingBar.setRating( Float.parseFloat( agendaData.getRating() ) );
//        } else {
            holder.avgRatingBar.setRating( Float.parseFloat( "3.5" ) );
//        }

        holder.addIv.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMessage();

            }
        } );
    }

    @Override
    public int getItemCount() {
        return agendaList.size();
    }

    // Send an Intent with an action named "custom-event-name". The Intent sent should
// be received by the ReceiverActivity.
    private void sendMessage() {
        Log.d( "sender", "Broadcasting message" );
        Intent intent = new Intent( "event-buddy" );
        // You can also include some extra data.
        intent.putExtra( "message", "My Agenda" );
        LocalBroadcastManager.getInstance( context ).sendBroadcast( intent );
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CustomTextView addIv;
        private CustomTextView titleTv, timeTv, rateTv, locationTv, headingTv;
        private RatingBar avgRatingBar;
        private CardView card;
        private RecyclerView nestedReyclerView;

        public ViewHolder(View itemView) {
            super( itemView );
            addIv = itemView.findViewById( R.id.addIv );
            titleTv = itemView.findViewById( R.id.titleTv );
            timeTv = itemView.findViewById( R.id.timeTv );
            headingTv = itemView.findViewById( R.id.headingTv );
            avgRatingBar = itemView.findViewById( R.id.avgRatingBar );
            card = itemView.findViewById( R.id.card );
            rateTv = itemView.findViewById( R.id.rateTv );
            nestedReyclerView = itemView.findViewById( R.id.nestedReyclerView );
            locationTv = itemView.findViewById( R.id.locationTv );

            LinearLayoutManager mLayoutManager = new LinearLayoutManager( context );
            mLayoutManager.setOrientation( LinearLayoutManager.HORIZONTAL );
            nestedReyclerView.setLayoutManager( mLayoutManager );
            nestedReyclerView.setHasFixedSize( true );
        }
    }
}
