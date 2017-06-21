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
    final int updateTime = 100;
    Player player = new Player(100.0, 0);
    boolean running = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resources.add(new Resource("lemons", true, 1.0, 10.0, 5.0));
        resources.add(new Resource("bananas", false, 1.0, 10.0, 5.0));
        resources.add(new Resource("apples", true, 1.0, 10.0, 5.0));
        resources.add(new Resource("oranges", false, 1.0, 10.0, 5.0));

        final ResourceAdapter resourceAdapter = new ResourceAdapter(this, 0, resources);
        ListView resourceList = (ListView) findViewById(R.id.resourceList);
        resourceList.setAdapter(resourceAdapter);




        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                for(int i = 0; i < resources.size(); i++) {
                    // UPDATES EACH RESOURCE
                    player.addBalance(resources.get(i).update(updateTime));
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // UPDATES THE UI
                            resourceAdapter.notifyDataSetChanged();
                        }

                    });
                }
            }
        }, updateTime, updateTime);
    }
}
