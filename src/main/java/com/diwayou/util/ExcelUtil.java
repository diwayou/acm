package com.diwayou.util;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;

import java.io.OutputStream;
import java.util.List;

/**
 * @author gaopeng 2021/2/20
 */
public class ExcelUtil {

    public static void write(Class<?> dataClass, List<?> dataList, OutputStream outputStream) {
        ExcelWriter excelWriter = EasyExcel.write(outputStream, dataClass).build();
        WriteSheet writeSheet = EasyExcel.writerSheet("数据").build();

        excelWriter.write(dataList, writeSheet);

        excelWriter.finish();
    }
}
