package com.example.cf.quakereport;

/**
 * 创建时间：2019/1/3
 * 编 写 人：inpure
 * 邮    箱：chfjinqh@gmail.com
 * 功能描述：{@link Earthquake}用于表示地震信息
 */
public class Earthquake {
    private Double mMag;
    private String mLocation;
    private Long mTimeInMilliseconds;

    /**
     * @param mag 震级
     * @param place 地点
     * @param TimeInMilliseconds  时间
     */
    public Earthquake(Double mag, String place, Long TimeInMilliseconds) {
        mMag = mag;
        mLocation = place;
        mTimeInMilliseconds = TimeInMilliseconds;
    }

    /**
     * @return 返回震级
     */
    public Double getMag() {
        return mMag;
    }

    /**
     * @return 返回地震地点
     */
    public String getLocation() {
        return mLocation;
    }

    /**
     * @return 返回地震时间
     */
    public Long getTime() {
        return mTimeInMilliseconds;
    }
}

