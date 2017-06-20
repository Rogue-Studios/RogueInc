package com.example.android.rogueinc;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class ResourceAdapter extends ArrayAdapter {

    private Activity context;
    private ArrayList<Resource> resources;

    public ResourceAdapter(Activity context, int resource,ArrayList<Resource> resources) {
        super(context, resource, resources);
        this.context = context;
        this.resources = resources;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        Resource tempResource = resources.get(position);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.resource_cell, null);


        ImageView icon = (ImageView) view.findViewById(R.id.resourceIcon);
        TextView timerText = (TextView) view.findViewById(R.id.timerText);
        TextView nameText = (TextView) view.findViewById(R.id.nameText);
        TextView productionAmountText = (TextView) view.findViewById(R.id.productionAmountText);
        TextView productionValueText = (TextView) view.findViewById(R.id.productionValueText);
        Button costButton = (Button) view.findViewById(R.id.costButton);
        ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar);


        if(tempResource.getTimeSinceProduction() < 10) {
            timerText.setText("00:0" + (int) tempResource.getTimeSinceProduction() + "s");
        }
        else if (9 < tempResource.getTimeSinceProduction() && tempResource.getTimeSinceProduction() < 60) {
            timerText.setText("00:" + (int) tempResource.getTimeSinceProduction() + "s");
        }
        else {
            timerText.setText("oliver needs to finish this");
        }

        nameText.setText(tempResource.getName());
        productionAmountText.setText(String.valueOf(tempResource.getAmount()) + " /s");
        productionValueText.setText(String.valueOf(tempResource.getValue() * tempResource.getValueModifier()));
        costButton.setText("$100");
        progressBar.setProgress((int) (tempResource.getTimeSinceProduction() / tempResource.getProductionTime() * 100));

        return view;
    }
}