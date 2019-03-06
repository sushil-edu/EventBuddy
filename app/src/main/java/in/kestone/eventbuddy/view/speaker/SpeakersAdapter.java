package in.kestone.eventbuddy.view.speaker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pkmmte.view.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.model.agenda_model.Speaker;

public class SpeakersAdapter extends RecyclerView.Adapter<SpeakersAdapter.MyHolder> {

    private Context context;
    private ArrayList<Speaker> speakerList;

    SpeakersAdapter(Context context, ArrayList<Speaker> speakerList) {

        this.speakerList = speakerList;
        this.context = context;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.speaker_list, parent, false );
        return new MyHolder( view );
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        final Speaker speakerData = speakerList.get( position );
        holder.nameTv.setText( speakerData.getSpeakerName() );
        holder.designationTv.setText( speakerData.getDesignation() );
        holder.organizationTv.setText( speakerData.getOrganization() );
        holder.itemView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if(!UserDetails.getEmailID().equals(speakerData.getEmailID())){
                Intent intent = new Intent( context, ActivitySpeaterDetails.class );
                intent.putExtra( "Name", speakerData.getSpeakerName() );
                intent.putExtra( "Designation", speakerData.getDesignation() );
                intent.putExtra( "Organization", speakerData.getOrganization() );
                intent.putExtra( "Email", speakerData.getBio() );
                intent.putExtra( "Image", speakerData.getSpeakerImage() );
                intent.putExtra( "Type", "Speaker" );
                intent.putExtra( "Tag", "Speaker" );
                intent.putExtra( "details", speakerData.getDescription() );
                context.startActivity( intent );
                ((Activity)context).finish();
//                }
            }
        } );
        Picasso.with( context ).load( speakerData.getSpeakerImage() )
                .resize( 80, 80 )
                .placeholder( R.drawable.user )
                .into( holder.profileIv );
    }

    @Override
    public int getItemCount() {
        return speakerList.size()-1;
    }

    //This method will filter the list
    //here we are passing the filtered data
    //and assigning it to the list with notifydatasetchanged method
    public void filterList(ArrayList<Speaker> filterdNames) {
        this.speakerList = filterdNames;
        notifyDataSetChanged();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView nameTv, organizationTv, designationTv;
        CircularImageView profileIv;

        private MyHolder(View itemView) {
            super( itemView );

            nameTv = itemView.findViewById( R.id.nameTv );
            organizationTv = itemView.findViewById( R.id.organizationTv );
            designationTv = itemView.findViewById( R.id.designationTv );
            profileIv = itemView.findViewById( R.id.profileIv );
        }
    }
}
