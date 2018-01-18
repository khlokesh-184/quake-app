package com.example.android.quakereport;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by LOKESH on 30-07-2017.
 */

public class Custom_Adapter extends ArrayAdapter<Earthquke> {
    private static final String LOCATION_SEPARATOR = " of ";
    Custom_Adapter(Context context, ArrayList<Earthquke> a){
        super(context,0,a);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listitem=convertView;
        if(listitem==null){
            listitem= LayoutInflater.from(getContext()).inflate(R.layout.simple_list_item,parent,false);
        }
        Earthquke e=getItem(position);

        TextView t1=(TextView) listitem.findViewById(R.id.text1);
        t1.setText(e.getMagnitude());
        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircle = (GradientDrawable) t1.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = e.getMagnitudeColor();

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(ContextCompat.getColor(getContext(), magnitudeColor));




        String originalLocation=e.getLocation();
        String primaryLocation;
        String locationOffset;
        if (originalLocation.contains(LOCATION_SEPARATOR)) {
            String[] parts = originalLocation.split(LOCATION_SEPARATOR);
            locationOffset = parts[0] + LOCATION_SEPARATOR;
            primaryLocation = parts[1];
        } else {
            locationOffset = getContext().getString(R.string.near_the);
            primaryLocation = originalLocation;
        }

        TextView t2=(TextView) listitem.findViewById(R.id.location_offset);
        t2.setText(locationOffset);

        TextView t3=(TextView) listitem.findViewById(R.id.primary_location);
        t3.setText(primaryLocation);

        TextView t4=(TextView) listitem.findViewById(R.id.text3);
        t4.setText(e.getDate());

        TextView t5=(TextView) listitem.findViewById(R.id.text4);
        t5.setText(e.getTime());

        return listitem;
    }
}
