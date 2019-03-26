package in.kestone.eventbuddy.fragment;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;

import in.kestone.eventbuddy.model.polling_model.Session;

class PollingSessionAdapter extends RecyclerView.Adapter {
    public PollingSessionAdapter(PollFragment pollFragment, ArrayList<Session> details, Dialog dialog, String type) {
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
