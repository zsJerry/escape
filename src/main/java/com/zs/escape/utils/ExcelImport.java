package com.zs.escape.utils;


import com.zs.escape.entity.escape.FinalRoute;
import com.zs.escape.entity.escape.RoutePoint;
import com.zs.escape.entity.escape.RouteResult;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ExcelImport {
    TransCoordinate transCoordinate = new TransCoordinate();

/*    String pointExcel = "C:\\Users\\周硕\\Desktop\\point.xls";
    String routeExcel = "C:\\Users\\周硕\\Desktop\\route.xls";*/


    String pointExcel = this.getClass().getClassLoader().getResource("escape/point.xls").getPath();
    String routeExcel = this.getClass().getClassLoader().getResource("escape/route.xls").getPath();
    /**
     * 根据文件名读取excel文件,返回一个带标号数组
     * @param fileName
     * @return
     */
    public double[][] read(String fileName){
        jxl.Workbook readwb = null;
        try {
            // 构建Workbook对象, 只读Workbook对象 直接从本地文件创建Workbook
            readwb = Workbook.getWorkbook(new FileInputStream(new File(fileName)));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BiffException e) {
            e.printStackTrace();
        }
        // Sheet的下标是从0开始 获取第一张Sheet表
            Sheet readsheet = readwb.getSheet(0);
            // 获取Sheet表中所包含的总列数
            int rsColumns = readsheet.getColumns();
            // 获取Sheet表中所包含的总行数
            int rsRows = readsheet.getRows();
            // 获取指定单元格的对象引用
            double[][] result = new double[rsRows-1][rsColumns];
            for (int i = 1; i < rsRows; i++) {
                for (int j = 0; j < rsColumns; j++) {
                    Cell cell = readsheet.getCell(j,i);
                    result[i-1][j] = Double.valueOf(cell.getContents());
                }
            }
            readwb.close();
            return result;
    }

    /**
     * 获取节点point坐标以及标号【标号,经度,纬度】（这里的经度和纬度是墨卡托坐标）
     * @return
     */
    public double[][] getPoint(){
        double[][] point = read(pointExcel);//读取标点数据
        String[] strings = new String[point.length];
        for (int i = 0; i < strings.length; i++) {
            strings[i] = point[i][0] +","+point[i][1];
        }
        strings = unique(strings);
        String[] a ;
        double[][] result = new double[strings.length][3];
        for (int i = 0; i < strings.length; i++) {
            a = strings[i].split(",");
            result[i][0] = (double)i;
            result[i][1] = Double.valueOf(a[0]);
            result[i][2] =Double.valueOf(a[1]);
        }
        return result;
    }
/*******************************************************************************************************************************************/
    /**
     * 根据文件路径获取人员/泄漏点/安全点的坐标信息 ,返回各个点的标号数组
     * @return
     */
    public int[] getAnyPoint(String filename){
        double[][] person = read(filename);//读取人员坐标数据
        double[][] point= getPoint();//读取路径节点坐标数据
        int[] result = new int[person.length];
        for (int i = 0; i < person.length; i++) {
            for (int i1 = 0; i1 < point.length; i1++) {
                if ((person[i][0] == point[i1][1])&&(person[i][1] == point[i1][2])){
                    result[i] = (int)point[i1][0];
                    break;
                }
            }
        }
        return result;
    }

/**************************************************能够计算邻接矩阵的函数************************************************************************/

/****************************************************带有标号的路径节点（经纬度为墨卡托坐标）**********************************************************************/
    /**
     * 获取带有标号的路径节点坐标{[起点标号，起点经度，起点纬度，终点标号，终点经度，终点纬度]}
     * @return
     */
    public double[][] getRoute(){
        double[][] routePoint = read(routeExcel);//不带节点标号的道路节点
        double[][] point = getPoint();//带有节点标号的节点
        double[][] result = new double[routePoint.length][6];//带有节点标号的道路节点
        for (int i = 0; i < result.length; i++) {
            result[i][1] = routePoint[i][0];
            result[i][2] = routePoint[i][1];
            result[i][4] = routePoint[i][2];
            result[i][5] = routePoint[i][3];
            for (int i1 = 0; i1 < point.length; i1++) {
                if ((result[i][1] == point[i1][1])&&(result[i][2] == point[i1][2])){
                    result[i][0] = point[i1][0];
                }
                if ((result[i][4] == point[i1][1])&&(result[i][5] == point[i1][2])){
                    result[i][3] = point[i1][0];
                }
            }
        }
        return result;
    }
/***************************************************笛卡尔坐标的节点标号***********************************************************************/
    /**
     * 根据输入的泄漏点坐标以及风向计算出带有标号的道路节点矩阵
     * @param source  泄漏源经纬度坐标（墨卡托坐标）
     * @param theta    风向角度
     * @return        带有标号的道路节点笛卡尔矩阵{【标号，笛卡尔坐标x，笛卡尔坐标y】}
     */
    public double[][] getDkPoint(double[]source ,double theta){
        double[][] mPoint = getPoint();
        double[][] resultDkPoint = new double[mPoint.length][3];
        double[] temp = new double[2];
        double[] temp2 ;
        for (int i = 0; i < resultDkPoint.length; i++) {
            temp[0] = mPoint[i][1];
            temp[1] = mPoint[i][2];
            temp2 = transCoordinate.transMercatorToCartesian(source,temp,theta);
            resultDkPoint[i][0] = mPoint[i][0];
            resultDkPoint[i][1] = temp2[0];
            resultDkPoint[i][2] = temp2[1];
        }
        return resultDkPoint;
    }
/*******************************************************相连的节点的标号*******************************************************************/
    /**
     * 获取相连的道路节点
     * @return  {[1,2],[1,3]}  1点与2点相连，1点与3点相连
     */
    public double[][] getLinkPoint(){
        double[][] routePoint = getRoute();//道路节点{[起点标号，起点经度，起点纬度，终点标号，终点经度，终点纬度]}
        int length = routePoint.length;
        double[][] linkPoint = new double[length*2][2];
        for (int i = 0; i < length; i++) {
            linkPoint[i][0] = routePoint[i][0];//起点标号
            linkPoint[i][1] = routePoint[i][3];//终点标号

            linkPoint[i+length][0] = routePoint[i][3];//起点标号
            linkPoint[i+length][1] = routePoint[i][0];//终点标号
        }

        return linkPoint;
    }
/**************************************************************************************************************************/

    /**
     * 根据最短路径的标号获取最短路径的坐标
     * @param routeResult  最短路径的标号类
     * @return              最短路径坐标类
     */
    public FinalRoute getFinalRoute(RouteResult routeResult){
        double[][] point = getPoint();
        FinalRoute finalRoute = new FinalRoute();
        String route = routeResult.getPath();
        String[] temp = route.split(",");
        int num = temp.length;//路径坐标点的个数
        List<RoutePoint> routePointList = new ArrayList<>();
        double[] path = new double[num];
        double[][] routePoint = new double[num][2];
        for (int i = 0; i < num; i++) {
            path[i] = Double.valueOf(temp[i]);
            for (int i1 = 0; i1 < point.length; i1++) {
                if (path[i] == point[i1][0]){
                    routePoint[i][0] = point[i1][1];
                    routePoint[i][1] = point[i1][2];
                    break;
                }
            }
        }
        for (int i = 0; i < num; i++) {
            RoutePoint routePoint1 = new RoutePoint(routePoint[i][0],routePoint[i][1]);
            routePointList.add(routePoint1);
        }
        finalRoute.setPointsNum(num);
        finalRoute.setRoutePointList(routePointList);//路径的坐标点
        finalRoute.setConcentrationValue(routeResult.getShortValue());//路径的毒负荷量
        return finalRoute;
    }







    /**
     * 去除一个字符串数组中重复的字符
     * @param array
     * @return
     */
    public String[] unique(String[] array){
        Set<String> set = new HashSet<>();
        for(int i=0;i<array.length;i++){
            set.add(array[i]);
        }
        String[] arrayResult = set.toArray(new String[set.size()]);
        return arrayResult;
    }



}
