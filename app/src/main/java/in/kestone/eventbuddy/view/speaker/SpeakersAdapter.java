package in.kestone.eventbuddy.view.speaker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pkmmte.view.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.common.LocalStorage;
import in.kestone.eventbuddy.model.speaker_model.SpeakerDetail;

public class SpeakersAdapter extends RecyclerView.Adapter<SpeakersAdapter.MyHolder> {

    Bundle bundle;
    private Context context;
    private ArrayList<SpeakerDetail> speakerList;

    public SpeakersAdapter(Context context, ArrayList<SpeakerDetail> speakerList) {

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
        final SpeakerDetail speakerData = speakerList.get( position );
        holder.nameTv.setText( speakerData.getFirstName() + " " + speakerData.getLastName() );
        holder.designationTv.setText( speakerData.getDesignation() );
        holder.organizationTv.setText( speakerData.getOrganization() );
        holder.item.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( context, ActivitySpeakerDetails.class );
                intent.putExtra( "Type", "Speaker" );
                intent.putExtra( "Tag", "Speaker" );
                bundle = new Bundle();
                bundle.putSerializable( "data", speakerData );
                intent.putExtras( bundle );
                context.startActivity( intent );

            }
        } );
        if (speakerData.getImage().contains( LocalStorage.getImagePath( context ) )) {
            Picasso.with( context ).load( speakerData.getImage() )
                    .placeholder( R.drawable.default_user_grey )
                    .into( holder.profileIv );
        } else {
            Picasso.with( context ).load( LocalStorage.getImagePath( context ).concat( speakerData.getImage() ) )
                    .placeholder( R.drawable.default_user_grey )
                    .into( holder.profileIv );
        }
    }

    @Override
    public int getItemCount() {
        return speakerList.size();
    }

    //filter speaker/delegate
    public void filterList(ArrayList<SpeakerDetail> filterdNames) {
        this.speakerList = filterdNames;
        notifyDataSetChanged();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView nameTv, organizationTv, designationTv;
        CircularImageView profileIv;
        RelativeLayout item;

        private MyHolder(View itemView) {
            super( itemView );

            nameTv = itemView.findViewById( R.id.nameTv );
            organizationTv = itemView.findViewById( R.id.organizationTv );
            designationTv = itemView.findViewById( R.id.designationTv );
            profileIv = itemView.findViewById( R.id.profileIv );
            item = itemView.findViewById( R.id.itemView );
        }
    }
}
