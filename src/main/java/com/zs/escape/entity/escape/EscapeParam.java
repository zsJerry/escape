package com.zs.escape.entity.escape;

/**
 * 人员路径规划参数的实体类
 */
public class EscapeParam {
    private int paramId;
    private double[] source;
    private double[] person;
    private double[] end;

    public double[] getSource() {
        return source;
    }

    public void setSource(double[] source) {
        this.source = source;
    }

    public int getParamId() {
        return paramId;
    }

    public void setParamId(int paramId) {
        this.paramId = paramId;
    }

    public double[] getPerson() {
        return person;
    }

    public void setPerson(double[] person) {
        this.person = person;
    }

    public double[] getEnd() {
        return end;
    }

    public void setEnd(double[] end) {
        this.end = end;
    }
}
