package in.kestone.eventbuddy.adapter;

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
import in.kestone.eventbuddy.model.app_config.Menu;
import in.kestone.eventbuddy.view.main.MainActivity;
import in.kestone.eventbuddy.widgets.CustomTextView;

public class NavMenuAdapter<T> implements ListAdapter {

    List<Menu> list;
    Context context;
    ViewClickListener vcl;
    private LayoutInflater mInflater;

    public NavMenuAdapter(MainActivity activity, List<Menu> list) {
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
            vh.textViewName.setText( item.getMenutitle() );
            if(item.getMenutitle().equalsIgnoreCase( "Log Out" )){
                Picasso.with( context ).load( R.drawable.log_out ).placeholder( R.mipmap.ic_launcher_round ).error( R.mipmap.ic_launcher ).into( vh.imageView );
            }else if(item.getMenutitle().equalsIgnoreCase( "Activity Stream" )){
                Picasso.with( context ).load( R.drawable.ic_activity_stream ).placeholder( R.mipmap.ic_launcher_round ).error( R.mipmap.ic_launcher ).into( vh.imageView );
            }else if(item.getMenutitle().equalsIgnoreCase( "Agenda" )){
                Picasso.with( context ).load( R.drawable.ic_agenda ).placeholder( R.mipmap.ic_launcher_round ).error( R.mipmap.ic_launcher ).into( vh.imageView );
            }else if(item.getMenutitle().equalsIgnoreCase( "Speakers" )){
                Picasso.with( context ).load( R.drawable.ic_speaker ).placeholder( R.mipmap.ic_launcher_round ).error( R.mipmap.ic_launcher ).into( vh.imageView );
            }else if(item.getMenutitle().equalsIgnoreCase( "Delegates" )){
                Picasso.with( context ).load( R.drawable.delegates ).placeholder( R.mipmap.ic_launcher_round ).error( R.mipmap.ic_launcher ).into( vh.imageView );
            }else if(item.getMenutitle().equalsIgnoreCase( "Networking" )){
                Picasso.with( context ).load( R.drawable.ic_menu_slideshow ).placeholder( R.mipmap.ic_launcher_round ).error( R.mipmap.ic_launcher ).into( vh.imageView );
            }else if(item.getMenutitle().equalsIgnoreCase( "Polls" )){
                Picasso.with( context ).load( R.drawable.ic_polls ).placeholder( R.mipmap.ic_launcher_round ).error( R.mipmap.ic_launcher ).into( vh.imageView );
            }else if(item.getMenutitle().equalsIgnoreCase( "Ask a Question" )){
                Picasso.with( context ).load( R.drawable.ask_question ).placeholder( R.mipmap.ic_launcher_round ).error( R.mipmap.ic_launcher ).into( vh.imageView );
            }else if(item.getMenutitle().equalsIgnoreCase( "Social" )){
                Picasso.with( context ).load( R.drawable.ic_menu_share ).placeholder( R.mipmap.ic_launcher_round ).error( R.mipmap.ic_launcher ).into( vh.imageView );
            }else if(item.getMenutitle().equalsIgnoreCase( "Gallery" )){
                Picasso.with( context ).load( R.drawable.ic_gallery_logo ).placeholder( R.mipmap.ic_launcher_round ).error( R.mipmap.ic_launcher ).into( vh.imageView );
            }else if(item.getMenutitle().equalsIgnoreCase( "Venue" )){
                Picasso.with( context ).load( R.drawable.ic_location ).placeholder( R.mipmap.ic_launcher_round ).error( R.mipmap.ic_launcher ).into( vh.imageView );
            }else if(item.getMenutitle().equalsIgnoreCase( "Feedback" )){
                Picasso.with( context ).load( R.drawable.ic_feedback ).placeholder( R.mipmap.ic_launcher_round ).error( R.mipmap.ic_launcher ).into( vh.imageView );
            }else if(item.getMenutitle().equalsIgnoreCase( "Help Desk" )){
                Picasso.with( context ).load( R.drawable.ic_help ).placeholder( R.mipmap.ic_launcher_round ).error( R.mipmap.ic_launcher ).into( vh.imageView );
            }




        vh.layoutDetail.setOnClickListener( new View.OnClickListener() {
            boolean visible;

            @Override
            public void onClick(View v) {
                Animation animation1 = new AlphaAnimation( 0.0f, 0.5f );
                animation1.setDuration( 100 );
                v.startAnimation( animation1 );

                 vcl.onClick(item.getMenuid(), item.getMenutitle());
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
        public final RelativeLayout rootView;
        public final ImageView imageView;
        public final CustomTextView textViewName;
        public final RelativeLayout layoutDetail;

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