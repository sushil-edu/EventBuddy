package in.kestone.eventbuddy.view.networking;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.common.CONSTANTS;
import in.kestone.eventbuddy.model.networking_model.NetworkingList;

public class MyMeetingAdapter extends RecyclerView.Adapter<MyMeetingAdapter.MyHolder> {

    private StatusUpdate statusUpdate;
    private int userID;
    private Context context;
    private ArrayList<NetworkingList> networkingLists;
    private String status, page;

    MyMeetingAdapter(Context context, ArrayList<NetworkingList> networkingLists, String type, StatusUpdate statusUpdate, int userID, String page) {

        this.networkingLists = networkingLists;
        this.context = context;
        this.status = type;
        this.statusUpdate = statusUpdate;
        this.userID = userID;
        this.page = page;

    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =
                LayoutInflater.from(parent.getContext()).inflate(R.layout.my_meeting_cell_new, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        final NetworkingList networkingList = networkingLists.get(position);

        if (page.equalsIgnoreCase(CONSTANTS.APPROVE)) {
            //for approve page
            holder.nameTv.setText(String.format("%s %s", networkingList.getFirstName(), networkingList.getLastName()));
            holder.designationTv.setText(networkingList.getDesignation());
            holder.organizationTv.setText(networkingList.getOrganization());
            holder.dateTv.setText(networkingList.getNetworkingRequestDate());
            holder.tvDate.setText(networkingList.getNetworkingRequestDate().substring(4, 6));
            holder.tvDay.setText(networkingList.getNetworkingRequestDate().substring(0, 3));
            holder.timeTv.setText(networkingList.getNetworingRequestTime());
            holder.locationTv.setText(networkingList.getNetworkingLocation());
            if (networkingList.getNetworkingComments().length() > 0) {
                holder.networkingCommentTv.setVisibility(View.VISIBLE);
                holder.networkingCommentTv.setText(networkingList.getNetworkingComments());
            } else {
                holder.networkingCommentTv.setVisibility(View.GONE);
                holder.networkingCommentTv.setVisibility(View.GONE);
            }
            holder.btnReschedule.setEnabled(false);//.setText(CONSTANTS.APPROVE);

        } else if (page.equalsIgnoreCase(CONSTANTS.PENDING)) {
            //for pending page
            holder.nameTv.setText(String.format("%s %s", networkingList.getToFname(), networkingList.getToLname()));
            holder.designationTv.setText(networkingList.getToDes());
            holder.organizationTv.setText(networkingList.getToORG());
            holder.dateTv.setText(networkingList.getNetworkingRequestDate());
            holder.tvDate.setText(networkingList.getNetworkingRequestDate().substring(4, 6));
            holder.tvDay.setText(networkingList.getNetworkingRequestDate().substring(0, 3));
            holder.timeTv.setText(networkingList.getNetworingRequestTime());
            holder.locationTv.setText(networkingList.getNetworkingLocation());
            if (networkingList.getNetworkingComments().length() > 0) {
                holder.networkingCommentTv.setVisibility(View.VISIBLE);
                holder.networkingCommentTv.setText(networkingList.getNetworkingComments());
            } else {
                holder.networkingCommentTv.setVisibility(View.GONE);
                holder.networkingCommentTv.setVisibility(View.GONE);
            }

        } else if (page.equalsIgnoreCase(CONSTANTS.SCHEDULE)) {
            if (userID == Integer.parseInt(networkingList.getRequestFromID())) {
                holder.nameTv.setText(networkingList.getToFname().concat(networkingList.getToLname()));
                holder.designationTv.setText(networkingList.getToDes());
                holder.organizationTv.setText(networkingList.getToORG());
                holder.dateTv.setText(networkingList.getNetworkingRequestDate());
                holder.tvDate.setText(networkingList.getNetworkingRequestDate().substring(4, 6));
                holder.tvDay.setText(networkingList.getNetworkingRequestDate().substring(0, 3));
                holder.timeTv.setText(networkingList.getNetworingRequestTime());
                holder.locationTv.setText(networkingList.getNetworkingLocation());
                if (networkingList.getNetworkingComments().length() > 0) {
                    holder.networkingCommentTv.setVisibility(View.VISIBLE);
                    holder.networkingCommentTv.setText(networkingList.getNetworkingComments());
                } else {
                    holder.networkingCommentTv.setVisibility(View.GONE);
                    holder.networkingCommentTv.setVisibility(View.GONE);
                }
            } else if (userID == Integer.parseInt(networkingList.getRequestToID())) {
                holder.nameTv.setText(String.format("%s %s", networkingList.getFirstName(), networkingList.getLastName()));
                holder.designationTv.setText(networkingList.getDesignation());
                holder.organizationTv.setText(networkingList.getOrganization());
                holder.dateTv.setText(networkingList.getNetworkingRequestDate());
                holder.tvDate.setText(networkingList.getNetworkingRequestDate().substring(4, 6));
                holder.tvDay.setText(networkingList.getNetworkingRequestDate().substring(0, 3));
                holder.timeTv.setText(networkingList.getNetworingRequestTime());
                holder.locationTv.setText(networkingList.getNetworkingLocation());
                if (networkingList.getNetworkingComments().length() > 0) {
                    holder.networkingCommentTv.setVisibility(View.VISIBLE);
                    holder.networkingCommentTv.setText(networkingList.getNetworkingComments());
                } else {
                    holder.networkingCommentTv.setVisibility(View.GONE);
                    holder.networkingCommentTv.setVisibility(View.GONE);
                }

            }
        }


        holder.btnReschedule.setOnClickListener(view -> {
            Log.e("ID ", "" + networkingList.getEBMRID());
            if (!page.equalsIgnoreCase(CONSTANTS.APPROVE)) {
                callSchedule(holder.nameTv.getText().toString(), networkingList.getToUsertype(),
                        networkingList.getEBMRID(), networkingList.getRequestToID(), networkingList.getNetworkingLocation(), status);
            } else {
                statusUpdate.onStatusUpdate(CONSTANTS.APPROVE, networkingList.getEBMRID(), position);
            }
        });

        holder.btnCancel.setOnClickListener(view -> statusUpdatedDialog(
                CONSTANTS.REJECT, networkingList.getEBMRID(), position, "Do you want to cancel meeting request."));

    }

    @Override
    public int getItemCount() {
        return networkingLists.size();
    }

    // Send an Intent with an action named "custom-event-name". The Intent sent should
// be received by the ReceiverActivity.
    private void callSchedule(String name, String type, long emb_id, String userId, String location, String status) {
        Intent intent = new Intent(CONSTANTS.SCHEDULE);
        // You can also include some extra data.
        intent.putExtra("message", CONSTANTS.SCHEDULE);
        intent.putExtra("name", name);
        intent.putExtra("type", type);
        intent.putExtra("emb_id", emb_id);
        intent.putExtra("id", userId);
        intent.putExtra("location", location);
        intent.putExtra("status", status);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    private void statusUpdatedDialog(String status, Long meetingID, int position, String message) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_correct_credentials);
        dialog.findViewById(R.id.icon).setVisibility(View.GONE);
        dialog.findViewById(R.id.titleTv).setVisibility(View.GONE);
//        titleTv.setText( title );
        TextView bodyTv = dialog.findViewById(R.id.bodyTv);
        bodyTv.setText(message);
        TextView yesTv = dialog.findViewById(R.id.yes);
        yesTv.setText("Yes");
        TextView noTv = dialog.findViewById(R.id.no);
        noTv.setVisibility(View.VISIBLE);
        noTv.setText("No");

        yesTv.setOnClickListener(view -> {
            dialog.dismiss();
            statusUpdate.onStatusUpdate(status, meetingID, position);
        });
        noTv.setOnClickListener(view -> dialog.dismiss());


        dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();
    }

    public interface StatusUpdate {
        void onStatusUpdate(String status, long id, int pos);

    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView nameTv, organizationTv, designationTv, dateTv, timeTv,
                locationTv, networkingCommentTv, tvDate, tvDay;
        TextView btnCancel, btnReschedule;
//        CircularImageView profileIv;

        private MyHolder(View itemView) {
            super(itemView);

            nameTv = itemView.findViewById(R.id.nameTv);
            organizationTv = itemView.findViewById(R.id.orgTv);
            designationTv = itemView.findViewById(R.id.desigTv);
//            profileIv = itemView.findViewById(R.id.profileIv);
            dateTv = itemView.findViewById(R.id.dateTv);
            dateTv.setVisibility(View.GONE);
            timeTv = itemView.findViewById(R.id.timeTv);
            btnCancel = itemView.findViewById(R.id.btnCancel);
            btnReschedule = itemView.findViewById(R.id.btnReschedule);
            locationTv = itemView.findViewById(R.id.locationTv);
            networkingCommentTv = itemView.findViewById(R.id.networkingCommentTv);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvDay = itemView.findViewById(R.id.tvDay);
        }
    }
}
