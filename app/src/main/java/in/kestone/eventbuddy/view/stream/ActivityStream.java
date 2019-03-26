package in.kestone.eventbuddy.view.stream;


import android.content.Intent;
import android.os.Bundle;
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

import java.util.ArrayList;

import in.kestone.eventbuddy.Altdialog.CustomDialog;
import in.kestone.eventbuddy.Altdialog.Progress;
import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.common.CONSTANTS;
import in.kestone.eventbuddy.common.CommonUtils;
import in.kestone.eventbuddy.http.APIClient;
import in.kestone.eventbuddy.http.APIInterface;
import in.kestone.eventbuddy.model.activity_stream_model.Stream;
import in.kestone.eventbuddy.model.activity_stream_model.StreamDatum;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class ActivityStream extends Fragment {
    FloatingActionButton fab;
        ArrayList<StreamDatum> streamDataList;
    ActivityStreamAdapter activityStreamAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    public ActivityStream() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_activity_stream, container, false );


        recyclerView = view.findViewById( R.id.recyclerView );
        LinearLayoutManager layoutManager = new LinearLayoutManager( getContext() ) {

            @Override
            public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
                LinearSmoothScroller smoothScroller = new LinearSmoothScroller(getActivity()) {

                    private static final float SPEED = 300f;

                    @Override
                    protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                        return SPEED / displayMetrics.densityDpi;
                    }

                };
                smoothScroller.setTargetPosition(position);
                startSmoothScroll(smoothScroller);
            }

        };
        recyclerView.setLayoutManager( layoutManager );
        streamDataList = new ArrayList<>();

        if(CommonUtils.isNetworkConnected( getContext() )){
            getStream();
            Progress.showProgress( getContext() );
        }


        fab = view.findViewById( R.id.fab );
        fab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( getContext(), AddActivity.class );
                intent.putExtra( "title", getArguments().getString( "title") );
                startActivityForResult( intent, 100 );


            }
        } );


        swipeRefreshLayout = view.findViewById( R.id.swipeRefreshLayout );
        swipeRefreshLayout.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (CommonUtils.isNetworkConnected( getContext() )) {
                    getStream();
                    Progress.showProgress( getContext() );
                }
                swipeRefreshLayout.setRefreshing( false );
            }
        } );
        return view;
    }

    public void getStream() {
        APIInterface apiInterface = APIClient.getClient().create( APIInterface.class );
        Call<Stream> call = apiInterface.activityStream( CONSTANTS.EVENTID );
        call.enqueue( new Callback<Stream>() {
            @Override
            public void onResponse(Call<Stream> call, Response<Stream> response) {
                if(response.code()==200) {
                    if (response.body().getStatusCode() == 200 && response.body().getStreamData().size() > 0) {
                        streamDataList = (ArrayList<StreamDatum>) response.body().getStreamData();
                        activityStreamAdapter = new ActivityStreamAdapter( getContext(), streamDataList );
                        recyclerView.setHasFixedSize( true );
                        recyclerView.setAdapter( activityStreamAdapter );
                        activityStreamAdapter.notifyDataSetChanged();
                    } else {
                        CustomDialog.showInvalidPopUp( getActivity(), CONSTANTS.ERROR, response.body().getMessage() );
                    }
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


}
