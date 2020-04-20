package com.diwayou.code;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.Data;

import java.io.File;
import java.nio.file.Path;

/**
 * @author gaopeng
 * @date 2020/4/20
 */
public class ReadExcel {
    public static void main(String[] args) {
        File file = Path.of("/Users/gaopeng/Downloads/region.xls").toFile();

        EasyExcel.read(file, DataRow.class, new DataListener()).sheet().doRead();
    }

    @Data
    private static class DataRow {

        private String code;

        private String code_p;

        private String code_c;

        private String code_t;

        private String c_name;

        private String py;

        private String jp;

        private String qp;

    }

    private static class DataListener extends AnalysisEventListener<DataRow> {

        @Override
        public void invoke(DataRow dataRow, AnalysisContext analysisContext) {
            System.out.println(dataRow);
        }

        @Override
        public void doAfterAllAnalysed(AnalysisContext analysisContext) {

        }
    }
}
