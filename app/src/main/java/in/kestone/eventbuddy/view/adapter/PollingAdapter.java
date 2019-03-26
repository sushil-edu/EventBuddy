package in.kestone.eventbuddy.view.adapter;

import android.app.Dialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.SplittableRandom;

import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.common.CONSTANTS;
import in.kestone.eventbuddy.fragment.AskQuestionFragment;
import in.kestone.eventbuddy.fragment.PollFragment;
import in.kestone.eventbuddy.model.polling_model.MDatum;
import in.kestone.eventbuddy.model.qanda_model.Track;

public class PollingAdapter extends RecyclerView.Adapter<PollingAdapter.MyHolder> {


    private ArrayList<MDatum> userList;
    private ArrayList<Track> trackList;
    private Selected selected;
    private Dialog dialog;
    private String type;


    public PollingAdapter(Selected selectedSpeaker, ArrayList<MDatum> userList, Dialog dialog, String type) {
        this.userList = userList;
        this.dialog = dialog;
        this.selected = selectedSpeaker;
        this.type = type;
    }

    public PollingAdapter(PollFragment selectedSpeaker, ArrayList<Track> details, Dialog dialog, String type) {
        this.trackList = details;
        this.dialog = dialog;
        this.selected = selectedSpeaker;
        this.type = type;
    }

    public PollingAdapter(AskQuestionFragment askQuestionFragment, ArrayList<Track> details, Dialog dialog, String type) {
        this.trackList = details;
        this.dialog = dialog;
        this.selected = askQuestionFragment;
        this.type = type;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.alert_user_cell, parent, false );
        return new MyHolder( view );
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        if(type.equalsIgnoreCase( CONSTANTS.POLLING )) {
            final MDatum details = userList.get( position );
            Log.e( "Name ", details.getTrackName() );
            holder.nameTv.setText( details.getTrackName() );
            holder.nameTv.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selected.onSelect( details.getTrackName(), details.getEBTMID(), "track" );
                    dialog.dismiss();
                }
            } );
        }
        else if(type.equalsIgnoreCase( CONSTANTS.ASKAQUESTION )) {
            final Track details = trackList.get( position );
            Log.e( "Name ", details.getTrackName() );
            holder.nameTv.setText( details.getTrackName() );
            holder.nameTv.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selected.onSelect( details.getTrackName(), details.getEBTMID(), "track" );
                    dialog.dismiss();
                }
            } );
        }

    }

    @Override
    public int getItemCount() {
        if (type.equalsIgnoreCase( CONSTANTS.POLLING )) {
            return userList.size();
        }else{// if(type.equalsIgnoreCase( CONSTANTS.ASKAQUESTION )){
            return trackList.size();
        }
    }

    public class MyHolder extends RecyclerView.ViewHolder {

        private TextView nameTv;

        public MyHolder(View itemView) {
            super( itemView );
            nameTv = itemView.findViewById( R.id.nameTv );
//            nameTv.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Speaker speaker= userList.get(getAdapterPosition());
//                    selected.onSelect( speaker.getSpeakerName());
//                    //NetworkScheduleFragment.toMailId = userData.getEmailID() + "";
//                    dialog.dismiss();
//                }
//            });

        }


    }
    public interface Selected {
        void onSelect(String trackName, long id, String title);
    }
}
