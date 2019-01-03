package com.example.cf.quakereport;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * 创建时间：2019/1/4
 * 编 写 人：inpure
 * 邮    箱：chfjinqh@gmail.com
 * 功能描述：{@link EarthquakeAdapter}是一个{@link ArrayAdapter}
 * 它可以根据数据源为每一个列表提供布局，数据源是{@link Earthquake}对象列表
 */
public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    public EarthquakeAdapter( Context context, int resource, List<Earthquake> objects) {
        super(context, resource, objects);
    }
}
