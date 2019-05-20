package com.zs.escape;

import com.zs.escape.utils.GaussDiffusion;
import com.zs.escape.utils.TransCoordinate;
import org.junit.Test;


public class CoordinateTest {
    TransCoordinate transCoordinate = new TransCoordinate();
    GaussDiffusion gaussDiffusion = new GaussDiffusion();
  /*  @Test
    public void testCoordinate(){
        double[] source_W = {118.854695,28.899146};
        StringBuffer resultC;
        resultC = gaussDiffusion.reMapCoordinateConcentration(source_W,30,3.4,5);
        System.out.println(resultC);
    }*/
    @Test
    public void test(){
        double[] source_W = {118.854695,28.899146};
        double[] point = {118.856695,28.999146};
        double[] point_D = transCoordinate.transWgsToMercator(source_W,point);
        System.out.println(point_D[0] +"***********"+ point_D[1]);
   }

}
