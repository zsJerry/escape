package com.zs.escape.test;

import com.zs.escape.entity.escape.FinalRoute;
import com.zs.escape.entity.escape.RouteJSON;
import com.zs.escape.entity.leak.LeakResultsJSON;
import com.zs.escape.service.FindParamById;
import com.zs.escape.utils.Dijkstra;
import com.zs.escape.utils.EscapeRoute;
import com.zs.escape.utils.ExcelImport;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class EsTest {
    EscapeRoute escapeRoute = new EscapeRoute();
    ExcelImport excelImport = new ExcelImport();
    Dijkstra dijkstra = new Dijkstra();

    String safeFile = this.getClass().getClassLoader().getResource("escape/1/safe1.xls").getPath();
    String personFile = this.getClass().getClassLoader().getResource("escape/1/person1.xls").getPath();
    String a =  this.getClass().getClassLoader().getResource("escape/route.xls").getPath();//获取文件路径

    @Test
    public void teatRead(){
        System.out.println(a);
        int[] s = excelImport.getAnyPoint(a);
        System.out.println(Arrays.toString(s));
    }



    @Test
    public void testAll(){
    /*    double[] source = {13234330,3364562};
        double[][] a = excelImport.getDkPoint(source,-45);
        double[][] b = excelImport.getLinkPoint();
        double[][] cal = escapeRoute.calAdjacentMatrix(a,b,10000,2.5);
        int[] end = excelImport.getAnyPoint(safeFile);//人员起点坐标
        int[] start = excelImport.getAnyPoint(personFile);//人员终点的坐标
        List<FinalRoute> finalRouteList = new ArrayList<>();
        boolean status = true;
        int pathNum = start.length;
        RouteJSON routeJSON = new RouteJSON();

        for (int i = 0; i < start.length; i++) {
            try {
                FinalRoute finalRoute =  dijkstra.finalEscapeRoute(start[i],end,cal);//计算结果中每一个起点对应的路径
                finalRouteList.add(finalRoute);
            }catch (Exception e){
                status = false;
            }
        }

        RouteJSON routeJSON1 = new RouteJSON(status,pathNum,finalRouteList);*/

    }
    FindParamById findParamById = new FindParamById();
    @Test
    public void getLeak() {
        Integer id = 1;
        LeakResultsJSON leakResultsJSON1 = findParamById.findLeakAreaById(id);
        System.out.println(leakResultsJSON1.getStatus());

    }

}
