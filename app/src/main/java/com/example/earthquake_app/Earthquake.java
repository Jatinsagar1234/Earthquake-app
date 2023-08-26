package com.example.earthquake_app;

public class Earthquake {
    private double mMagnitude;
    private String mLocation;
    private String mDate;
    private  long mtime;
    private  String mUrl;
    public Earthquake(double Magnitude,String location,long time,String url){
        mMagnitude=Magnitude;
        mLocation=location;
        mtime=time;
        mUrl=url;
    }
    public double getMagnitude(){
        return mMagnitude;
    }
    public String getLocation(){
        return mLocation;
    }
    public String getDate(){
        return mDate;
    }
    public long getTime(){
        return mtime;
    }

    public String getUrl() {
        return mUrl;
    }
}
