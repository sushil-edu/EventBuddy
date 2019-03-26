package in.kestone.eventbuddy.view.networking;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pkmmte.view.CircularImageView;

import java.util.ArrayList;

import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.common.CONSTANTS;
import in.kestone.eventbuddy.model.networking_model.NetworkingList;

public class MyMeetingAdapter extends RecyclerView.Adapter<MyMeetingAdapter.MyHolder> {

    StatusUpdate statusUpdate;
    private Context context;
    private ArrayList<NetworkingList> networkingLists;
    private String status;

    MyMeetingAdapter(Context context, ArrayList<NetworkingList> networkingLists, String type, StatusUpdate statusUpdate) {

        this.networkingLists = networkingLists;
        this.context = context;
        this.status = type;
        this.statusUpdate = statusUpdate;

    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.my_meeting_cell, parent, false );
        return new MyHolder( view );
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        final NetworkingList speakerData = networkingLists.get( position );
//        if(status.contains( speakerData.getIsApproved() )) {
        holder.nameTv.setText( speakerData.getFirst_Name() + " " + speakerData.getLast_Name() );
        holder.designationTv.setText( speakerData.getDesignation() );
        holder.organizationTv.setText( speakerData.getOrganization() );
        holder.dateTv.setText( speakerData.getNetworkingRequestDate() );
        holder.timeTv.setText( speakerData.getNetworingRequestTime() );
        if (status.contains( "approve" )) {
            holder.btnReschedule.setText( "Approve" );
        }
        holder.btnReschedule.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!status.contains( "approve" )) {
                    callSchedule( holder.nameTv.getText().toString(), speakerData.getUserType(),
                            speakerData.getEBMRID(), speakerData.getRequestToID(), status );
                } else {
                    statusUpdate.onStatusUpdate( "approve", speakerData.getEBMRID(), position );
//                        approve(speakerData.getEBMRID());
                }
            }
        } );

        holder.btnCancel.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                statusUpdate.onStatusUpdate( "reject", speakerData.getEBMRID(), position );
//                    rejectRequest(speakerData.getEBMRID());
            }
        } );

//        }

    }

    @Override
    public int getItemCount() {
        return networkingLists.size();
    }

    // Send an Intent with an action named "custom-event-name". The Intent sent should
// be received by the ReceiverActivity.
    private void callSchedule(String name, String type, long emb_id, String userId, String status) {
        Intent intent = new Intent( CONSTANTS.SCHEDULE );
        // You can also include some extra data.
        intent.putExtra( "message", CONSTANTS.SCHEDULE );
        intent.putExtra( "name", name );
        intent.putExtra( "type", type );
        intent.putExtra( "emb_id", "" + emb_id );
        intent.putExtra( "id", userId );
        intent.putExtra( "status", status );
        LocalBroadcastManager.getInstance( context ).sendBroadcast( intent );
    }

    public interface StatusUpdate {
        void onStatusUpdate(String type, long id, int pos);

    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView nameTv, organizationTv, designationTv, dateTv, timeTv, btnCancel, btnReschedule;
        CircularImageView profileIv;

        private MyHolder(View itemView) {
            super( itemView );

            nameTv = itemView.findViewById( R.id.nameTv );
            organizationTv = itemView.findViewById( R.id.orgTv );
            designationTv = itemView.findViewById( R.id.desigTv );
            profileIv = itemView.findViewById( R.id.profileIv );
            dateTv = itemView.findViewById( R.id.dateTv );
            timeTv = itemView.findViewById( R.id.timeTv );
            btnCancel = itemView.findViewById( R.id.btnCancel );
            btnReschedule = itemView.findViewById( R.id.btnReschedule );
        }
    }
}
