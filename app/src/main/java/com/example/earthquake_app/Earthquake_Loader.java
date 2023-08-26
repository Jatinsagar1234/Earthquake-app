package com.example.earthquake_app;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class Earthquake_Loader extends AsyncTaskLoader<List<Earthquake>> {
    /**
     * @param context
     * @deprecated
     */
    private  String mUrl;
    public Earthquake_Loader(Context context,String url ) {
        super(context);
        mUrl=url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Earthquake> loadInBackground() {
        if(mUrl==null){
            return null;
        }
        List<Earthquake> earthquakes=QueryUtils.fetchData(mUrl);
        return earthquakes;
    }
}
