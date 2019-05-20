package com.zs.escape.entity.leak;

import com.zs.escape.entity.leak.ConcentrationPoint;

import java.util.List;

/**
 * 最后输出为JSON格式的污染浓度范围坐标以及坐标值
 */
public class LeakResultsJSON {
    private boolean status;
    private int row;
    private int col;
    private List<ConcentrationPoint> results;

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public List<ConcentrationPoint> getResults() {
        return results;
    }

    public void setResults(List<ConcentrationPoint> results) {
        this.results = results;
    }

    public LeakResultsJSON() {
    }

    public LeakResultsJSON(boolean status, int row, int col, List<ConcentrationPoint> results) {
        this.status = status;
        this.row = row;
        this.col = col;
        this.results = results;
    }
}
