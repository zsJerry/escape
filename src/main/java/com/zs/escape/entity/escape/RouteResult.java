package com.zs.escape.entity.escape;

/**
 *已知起点和终点计算出来的最短路径以及这条路径上的毒负荷量
 */
public class RouteResult {
    private String path;  //最短路径标号
    private double shortValue;//毒负荷量

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public double getShortValue() {
        return shortValue;
    }

    public void setShortValue(double shortValue) {
        this.shortValue = shortValue;
    }
}
