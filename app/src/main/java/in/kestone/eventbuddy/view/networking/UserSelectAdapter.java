package in.kestone.eventbuddy.view.networking;

import android.app.Dialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.model.agenda_model.Speaker;

public class UserSelectAdapter extends RecyclerView.Adapter<UserSelectAdapter.MyHolder> {


    private ArrayList<Speaker> userList;
    private SelectedSpeaker selected;
    private Dialog dialog;


    UserSelectAdapter(SelectedSpeaker selectedSpeaker, ArrayList<Speaker> userList, Dialog dialog) {
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
        final Speaker speaker = userList.get(position);
        holder.nameTv.setText(speaker.getSpeakerName());
        holder.nameTv.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selected.onSelect( speaker.getSpeakerName(), speaker.getSId() );
                dialog.dismiss();
            }
        } );

    }

    @Override
    public int getItemCount() {
        return userList.size()-1;
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

