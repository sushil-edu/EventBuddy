package in.kestone.eventbuddy.fragment;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.model.qanda_model.Session;

class QASessionAdapter extends RecyclerView.Adapter<QASessionAdapter.MyHolder> {


    String type;
    private ArrayList<Session> qandAsessionList;
    private Selected selected;
    private Dialog dialog;

    QASessionAdapter(AskQuestionFragment askQuestionFragment, ArrayList<Session> listQASession, Dialog dialog, String type) {
        this.qandAsessionList = listQASession;
        this.dialog = dialog;
        this.selected = askQuestionFragment;
        this.type = type;
    }


    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.alert_user_cell, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        final Session session = qandAsessionList.get(position);
        holder.nameTv.setText(session.getSessionLongTitleLabel());
        holder.nameTv.setOnClickListener(view -> {
            selected.onSelect(session.getSessionLongTitleLabel(), session.getEBTSID(), "session");
            dialog.dismiss();
        });


    }

    @Override
    public int getItemCount() {

        return qandAsessionList.size();
    }

    public interface Selected {
        void onSelect(String trackName, long id, String title);
    }

    class MyHolder extends RecyclerView.ViewHolder {

        private TextView nameTv;

        MyHolder(View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.nameTv);
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
