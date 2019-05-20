package com.zs.escape.test;

import com.zs.escape.utils.ExcelImport;
import org.junit.Test;


import java.util.Arrays;



public class ExcelTest {
    ExcelImport excelImport = new ExcelImport();
    @Test
    public void testExcel(){

        /*double[][] a = excelImport.read("C:\\Users\\周硕\\Desktop\\point.xls");*/
        double[][] b = excelImport.getRoute();
        System.out.println(Arrays.deepToString(b));
    }

    @Test
    public void testQ(){
        double[][] a = excelImport.getPoint();
        System.out.println(Arrays.deepToString(a));
        System.out.println(a.length);
    }
}
