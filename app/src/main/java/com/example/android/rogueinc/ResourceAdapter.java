package com.example.android.rogueinc;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

public class ResourceAdapter extends BaseAdapter {

    private Activity context;
    private ArrayList<Resource> resources;

    public ResourceAdapter(Activity context, ArrayList<Resource> resources) {
        this.context = context;
        this.resources = resources;
    }

    @Override
    public int getCount() {
        return resources.size();
    }

    @Override
    public Resource getItem(int position) {
        return resources.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.resource_cell, null);
        // tempView.findViewById(R.id.resourceIcon).set

        Resource resource = getItem(position);

        ImageView icon = (ImageView) view.findViewById(R.id.resourceIcon);
        TextView timerText = (TextView) view.findViewById(R.id.timerText);
        TextView amountText = (TextView) view.findViewById(R.id.amountText);
        TextView productionAmountText = (TextView) view.findViewById(R.id.productionAmountText);
        TextView productionValueText = (TextView) view.findViewById(R.id.productionValueText);
        Button costButton = (Button) view.findViewById(R.id.costButton);
        ProgressBar progressBar = (ProgressBar) view.findViewById(R.id.progressBar);

        timerText.setText("00:05s");
        amountText.setText(resource.getAmount());
        productionAmountText.setText(Double.toString(resource.getAmount()) + " /s");
        productionValueText.setText(Double.toString(resource.getValue() * resource.getValueModifier()));
        costButton.setText("$100");
        progressBar.setProgress(50);

        return view;
    }



}
