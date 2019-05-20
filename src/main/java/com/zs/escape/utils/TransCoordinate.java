package com.zs.escape.utils;

/**
 * 坐标转化类
 */
public class TransCoordinate {
    /**
     * 将经纬度转化为墨卡托
     */
    public double[] wgsToMercator(double[] point){
        double longitude = point[0];
        double latitude = point[1];
        double x = 0;
        double y = 0;
        double[] result = new double[2];
        x = longitude * 20037508.34 / 180;
        y = Math.log(Math.tan((90 + latitude) * Math.PI / 360)) / (Math.PI / 180);
        y = y * 20037508.34 / 180;
        result[0] = x;
        result[1] = y;
        return result;
    }

    /**
     * 将墨卡托坐标转化为经纬度坐标
     *
     */
    public double[] mercatorToWgs(double longitude,double latitude){
        double[] result = new double[2];
        result[0] = longitude / 20037508.34 * 180;
        latitude = latitude / 20037508.34 * 180;
        result[1] = 180 / Math.PI * (2 * Math.atan(Math.exp(latitude * Math.PI / 180)) - Math.PI / 2);
        return result;
    }

    /**
     * 将目标点的墨卡托坐标转化为以泄漏源为原点的坐标
     * @param source 泄漏点的墨卡托坐标
     * @param target 目标点的墨卡托坐标
     * @param theta  风向角度，以正东方向为0度，遵循上北下南
     * @return       以泄漏源为原点的坐标
     */
    public double[] transMercatorToCartesian(double[] source,double[]target, double theta){
        double x = target[0] - source[0];
        double y = target[1] - source[1];
        double[] result = new double[2];
        result[0] = x * Math.cos(Math.toRadians(theta)) + y * Math.sin(Math.toRadians(theta));
        result[1] = y * Math.cos(Math.toRadians(theta)) - x * Math.sin(Math.toRadians(theta));
        return result;
    }

    /**
     * 将以泄漏点为原点的坐标转化为墨卡托坐标
     * @param source  泄漏点的墨卡托坐标
     * @param theta   风向角度，以正东方向为0度，遵循上北下南
     * @return        目标点的墨卡托坐标
     */
    public double[] transCartesianToMercator(double[] source,double targetX, double targetY, double theta){
        double[] result = new double[2];
        result[0] = targetX * Math.cos(Math.toRadians(theta)) - targetY * Math.sin(Math.toRadians(theta));
        result[1] = targetY * Math.cos(Math.toRadians(theta)) + targetX * Math.sin(Math.toRadians(theta));
        result[0] = result[0] + source[0];
        result[1] = result[1] + source[1];
        return result;
    }

    /**
     * 将以泄漏点为原点的坐标转化为地图坐标
     * @param source  泄漏点的墨卡托坐标
     * @param targetX、targetY  以泄漏点为原点的目标点的笛卡尔坐标
     * @param theta   风向角度，以正东方向为0度，遵循上北下南
     * @return        目标点的地图坐标
     */
    public double[] transCartesianToWgs(double[] source,double targetX, double targetY,double theta){
        double[] result ;
        result = transCartesianToMercator(source,targetX,targetY,theta);
        result = mercatorToWgs(result[0],result[1]);
        return result;
    }
    /**
     * 将以泄漏点为原点的经纬度坐标转化为笛卡尔坐标
     * @param source  泄漏点的经纬度坐标
     * @param point    目标点的经纬度坐标
     * @return        目标点的笛卡尔坐标
     */
    public double[] transWgsToMercator(double[] source,double[] point){
        double[] point_D;//目标点的笛卡尔坐标
        double[] source_M = wgsToMercator(source);
        double[] point_M = wgsToMercator(point);
        point_D = transMercatorToCartesian(source_M,point_M,0);
        return  point_D;
    }
}
