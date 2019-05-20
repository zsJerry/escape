package com.zs.escape.test;

import com.zs.escape.utils.Dijkstra;
import com.zs.escape.utils.EscapeRoute;
import org.junit.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class TestDij {
    Dijkstra di = new Dijkstra();
    EscapeRoute ec = new EscapeRoute();
    @Test
    public void testDij(){
        double M =-1;
         double[][] adjMat ={
                {0,2,M,3,6,5},
                {2,0,5,4,M,7},
                {M,5,0,6,7,10},
                {3,4,6,0,M,12},
                {6,M,7,M,0,4},
                {5,7,10,12,4,0}
        };
        ec.sortDoubleArray(adjMat);
        System.out.println(Arrays.deepToString(adjMat));
    /*    List list = di.dijkstra(4,adjMat);
        String[] a = (String[]) list.remove(0);
        System.out.println(a[0]);*/
    }
    @Test
    public void testCalAdjacentMatrix(){
        double[][] a = {
                {0,2.3,5},
                {2,1.4,-0.6},
                {1,0.2,1.8},
                {3,1.4,8},
                {6,4.9,-3.5},
                {5,9.8,15},
                {4,2.7,12.3}
        };
        double[][] b = {
                {0,1},
                {0,3},
                {0,4},
                {0,5},
                {1,0},{1,3},{1,4},{1,5},{2,1},{2,4},{2,5},{2,6},{3,1},{3,3},{3,4},{3,5},{4,0},{4,2},{4,5},{5,0},{5,2},{5,6},{6,2},{6,5}
        };

       double[][] adM = ec.calAdjacentMatrix(a,b,10000,2.5);
        System.out.println("***" + Arrays.deepToString(adM));
    }
}
