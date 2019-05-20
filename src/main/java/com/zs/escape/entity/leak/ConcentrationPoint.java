package com.zs.escape.entity.leak;

/**
 * 输出污染浓度区域的点的类
 */
public class ConcentrationPoint {
    private double longitude;
    private double latitude;
    private double value;

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public ConcentrationPoint() {
    }

    public ConcentrationPoint(double longitude, double latitude, double value) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.value = value;
    }
}
