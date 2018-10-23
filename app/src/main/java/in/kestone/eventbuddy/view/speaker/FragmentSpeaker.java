package in.kestone.eventbuddy.view.speaker;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;

import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.common.ReadJson;
import in.kestone.eventbuddy.model.agenda_holder.AgendaList;
import in.kestone.eventbuddy.model.agenda_holder.ModelAgenda;
import in.kestone.eventbuddy.model.agenda_holder.Speaker;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSpeaker extends Fragment {
    JSONArray jsonArray;
    ModelAgenda modelAgenda;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<Speaker> speakerList;
    private SpeakersAdapter speakerAdapter;
    private EditText searchEt;
    private String myReponse;

    public FragmentSpeaker() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_speaker, container, false );
        RecyclerView recyclerView = view.findViewById( R.id.recyclerView );
        searchEt = view.findViewById( R.id.searchEt );
        searchEt.setTextSize( 12f );
        recyclerView.setLayoutManager( new LinearLayoutManager( getContext() ) );
        recyclerView.setHasFixedSize( true );
        speakerList = new ArrayList<>();
        setAgenda( getActivity() );
        speakerList.addAll( AgendaList.getAgenda().getAgenda().get( 1 ).getDetails().get( 0 ).getSpeaker() );
        speakerAdapter = new SpeakersAdapter( getContext(), speakerList );
        recyclerView.setAdapter( speakerAdapter );
//        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                Progress.showProgress(getContext());
////                if (ConnectionCheck.connectionStatus(getActivity())) {
//
//                    speakerList.clear();
//                    speakerAdapter.notifyDataSetChanged();
//
////                    JSONObject jsonObject = new JSONObject();
////                    try {
////                        jsonObject.put("UserType","Speaker");
////                        jsonObject.put("RefEmailID", UserDetails.EmailID);
////                        new SpeakerDataFetch(ApiUrl.Users, jsonObject.toString());
////                    } catch (JSONException e) {
////                        e.printStackTrace();
////                    }
////                } else {
////                    Progress.closeProgress();
////                    Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
////                }
//            }
//        });

        searchEt.addTextChangedListener( new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter( s.toString() );
            }
        } );

        return view;
    }

    public void setAgenda(Activity activity) {
        modelAgenda = new Gson().fromJson( new ReadJson().loadJSONFromAsset( activity, "agenda.json" ), ModelAgenda.class );
        if (modelAgenda.getStatusCode().equalsIgnoreCase( "200" )) {
            AgendaList.setAgenda( modelAgenda );

        } else {
            Log.e( "Status", String.valueOf( modelAgenda.getStatusCode() ) );
        }
    }

    private void filter(String text) {
        //new array list that will hold the filtered data
        ArrayList<Speaker> filterdNames = new ArrayList<>();

        //looping through existing elements
        for (Speaker s : speakerList) {
            //if the existing elements contains the search input
            if (s.getSpeakerName().toLowerCase().contains( text.toLowerCase() )) {
                //adding the element to filtered list
                filterdNames.add( s );
            }
        }

        //calling a method of the adapter class and passing the filtered list
        speakerAdapter.filterList( filterdNames );
    }

}
