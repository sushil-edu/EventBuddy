package in.kestone.eventbuddy.view.agenda;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import in.kestone.eventbuddy.Altdialog.CustomDialog;
import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.data.SharedPrefsHelper;
import in.kestone.eventbuddy.model.speaker_model.SpeakerDetail;
import in.kestone.eventbuddy.view.speaker.ActivitySpeaterDetails;
import in.kestone.eventbuddy.widgets.CustomTextView;

public class SpeakerAdapter extends RecyclerView.Adapter<SpeakerAdapter.ViewHolder> {

    private Context context;
    private ArrayList<SpeakerDetail> speakerList;

    SpeakerAdapter(Activity context, ArrayList<SpeakerDetail> detailArrayList) {
        this.context = context;
        this.speakerList = detailArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.speaker_cell, parent, false );

        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final SpeakerDetail speakerData = speakerList.get( position );
//        holder.typeTv.setText(speakerDate.get());
//        Log.e("Name ", speakerList.get( position ).getFirstName());

        holder.nameTv.setText( speakerData.getFirstName() + " " + speakerData.getLastName() );
        holder.designationTv.setText( speakerData.getDesignation() );
        holder.organisationTv.setText( speakerData.getOrganization() );


        holder.cardView.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (speakerData.getUserID() != new SharedPrefsHelper( context ).getUserId()) {
                    if (speakerData.getFirstName().equalsIgnoreCase( "Add" )) {
                        context.startActivity( new Intent( context, AddSpeaker.class ) );
                        ((Activity) context).finish();
                    } else {
                        Intent intent = new Intent( context, ActivitySpeaterDetails.class );
//                    intent.putExtra( "Name", speakerData.getSpeakerName() );
//                    intent.putExtra( "Designation", speakerData.getDesignation() );
//                    intent.putExtra( "Organization", speakerData.getOrganization() );
//                    intent.putExtra( "Email", speakerData.getBio() );
//                    intent.putExtra( "Image", speakerData.getSpeakerImage() );
//                    intent.putExtra( "Type", "Speaker" );
//                    intent.putExtra( "Tag", "Agenda" );
                        Bundle bundle = new Bundle();
                        bundle.putSerializable( "data", speakerData );
//                    intent.putExtra( "details", speakerData.getDescription() );
                        intent.putExtras( bundle );
                        context.startActivity( intent );
//                    ((Activity)context).finish();
                    }
                }else {
                    CustomDialog.showInvalidPopUp( context,"","You should not send meeting request yourself" );
                }
            }
        } );

//        Log.d("NestedImg",nestedSpeakerData.getSpeakerImageURL());

        Picasso.with( context ).load( speakerData.getImage() )
                .resize( 80, 80 )
                .placeholder( R.drawable.user )
                .into( holder.profileIv );

    }

    @Override
    public int getItemCount() {
        return speakerList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        private CustomTextView typeTv, nameTv, designationTv, organisationTv;
        private com.pkmmte.view.CircularImageView profileIv;

        private ViewHolder(View itemView) {
            super( itemView );

            typeTv = itemView.findViewById( R.id.typeTv );
            nameTv = itemView.findViewById( R.id.nameTv );
            designationTv = itemView.findViewById( R.id.designationTv );
            organisationTv = itemView.findViewById( R.id.organisationTv );
            profileIv = itemView.findViewById( R.id.profileIv );
            cardView = itemView.findViewById( R.id.card );
        }
    }


}
