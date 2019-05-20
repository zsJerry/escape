package com.zs.escape.entity.escape;

import java.util.List;

/**
 * 最短路径坐标类
 */
public class FinalRoute {
    public int pointsNum;       //路径的点的个数
    double  concentrationValue;  //最短路径的毒负荷量值
    public List<RoutePoint> routePointList;  //最短路径坐标，墨卡托坐标

    public int getPointsNum() {
        return pointsNum;
    }

    public void setPointsNum(int pointsNum) {
        this.pointsNum = pointsNum;
    }

    public double getConcentrationValue() {
        return concentrationValue;
    }

    public void setConcentrationValue(double concentrationValue) {
        this.concentrationValue = concentrationValue;
    }



    public FinalRoute() {
    }

    public List<RoutePoint> getRoutePointList() {
        return routePointList;
    }

    public void setRoutePointList(List<RoutePoint> routePointList) {
        this.routePointList = routePointList;
    }

    public FinalRoute(int pointsNum, double concentrationValue, List<RoutePoint> routePointList) {
        this.pointsNum = pointsNum;
        this.concentrationValue = concentrationValue;
        this.routePointList = routePointList;
    }
}
