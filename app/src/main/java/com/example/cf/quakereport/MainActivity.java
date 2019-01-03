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

        //配置 ListView ,显示earthquake列表
        ListView earthquakeListView = findViewById(R.id.list);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, earthquake);
        earthquakeListView.setAdapter(adapter);
    }
}
