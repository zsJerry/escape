package com.zs.escape.entity.leak;

/**
 * 泄漏事故的参数类
 *
 */
public class LeakParam {
    double[] source;//泄漏源的经纬度
    double windSpeed;  //风速
    double theta;  //风向
    int gridSize;//网格大小
    double leakQ;//泄漏总量

    public LeakParam(double[] source, double windSpeed, double theta, int gridSize, double leakQ) {
        this.source = source;
        this.windSpeed = windSpeed;
        this.theta = theta;
        this.gridSize = gridSize;
        this.leakQ = leakQ;
    }

    public double[] getSource() {
        return source;
    }

    public void setSource(double[] source) {
        this.source = source;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getTheta() {
        return theta;
    }

    public void setTheta(double theta) {
        this.theta = theta;
    }

    public int getGridSize() {
        return gridSize;
    }

    public void setGridSize(int gridSize) {
        this.gridSize = gridSize;
    }

    public double getLeakQ() {
        return leakQ;
    }

    public void setLeakQ(double leakQ) {
        this.leakQ = leakQ;
    }

    public LeakParam() {
    }
}
