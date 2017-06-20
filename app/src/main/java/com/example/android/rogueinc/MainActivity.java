package com.example.android.rogueinc;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Resource> resources = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resources.add(new Resource("lemons", 1.0, 10.0, 5.0));
        resources.add(new Resource("bananas", 1.0, 10.0, 5.0));
        resources.add(new Resource("apples", 1.0, 10.0, 5.0));
        resources.add(new Resource("oranges", 1.0, 10.0, 5.0));

        ResourceAdapter resourceAdapter = new ResourceAdapter(this, 0, resources);
        ListView resourceList = (ListView) findViewById(R.id.resourceList);
        resourceList.setAdapter(resourceAdapter);




        /*
        final Handler handler = new Handler();
        Timer timer = new Timer(false);
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        int x = 0;

                        x += 1;
                        resources.get(0).setName(Integer.toString(x));

                    }
                });
            }
        };
        timer.schedule(timerTask, 100);
        */

        resources.get(0).setName("lemons changed");
        resourceAdapter.notifyDataSetChanged();

        resources.get(1).setTimeSinceProduction(2);
    }
}
