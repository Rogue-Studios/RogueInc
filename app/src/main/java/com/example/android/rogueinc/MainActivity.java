package com.example.android.rogueinc;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    final int num = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Resource> resources = new ArrayList<>();
        for(int i = 0; i < num; i++) {
            Resource tempResource = new Resource(
                    "lemons",
                    1.0,
                    10.0,
                    5.0);
            resources.add(tempResource);
        }

        ListView resourceList = (ListView) findViewById(R.id.resourceList);
        ResourceAdapter resourceAdapter = new ResourceAdapter(this, resources);
        resourceList.setAdapter(resourceAdapter);

    }

}
