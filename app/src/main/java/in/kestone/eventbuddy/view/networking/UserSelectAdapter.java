package in.kestone.eventbuddy.view.networking;

import android.app.Dialog;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.model.speaker_model.SpeakerDetail;

public class UserSelectAdapter extends RecyclerView.Adapter<UserSelectAdapter.MyHolder> {


    private ArrayList<SpeakerDetail> userList;
    private SelectedSpeaker selected;
    private Dialog dialog;


    UserSelectAdapter(SelectedSpeaker selectedSpeaker, ArrayList<SpeakerDetail> userList, Dialog dialog) {
        this.userList = userList;
        this.dialog = dialog;
        this.selected=selectedSpeaker;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate( R.layout.alert_user_cell, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        final SpeakerDetail speaker = userList.get(position);
        Log.e("Name ", speaker.getFirstName());
        holder.nameTv.setText(speaker.getFirstName()+" "+speaker.getLastName());
        holder.nameTv.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected.onSelect( speaker.getFirstName()+" "+speaker.getLastName(), speaker.getUserID(),"Speaker");
                dialog.dismiss();
            }
        } );

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        private TextView nameTv;

        public MyHolder(View itemView) {
            super(itemView);
            nameTv =  itemView.findViewById(R.id.nameTv);
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

