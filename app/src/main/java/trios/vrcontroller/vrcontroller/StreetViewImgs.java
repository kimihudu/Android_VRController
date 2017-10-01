package trios.vrcontroller.vrcontroller;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.io.InputStream;
import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class StreetViewImgs extends AppCompatActivity {

    Double latitude;
    Double longitude;
    ImageView streetImg;
    final String GG_API_KEY = "AIzaSyA9UX1Ajb6h5y2Op4Fnf0K1IUsQdM5D0IA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_street_view_imgs);

        streetImg = (ImageView) findViewById(R.id.StreetImg);
        streetImg.setOnClickListener(viewSphere);

        latitude = getIntent().getDoubleExtra("Lat",0);
        longitude = getIntent().getDoubleExtra("Long",0);

        try{
//            Picasso.with(getBaseContext()).load(getImg(longitude,latitude)).into(streetImg);
            String urlRe = getImg(longitude,latitude);
            RetrieveURLTask tmpObj = new RetrieveURLTask();
            tmpObj.execute(urlRe);
            Log.i("test","test");
        }catch (Exception e){
            Log.i("get img", e.getMessage());
        }

    }

//    TODO: get img from long/lat
    public String getImg(Double longitude,Double latitude){
//        String imageURL = "https://maps.googleapis.com/maps/api/streetview?size=300x300&location=";
//        imageURL += latitude + "," + longitude + "&fov=120&heading=0&pitch=0";

//        https://maps.googleapis.com/maps/api/streetview/metadata?location=30.7451333%2C34.8850511&key=GG_API_KEY

        String imageURL = "https://maps.googleapis.com/maps/api/streetview/metadata?location=";
        imageURL += latitude + "," + longitude + "&key=" + GG_API_KEY;
        Log.wtf("prepareInfoView", imageURL);
        return imageURL;

    }
//TODO: get XML return with pano_id --> error return null/ try with the other location
    public static Drawable LoadImageFromWebOperations(String url) {
        try {
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }

    private AdapterView.OnClickListener viewSphere = new AdapterView.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent i = new Intent(getApplicationContext(), ViewSphere.class);
            startActivity(i);
        }
    };

    class RetrieveURLTask extends AsyncTask<String, Void, Drawable> {

        private Exception exception;

        protected Drawable doInBackground(String... urls) {
            try {
                return LoadImageFromWebOperations(urls[0]);

            } catch (Exception e) {
                this.exception = e;

                return null;
            }
        }

//        protected void onPostExecute(RSSFeed feed) {
//            // TODO: check this.exception
//            // TODO: do something with the feed
//        }
    }
}
