package in.kestone.eventbuddy.view.venue;


import android.os.Bundle;
import android.support.v4.app.Fragment;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.widgets.CustomTextView;
import okhttp3.MediaType;


public class FragmentVenue extends Fragment implements OnMapReadyCallback {
    private static final MediaType JSON = MediaType.parse( "application/json; charset=utf-8" );
    @BindView( R.id.venueAddressTv )
    CustomTextView venueAddressTv;
    @BindView( R.id.venueNameTv)
    CustomTextView venueNameTv;
    private GoogleMap mMap;

    public static FragmentVenue newInstance() {
        FragmentVenue fragment = new FragmentVenue();
        return fragment;
    }

//[{"ID":"1","VenueName":"Hotel Fairmont","Address":"Monte Carlo, Monaco Fairmont Monte Carlo\r\n12 Avenue des Spélugues, 98000 Monaco\r\n","Latitude":"43.7409642","Longitude":"7.4290689","ImageURL":""}]

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate( R.layout.fragment_venue, container, false );
        ButterKnife.bind( this,view );

        venueNameTv.setText( "Hotel Fairmont" );
        venueAddressTv.setText( "Monte Carlo, Monaco Fairmont Monte Carlo\r\n12 Avenue des Spélugues, 98000 Monaco\r\n" );

        SupportMapFragment mapFragment = (SupportMapFragment) this.getChildFragmentManager()
                .findFragmentById( R.id.map );
        mapFragment.getMapAsync( this );
        return view;

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng sydney = new LatLng( 43.7409642, 7.4290689 );
        mMap.addMarker( new MarkerOptions().position( sydney ).title( "Hotel Radisson" ).snippet( "Marker Description" ) );
        mMap.moveCamera( CameraUpdateFactory.newLatLng( sydney ) );
        // For zooming automatically to the location of the marker

        CameraPosition cameraPosition = new CameraPosition.Builder().target(
                new LatLng(43.7409642, 7.4290689 )).zoom(12).build();

        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}
