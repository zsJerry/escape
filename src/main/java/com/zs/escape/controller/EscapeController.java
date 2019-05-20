package com.zs.escape.controller;

import com.zs.escape.entity.escape.RouteJSON;
;
import com.zs.escape.entity.leak.LeakResultsJSON;
import com.zs.escape.entity.map.MapLeakJSON;
import com.zs.escape.service.FindParamById;

/**
 * 传给前台的数据，分为三部分
 */
public class EscapeController {

    FindParamById findParamById = new FindParamById();
    /**
     * 1、泄漏点的位置坐标以及对应标号
     */
    public MapLeakJSON getMapLeak(){
        MapLeakJSON mapLeakJSON = new MapLeakJSON();
        return mapLeakJSON;
    }


    /**
     * 2、根据前端的选择，计算出的污染区域
     * 根据传入参数返回污染区域实体类，并转化为JSON形式
     * @param id
     * @return
     */
   public LeakResultsJSON getLeak(int id){
       LeakResultsJSON leakResultsJSON = findParamById.findLeakAreaById(id);
       return leakResultsJSON;
   }


    /**
     * 2、根据前端的选择，计算出的人员路径
     * 根据传入参数返回路径数据实体类，并转化为JSON形式
     * @param id
     * @return
     */
    public RouteJSON getRoute(int id){
        RouteJSON routeJSON = findParamById.findRouteById(id);
        return routeJSON;
    }


}
