package com.example.cf.quakereport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * 创建时间：2019/1/4
 * 编 写 人：inpure
 * 邮    箱：chfjinqh@gmail.com
 * 功能描述：{@link EarthquakeAdapter}是一个{@link ArrayAdapter}
 * 它可以根据数据源为每一个列表提供布局，数据源是{@link Earthquake}对象列表
 */
public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    public EarthquakeAdapter(Context context,  ArrayList<Earthquake> objects) {
        super(context, 0, objects);

    }

    /**
     * @param position 在 list item 中显示的数据在 list 中的位置
     * @param convertView 填充的回收视图
     * @param parent 用于加载的父 ViewGroup
     * @return
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        Earthquake currentEarthquake = getItem(position);
        TextView magText = listItemView.findViewById(R.id.mag_text_view);
        magText.setText("" + currentEarthquake.getMag());

        TextView placeText = listItemView.findViewById(R.id.place_text_view);
        placeText.setText(currentEarthquake.getPlace());

        TextView timeText = listItemView.findViewById(R.id.time_text_view);
        timeText.setText(currentEarthquake.getTime());

        return listItemView;
    }


}
