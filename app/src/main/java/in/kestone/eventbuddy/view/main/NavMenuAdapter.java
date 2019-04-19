package in.kestone.eventbuddy.view.main;

import android.content.Context;
import android.database.DataSetObserver;
import android.media.MediaCodec;
import android.util.Patterns;
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
import java.util.regex.Pattern;

import in.kestone.eventbuddy.Eventlistener.ViewClickListener;
import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.common.CONSTANTS;
import in.kestone.eventbuddy.common.LocalStorage;
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
        if (item.getMenutitle().equalsIgnoreCase( "Log Out" )) {
            Picasso.with( context ).load( "test" ).placeholder( R.drawable.logout_grey ).error( R.drawable.logout_grey ).into( vh.imageView );
        } else if (item.getMenutitle().equalsIgnoreCase( CONSTANTS.ACTIVITYSTREAM ) || item.getMenutitle().equalsIgnoreCase( CONSTANTS.ACTIVITYSTREAM2 )) {
            if( Patterns.WEB_URL.matcher(item.getMenuicon()).matches()) {
                Picasso.with( context ).load( item.getMenuicon() ).placeholder( R.drawable.activity_stream_grey ).error( R.drawable.activity_stream_grey ).into( vh.imageView );
            }else {
                Picasso.with( context ).load( LocalStorage.getImagePath( context ).concat( item.getMenuicon() )).placeholder( R.drawable.activity_stream_grey ).error( R.drawable.activity_stream_grey ).into( vh.imageView );
            }
        } else if (item.getMenutitle().equalsIgnoreCase( CONSTANTS.AGENDA )) {
            if( Patterns.WEB_URL.matcher(item.getMenuicon()).matches()) {
                Picasso.with( context ).load( item.getMenuicon() ).placeholder( R.drawable.agenda_grey ).error( R.drawable.agenda_grey ).into( vh.imageView );
            }else {
                Picasso.with( context ).load( LocalStorage.getImagePath( context ).concat( item.getMenuicon() ) ).placeholder( R.drawable.agenda_grey ).error( R.drawable.agenda_grey ).into( vh.imageView );
            }
        } else if (item.getMenutitle().equalsIgnoreCase( CONSTANTS.SPEAKER )) {
            if( Patterns.WEB_URL.matcher(item.getMenuicon()).matches()) {
                Picasso.with( context ).load( item.getMenuicon() ).placeholder( R.drawable.speakers_grey ).error( R.drawable.speakers_grey ).into( vh.imageView );
            }else {
                Picasso.with( context ).load( LocalStorage.getImagePath( context ).concat( item.getMenuicon() ) ).placeholder( R.drawable.speakers_grey ).error( R.drawable.speakers_grey ).into( vh.imageView );
            }
        } else if (item.getMenutitle().equalsIgnoreCase( CONSTANTS.DELEGATES )) {
            if( Patterns.WEB_URL.matcher(item.getMenuicon()).matches()) {
                Picasso.with( context ).load( item.getMenuicon() ).placeholder( R.drawable.delegates_grey ).error( R.drawable.delegates_grey ).into( vh.imageView );
            }else {
                Picasso.with( context ).load( LocalStorage.getImagePath( context ).concat( item.getMenuicon() ) ).placeholder( R.drawable.delegates_grey ).error( R.drawable.delegates_grey ).into( vh.imageView );
            }
        } else if (item.getMenutitle().equalsIgnoreCase( CONSTANTS.NETWORKING )) {
            if( Patterns.WEB_URL.matcher(item.getMenuicon()).matches()) {
                Picasso.with( context ).load( item.getMenuicon() ).placeholder( R.drawable.networking_grey ).error( R.drawable.networking_grey ).into( vh.imageView );
            }else {
                Picasso.with( context ).load( LocalStorage.getImagePath( context ).concat( item.getMenuicon() ) ).placeholder( R.drawable.networking_grey ).error( R.drawable.networking_grey ).into( vh.imageView );
            }
        } else if (item.getMenutitle().equalsIgnoreCase( CONSTANTS.POLLING )) {
            if( Patterns.WEB_URL.matcher(item.getMenuicon()).matches()) {
                Picasso.with( context ).load( item.getMenuicon() ).placeholder( R.drawable.polls_grey ).error( R.drawable.polls_grey ).into( vh.imageView );
            }else {
                Picasso.with( context ).load( LocalStorage.getImagePath( context ).concat( item.getMenuicon() ) ).placeholder( R.drawable.polls_grey ).error( R.drawable.polls_grey ).into( vh.imageView );
            }
        } else if (item.getMenutitle().equalsIgnoreCase( CONSTANTS.ASKAQUESTION )) {
            if( Patterns.WEB_URL.matcher(item.getMenuicon()).matches()) {
                Picasso.with( context ).load( item.getMenuicon() ).placeholder( R.drawable.question_grey ).error( R.drawable.question_grey ).into( vh.imageView );
            }else {
                Picasso.with( context ).load( LocalStorage.getImagePath( context ).concat( item.getMenuicon() ) ).placeholder( R.drawable.question_grey ).error( R.drawable.question_grey ).into( vh.imageView );
            }
        } else if (item.getMenutitle().equalsIgnoreCase( CONSTANTS.SOCIAL )) {
            if( Patterns.WEB_URL.matcher(item.getMenuicon()).matches()) {
                Picasso.with( context ).load( item.getMenuicon() ).placeholder( R.drawable.social_grey ).error( R.drawable.social_grey ).into( vh.imageView );
            }else {
                Picasso.with( context ).load( LocalStorage.getImagePath( context ).concat( item.getMenuicon() ) ).placeholder( R.drawable.social_grey ).error( R.drawable.social_grey ).into( vh.imageView );
            }
        } else if (item.getMenutitle().equalsIgnoreCase( CONSTANTS.VENUE )) {
            if( Patterns.WEB_URL.matcher(item.getMenuicon()).matches()) {
                Picasso.with( context ).load( item.getMenuicon() ).placeholder( R.drawable.venue_grey ).error( R.drawable.venue_grey ).into( vh.imageView );
            }else {
                Picasso.with( context ).load( LocalStorage.getImagePath( context ).concat( item.getMenuicon() ) ).placeholder( R.drawable.venue_grey ).error( R.drawable.venue_grey ).into( vh.imageView );
            }
        } else if (item.getMenutitle().equalsIgnoreCase( CONSTANTS.FEEDBACK )) {
            if( Patterns.WEB_URL.matcher(item.getMenuicon()).matches()) {
                Picasso.with( context ).load( item.getMenuicon() ).placeholder( R.drawable.feedback_grey ).error( R.drawable.feedback_grey ).into( vh.imageView );
            }else {
                Picasso.with( context ).load( LocalStorage.getImagePath( context ).concat( item.getMenuicon() ) ).placeholder( R.drawable.feedback_grey ).error( R.drawable.feedback_grey ).into( vh.imageView );
            }
        } else if (item.getMenutitle().equalsIgnoreCase( CONSTANTS.HELPDESK )) {
            if( Patterns.WEB_URL.matcher(item.getMenuicon()).matches()) {
                Picasso.with( context ).load( item.getMenuicon() ).placeholder( R.drawable.help_desk_grey ).error( R.drawable.help_desk_grey ).into( vh.imageView );
            }    else{
            Picasso.with( context ).load( LocalStorage.getImagePath( context ).concat( item.getMenuicon() ) ).placeholder( R.drawable.help_desk_grey ).error( R.drawable.help_desk_grey ).into( vh.imageView );
            }
        } else if (item.getMenutitle().equalsIgnoreCase( CONSTANTS.PARTNERS )) {
            if( Patterns.WEB_URL.matcher(item.getMenuicon()).matches()) {
                Picasso.with( context ).load( item.getMenuicon() ).placeholder( R.drawable.default_user_grey ).error( R.drawable.default_user_grey ).into( vh.imageView );
            }else {
                Picasso.with( context ).load( LocalStorage.getImagePath( context ).concat( item.getMenuicon() ) ).placeholder( R.drawable.default_user_grey ).error( R.drawable.default_user_grey ).into( vh.imageView );
            }
        } else if (item.getMenutitle().equalsIgnoreCase( CONSTANTS.SPONSORS )) {
            if( Patterns.WEB_URL.matcher(item.getMenuicon()).matches()) {
                Picasso.with( context ).load( item.getMenuicon() ).placeholder( R.drawable.default_user_grey ).error( R.drawable.default_user_grey ).into( vh.imageView );
            }else {
                Picasso.with( context ).load( LocalStorage.getImagePath( context ).concat( item.getMenuicon() ) ).placeholder( R.drawable.default_user_grey ).error( R.drawable.default_user_grey ).into( vh.imageView );
            }
        } else if (item.getMenutitle().equalsIgnoreCase( CONSTANTS.KNOWLEDGEBASE )) {
            if( Patterns.WEB_URL.matcher(item.getMenuicon()).matches()) {
                Picasso.with( context ).load( item.getMenuicon() ).placeholder( R.drawable.knowledge_bank ).error( R.drawable.knowledge_bank ).into( vh.imageView );
            }else {
                Picasso.with( context ).load( LocalStorage.getImagePath( context ).concat( item.getMenuicon() ) ).placeholder( R.drawable.knowledge_bank ).error( R.drawable.knowledge_bank ).into( vh.imageView );
            }
        } else if (item.getMenutitle().equalsIgnoreCase( CONSTANTS.FAQS )) {
            if( Patterns.WEB_URL.matcher(item.getMenuicon()).matches()) {
                Picasso.with( context ).load( item.getMenuicon() ).placeholder( R.drawable.faq_grey ).error( R.drawable.faq_grey ).into( vh.imageView );
            }else {
                Picasso.with( context ).load( LocalStorage.getImagePath( context ).concat( item.getMenuicon() ) ).placeholder( R.drawable.faq_grey ).error( R.drawable.faq_grey ).into( vh.imageView );
            }
        } else if (item.getMenutitle().equalsIgnoreCase( CONSTANTS.TANDC )) {
            if( Patterns.WEB_URL.matcher(item.getMenuicon()).matches()) {
                Picasso.with( context ).load( item.getMenuicon() ).placeholder( R.drawable.ask_question ).error( R.drawable.ask_question ).into( vh.imageView );
            }else {
                Picasso.with( context ).load( LocalStorage.getImagePath( context ).concat( item.getMenuicon() ) ).placeholder( R.drawable.ask_question ).error( R.drawable.ask_question ).into( vh.imageView );
            }
        }else if (item.getMenutitle().equalsIgnoreCase( CONSTANTS.NOTIFICATION )) {
            if( Patterns.WEB_URL.matcher(item.getMenuicon()).matches()) {
                Picasso.with( context ).load( item.getMenuicon() ).placeholder( R.drawable.notification_grey ).error( R.drawable.notification_grey ).into( vh.imageView );
            }else {
                Picasso.with( context ).load( LocalStorage.getImagePath( context ).concat( item.getMenuicon()) ).placeholder( R.drawable.notification_grey ).error( R.drawable.notification_grey ).into( vh.imageView );
            }
        }


        vh.layoutDetail.setOnClickListener( new View.OnClickListener() {
            boolean visible;

            @Override
            public void onClick(View v) {
                Animation animation1 = new AlphaAnimation( 0.0f, 0.5f );
                animation1.setDuration( 100 );
                v.startAnimation( animation1 );

                vcl.onClick( item.getMenuid(), item.getDisplayTitle(), item.getMenutitle(), item.getHeader(), item.getSubheader() );
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