package in.kestone.eventbuddy.fragment;

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
import in.kestone.eventbuddy.model.speaker_model.SpeakerDetail;

class KnowledgeSpeakerAdapter extends RecyclerView.Adapter<KnowledgeSpeakerAdapter.MyHolder> {


    private ArrayList<SpeakerDetail> userList;
    private ArrayList<in.kestone.eventbuddy.model.qanda_model.Session> qandAsessionList;
    private Selected selected;
    private Dialog dialog;
    private String type;


    public KnowledgeSpeakerAdapter(Selected selectedSpeaker, ArrayList<SpeakerDetail> userList, Dialog dialog, String type) {
        this.userList = userList;
        this.dialog = dialog;
        this.selected = selectedSpeaker;
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
            final SpeakerDetail details = userList.get( position );
            Log.e( "Name ", details.getFirstName() + " " + details.getLastName() );
            holder.nameTv.setText( details.getFirstName() + " " + details.getLastName() );
            holder.nameTv.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selected.onSelect( details.getFirstName() + " " + details.getLastName(), details.getUserID(), "Speaker" );
                    dialog.dismiss();
                }
            } );
        }
    }

    @Override
    public int getItemCount() {
        return userList.size();
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
