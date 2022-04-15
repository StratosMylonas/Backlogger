package com.stratos.backlogger;

import android.app.Activity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final ArrayList<String> mainTitle;
    private final ArrayList<String> subtitle;
    private final ArrayList<Integer> imgId;

    public CustomListAdapter(Activity context, ArrayList<String> mainTitle, ArrayList<String> subtitle, ArrayList<Integer> imgId) {
        super(context, R.layout.custom_view, mainTitle);

        this.context = context;
        this.mainTitle = mainTitle;
        this.subtitle = subtitle;
        this.imgId = imgId;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.custom_view, null, true);

        TextView titleText = rowView.findViewById(R.id.title);
        ImageView imageView = rowView.findViewById(R.id.icon);
        TextView subtitleText = rowView.findViewById(R.id.subtitle);

        titleText.setText(mainTitle.get(position));
        imageView.setImageResource(imgId.get(position));
        subtitleText.setText(subtitle.get(position));

        return rowView;
    }

}