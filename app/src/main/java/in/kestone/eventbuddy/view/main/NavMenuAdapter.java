package in.kestone.eventbuddy.view.main;

import android.content.Context;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;

import java.util.List;

import in.kestone.eventbuddy.Eventlistener.ViewClickListener;
import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.model.app_config_model.Menu;
import in.kestone.eventbuddy.widgets.CustomTextView;

public class NavMenuAdapter<T> implements ListAdapter {

    private List<Menu> list;
    private Context context;
    private ViewClickListener vcl;
    private LayoutInflater mInflater;

    NavMenuAdapter(MainActivity activity, List<Menu> list) {
        this.context = activity;
        this.mInflater = LayoutInflater.from( context );
        this.list = list;
        vcl = (ViewClickListener) context;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int i) {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Menu getItem(int i) {
        return list.get( i );
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int i, final View view, final ViewGroup viewGroup) {
        final ViewHolder vh;
        if (view == null) {
            View view2 = mInflater.inflate( R.layout.menu_list, viewGroup, false );
            vh = ViewHolder.create( (RelativeLayout) view2 );
            view2.setTag( vh );
        } else {
            vh = (ViewHolder) view.getTag();
        }

        final Menu item = getItem( i );
            vh.textViewName.setText( item.getDisplayTitle() );
            if(item.getMenutitle().equalsIgnoreCase( "Log Out" )){
                Picasso.with( context ).load( item.getMenuicon() ).placeholder( R.drawable.log_out).error( R.drawable.log_out).into( vh.imageView );
            }else if(item.getMenutitle().equalsIgnoreCase( "Activity Stream" )){
                Picasso.with( context ).load( item.getMenuicon()).placeholder( R.drawable.ic_activity_stream ).error( R.drawable.ic_activity_stream ).into( vh.imageView );
            }else if(item.getMenutitle().equalsIgnoreCase( "Agenda" )){
                Picasso.with( context ).load( item.getMenuicon()).placeholder( R.drawable.ic_agenda ).error( R.drawable.ic_agenda ).into( vh.imageView );
            }else if(item.getMenutitle().equalsIgnoreCase( "Speakers" )){
                Picasso.with( context ).load( item.getMenuicon()).placeholder(R.drawable.ic_speaker ).error( R.drawable.ic_speaker).into( vh.imageView );
            }else if(item.getMenutitle().equalsIgnoreCase( "Delegates" )){
                Picasso.with( context ).load( item.getMenuicon()).placeholder( R.drawable.delegates ).error( R.drawable.delegates).into( vh.imageView );
            }else if(item.getMenutitle().equalsIgnoreCase( "Networking" )){
                Picasso.with( context ).load( item.getMenuicon()).placeholder( R.drawable.ic_menu_slideshow ).error( R.drawable.ic_menu_slideshow).into( vh.imageView );
            }else if(item.getMenutitle().equalsIgnoreCase( "Polls" )){
                Picasso.with( context ).load( item.getMenuicon()).placeholder( R.drawable.ic_polls ).error( R.drawable.ic_polls).into( vh.imageView );
            }else if(item.getMenutitle().equalsIgnoreCase( "Ask a Question" )){
                Picasso.with( context ).load( item.getMenuicon()).placeholder( R.drawable.ask_question ).error( R.drawable.ask_question).into( vh.imageView );
            }else if(item.getMenutitle().equalsIgnoreCase( "Social" )){
                Picasso.with( context ).load( item.getMenuicon()).placeholder( R.drawable.ic_menu_share ).error( R.drawable.ic_menu_share).into( vh.imageView );
            }else if(item.getMenutitle().equalsIgnoreCase( "Gallery" )){
                Picasso.with( context ).load( item.getMenuicon()).placeholder( R.drawable.ic_gallery_logo ).error( R.drawable.ic_gallery_logo ).into( vh.imageView );
            }else if(item.getMenutitle().equalsIgnoreCase( "Venue" )){
                Picasso.with( context ).load( item.getMenuicon()).placeholder( R.drawable.ic_location ).error( R.drawable.ic_location ).into( vh.imageView );
            }else if(item.getMenutitle().equalsIgnoreCase( "Feedback" )){
                Picasso.with( context ).load( item.getMenuicon() ).placeholder( R.drawable.ic_feedback ).error( R.drawable.ic_feedback ).into( vh.imageView );
            }else if(item.getMenutitle().equalsIgnoreCase( "Help Desk" )){
                Picasso.with( context ).load( item.getMenuicon() ).placeholder( R.drawable.ic_help ).error( R.drawable.ic_help  ).into( vh.imageView );
            }




        vh.layoutDetail.setOnClickListener( new View.OnClickListener() {
            boolean visible;

            @Override
            public void onClick(View v) {
                Animation animation1 = new AlphaAnimation( 0.0f, 0.5f );
                animation1.setDuration( 100 );
                v.startAnimation( animation1 );

                 vcl.onClick(item.getMenuid(), item.getDisplayTitle(), item.getMenutitle());
            }
        } );



        return vh.rootView;
    }

    @Override
    public int getItemViewType(int i) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    private static class ViewHolder {
        private final RelativeLayout rootView;
        private final ImageView imageView;
        private final CustomTextView textViewName;
        private final RelativeLayout layoutDetail;

        private ViewHolder(RelativeLayout rootView, ImageView imageView, CustomTextView textViewName, RelativeLayout layoutDetail) {
            this.rootView = rootView;
            this.imageView = imageView;
            this.textViewName = textViewName;
            this.layoutDetail = layoutDetail;
        }

        public static ViewHolder create(RelativeLayout rootView) {
            ImageView imageView = rootView.findViewById( R.id.imageView );
            CustomTextView textViewName = rootView.findViewById( R.id.textViewName );
            RelativeLayout layoutDetail = rootView.findViewById( R.id.layout_detail );

            return new ViewHolder( rootView, imageView, textViewName, layoutDetail );
        }
    }
}