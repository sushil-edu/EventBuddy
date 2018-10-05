package in.kestone.eventbuddy.view.stream;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import in.kestone.eventbuddy.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ActivityStream extends Fragment {
    //    ArrayList<StreamData> streamDataList;
//    ActivityStreamAdapter activityStreamAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    public ActivityStream() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_activity_stream, container, false );


        RecyclerView recyclerView = view.findViewById( R.id.recyclerView );
        recyclerView.setLayoutManager( new LinearLayoutManager( getContext() ) );
//        streamDataList = new ArrayList<>();
//        activityStreamAdapter = new ActivityStreamAdapter( getContext(), streamDataList );
//        recyclerView.setHasFixedSize( true );
//        recyclerView.setAdapter( activityStreamAdapter );

        FloatingActionButton fab = view.findViewById( R.id.fab );
        fab.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent( getContext(), AddActivity.class );
//                startActivityForResult( intent, 100 );

            }
        } );


        swipeRefreshLayout = view.findViewById( R.id.swipeRefreshLayout );
        swipeRefreshLayout.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                Progress.showProgress(getActivity());
//                if (ConnectionCheck.connectionStatus(getContext())) {
//                    streamDataList.clear();
//                    activityStreamAdapter.notifyDataSetChanged();
//                    new GetStreamData(ApiUrl.ActivityStream);
//                } else {
//                    Progress.closeProgress();
//                    Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
//                }

            }
        } );
        return view;
    }

}
