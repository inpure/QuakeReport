package com.example.cf.quakereport;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;

/**
 * 创建时间：2019/1/23
 * 编 写 人：inpure
 * 邮    箱：chfjinqh@gmail.com
 * 功能描述：
 */
public class EarthquakeLoader extends AsyncTaskLoader<ArrayList<Earthquake>> {
    private String mStringUrl;

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    public EarthquakeLoader(@NonNull Context context, String stringUrl) {
        super(context);
        mStringUrl = stringUrl;
    }

    @Nullable
    @Override
    public ArrayList<Earthquake> loadInBackground() {
        ArrayList<Earthquake> earthquakes = QueryUtils.fetchEarthquakeData(mStringUrl);
        return earthquakes;
    }
}
