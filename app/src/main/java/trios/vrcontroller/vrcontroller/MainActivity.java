package trios.vrcontroller.vrcontroller;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_SHORT;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this.getBaseContext(),"test",LENGTH_SHORT).show();
        Toast.makeText(this.getBaseContext(),"test",LENGTH_SHORT).show();
    }
}
