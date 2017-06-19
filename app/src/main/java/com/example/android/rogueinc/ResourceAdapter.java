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

        timerText.setText("00:05s");
        nameText.setText(tempResource.getName());
        productionAmountText.setText(Double.toString(tempResource.getAmount()) + " /s");
        productionValueText.setText(Double.toString(tempResource.getValue() * tempResource.getValueModifier()));
        costButton.setText("$100");
        progressBar.setProgress(50);

        return view;
    }
}