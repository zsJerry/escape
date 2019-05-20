package com.zs.escape.utils;

import com.zs.escape.entity.escape.FinalRoute;
import com.zs.escape.entity.escape.RouteResult;


public class Dijkstra {
    ExcelImport excelImport = new ExcelImport();

    /**
     * 迪杰斯特拉算法：输入起点标号以及一个带有权值的邻接矩阵，输出起点到各个点的最短路径以及路径上的毒负荷量
     * @param orig   起点标号
     * @param map    带有权重的邻接矩阵
     * @return        RouteResult类型：
     *
     */
    public RouteResult dijkstra(int orig, int end, double[][] map) {
        int n =map.length;       //顶点的个数
        double[][] localMap = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                localMap[i][j] = map[i][j];
            }
        }
        RouteResult routeResult = new RouteResult();

        double[] shortest = new double[n];  //存放从start到其他节点的最短路径
        boolean[] visited = new boolean[n]; //标记当前该顶点的最短路径是否已经求出，true表示已经求出
        // TODO Auto-generated method stub
        // 初始化，第一个顶点求出
        shortest[orig] = 0;
        visited[orig] = true;

        //存放从start到其他各节点的最短路径
        String[] path = new String[n];
        for(int i = 0; i < n; i++){
            path[i] = orig + "," + i;
        }
        for(int count = 0; count != n-1; count ++)
        {
            //选出一个距离初始顶点最近的为标记顶点
            int k = -1;
            double min = -1;
            for(int i =0; i< n ; i++)//遍历每一个顶点
            {
                if( !visited[i] && localMap[orig][i] != -1) //如果该顶点未被遍历过且与orig相连
                {
                    if(min == -1 || min > localMap[orig][i]) //找到与orig最近的点
                    {
                        min = localMap[orig][i];
                        k = i;
                    }
                }
            }
            //正确的图生成的矩阵不可能出现K== M的情况
            if(k == -1)
            {
                System.out.println("the input map matrix is wrong!");
                return null;
            }
            shortest[k] = min;
            visited[k] = true;
            //以k为中心点，更新oirg到未访问点的距离
            for (int i = 0; i < n; i++)
            {
                if (!visited[i] && localMap[k][i] != -1)
                {
                    double callen = min + localMap[k][i];
                    if (localMap[orig][i] == -1 || localMap[orig][i] > callen)
                    {
                        localMap[orig][i] = callen;
                        path[i] = path[k] + "," + i;
                    }
                }
            }
        }
        routeResult.setPath(path[end]);
        routeResult.setShortValue(shortest[end]);
        return routeResult;
    }

    /**
     * 当存在两个终点时，判断毒负荷量最小的一条路径为最终路径
     * @param person
     * @param safe        安全点有两个
     * @param map
     * @return
     */
    public FinalRoute finalEscapeRoute(int person, int[] safe, double[][] map){
        FinalRoute finalRoute ;
        RouteResult result1 ;
        RouteResult result2 ;
        result1 = dijkstra(person,safe[0],map);
        result2 = dijkstra(person,safe[1],map);
        if (result1.getShortValue() < result2.getShortValue()){
            finalRoute = excelImport.getFinalRoute(result1);
        }else {
            finalRoute = excelImport.getFinalRoute(result2);
        }
        return  finalRoute;
    }


}
