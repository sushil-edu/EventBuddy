package in.kestone.eventbuddy.view.venue;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONException;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.kestone.eventbuddy.Altdialog.CustomDialog;
import in.kestone.eventbuddy.Altdialog.Progress;
import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.common.CONSTANTS;
import in.kestone.eventbuddy.http.APIClient;
import in.kestone.eventbuddy.http.APIInterface;
import in.kestone.eventbuddy.model.venue_model.MVenue;
import in.kestone.eventbuddy.widgets.CustomTextView;
import okhttp3.MediaType;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FragmentVenue extends Fragment {
    private static final MediaType JSON = MediaType.parse( "application/json; charset=utf-8" );
    @BindView(R.id.venueAddressTv)
    CustomTextView venueAddressTv;
    @BindView(R.id.venueNameTv)
    CustomTextView venueNameTv;
    String Venue_Name, Venue_Latitude, Venue_Longitude;
    private GoogleMap mMap;

//    public static FragmentVenue newInstance() {
//        FragmentVenue fragment = new FragmentVenue();
//        return fragment;
//    }

//[{"ID":"1","VenueName":"Hotel Fairmont","Address":"Monte Carlo, Monaco Fairmont Monte Carlo\r\n12 Avenue des Spélugues, 98000 Monaco\r\n","Latitude":"43.7409642","Longitude":"7.4290689","ImageURL":""}]

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_venue, container, false );
        ButterKnife.bind( this, view );
        getVenue();
        Progress.showProgress( getContext() );
        return view;

    }


    public void loadVenue(String venue_name, double venue_Latitude, double venue_Longitude) {
        Log.e( "Location", ""+venue_Latitude );
        venueNameTv.setText( "Hotel Fairmont" );
        venueAddressTv.setText( venue_name );
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById( R.id.map );
        mapFragment.getMapAsync( new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                mMap = googleMap;

                LatLng sydney = new LatLng( venue_Longitude, venue_Latitude);
                mMap.addMarker( new MarkerOptions().position( sydney ).title( "Hotel Radisson" ).snippet( "Marker Description" ) );
                mMap.moveCamera( CameraUpdateFactory.newLatLng( sydney ) );
                // For zooming automatically to the location of the marker

                CameraPosition cameraPosition = new CameraPosition.Builder().target(
                        new LatLng( venue_Longitude, venue_Latitude) ).zoom( 12 ).build();

                googleMap.animateCamera( CameraUpdateFactory.newCameraPosition( cameraPosition ) );
            }
        } );
    }

    public void getVenue() {
        APIInterface apiInterface = APIClient.getClient().create( APIInterface.class );
        Call<MVenue> call = apiInterface.venue( CONSTANTS.EVENTID );
        call.enqueue( new Callback<MVenue>() {

            @Override
            public void onResponse(Call<MVenue> call, Response<MVenue> response) {
                if(response.code()==200) {
                    if (response.body().getStatusCode() == 200 && response.body().getVenueData().size() > 0) {
                        Venue_Name = response.body().getVenueData().get( 0 ).getVenueName();
                        Venue_Latitude = response.body().getVenueData().get( 0 ).getVenueLatitude();
                        Venue_Longitude = response.body().getVenueData().get( 0 ).getVenueLongitude();
                        loadVenue( Venue_Name, Double.parseDouble( Venue_Latitude ), Double.parseDouble( Venue_Longitude ) );
                    } else {
                        CustomDialog.showInvalidPopUp( getActivity(), CONSTANTS.ERROR, response.body().getMessage() );
                    }
                }

                Progress.closeProgress();
            }

            @Override
            public void onFailure(Call<MVenue> call, Throwable t) {
                Progress.closeProgress();
                CustomDialog.showInvalidPopUp( getActivity(), CONSTANTS.ERROR, CONSTANTS.CONNECTIONERROR );
            }
        } );
    }
}
