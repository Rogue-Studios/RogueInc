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

    private ArrayList<Resource> resources = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resources.add(new Resource("lemons", 1.0, 10.0, 5.0));
        resources.add(new Resource("bananas", 1.0, 10.0, 5.0));
        resources.add(new Resource("apples", 1.0, 10.0, 5.0));
        resources.add(new Resource("oranges", 1.0, 10.0, 5.0));

        ArrayAdapter resourceAdapter = new ArrayAdapter(this, 0, resources);
        ListView resourceList = (ListView) findViewById(R.id.resourceList);
        resourceList.setAdapter(resourceAdapter);
        setContentView(resourceList);

    }
}
