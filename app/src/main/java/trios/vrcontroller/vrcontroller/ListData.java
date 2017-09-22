package trios.vrcontroller.vrcontroller;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import static trios.vrcontroller.vrcontroller.R.id.btnAddLoc;

public class ListData extends AppCompatActivity {

    private FloatingActionButton btnAddLoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_data);

        btnAddLoc = (FloatingActionButton) findViewById(R.id.btnAddLoc);
        btnAddLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }
}
