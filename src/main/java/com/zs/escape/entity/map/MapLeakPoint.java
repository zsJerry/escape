package com.zs.escape.entity.map;

public class MapLeakPoint {
    private int id;
    private double sourceLon;
    private double sourceLat;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public MapLeakPoint(int id, double sourceLon, double sourceLat) {
        this.id = id;
        this.sourceLon = sourceLon;
        this.sourceLat = sourceLat;
    }


    public double getSourceLon() {
        return sourceLon;
    }

    public void setSourceLon(double sourceLon) {
        this.sourceLon = sourceLon;
    }

    public double getSourceLat() {
        return sourceLat;
    }

    public void setSourceLat(double sourceLat) {
        this.sourceLat = sourceLat;
    }

    public MapLeakPoint() {
    }

}
