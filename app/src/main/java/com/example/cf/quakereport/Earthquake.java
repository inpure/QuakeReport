package com.example.cf.quakereport;

/**
 * 创建时间：2019/1/3
 * 编 写 人：inpure
 * 邮    箱：chfjinqh@gmail.com
 * 功能描述：{@link Earthquake}用于表示地震信息
 */
public class Earthquake {
    private int mMag;
    private String mPlace;
    private String mTime;

    /**
     * @param mag 震级
     * @param place 地点
     * @param time  时间
     */
    public Earthquake(int mag, String place, String time) {
        mMag = mag;
        mPlace = place;
        mTime = time;
    }

    /**
     * @return 返回震级
     */
    public int getMag() {
        return mMag;
    }

    /**
     * @return 返回地震地点
     */
    public String getPlace() {
        return mPlace;
    }

    /**
     * @return 返回地震时间
     */
    public String getTime() {
        return mTime;
    }
}

