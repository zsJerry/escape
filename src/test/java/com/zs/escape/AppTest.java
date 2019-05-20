package com.zs.escape;

import com.zs.escape.utils.ExcelImport;
import com.zs.escape.utils.TransCoordinate;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.Assert.assertTrue;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    TransCoordinate tc = new TransCoordinate();
    ExcelImport ei = new ExcelImport();
    @Test
    public void sd(){
        String a = this.getClass().getClassLoader().getResource("escape/3/person3.xls").getPath();
      /*double[] a = tc.mercatorToWgs(13234950,3363773);
        System.out.println(Arrays.toString(a));*/
        int[] b = ei.getAnyPoint(a);
        System.out.println(Arrays.toString(b));
    }
  /*  EscapeRoute er = new EscapeRoute();
    LeakParam leakParam = new LeakParam(10000,3.5,0);//泄漏事故的参数定义

    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }
   @Test
    public void testRouteC(){
        double[] start = {18.21,37.372};
        double[] end = {23.87,-13.21};
        double C = er.routeC(start,end,leakParam.getQ(),leakParam.getU());
       System.out.println(C);
   }*/
}
