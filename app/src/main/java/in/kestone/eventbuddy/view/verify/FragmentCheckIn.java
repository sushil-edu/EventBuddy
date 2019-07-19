package in.kestone.eventbuddy.view.verify;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.karumi.dexter.BuildConfig;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.kestone.eventbuddy.Altdialog.CustomDialog;
import in.kestone.eventbuddy.Eventlistener.OnVerifiedListener;
import in.kestone.eventbuddy.R;
import in.kestone.eventbuddy.common.CONSTANTS;
import in.kestone.eventbuddy.common.CompareDateTime;
import in.kestone.eventbuddy.common.LocalStorage;
import in.kestone.eventbuddy.model.app_config_model.GeoTag;
import in.kestone.eventbuddy.model.app_config_model.ListEvent;
import in.kestone.eventbuddy.widgets.CustomButton;
import in.kestone.eventbuddy.widgets.CustomTextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentCheckIn extends Fragment {

    // location updates interval - 10sec
    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    // fastest updates interval - 5 sec
    // location updates will be received if another app is requesting the locations
    // than your app can handle
    private static final long FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = 10000;
    private static final int REQUEST_CHECK_SETTINGS = 100;
    //    my location
    double latA = 28.521195199999998;
    double logA = 77.2933742;
    double latB = 0.0;
    double logB = 0.0;
    double radius = 0.0;
    String err_msg = "", err_header = "";
    View view;
    @BindView(R.id.tv_checkin_title)
    CustomTextView checkIn_title;
    @BindView(R.id.tv_checkin)
    CustomButton tv_checkIn;
    @BindView(R.id.tv_skip)
    CustomTextView tv_skip;
    @BindView( R.id.image_background )
    ImageView imageBackGround;
    CustomDialog dialog;
    OnVerifiedListener onVerifiedListener;
    //event activation date and time
    String activationDateFrom = null, activationTimeFrom = null;
    String activationDateTo = null, activationTimeTo = null;
    //current date and time
    SimpleDateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd" );
    //    SimpleDateFormat timeFormat = new SimpleDateFormat( "h:mm a" );
    SimpleDateFormat timeFormat = new SimpleDateFormat( "kk:mm" );
    Date date = Calendar.getInstance().getTime();
    String strDate = dateFormat.format( date );
    String strTime = timeFormat.format( new Date() );
    Date dtDate, dtTime;
    // bunch of location related apis
    private FusedLocationProviderClient mFusedLocationClient;
    private SettingsClient mSettingsClient;
    private LocationRequest mLocationRequest;
    private LocationSettingsRequest mLocationSettingsRequest;
    private LocationCallback mLocationCallback;
    // boolean flag to toggle the ui
    private Location mCurrentLocation;
    private Boolean mRequestingLocationUpdates = false;
    private GeoTag geoTag;

    public FragmentCheckIn() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate( R.layout.fragment_check_in, container, false );
        ButterKnife.bind( this, view );

        geoTag = ListEvent.getAppConf().getEvent().getGeoTag();

        latA = geoTag.getLatitude();
        logA = geoTag.getLongitude();
        radius = geoTag.getRadius();

        if(LocalStorage.getEventID( getActivity() )!=0){
            Picasso.with( getActivity() ).load(  "http://eventsbuddy.in/beta/".concat( LocalStorage.getBackground( getActivity() ) ))
                    .into( imageBackGround );
        }

        dialog = new CustomDialog();
        onVerifiedListener = (OnVerifiedListener) getActivity();
        //for speaker
        String [] dFrom;
        String [] dTo;

        dFrom = geoTag.getActivationDateFrom().split("T");
        dTo = geoTag.getActivationDateTo().split("T");
        activationDateFrom = dFrom[0];
        activationTimeFrom = dFrom[1];

        activationDateTo = dTo[0];
        activationTimeTo = dTo[1];

        //fetch location
        init();
        startLocation();

        try {
            dtDate = dateFormat.parse( strDate );
            dtTime = timeFormat.parse( strTime );
        } catch (ParseException e) {
            e.printStackTrace();
        }


//        if (CompareDateTime.funCompareDateTime( activationDateFrom, activationDateTo, activationTimeFrom, activationTimeTo )) {//dtDate.compareTo( dateFormat.parse( activationDateFrom ) )>=0 && dtDate.compareTo( dateFormat.parse( activationDateTo ) )<=0) {
        if (CompareDateTime.compareDateTime( activationDateFrom, activationDateTo, activationTimeFrom, activationTimeTo )) {//dtDate.compareTo( dateFormat.parse( activationDateFrom ) )>=0 && dtDate.compareTo( dateFormat.parse( activationDateTo ) )<=0) {
            tv_checkIn.setEnabled( true );
        } else {
            tv_checkIn.setEnabled( false );
            tv_checkIn.setBackgroundColor( getResources().getColor( R.color.grey ) );
        }


        checkIn_title.setText( geoTag.getWelcomeText() );
        tv_checkIn.setText( geoTag.getLabel() );
        err_msg = geoTag.getErrorMessage();
        err_header = geoTag.getErrorHeader();
        tv_checkIn.setOnClickListener(view -> {
//                startLocation();

            if (mCurrentLocation != null) {

//                    updateLocationUI();
                latB = mCurrentLocation.getLatitude();
                logB = mCurrentLocation.getLongitude();

                Log.d( "Get Difference ", "" + getDifference( latB, logB ) + " error " + err_msg );
                if (getDifference( latB, logB ) <= radius) {

                    onVerifiedListener.onVerified( "check-in", "" );
                } else {
                    dialog.showInvalidPopUp( getActivity(), err_header, err_msg );
                }
            } else {
                dialog.showInvalidPopUp( getActivity(), CONSTANTS.ERROR, "Please try again." );
            }
        });

        //skip
        tv_skip.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onVerifiedListener.onVerified( "check-in", "" );
            }
        } );
        return view;
    }

    //location

    private void init() {

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient( getActivity() );
        mSettingsClient = LocationServices.getSettingsClient( getActivity() );

        mLocationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult( locationResult );
                // location is received
                mCurrentLocation = locationResult.getLastLocation();

                updateLocationUI();
            }
        };
        mRequestingLocationUpdates = false;

        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval( UPDATE_INTERVAL_IN_MILLISECONDS );
        mLocationRequest.setFastestInterval( FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS );
        mLocationRequest.setPriority( LocationRequest.PRIORITY_HIGH_ACCURACY );

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest( mLocationRequest );
        mLocationSettingsRequest = builder.build();
    }

    /**
     * Update the UI displaying the location data
     * and toggling the buttons
     */
    private void updateLocationUI() {
        if (mCurrentLocation != null) {
            Log.e( "Location",
                    "Lat: " + mCurrentLocation.getLatitude() + ", " +
                            "Lng: " + mCurrentLocation.getLongitude()
            );
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState( outState );
        outState.putBoolean( "is_requesting_updates", mRequestingLocationUpdates );
        outState.putParcelable( "last_known_location", mCurrentLocation );

    }

    /**
     * Starting location updates
     * Check whether location settings are satisfied and then
     * location updates will be requested
     */
    private void startLocationUpdates() {
        mSettingsClient
                .checkLocationSettings( mLocationSettingsRequest )
                .addOnSuccessListener( getActivity(), new OnSuccessListener<LocationSettingsResponse>() {
                    @SuppressLint("MissingPermission")
                    @Override
                    public void onSuccess(LocationSettingsResponse locationSettingsResponse) {
                        Log.i( "Success ", "All location settings are satisfied." );

//                        Toast.makeText( getApplicationContext(), "Started location updates!", Toast.LENGTH_SHORT ).show();

                        //noinspection MissingPermission
                        mFusedLocationClient.requestLocationUpdates( mLocationRequest,
                                mLocationCallback, Looper.myLooper() );

                        updateLocationUI();

                    }
                } )
                .addOnFailureListener( getActivity(), new OnFailureListener() {
                    @RequiresApi(api = Build.VERSION_CODES.DONUT)
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        int statusCode = ((ApiException) e).getStatusCode();
                        switch (statusCode) {
                            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                                Log.i( "Meg", "Location settings are not satisfied. Attempting to upgrade " +
                                        "location settings " );
                                try {
                                    // Show the dialog by calling startResolutionForResult(), and check the
                                    // result in onActivityResult().
                                    ResolvableApiException rae = (ResolvableApiException) e;
                                    rae.startResolutionForResult( getActivity(), REQUEST_CHECK_SETTINGS );
                                } catch (IntentSender.SendIntentException sie) {
                                    Log.i( "msg", "PendingIntent unable to execute request." );
                                }
                                break;
                            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                String errorMessage = "Location settings are inadequate, and cannot be " +
                                        "fixed here. Fix in Settings.";
                                Log.e( "Error ", errorMessage );

                                Toast.makeText( getActivity(), errorMessage, Toast.LENGTH_LONG ).show();
                        }

                        // updateLocationUI();
                    }
                } );
    }

    public void startLocation() {
        // Requesting ACCESS_FINE_LOCATION using Dexter library
        Dexter.withActivity( getActivity() )
                .withPermission( Manifest.permission.ACCESS_FINE_LOCATION )
                .withListener( new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse response) {
                        mRequestingLocationUpdates = true;
                        startLocationUpdates();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse response) {
                        if (response.isPermanentlyDenied()) {
                            // open device settings when the permission is
                            // denied permanently
                            openSettings();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                } ).check();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            // Check for the integer request code originally supplied to startResolutionForResult().
            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {
                    case Activity.RESULT_OK:
                        Log.e( "Alert ", "User agreed to make required location settings changes." );
                        // Nothing to do. startLocationupdates() gets called in onResume again.
                        break;
                    case Activity.RESULT_CANCELED:
                        Log.e( "Alert ", "User chose not to make required location settings changes." );
                        mRequestingLocationUpdates = false;
                        break;
                }
                break;
        }
    }

    private void openSettings() {
        Intent intent = new Intent();
        intent.setAction(
                Settings.ACTION_APPLICATION_DETAILS_SETTINGS );
        Uri uri = Uri.fromParts( "package",
                BuildConfig.APPLICATION_ID, null );
        intent.setData( uri );
        intent.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
        startActivity( intent );
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mRequestingLocationUpdates && checkPermissions()) {
            startLocationUpdates();
        }
    }

    private boolean checkPermissions() {
        int permissionState = ActivityCompat.checkSelfPermission( getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION );
        return permissionState == PackageManager.PERMISSION_GRANTED;
    }

    @Override
    public void onPause() {
        super.onPause();

        if (mRequestingLocationUpdates) {
            // pausing location updates
            stopLocationUpdates();
        }
    }

    public void stopLocationUpdates() {
        // Removing location updates
        mFusedLocationClient
                .removeLocationUpdates( mLocationCallback )
                .addOnCompleteListener( getActivity(), new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.e( "Location", "updates stopped" );
                    }
                } );
    }

    public double getDifference(double latB, double logB) {
        Location locationA = new Location( "point A" );

        locationA.setLatitude( latA );
        locationA.setLongitude( logA );

        Location locationB = new Location( "point B" );

        locationB.setLatitude( latB );
        locationB.setLongitude( logB );

        Log.e( "LatLong ", "" + locationA + " B " + locationB );

        return locationA.distanceTo( locationB );
    }


}
