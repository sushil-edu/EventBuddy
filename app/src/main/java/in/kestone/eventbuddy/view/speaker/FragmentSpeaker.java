package in.kestone.eventbuddy.view.speaker;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import org.json.JSONArray;

import java.util.ArrayList;

import in.kestone.eventbuddy.Altdialog.CustomDialog;
import in.kestone.eventbuddy.Altdialog.Progress;
import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.common.CONSTANTS;
import in.kestone.eventbuddy.common.LocalStorage;
import in.kestone.eventbuddy.data.SharedPrefsHelper;
import in.kestone.eventbuddy.http.APIClient;
import in.kestone.eventbuddy.http.APIInterface;
import in.kestone.eventbuddy.http.CallUtils;
import in.kestone.eventbuddy.model.agenda_model.ModelAgenda;
import in.kestone.eventbuddy.model.speaker_model.Speaker;
import in.kestone.eventbuddy.model.speaker_model.SpeakerDetail;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentSpeaker extends Fragment {
    final String TAG = "Speaker Fragment";
    JSONArray jsonArray;
    ModelAgenda modelAgenda;
    APIInterface apiInterface;
    CustomDialog customDialog;
    RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ArrayList<SpeakerDetail> speakerList;
    private SpeakersAdapter speakerAdapter;
    private EditText searchEt;
    private String module;

    public FragmentSpeaker() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_speaker, container, false );
        setRetainInstance( true );

        if (getArguments() != null) {
            module = getArguments().getString( "module" );
            getSpeaker( module );
            Progress.showProgress( getActivity() );
        }


        recyclerView = view.findViewById( R.id.recyclerView );
        searchEt = view.findViewById( R.id.searchEt );
        searchEt.setTextSize( 12f );
        recyclerView.setLayoutManager( new LinearLayoutManager( getContext() ) );
        recyclerView.setHasFixedSize( true );
        int resId = R.anim.layout_animation_fall_down;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getActivity(), resId);
        recyclerView.setLayoutAnimation(animation);
        speakerList = new ArrayList<>();
        customDialog = new CustomDialog();

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById( R.id.swipeRefreshLayout );
        swipeRefreshLayout.setOnRefreshListener( new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Progress.showProgress( getContext() );

//                if (ConnectionCheck.connectionStatus(getActivity())) {

                speakerList.clear();
                getSpeaker( module );
                speakerAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing( false );

//                }
            }
        } );

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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated( savedInstanceState );
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService( Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
    }

    private void filter(String text) {
        //new array list that will hold the filtered data
        ArrayList<SpeakerDetail> filterdNames = new ArrayList<>();
        filterdNames.clear();
        //looping through existing elements
        for (SpeakerDetail s : speakerList) {
            //if the existing elements contains the search input
            if (s.getFirstName().toLowerCase().contains( text.toLowerCase() )) {
                //adding the element to filtered list
                filterdNames.add( s );
                Log.e( "Name ", s.getFirstName() );
            }
        }

        //calling a method of the adapter class and passing the filtered list
        speakerAdapter.filterList( filterdNames );
    }

    public void getSpeaker(String type) {
        apiInterface = APIClient.getClient().create( APIInterface.class );
        Call<Speaker> call;
        if (type.equalsIgnoreCase( "speaker" )) {
            call = apiInterface.getAllSpeaker( LocalStorage.getEventID( getActivity() ));
        } else {
            call = apiInterface.getAllDelegates(LocalStorage.getEventID( getActivity() ));
        }
        CallUtils.enqueueWithRetry( call, 3, new Callback<Speaker>() {
            @Override
            public void onResponse(Call<Speaker> call, Response<Speaker> response) {
                if (response.code() == 200) {
                    if (response.body().getStatusCode() == 200 && response.body().getData().size() > 0) {
                        speakerList.clear();
                        for (int i = 0; i < response.body().getData().size(); i++) {
                            if (response.body().getData().get( i ).getUserID() != new SharedPrefsHelper( getContext() ).getUserId() &&
                                    type.equalsIgnoreCase( response.body().getData().get( i ).getUserType() )) {
                                speakerList.add( response.body().getData().get( i ) );
                            }
                        }

                        speakerAdapter = new SpeakersAdapter( getContext(), speakerList );
                        recyclerView.setAdapter( speakerAdapter );
                        speakerAdapter.notifyDataSetChanged();
                        Log.e( "Size ", "" + speakerList.size() );
                    } else {
                        customDialog.showInvalidPopUp( getActivity(), CONSTANTS.ERROR, response.body().getMessage() );
                    }
                }
                else {
                    CustomDialog.showInvalidPopUp( getActivity(), CONSTANTS.ERROR, response.message() );
                }
                Progress.closeProgress();
            }

            @Override
            public void onFailure(Call<Speaker> call, Throwable t) {
                CustomDialog.showInvalidPopUp( getActivity(), CONSTANTS.ERROR, CONSTANTS.CONNECTIONERROR );
                Progress.closeProgress();
            }
        } );
    }
}
