package com.zs.escape.entity.escape;

import java.util.List;

/**
 * 转化为JSON传递出接口的类
 */
public class RouteJSON {
    private boolean status;//状态
    private int num;//路径的个数
    private List<FinalRoute>  results;//路径的坐标点、毒负荷量以及路径上节点数量的结果

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public List<FinalRoute> getResults() {
        return results;
    }

    public void setResults(List<FinalRoute> results) {
        this.results = results;
    }

    public RouteJSON() {
    }

    public RouteJSON(boolean status, int num, List<FinalRoute> results) {
        this.status = status;
        this.num = num;
        this.results = results;
    }
}
