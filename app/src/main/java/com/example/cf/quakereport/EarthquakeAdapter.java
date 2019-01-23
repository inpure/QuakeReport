package com.example.cf.quakereport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * 创建时间：2019/1/4
 * 编 写 人：inpure
 * 邮    箱：chfjinqh@gmail.com
 * 功能描述：{@link EarthquakeAdapter}是一个{@link ArrayAdapter}
 * 它可以根据数据源为每一个列表提供布局，数据源是{@link Earthquake}对象列表
 */
public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    public EarthquakeAdapter(Context context, ArrayList<Earthquake> objects) {
        super(context, 0, objects);
    }

    /**
     * @param position    在 list item 中显示的数据在 list 中的位置
     * @param convertView 填充的回收视图
     * @param parent      用于加载的父 ViewGroup
     * @return 返回AdapterView中的位置视图
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 检查 view 是否被重用，否则加载一个
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        // 获得列表中当前位置的对象{@link Earthquake}
        Earthquake currentEarthquake = getItem(position);

        // 获得显示震级的 TextView 并设置
        TextView magText = listItemView.findViewById(R.id.mag_text_view);
        Double mag = currentEarthquake.getMag();
        magText.setText(formatMagnitude(mag));

        // 获得显示地点的 TextView 并设置
        TextView placeText = listItemView.findViewById(R.id.place_text_view);
        placeText.setText(currentEarthquake.getLocation());

        // 获得显示时间的 TextView 并设置
        TextView timeText = listItemView.findViewById(R.id.time_text_view);
        Date date = new Date(currentEarthquake.getTime());
        timeText.setText(formatDate(date));

        // 返回整个 list item 布局（包括3个 TextView）
        // 显示在 ListView 中
        return listItemView;
    }

    /**
     * 将震级格式化为十进制数，
     * 含一位小数。
     */
    private String formatMagnitude(Double mag) {
        DecimalFormat magFormat = new DecimalFormat("0.0");
        return magFormat.format(mag);
    }

    /**
     * @return 格式化的日期字符串
     */
    private String formatDate(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyy");
        return dateFormat.format(date);
    }
}
