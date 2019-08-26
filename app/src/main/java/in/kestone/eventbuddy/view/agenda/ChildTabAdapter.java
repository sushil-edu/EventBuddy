package in.kestone.eventbuddy.view.agenda;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.model.agenda_model.Track;

public class ChildTabAdapter extends RecyclerView.Adapter<ChildTabAdapter.ViewHolder> {

    private TabClick tabClick;
    private Context context;
    Activity activity;
    private ArrayList<Track> trackList;
    private int  selectedPos=-1;


    ChildTabAdapter(Activity context, ArrayList<Track> trackList, TabClick tabClick, boolean commonSessionFlag) {
        this.context = context;
        this.trackList = trackList;
        this.activity = context;
        this.tabClick = tabClick;

        if(commonSessionFlag){
            selectedPos=0;
        }else
            selectedPos=-1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.track_tab, parent, false );

        return new ViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        holder.title.setText( trackList.get( position ).getTrackName() );

        if(selectedPos==position){
            holder.title.setTextColor( context.getResources().getColor( R.color.white ) );
            holder.title.setTypeface( Typeface.defaultFromStyle( Typeface.BOLD ) );
            holder.viewIndicator.setBackgroundColor( context.getResources().getColor( R.color.white ) );
            holder.viewIndicator.setVisibility( View.VISIBLE                             );
        } else {
            holder.title.setTextColor( context.getResources().getColor( R.color.colorAccent ) );
            holder.title.setTypeface( Typeface.defaultFromStyle( Typeface.NORMAL ) );
            holder.viewIndicator.setVisibility( View.GONE );
        }

        holder.root.setOnClickListener(view -> {
            tabClick.onTabClick( position);
            selectedPos = position;
            notifyDataSetChanged();
        });
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
        private RelativeLayout root;
        private View viewIndicator;

        private ViewHolder(View itemView) {
            super( itemView );

            title = itemView.findViewById( R.id.tv_tab_title );
            root = itemView.findViewById( R.id.root );
            viewIndicator =itemView.findViewById( R.id.viewIndicator );

        }
    }

}
