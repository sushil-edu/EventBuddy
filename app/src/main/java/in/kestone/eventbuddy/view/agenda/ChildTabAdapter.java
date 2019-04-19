package in.kestone.eventbuddy.view.agenda;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.model.agenda_model.Track;

public class ChildTabAdapter extends RecyclerView.Adapter<ChildTabAdapter.ViewHolder> {

    TabClick tabClick;
    private Context context;
    private Activity activity;
    private ArrayList<Track> trackList;
    private int parentTabPos, selectedPos=0;

    ChildTabAdapter(Activity context, ArrayList<Track> trackList, TabClick tabClick, int parentTabPos) {
        this.context = context;
        this.trackList = trackList;
        this.activity = context;
        this.tabClick = tabClick;
        this.parentTabPos = parentTabPos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.track_tab, parent, false );

        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.title.setText( trackList.get( position ).getTrackName() );

        if(selectedPos==position){
            holder.title.setTextColor( context.getResources().getColor( R.color.colorPrimaryDark ) );
            holder.viewIndicator.setBackgroundColor( context.getResources().getColor( R.color.colorPrimaryDark ) );
            holder.viewIndicator.setVisibility( View.VISIBLE );
        } else {
            holder.title.setTextColor( context.getResources().getColor( android.R.color.black ) );
            holder.viewIndicator.setVisibility( View.GONE );
        }

        holder.root.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tabClick.onTabClick( position);
                selectedPos = position;
                notifyDataSetChanged();
            }
        } );
    }

    @Override
    public int getItemCount() {
        return trackList.size();
    }

    public interface TabClick {
        void onTabClick(int pos);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private LinearLayout root;
        private View viewIndicator;

        private ViewHolder(View itemView) {
            super( itemView );

            title = itemView.findViewById( R.id.tv_tab_title );
            root = itemView.findViewById( R.id.root );
            viewIndicator =itemView.findViewById( R.id.viewIndicator );

        }
    }

}
