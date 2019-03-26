package in.kestone.eventbuddy.view.stream;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pkmmte.view.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.data.SharedPrefsHelper;
import in.kestone.eventbuddy.model.activity_stream_model.StreamDatum;
import in.kestone.eventbuddy.widgets.TopCropImageView;

class ActivityStreamAdapter extends RecyclerView.Adapter<ActivityStreamAdapter.MyHolder> {

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

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
//        holder.nameLl.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mContext, SpeakerDetailsActivity.class);
//                mContext.startActivity(intent);
//            }
//        });

        StreamDatum streamData = streamList.get( position );
        holder.nameTv.setText( new SharedPrefsHelper( mContext ).getName() );
        holder.designationTv.setText( new SharedPrefsHelper( mContext ).getDesignation() );
        holder.timeTv.setVisibility( View.GONE );//.setText(streamData.getPunchTime());

        if (!streamData.getComment().isEmpty()) {
            holder.mActivityTv.setVisibility( View.VISIBLE );
            holder.mActivityTv.setText( streamData.getComment() );
        } else {
            holder.mActivityTv.setVisibility( View.GONE );
        }

        if (URLUtil.isValidUrl( streamData.getImageURL() )) {
            holder.mActivityIv.setVisibility( View.VISIBLE );

            Picasso.with( mContext )
                    .load( streamData.getImageURL() )
                    .placeholder( R.drawable.def_image )
                    .error( R.drawable.def_image )
                    .into( holder.mActivityIv );

        } else {
            holder.mActivityIv.setVisibility( View.GONE );
        }

        // int pos = getItemViewType(position);

//        if (streamData.getImageURL().length() > 0) {
        Picasso.with( mContext )
                .load( "aksjaks" )
                .placeholder( R.drawable.user )
                .error( R.drawable.user )
                .into( holder.userIv );
//        }else {
//            holder.userIv.setBackgroundResource(R.drawable.ic_user);
//        }

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

        ImageView imageView;
        private LinearLayout nameLl;
        private CircularImageView userIv;
        private TextView nameTv, timeTv, designationTv, mActivityTv;
        private TopCropImageView mActivityIv;

        public MyHolder(View itemView) {
            super( itemView );
//            nameLl = itemView.findViewById(R.id.nameLl);
            userIv = itemView.findViewById( R.id.userIv );
            nameTv = itemView.findViewById( R.id.nameTv );
            timeTv = itemView.findViewById( R.id.timeTv );
            designationTv = itemView.findViewById( R.id.designationTv );
            mActivityTv = itemView.findViewById( R.id.mActivityTv );
            mActivityIv = itemView.findViewById( R.id.mActivityIv );


        }
    }
}
