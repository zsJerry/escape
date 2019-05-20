package com.zs.escape.service;

import com.zs.escape.entity.escape.FinalRoute;
import com.zs.escape.entity.escape.RouteJSON;
import com.zs.escape.entity.leak.ConcentrationPoint;
import com.zs.escape.entity.leak.LeakParam;
import com.zs.escape.entity.leak.LeakResultsJSON;
import com.zs.escape.entity.map.MapLeakJSON;
import com.zs.escape.entity.map.MapLeakPoint;
import com.zs.escape.utils.Dijkstra;
import com.zs.escape.utils.EscapeRoute;
import com.zs.escape.utils.ExcelImport;
import com.zs.escape.utils.GaussDiffusion;

import java.util.ArrayList;
import java.util.List;

public class FindParamById {

    ExcelImport excelImport = new ExcelImport();
    EscapeRoute escapeRoute = new EscapeRoute();
    Dijkstra dijkstra = new Dijkstra();
    GaussDiffusion gaussDiffusion = new GaussDiffusion();
    private double[][] allLeakSource = {{13234330,3364562},{},{}};
    private double[] allLeakQ = {10000,25000,20000};//泄漏总量
    private int[] allGridSize = {5,5,5};//网格大小
    private double[] allWindSpeed = {3.5,4.5,3};//风速
    private double[] allTheta = {-45,0,};  //风向

    /**
     * 根据前端传入泄漏源坐标获取泄漏参数
     * @param id
     * @return
     */
    public LeakParam findLeakParamById(int id){
        int num = id-1;
        LeakParam leakParam = new LeakParam();
                double[] source = {allLeakSource[num][0],allLeakSource[num][1]};//泄漏点的泄漏坐标
                leakParam.setSource(source);
                leakParam.setWindSpeed(allWindSpeed[num]);
                leakParam.setTheta(allTheta[num]);
                leakParam.setGridSize(allGridSize[num]);
                leakParam.setLeakQ(allLeakQ[num]);
        return  leakParam;
    }

    /**
     * 将泄漏源的坐标信息返回
     * @return
     */
    public MapLeakJSON getLeakPoint(){
        boolean status = true;
        int num = allLeakSource.length;
        List<MapLeakPoint> leakPoints = new ArrayList<>();

        try {
            for (int i = 0; i < num; i++) {
                MapLeakPoint mapLeakPoint = new MapLeakPoint(i+1,allLeakSource[i][0],allLeakSource[i][1]);
                leakPoints.add(mapLeakPoint);
            }
        }catch (Exception e){
            status = false;
        }
        MapLeakJSON mapLeakJSON = new MapLeakJSON(status,num,leakPoints);
        return mapLeakJSON;
    }


    /**
     * 根据前台传入数据，返回人员路径实体类
     * @param id   选择不同的泄漏源参数不同
     * @return
     */
    public RouteJSON findRouteById(int id){
        LeakParam leakParam = findLeakParamById(id);//获取泄漏参数

        String safeFile = getSafe(id);      //获取安全区域位置
        String personFile = getPerson(id);  //获得人员位置

        double[][] a = excelImport.getDkPoint(leakParam.getSource(),leakParam.getTheta());//得到整个园区的节点坐标

        double[][] b = excelImport.getLinkPoint();// 园区相邻节点的标号

        double[][] cal = escapeRoute.calAdjacentMatrix(a,b,leakParam.getLeakQ(),leakParam.getWindSpeed());//路网的邻接矩阵

        int[] end = excelImport.getAnyPoint(safeFile);//人员起点坐标
        int[] start = excelImport.getAnyPoint(personFile);//人员终点的坐标
        List<FinalRoute> finalRouteList = new ArrayList<>();
        boolean status = true;
        int pathNum = start.length;
        for (int i = 0; i < start.length; i++) {
            try {
                FinalRoute finalRoute =  dijkstra.finalEscapeRoute(start[i],end,cal);//计算结果中每一个起点对应的路径
                finalRouteList.add(finalRoute);
            }catch (Exception e){
                status = false;
            }
        }
        RouteJSON routeJSON = new RouteJSON(status,pathNum,finalRouteList);

        return routeJSON;
    }


    /**
     * 根据前台传入数据，返回污染区域实体类
     * @param id   选择不同的泄漏源参数不同
     * @return
     */
    public LeakResultsJSON findLeakAreaById(Integer id){
        int idd = id;
        LeakParam leakParam = findLeakParamById(idd);
        boolean status = true;
        double[][] results; //网格浓度
        List<ConcentrationPoint> resultslist = new ArrayList<>();
        try {
            results = gaussDiffusion.reMapCoordinateConcentration(leakParam);
            //计算出的网格中每一点的浓度值
            for (int i = 0; i < results.length; i++) {
                ConcentrationPoint concentrationPoint = new ConcentrationPoint(results[i][0],results[i][1],results[i][2]);
                resultslist.add(concentrationPoint);
            }
        }catch (Exception e){
            status = false;
        }
        int row = gaussDiffusion.CROSSWISE/leakParam.getGridSize();//污染区域横向网格数量
        int col = gaussDiffusion.LENGTHWAYS*2/leakParam.getGridSize();//污染区域纵向网格数量
        LeakResultsJSON leakResultsJSON = new LeakResultsJSON(status,row,col,resultslist);//污染区域类，可以转化为JSON格式
        return leakResultsJSON;
    }




    /**
     * 得到安全点的坐标
     * @param id
     * @return
     */
    public String getSafe(int id){
        String safeFile = this.getClass().getClassLoader().getResource("escape/" +
                id +
                "/safe" +
                id +
                ".xls").getPath();
        return safeFile;
    }


    /**
     * 得到人员点的坐标
     * @param id
     * @return
     */
    public String getPerson(int id){
        String personFile = this.getClass().getClassLoader().getResource("escape/" +
                id +
                "/person" +
                id +
                ".xls").getPath();
        return personFile;
    }

}
