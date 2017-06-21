package com.example.android.rogueinc;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
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

        if(tempResource.isUnlocked() == true) {
            View view = inflater.inflate(R.layout.resource_cell, null);


            ImageView icon = (ImageView) view.findViewById(R.id.resourceIcon);
            TextView timerText = (TextView) view.findViewById(R.id.timerText);
            TextView nameText = (TextView) view.findViewById(R.id.nameText);
            TextView productionAmountText = (TextView) view.findViewById(R.id.productionAmountText);
            TextView productionValueText = (TextView) view.findViewById(R.id.productionValueText);
            Button costButton = (Button) view.findViewById(R.id.costButton);
            ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

            // CALCULATES AND DISPLAYS TIME SINCE PRODUCTION
            if(tempResource.getProductionTime() - tempResource.getTimeSinceProduction() < 10) {
                timerText.setText("00:0" + (int) (tempResource.getProductionTime() - tempResource.getTimeSinceProduction()) + "s");
            }
            else if (9 < tempResource.getProductionTime() - tempResource.getTimeSinceProduction() && tempResource.getProductionTime() - tempResource.getTimeSinceProduction() < 60) {
                timerText.setText("00:" + (int) tempResource.getTimeSinceProduction() + "s");
            }
            else {
                timerText.setText("oliver needs to finish this");
            }

            // DISPLAYS THE NAME OF THE RESOURCE
            nameText.setText(tempResource.getName());

            // DISPLAYS THE AMOUNT PRODUCED PER SECOND
            productionAmountText.setText(String.valueOf(tempResource.getAmount() - tempResource.getAmountUsed()) + " /s");
            productionValueText.setText(String.valueOf(tempResource.getValue() * tempResource.getValueModifier() * tempResource.getAmount()) + "/s");
            costButton.setText("$" + String.valueOf((int) tempResource.getCost()));
            // OLD AND INEFFICIENT progressBar.setProgress((int) (tempResource.getTimeSinceProduction() / tempResource.getProductionTime() * 100));

            if(tempResource.isStarted() == false) {
                ObjectAnimator progressBarAnimation = ObjectAnimator.ofInt(progressBar, "progress", 0, 100);
                progressBarAnimation.setDuration((long) tempResource.getProductionTime() * 1000);
                progressBarAnimation.setInterpolator(new LinearInterpolator());
                progressBarAnimation.start();
                tempResource.setStarted(true);
            }
            
            return view;
        }
        else {
            View view = inflater.inflate(R.layout.resource_cell_locked, null);

            TextView nameLockedText = (TextView) view.findViewById(R.id.nameLockedText);
            TextView productionText = (TextView) view.findViewById(R.id.productionText);
            TextView valueText = (TextView) view.findViewById(R.id.valueText);

            nameLockedText.setText(tempResource.getName());
            productionText.setText(String.valueOf(((1 / tempResource.getProductionTime())) * tempResource.getSpeedModifier()) + "/s");
            valueText.setText("$" + String.valueOf( ((1 / tempResource.getProductionTime()) * tempResource.getValue() * tempResource.getValueModifier())) + "/s");


            return view;
        }

    }
}