package in.kestone.eventbuddy.view.stream;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pkmmte.view.CircularImageView;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import in.kestone.eventbuddy.Altdialog.CustomDialog;
import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.common.LocalStorage;
import in.kestone.eventbuddy.data.SharedPrefsHelper;
import in.kestone.eventbuddy.model.activity_stream_model.StreamDatum;
import in.kestone.eventbuddy.model.speaker_model.SpeakerDetail;
import in.kestone.eventbuddy.view.speaker.ActivitySpeakerDetails;

class ActivityStreamAdapter extends RecyclerView.Adapter<ActivityStreamAdapter.MyHolder> {

    DateFormat writeFormat = new SimpleDateFormat( "MMM dd yyyy hh:mm aaa" );
    DateFormat readFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss" );
    private Context mContext;
    private ArrayList<StreamDatum> streamList;

    public ActivityStreamAdapter(Context mContext, ArrayList<StreamDatum> streamList) {
        this.mContext = mContext;
        this.streamList = streamList;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.stream_cell, parent, false );
        return new MyHolder( view );
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        StreamDatum streamData = streamList.get( position );
        holder.nameTv.setText( streamData.getFirstName() + " " + streamData.getLastName() );
        holder.designationTv.setText( streamData.getDesignation() );
        String[] st = streamData.getDt().split( "ago" );
        String str = streamData.getDt();
        holder.timeTv.setText( st[0] + "\n" + streamData.getDt().substring( str.length()-3, str.length() ) );


        holder.layout_profile.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent( mContext, ActivitySpeakerDetails.class );
                SpeakerDetail speakerDetail = new SpeakerDetail();
                speakerDetail.setUserID( Math.toIntExact( streamData.getUserID() ) );
                speakerDetail.setFirstName(streamData.getFirstName());
                speakerDetail.setLastName( streamData.getLastName() );
                speakerDetail.setDesignation( streamData.getDesignation() );
                speakerDetail.setOrganization( streamData.getOrganization() );
                speakerDetail.setImage( LocalStorage.getImagePath( mContext ).concat( streamData.getImage()));
                Bundle bundle = new Bundle();
                bundle.putSerializable( "data", speakerDetail );
                intent.putExtras( bundle );
                mContext.startActivity( intent );
            }
        } );

        if (!streamData.getComment().isEmpty()) {
            holder.mActivityTv.setVisibility( View.VISIBLE );
            holder.mActivityTv.setText( streamData.getComment() );
        } else {
            holder.mActivityTv.setVisibility( View.GONE );
        }

        if (URLUtil.isValidUrl( streamData.getImageURL() )) {
            holder.mActivityIv.setVisibility( View.VISIBLE );
            Glide.with( mContext )
                    .load( streamData.getImageURL() )
                    .placeholder( R.drawable.gallery_grey )
                    .centerCrop()
                    .into( holder.mActivityIv );

            holder.mActivityIv.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CustomDialog.imagePreview( mContext, streamData.getImageURL() );
                }
            } );
        } else {
            holder.mActivityIv.setVisibility( View.GONE );
        }

        Picasso.with( mContext )
                .load( LocalStorage.getImagePath( mContext ).concat( streamData.getImage()) )
                .placeholder( R.drawable.default_user_grey )
                .error( R.drawable.default_user_grey )
                .into( holder.userIv );
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


    @Override
    public int getItemCount() {
        return streamList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        private LinearLayout layout_profile;
        private CircularImageView userIv;
        private TextView nameTv, timeTv, designationTv, mActivityTv;
        //                private TopCropImageView mActivityIv;
        private ImageView mActivityIv;

        public MyHolder(View itemView) {
            super( itemView );
            layout_profile = itemView.findViewById( R.id.layout_profile );
            userIv = itemView.findViewById( R.id.userIv );
            nameTv = itemView.findViewById( R.id.nameTv );
            timeTv = itemView.findViewById( R.id.timeTv );
            designationTv = itemView.findViewById( R.id.designationTv );
            mActivityTv = itemView.findViewById( R.id.mActivityTv );
            mActivityIv = itemView.findViewById( R.id.mActivityIv );


        }
    }

}
