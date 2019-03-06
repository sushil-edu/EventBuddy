package in.kestone.eventbuddy.view.partners;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import in.kestone.eventbuddy.Eventlistener.PartnerDetailsCallback;
import in.kestone.eventbuddy.R;

public class PartnerAdapter extends RecyclerView.Adapter<PartnerAdapter.ViewHolder> {

    Activity activity;
    ArrayList<in.kestone.eventbuddy.model.partners_model.List> detailArrayList;
    PartnerDetailsCallback detailsCallback;
    public PartnerAdapter(FragmentActivity activity, ArrayList<in.kestone.eventbuddy.model.partners_model.List> detailArrayList, PartnerDetailsCallback detailsCallback) {
        this.activity = activity;
        this.detailArrayList = detailArrayList;
        this.detailsCallback = detailsCallback;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.partner_cell, parent, false );

        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Picasso.with( activity ).load( detailArrayList.get( position ).getLogo() )
                .resize( 80, 80 )
                .placeholder( R.drawable.user )
                .into( holder.partnerLogo );
        holder.partnerName.setText( detailArrayList.get( position ).getName() );

        holder.rootLayout.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                detailsCallback.onDetailClickCallback( detailArrayList.get( position ) );
            }
        } );
    }

    @Override
    public int getItemCount() {
        return detailArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView partnerLogo;
        TextView partnerName;
        RelativeLayout rootLayout;
        public ViewHolder(View view) {
            super( view );

            partnerLogo = view.findViewById( R.id.partnerIv );
            partnerName = view.findViewById( R.id.partnerName );

            rootLayout = view.findViewById( R.id.layout_root );
        }
    }

}
