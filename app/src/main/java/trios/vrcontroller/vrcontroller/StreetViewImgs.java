package trios.vrcontroller.vrcontroller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.StreetViewPanoramaFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.StreetViewPanoramaLocation;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import trios.vrcontroller.vrcontroller.model.Cache;

import static android.R.attr.bitmap;


public class StreetViewImgs extends FragmentActivity implements OnStreetViewPanoramaReadyCallback {

    private static final String MARKER_POSITION_KEY = "MarkerPosition";
    private Marker mMarker;
    Double longitude = null;
    Double latitude = null;
    StreetViewPanoramaLocation currentLoc;
    StreetViewPanorama mStreetViewPanorama;
    String panoId;
    FloatingActionButton btn360;
    Bitmap img360;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_street_view_imgs);

        longitude = getIntent().getDoubleExtra("Long", 0);
        latitude = getIntent().getDoubleExtra("Lat", 0);

        StreetViewPanoramaFragment streetViewPanoramaFragment = (StreetViewPanoramaFragment) getFragmentManager().findFragmentById(R.id.streetviewpanorama);
        streetViewPanoramaFragment.getStreetViewPanoramaAsync(this);


        btn360 = (FloatingActionButton)findViewById(R.id.fab);
        btn360.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getPanoID(mStreetViewPanorama);
//                Thread wait for loading img from url request
                BitmapFromUrl bitmapFromUrl = new BitmapFromUrl();
                bitmapFromUrl.execute();



            }
        });

    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putParcelable(MARKER_POSITION_KEY, mMarker.getPosition());
//    }



    private void getPanoID(StreetViewPanorama streetViewPanorama) {
        try{
            currentLoc = streetViewPanorama.getLocation();
            panoId = currentLoc.links[0].panoId;
            Log.i("panoID", panoId);
        }catch (Exception e){
            Log.wtf("getPanoID", e.getMessage());
            Toast.makeText(getBaseContext(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
            return;
        }

    }

    @Override
    public void onStreetViewPanoramaReady(StreetViewPanorama streetViewPanorama) {
        streetViewPanorama.setPosition(new LatLng(latitude, longitude));
        mStreetViewPanorama = streetViewPanorama;
    }

    //    TODO: get img from long/lat
    public String getImgUrl(String panoId) {

//        http://cbk0.google.com/cbk?output=tile&panoid=w2x2xcYAvShN4DVXUGEXHg&zoom=3&x=5&y=1
        String imageURL = "http://cbk0.google.com/cbk?output=tile&panoid=";
        imageURL += panoId +"&zoom=3&x=5&y=1";
        Log.wtf("getImgUrl", imageURL);
        return imageURL;

    }

    //    TODO: download bitmap from URL
    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }

//        try {
//
////            String urlRe = getImg(-33.87365,151.20689);
//            Bitmap bitmap = BitmapFactory.decodeStream((InputStream)new URL(src).getContent());
////            streetImg.setImageBitmap(bitmap);
//            return bitmap;
//        } catch (MalformedURLException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
    }

    private class BitmapFromUrl extends AsyncTask<Void,Void,Boolean>{

//        @Override
//        protected void onPreExecute(){
//            super.onPreExecute();
//            //        Thread wait for loading complete location before get panoID
//            MapPanoID mapPanoID = new MapPanoID();
//            mapPanoID.execute();
////            Log.i("MapPanoID", panoId);
//        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try{
                Thread.sleep(1000);
                //get bitmap from url with panoID
                img360 = getBitmapFromURL(getImgUrl(panoId));
            }catch (Exception e){}
            return true;
        }
//TODO: send bitmap img to view sphere
        @Override
        protected void onPostExecute(final Boolean success) {
            Intent i = new Intent(getApplicationContext(), ViewSphere.class);
//            data too big for intent
//            i.putExtra("img360", img360);
//            Use cache for the big data
            Cache.getInstance().getLru().put("bitmap_image", img360);
            startActivity(i);
        }
    }




}
