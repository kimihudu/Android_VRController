package trios.vrcontroller.vrcontroller;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;


public class ListData extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_list_data);

        Intent intent = getIntent();
        TextView txloc = (TextView)findViewById(R.id.txtLoc);
        ImageView imloc = (ImageView)findViewById(R.id.iconLoc);


    }
}
