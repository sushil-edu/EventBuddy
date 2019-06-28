package in.kestone.eventbuddy.view.stream;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import java.util.ArrayList;

import in.kestone.eventbuddy.Altdialog.CustomDialog;
import in.kestone.eventbuddy.Altdialog.Progress;
import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.common.CONSTANTS;
import in.kestone.eventbuddy.common.LocalStorage;
import in.kestone.eventbuddy.http.APIClient;
import in.kestone.eventbuddy.http.APIInterface;
import in.kestone.eventbuddy.http.CallUtils;
import in.kestone.eventbuddy.model.activity_stream_model.Stream;
import in.kestone.eventbuddy.model.activity_stream_model.StreamDatum;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ActivityStream extends Fragment {
    FloatingActionButton fab;
    ArrayList<StreamDatum> streamDataList;
    ActivityStreamAdapter activityStreamAdapter;
    RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    public ActivityStream() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_activity_stream, container, false );
        setRetainInstance( true );

        recyclerView = view.findViewById( R.id.recyclerView );
        LinearLayoutManager layoutManager = new LinearLayoutManager( getContext() ) {

            @Override
            public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
                LinearSmoothScroller smoothScroller = new LinearSmoothScroller( getActivity() ) {

                    private static final float SPEED = 300f;

                    @Override
                    protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                        return SPEED / displayMetrics.densityDpi;
                    }

                };
                smoothScroller.setTargetPosition( position );
                startSmoothScroll( smoothScroller );
            }

        };
        recyclerView.setLayoutManager( layoutManager );
        streamDataList = new ArrayList<>();
        getStream();
        Progress.showProgress( getActivity() );

        fab = view.findViewById( R.id.fab );
        fab.setOnClickListener( v -> {
            Intent intent = new Intent( getContext(), AddActivity.class );
            intent.putExtra( "title", getArguments().getString( "title" ) );
            startActivityForResult( intent, 100 );

        } );


        swipeRefreshLayout = view.findViewById( R.id.swipeRefreshLayout );
        swipeRefreshLayout.setOnRefreshListener( () -> {
            getStream();
            Progress.showProgress( getContext() );
            swipeRefreshLayout.setRefreshing( false );
        } );
        return view;
    }

    public void getStream() {
        APIInterface apiInterface = APIClient.getClient().create( APIInterface.class );
        Call<Stream> call = apiInterface.activityStream( LocalStorage.getEventID( getActivity() ) );

        CallUtils.enqueueWithRetry( call, 3, new Callback<Stream>() {
            @Override
            public void onResponse(Call<Stream> call, Response<Stream> response) {
                if (response.code() == 200) {
                    if (response.body() != null) {
                        if (response.body().getStatusCode() == 200 && response.body().getStreamData().size() > 0) {

                            streamDataList.addAll( response.body().getStreamData() );
                            activityStreamAdapter = new ActivityStreamAdapter( getActivity(), streamDataList );
                            recyclerView.setHasFixedSize( true );
                            recyclerView.setAdapter( activityStreamAdapter );
                            activityStreamAdapter.notifyDataSetChanged();
                            int resId = R.anim.layout_animation_fall_down;
                            LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation( getActivity(), resId );
                            recyclerView.setLayoutAnimation( animation );

                        } else {
                            CustomDialog.showInvalidPopUp( getActivity(), CONSTANTS.ERROR, response.body().getMessage() );
                        }
                    }
                } else {
                    CustomDialog.showInvalidPopUp( getActivity(), CONSTANTS.ERROR, response.message() );
                }


                Progress.closeProgress();
            }

            @Override
            public void onFailure(Call<Stream> call, Throwable t) {
                CustomDialog.showInvalidPopUp( getActivity(), CONSTANTS.ERROR, CONSTANTS.CONNECTIONERROR );
                Progress.closeProgress();
            }
        } );


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) {
            if (resultCode == Activity.RESULT_OK) {
                if (data.getStringExtra( "Status" ).equalsIgnoreCase( "yes" )) {
                    getStream();
                    Progress.showProgress( getContext() );
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }


}
