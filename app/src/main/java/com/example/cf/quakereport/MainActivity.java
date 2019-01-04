package com.example.cf.quakereport;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earthquake);

        //创建伪地震列表
        ArrayList<Earthquake> earthquake = new ArrayList<>();
        earthquake.add(new Earthquake(7.2, "San francisco", "Feb 2,2016"));
        earthquake.add(new Earthquake(6.1, "London", "July 20,2015"));
        earthquake.add(new Earthquake(3.9, "Tokyo", "Nov 10,2014"));

        EarthquakeAdapter adapter = new EarthquakeAdapter(this,  earthquake);

        //获得一个 ListView 的引用，并为其配置 adapter
        ListView earthquakeListView = findViewById(R.id.list);
        earthquakeListView.setAdapter(adapter);
    }
}
