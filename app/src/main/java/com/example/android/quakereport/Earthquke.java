package com.example.android.quakereport;

import android.support.v4.content.ContextCompat;

import java.text.DecimalFormat;

/**
 * Created by LOKESH on 30-07-2017.
 */

public class Earthquke {
    private String magnitude;
    private  String location;
    private  String date;
    private String time;
    private  String mdate;
    private String mtime;
    Double magForColor;
    private  String url;

    Earthquke(double mag,String loc,String dat,String mURL){
        DecimalFormat formatter = new DecimalFormat("0.0");
        magForColor=mag;
        String output = formatter.format(mag);
        magnitude=output;
        location=loc;
        date=dat;
        mdate=date.substring(0,13);
        mtime=date.substring(14,22);
        url=mURL;
       // time=tim;
    }
    public  String getUrl(){
        return  url;
    }
    public int getMagnitudeColor() {
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magForColor);
        switch (magnitudeFloor) {
            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        //int magnitude1Color = ContextCompat.getColor(getContext(), R.color.magnitude1);
        return  magnitudeColorResourceId;
    }

    public  String getMagnitude(){
        return magnitude;
    }
    public String getLocation(){
        return location;
    }
    public  String getDate(){
        return mdate;
    }
    public String getTime(){return  mtime;}
}
