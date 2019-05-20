package com.zs.escape.utils;


import java.util.Arrays;
import java.util.Comparator;

/**
 * 人员路径规划类
 */
public class EscapeRoute {
    GaussDiffusion gd = new GaussDiffusion();//定义高斯泄漏类


    final public double STEPSIZE = 300;//使用道路分段累加的方法计算一条道路的毒负荷量，定义分段的大小
    /**
     * 计算一条道路上的毒负荷量
     * @param start   道路起点的笛卡尔坐标
     * @param end     道路终点的笛卡尔坐标
     * @return        从起点到终点的毒负荷量
     */
    public double routeC(double[] start, double[] end, double Q, double u){
        double C = 0;
        double start_X= start[0];
        double start_Y = start[1];
        double end_X = end[0];
        double end_Y = end[1];
        if (start_X == end_X){
            C = cal1(start_X,start_Y,end_Y,Q,u);
        }else if (start_Y == end_Y){
            C = cal2(start_Y,start_X,end_X,Q,u);
        }else {
                double k = (start_Y - end_Y) / (start_X - end_X);//求直线斜率
            double b = start_Y - k * start_X;
            double t = Math.abs(start_X - end_X) / STEPSIZE;
            if (start_X < end_X) {
                for (double x = start_X; x < end_X; x = x + t) {
                    C = C + gd.calPoint(x, k * x + b, Q, u);//计算每个点的毒气浓度进行累加
                }
            } else {
                for (double x = end_X; x < start_X; x = x + t) {
                    C = C + gd.calPoint(x, k * x + b, Q, u);
                }
            }
        }
            return C;

    }

/*******************************************************计算邻接矩阵******************************************************************************************/
    /**
     * 计算邻接矩阵，输入点的标号与笛卡尔坐标，以及相连的点的标号，计算邻接矩阵
     * @param pointCoor   带有标号的点的坐标，第一列为点的标号，第二列为X轴坐标，第三列为Y轴坐标（以泄漏点为原点的节点笛卡尔坐标）
     *                  double[][] a = {
     *                 {0,2.3,5},
     *                 {2,1.4,-0.6},
     *                 {1,0.2,1.8},
     *                 {3,1.4,8},
     *                 {6,4.9,-3.5},
     *                 {5,9.8,15},
     *                 {4,2.7,12.3}
     *         };
     * @param link   二维矩阵，标记相邻点，示例：0与1相连.......0，1  与  1，0  属于不同元素
     *                double[][] b = {
     *                 {0,1},
     *                 {0,3},
     *                 {0,4},
     *                 {0,5},
     *                 {1,0},{1,3},{1,4},{1,5},{2,1},{2,4},{2,5},{2,6},{3,1},{3,3},{3,4},{3,5},{4,0},{4,2},{4,5},{5,0},{5,2},{5,6},{6,2},{6,5}
     *         }
     * @param Q        泄漏总量
     * @param u        风速
     * @return         邻接矩阵
     */
   public double[][] calAdjacentMatrix(double[][] pointCoor, double[][] link,double Q,double u){
        int pointNum = pointCoor.length;//点的个数
        double[][] adM = new double[pointNum][pointNum];//输出结果邻接矩阵
        int x,y;
        double[] start = new double[2];
        double[] end = new double[2];
        sortDoubleArray(pointCoor);
        sortDoubleArray(link);
       for (int i = 0; i < pointNum ; i++) {
           for (int j = 0; j < pointNum; j++) {
               adM[i][j] = -1;
           }
       }
       for (int i = 0; i < link.length; i++) {
            x = (int) link[i][0];
            y = (int) link[i][1];
            start[0] = pointCoor[x][1];
            start[1] = pointCoor[x][2];

           end[0] = pointCoor[y][1];
           end[1] = pointCoor[y][2];


            adM[x][y] = routeC(start,end,Q,u);
       }
       for (int i = 0; i < pointNum; i++) {
           for (int j = 0; j < pointNum; j++) {
               if (i == j){
                   adM[i][j] = 0;
               }
           }
       }
        return adM;
   }

/*************************************************************************************************************************************************/


    /**
     * 污染区域中一条道路与y轴平行时，道路上的毒负荷量的计算方法
     * @param x1  道路的x轴坐标
     * @param y1  起点的y坐标
     * @param y2  终点的y坐标
     * @param Q
     * @param u
     * @return
     */
   public double cal1(double x1, double y1, double y2,double Q, double u){
       double C =0;
       double t =  Math.abs(y1-y2) /STEPSIZE;
        if (y1 < y2){
            for (double y=y1; y < y2; y=y+t) {
                C = C + gd.calPoint(x1, y, Q, u);
            }
        }else {
            for (double y=y2; y < y1; y=y+t) {
                C = C + gd.calPoint(x1, y, Q, u);
            }
        }
        return C;
   }

    /**
     * 污染区域中一条道路与x轴平行时，道路上的毒负荷量的计算方法
     * @param y1    道路的y轴坐标
     * @param x1    起点的x坐标
     * @param x2    终点的x坐标
     * @param Q
     * @param u
     * @return
     */
    public double cal2(double y1, double x1, double x2,double Q, double u){
        double C =0;
        double t =  Math.abs(x1-x2) /STEPSIZE;
        if (x1 < x2){
            for (double x=x1; x < x2; x=x+t) {
                C = C + gd.calPoint(x, y1 , Q, u);
            }
        }else {
            for (double x=x2; x < y1; x=x+t) {
                C = C + gd.calPoint(x, y1, Q, u);
            }
        }
        return C;
    }


    /**
     * 根据二维数组的第一列对数组进行排序，从小到大
     * @param a  double类型的二维数组
     */
    public void sortDoubleArray(double[][] a){
        Arrays.sort(a, new Comparator<double[]>() {
            @Override
            public int compare(double[] o1, double[] o2) {
                if (o1[0] > o2[0]){
                    return 1;
                }else if (o1[0] < o2[0]){
                    return -1;
                }else {
                    return 0;
                }
            }
        });
    }




}
