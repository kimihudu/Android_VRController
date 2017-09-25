package trios.vrcontroller.vrcontroller;


import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import com.google.android.gms.location.LocationServices;

import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import static trios.vrcontroller.vrcontroller.R.id.map;


public class MainActivity extends FragmentActivity implements OnMapReadyCallback{

    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private FloatingActionButton btnCurrentLoc;
    private FloatingActionButton btnSaveLoc;
    private FloatingActionButton btnViewImg;
    private Button btnGo;
    private GoogleApiClient mGoogleApiClient;
    private Location mLastKnownLocation;
    private boolean mLocationPermissionGranted;
    private CameraPosition mCameraPosition;
    private LatLng mDefaultLocation = new LatLng(43.5938589, -79.6384027);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
//                .enableAutoManage(this /* FragmentActivity */,
//                        this /* OnConnectionFailedListener */)
//                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
//                .addApi(Places.GEO_DATA_API)
//                .addApi(Places.PLACE_DETECTION_API)
                .build();

        // fragment map
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(map);
        mapFragment.getMapAsync(this);

        // button controller
        btnCurrentLoc = (FloatingActionButton) findViewById(R.id.btnCurrentLoc);
        btnGo = (Button) findViewById(R.id.btnGo);
        btnViewImg = (FloatingActionButton) findViewById(R.id.btnView);
        btnSaveLoc = (FloatingActionButton) findViewById(R.id.btnSave);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();
        // Get the current location of the device and set the position of the map.
        getDeviceLocation();


//        // Add a marker in Sydney and move the camera
//        LatLng currentLocation = new LatLng(43.5938589, -79.6384027);
//        mMap.addMarker(new MarkerOptions().position(currentLocation).title("Marker in here"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom((currentLocation), 16.0f));

    }

    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }

        int curPermission = ContextCompat.checkSelfPermission(this.getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION);
        if ( curPermission == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        }

        if (mLocationPermissionGranted) {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        } else {
            mMap.setMyLocationEnabled(false);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
            mLastKnownLocation = null;
        }
    }

    private void getDeviceLocation() {
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        }

        if (mLocationPermissionGranted) {
                mLastKnownLocation = LocationServices.FusedLocationApi
                    .getLastLocation(mGoogleApiClient);
        }

// Set the map's camera position to the current location of the device.
        float DEFAULT_ZOOM = 15;
        if (mCameraPosition != null) {
            mMap.moveCamera(CameraUpdateFactory.newCameraPosition(mCameraPosition));
        } else if (mLastKnownLocation != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                    new LatLng(mLastKnownLocation.getLatitude(),
                            mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
        } else {
            Log.d("pouya", "Current location is null. Using defaults.");
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
        }
    }

    //    TODO: get current loc

    //    TODO: go to search loc

    //    TODO: go to view img from selected loc

    //    TODO: save to db current loc
}
