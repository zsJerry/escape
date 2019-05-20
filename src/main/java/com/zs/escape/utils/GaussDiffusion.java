package com.zs.escape.utils;

import com.zs.escape.entity.leak.LeakParam;


/**
 * 浓度计算类 ,最终目的是计算污染区域以及向对应的浓度
 */
public class GaussDiffusion {
    TransCoordinate coordinate = new TransCoordinate();//坐标转换类
    public final int CROSSWISE = 1500; // 下风向正风向距离
    public final int LENGTHWAYS = 400; //纵向风向距离的一半

    /**
     * 根据高斯烟羽模型计算某一点的浓度值
     * @param x 某一点的笛卡尔x坐标
     * @param y 某一点的笛卡尔y坐标
     * @param Q 泄漏总量
     * @param u  风速
     * @return   浓度值
     */
    public double calPoint(double x, double y, double Q ,double u){
        double z = 1.5, H = 7.4;
        double oy0 = 0.22 * x / (Math.pow((1+0.0001*x),0.5));
        double oz0 = 0.2 * x;
        double a0=0.042,b0=1.1,c0=0.0364,d0=0.4364,e0=0.05,f0=0.273,g0=0.024;
        double f = 1 + a0 * 4;
        double oy = oy0 * f;
        double fz = (b0-c0*Math.log(x)) * Math.pow((d0+e0* Math.log(x)),-1) * (Math.pow(4,(f0-g0* Math.log(x))));
        double oz = oz0 * fz;
        double c1 = Q / (2*Math.PI*u*oy*oz);
        double c2 = Math.exp(-((Math.pow(y,2)) / (2* Math.pow(oy,2))));
        double c3 = Math.exp(-((Math.pow((z-H),2)) / (2* Math.pow(oz,2))));
        double c4 = Math.exp(-((Math.pow((z+H),2)) / (2* Math.pow(oz,2))));
        double c = c1 * c2 * (c3 + c4);
        return c;
    }
    /**
     * 计算毒气污染区域内毒气浓度，取网格计算，以污染源为原点，下风向为x轴正方向，x取值为0--CROSSWISE，y取值为-LENGTHWAYS--LENGTHWAYS
     * @param gridSize 网格大小
     * @param Q        泄漏量
     * @param u        风速
     * @return         相对泄漏原点的坐标值以及对应点的浓度
     */
    public double[][] calPollutionGridConcentration(int gridSize,double Q ,double u){
        int xi = 0;
        xi = (CROSSWISE/gridSize)*(LENGTHWAYS*2/gridSize); //根据网格的大小
        double[][] result = new double[xi][3];
        int i = 0;
        for (int x=gridSize; x<=CROSSWISE; x=x+gridSize){
            for (int y=-LENGTHWAYS; y<LENGTHWAYS; y=y+gridSize){
                result[i][0] = x;
                result[i][1] = y;
                result[i][2] = calPoint(x,y,Q,u); //计算一个坐标点的浓度值
                i++;
            }
        }
        return result;
    }


/******************************************************************主要使用的函数：计算受污染的面积********************************************************************************/
    /**
     * 根据输入的泄漏点地图坐标以及风向风速，计算出受污染的区域的毒气浓度值
     * @param leakParam   泄漏参数类
     * @return           二维数组形式的坐标值以及浓度值，示例：{{118.8559397422, 8.8997751578, 0.4340500114},
     *                                                          {118.8559172843, 28.8998092119,0.4308010691}}
     */
    public double[][]  reMapCoordinateConcentration(LeakParam leakParam){
        double[] source_MKT = leakParam.getSource();//泄漏源经纬度坐标（墨卡托坐标）
        double theta = leakParam.getTheta();
        double u = leakParam.getWindSpeed();
        int gridSize = leakParam.getGridSize();
        double Q = leakParam.getLeakQ();
        //计算毒气污染区域内毒气浓度
        double[][] areaC = calPollutionGridConcentration(gridSize,Q,u);//污染区域的浓度，二维坐标中的一行：【笛卡尔x，笛卡尔y，浓度】
        double[][] result = new double[areaC.length][3];
        double[] temp;
        //3.将毒气污染区域的经纬度进行转化
        for (int i = 0; i < areaC.length; i++) {
            temp = coordinate.transCartesianToMercator(source_MKT,areaC[i][0],areaC[i][1],theta);
            result[i][0] = temp[0];
            result[i][1] = temp[1];
            result[i][2] = areaC[i][2];
        }
       return result;
    }
/**************************************************************************************************************************************************/







}
