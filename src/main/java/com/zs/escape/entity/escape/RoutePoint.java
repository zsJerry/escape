package com.zs.escape.entity.escape;

/**
 * 记录人员逃生路径的各个坐标（墨卡托坐标）
 */
public class RoutePoint {
    private double lon;
    private double lat;

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public RoutePoint() {
    }

    public RoutePoint(double lon, double lat) {
        this.lon = lon;
        this.lat = lat;
    }
}
