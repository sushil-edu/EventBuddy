package in.kestone.eventbuddy.view.adapter;

import android.app.Dialog;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.common.CONSTANTS;
import in.kestone.eventbuddy.fragment.AskQuestionFragment;
import in.kestone.eventbuddy.fragment.PollFragment;
import in.kestone.eventbuddy.model.knowledgebase_model.Datum;
import in.kestone.eventbuddy.model.polling_model.Session;

public class KnowledgeAdapter extends RecyclerView.Adapter<KnowledgeAdapter.MyHolder> {


    private ArrayList<Datum> userList;
    private ArrayList<Session> sessionList;
    private ArrayList<in.kestone.eventbuddy.model.qanda_model.Session> qandAsessionList;
    private Selected selected;
    private Dialog dialog;
    private String type;


    public KnowledgeAdapter(Selected selectedSpeaker, ArrayList<Datum> userList, Dialog dialog, String type) {
        this.userList = userList;
        this.dialog = dialog;
        this.selected = selectedSpeaker;
        this.type = type;
    }

    public KnowledgeAdapter(AskQuestionFragment selectedSpeaker, ArrayList<Session> details, Dialog dialog, String type) {
        this.sessionList = details;
        this.dialog = dialog;
        this.selected = selectedSpeaker;
        this.type = type;
    }

    public KnowledgeAdapter(PollFragment pollFragment, ArrayList<Session> details, Dialog dialog, String type) {
        this.sessionList = details;
        this.dialog = dialog;
        this.selected = pollFragment;
        this.type = type;
    }


    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.alert_user_cell, parent, false );
        return new MyHolder( view );
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        if (type.equalsIgnoreCase( CONSTANTS.KNOWLEDGEBASE )) {
            final Datum details = userList.get( position );
            Log.e( "Name ", details.getTrackName() );
            holder.nameTv.setText( details.getTrackName() );
            holder.nameTv.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selected.onSelect( details.getTrackName(), details.getEBTMID(), "track" );
                    dialog.dismiss();
                }
            } );
        } else {
            final Session session = sessionList.get( position );
            holder.nameTv.setText( session.getSessionShortTitleLabel() );
            holder.nameTv.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selected.onSelect( session.getSessionShortTitleLabel(), session.getEBTSID(), "session" );
                    dialog.dismiss();
                }
            } );
        }

    }

    @Override
    public int getItemCount() {
        if (type.equalsIgnoreCase( CONSTANTS.KNOWLEDGEBASE )) {
            return userList.size();
        } else if (type.equalsIgnoreCase( CONSTANTS.ASKAQUESTION )) {
            return qandAsessionList.size();
        } else {
            return sessionList.size();
        }
    }

    public interface Selected {
        void onSelect(String trackName, long id, String title);
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
}
