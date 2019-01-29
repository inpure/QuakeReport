package com.example.cf.quakereport;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.ArrayList;

/**
 * 创建时间：2019/1/23
 * 编 写 人：inpure
 * 邮    箱：chfjinqh@gmail.com
 * 功能描述：
 */
public class EarthquakeLoader extends AsyncTaskLoader<ArrayList<Earthquake>> {
    private String mStringUrl;
    private static  final String LOG_TAG = EarthquakeAdapter.class.getName();

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        Log.i(LOG_TAG, "onStartLoading() 方法被调用");
        forceLoad();
    }

    public EarthquakeLoader(@NonNull Context context, String stringUrl) {
        super(context);
        mStringUrl = stringUrl;
    }

    @Nullable
    @Override
    public ArrayList<Earthquake> loadInBackground() {
        Log.i(LOG_TAG, "loadInBackground() 方法被调用");
        ArrayList<Earthquake> earthquakes = QueryUtils.fetchEarthquakeData(mStringUrl);
        return earthquakes;
    }
}
