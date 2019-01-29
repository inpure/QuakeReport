package com.example.cf.quakereport;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Earthquake>> {

    public static final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&minmagnitude=1&limit=10";

    private static final String LOG_TAG = EarthquakeActivity.class.getName();

    private EarthquakeAdapter mAdapter;

    private TextView mEmptyTextView;

    private View mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i(LOG_TAG, "onCreate() 方法被调用");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earthquake);
        // 获得一个 ListView 的引用，并为其配置 adapter
        ListView earthquakeListView = findViewById(R.id.list);

        mEmptyTextView = findViewById(R.id.empty_view);
        earthquakeListView.setEmptyView(mEmptyTextView);

        mAdapter = new EarthquakeAdapter(this, new ArrayList<Earthquake>());
        earthquakeListView.setAdapter(mAdapter);

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        mLoadingIndicator = findViewById(R.id.loading_indicator);
        //检查是否有网络连接
        if (cm != null && networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = LoaderManager.getInstance(this);
            loaderManager.initLoader(0, null, this);
            Log.i(LOG_TAG, "initLoader() 方法被调用");
        } else {
            mLoadingIndicator.setVisibility(View.GONE);
            mEmptyTextView.setText(getString(R.string.no_internet_connection));
        }
    }

    @NonNull
    @Override
    public Loader<ArrayList<Earthquake>> onCreateLoader(int i, @Nullable Bundle bundle) {
        Log.i(LOG_TAG, "onCreateLoader() 方法被调用");
        return new EarthquakeLoader(EarthquakeActivity.this, USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<Earthquake>> loader, ArrayList<Earthquake> earthquakes) {
        Log.i(LOG_TAG, "onLoadFinished() 方法被调用");
        mLoadingIndicator.setVisibility(View.GONE);
        mEmptyTextView.setText(getString(R.string.no_earthquakes));
        //清空适配器里的 earthquake 数据
        mAdapter.clear();
        //如果有有效的 Earthquake 列表，则将他们添加到适配器
        //这将触发 ListView 更新
        if (earthquakes != null && !earthquakes.isEmpty()) {
            mAdapter.addAll(earthquakes);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<Earthquake>> loader) {
        Log.i(LOG_TAG, "onLoaderReset() 方法被调用");
        //清空数据
        mAdapter.clear();
    }
}
