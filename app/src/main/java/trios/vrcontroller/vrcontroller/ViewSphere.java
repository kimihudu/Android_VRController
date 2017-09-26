package trios.vrcontroller.vrcontroller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.model.LatLng;


import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;

import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;



public class ViewSphere extends AppCompatActivity {

    // George St, Sydney
    private static final LatLng SYDNEY = new LatLng(-33.87365, 151.20689);

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sphere);

        SupportStreetViewPanoramaFragment streetViewPanoramaFragment =
                (SupportStreetViewPanoramaFragment)
                        getSupportFragmentManager().findFragmentById(R.id.streetviewpanorama);
        streetViewPanoramaFragment.getStreetViewPanoramaAsync(
                new OnStreetViewPanoramaReadyCallback() {
                    @Override
                    public void onStreetViewPanoramaReady(StreetViewPanorama panorama) {
                        // Only set the panorama to SYDNEY on startup (when no panoramas have been
                        // loaded which is when the savedInstanceState is null).
                        if (savedInstanceState == null) {
                            panorama.setPosition(SYDNEY);
                        }
                    }
                });
    }

}
