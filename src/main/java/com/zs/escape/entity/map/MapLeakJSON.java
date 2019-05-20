package com.zs.escape.entity.map;

import java.util.List;

/**
 * 返回前端的地图上的泄漏源的信息
 */
public class MapLeakJSON {
    private boolean status;
    private int num;
    private List<MapLeakPoint> results;

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

    public List<MapLeakPoint> getResults() {
        return results;
    }

    public void setResults(List<MapLeakPoint> results) {
        this.results = results;
    }

    public MapLeakJSON() {
    }

    public MapLeakJSON(boolean status, int num, List<MapLeakPoint> results) {
        this.status = status;
        this.num = num;
        this.results = results;
    }
}
