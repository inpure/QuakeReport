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
        ArrayList<String> earthquake = new ArrayList<>();
        earthquake.add("San francisco");
        earthquake.add("London");
        earthquake.add("Tokyo");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, earthquake);

        //获得一个ListView的引用，并为其配置adapter
        ListView earthquakeListView = findViewById(R.id.list);
        earthquakeListView.setAdapter(adapter);
    }
}
