package in.kestone.eventbuddy.view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.kestone.eventbuddy.Altdialog.CustomDialog;
import in.kestone.eventbuddy.Altdialog.Progress;
import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.common.CONSTANTS;
import in.kestone.eventbuddy.common.LocalStorage;
import in.kestone.eventbuddy.http.APIClient;
import in.kestone.eventbuddy.http.APIInterface;
import in.kestone.eventbuddy.http.CallUtils;
import in.kestone.eventbuddy.model.ActiveEventModel;
import in.kestone.eventbuddy.model.EventName;
import in.kestone.eventbuddy.view.splash.ActivitySplash;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectEventActivity extends AppCompatActivity {


    @BindView(R.id.recyclerView_select_event)
    RecyclerView recyclerView_select_event;
    @BindView(R.id.layout_reload)
    SwipeRefreshLayout refreshLayout;
    int resId = R.anim.layout_animation_fall_down;

    ArrayList<EventName> eventList = new ArrayList<>();
    SelectEventAdapter adapter;
    LayoutAnimationController animation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        requestWindowFeature( Window.FEATURE_NO_TITLE );
        getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN );

        setContentView( R.layout.activity_select_event );


        ButterKnife.bind( this );
        LinearLayoutManager mLayoutManager = new LinearLayoutManager( SelectEventActivity.this );
        mLayoutManager.setOrientation( LinearLayoutManager.VERTICAL );
        recyclerView_select_event.setLayoutManager( mLayoutManager );
        recyclerView_select_event.setHasFixedSize( true );

        if (LocalStorage.getEventID( SelectEventActivity.this ) == 0) {
            getAllActiveEvent();
            Progress.showProgress( this );
        } else {
            startActivity( new Intent( SelectEventActivity.this, ActivitySplash.class ) );
            finish();
        }

        refreshLayout.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getAllActiveEvent();
                Progress.showProgress( SelectEventActivity.this );
                refreshLayout.setRefreshing( false );
            }
        } );

    }

    private void getAllActiveEvent() {
        eventList.clear();
        APIInterface apiInterface = APIClient.getClient().create( APIInterface.class );
        Call<ActiveEventModel> call = apiInterface.activeEventList();
        CallUtils.enqueueWithRetry( call, 2, new Callback<ActiveEventModel>() {
            @Override
            public void onResponse(Call<ActiveEventModel> call, Response<ActiveEventModel> response) {
                if (response.isSuccessful()) {
                    eventList.addAll( response.body().getData().getEventName() );
                    adapter = new SelectEventAdapter( SelectEventActivity.this, eventList );
                    recyclerView_select_event.setAdapter( adapter );
                    adapter.notifyDataSetChanged();
                    animation = AnimationUtils.loadLayoutAnimation( SelectEventActivity.this, resId );
                    recyclerView_select_event.setLayoutAnimation( animation );
                    Progress.closeProgress();
                } else {
                    CustomDialog.showInvalidPopUp( SelectEventActivity.this, CONSTANTS.ERROR, response.message() );
                    Progress.closeProgress();
                }


            }

            @Override
            public void onFailure(Call<ActiveEventModel> call, Throwable t) {
                CustomDialog.showInvalidPopUp( SelectEventActivity.this, CONSTANTS.ERROR, CONSTANTS.CONNECTIONERROR );
                Progress.closeProgress();
            }
        } );


    }

    private class SelectEventAdapter extends RecyclerView.Adapter<SelectEventAdapter.ViewHolder> {
        Activity activity;
        ArrayList<EventName> eventList;

        public SelectEventAdapter(SelectEventActivity selectEventActivity, ArrayList<EventName> eventList) {
            this.activity = selectEventActivity;
            this.eventList = eventList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from( viewGroup.getContext() ).inflate( R.layout.active_eventlist_cell, viewGroup, false );

            return new SelectEventAdapter.ViewHolder( view );
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
            EventName eventName = eventList.get( i );
            holder.tv_event_name.setText( eventName.getEventName() );
            if (eventName.getAppBannerImage() != null) {
                Glide.with( activity )
                        .load( CONSTANTS.betaimagepath.concat( eventName.getAppBannerImage() ) )
                        .placeholder( R.drawable.gallery_grey )
                        .centerCrop()
                        .error( R.drawable.gallery_grey )
                        .into( holder.imageEvent );
            }

            holder.layout.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText( activity, "App will restart to reconfigure event", Toast.LENGTH_SHORT ).show();
                    LocalStorage.saveEventID( eventName.getEventID(), activity );
                    LocalStorage.saveSplashBackground( eventName.getSplashScreen(), activity );
                    LocalStorage.saveBackground( eventName.getBackgroundImage(), activity );
                    LocalStorage.saveMasterHead( eventName.getMastheadImage(), activity );
                    activity.startActivity( new Intent( activity, ActivitySplash.class ) );
                    activity.finish();
                }
            } );

        }

        @Override
        public int getItemCount() {
            return eventList.size();
        }

        private class ViewHolder extends RecyclerView.ViewHolder {
            ImageView imageEvent;
            TextView tv_event_name;
            RelativeLayout layout;

            public ViewHolder(View view) {
                super( view );
                imageEvent = view.findViewById( R.id.image_event );
                tv_event_name = view.findViewById( R.id.tv_event_name );
                layout = view.findViewById( R.id.layout_active_eventlist );
            }
        }
    }
}
