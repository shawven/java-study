package web.supports.utils;


import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 *
 * @description: excel工具类
 * @author: FS
 * @date: 2018-03-12 14:04
 */
public class ExcelUtils {


    /**
     * 创建2003excel xls后缀
     *
     * @param response
     * @param excel
     * @param rows
     * @param <T>
     */
    public static <T> void create2003Excel(HttpServletResponse response, Excel excel, List<T> rows) {
        createExcel(response, excel, rows, "xls");
    }

    /**
     * 创建2007Excel xlsx后缀
     *
     * @param response
     * @param excel
     * @param rows
     * @param <T>
     */
    public static <T> void create2007Excel(HttpServletResponse response, Excel excel, List<T> rows) {
        createExcel(response, excel, rows, "xlsx");
    }

    /**
     * 创建excel表格
     *
     * @param response
     * @param excel
     * @param rows
     * @param extend
     * @param <T>
     */
    public static <T> void createExcel(HttpServletResponse response, Excel excel, List<T> rows, String extend) {
        if (CollectionUtils.isEmpty(excel.getColumns()) || CollectionUtils.isEmpty(rows)) {
            return;
        }

        // 列总数
        int columnSize = excel.getColumns().size();
        // 行总数
        int rowSize = rows.size();

        // 创建excel工作簿
        Workbook wb = new HSSFWorkbook();

        // 创建第一个sheet（页），并命名
        Sheet sheet = wb.createSheet(excel.getFileName());

        //设置缺省
        sheet.setDefaultRowHeightInPoints(15);
        sheet.setDefaultColumnWidth(20);

        // 设置表头
        if (excel.getTableName() != null) {
            Cell tableHeader = sheet.createRow(0).createCell(0);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, columnSize - 1));
            tableHeader.setCellValue(excel.getTableName());
            setTableHeaderStyle(wb, tableHeader);
        }


        // 设置列标题
        Row columnTitleRow = sheet.createRow(1);
        for (int i = 0; i < columnSize; i++) {
            // 创建列
            Excel.Column column = excel.getColumns().get(i);
            Cell columnTitle = columnTitleRow.createCell(i);
            columnTitle.setCellValue(column.getTitle());
            setColumnTitleStyle(wb, columnTitle, column.getColumnType());
            //设置列宽高
            if (column.getWidth() != null) {
                sheet.setColumnWidth(i, 256 * column.getWidth());
            } else {
                sheet.autoSizeColumn(i);
            }
        }

        //设置行数据
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (int i = 0; i < rowSize; i++) {
            T row = rows.get(i);

            Row dataRow = sheet.createRow(i + 2);

            for (int j = 0; j < columnSize; j++) {
                Excel.Column column = excel.getColumns().get(j);

                Object originalValue = getCellValue(column, row);

                Cell dataCell = dataRow.createCell(j);
                setRowStyle(wb, dataCell, column.getColumnType());
                switch (column.getColumnType()) {
                    case STRING:
                        String cellValue = originalValue == null ? "" : String.valueOf(originalValue);
                        dataCell.setCellValue(cellValue);
                        break;
                    case INTEGER:
                        if (originalValue != null ) {
                            dataCell.setCellValue(Integer.valueOf(originalValue.toString()));
                        }
                    case DOUBLE:
                        if (originalValue != null ) {
                            dataCell.setCellValue(Double.parseDouble(originalValue.toString()));
                        }
                        break;
                    case DATE:
                        if (originalValue != null) {
                            try {
                                dataCell.setCellValue(sdf.format(
                                        new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH)
                                        .parse(originalValue.toString())));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case TIMESTAMP:
                        dataCell.setCellValue(originalValue == null ? "" : sdf.format(originalValue.toString()));
                        break;
                    default:
                        dataCell.setCellValue(originalValue == null ? "" : originalValue.toString());
                        break;
                }
            }
        }

        // 输出excel
        response.reset();
        response.setContentType("application/vnd.ms-excel;charset=utf-8");

        if (excel.getFileName() == null) {
            excel.setFileName(excel.getTableName() != null ?
                    excel.getTableName() : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        }

        String fileName = new String((excel.getFileName() + "." +extend).getBytes(), StandardCharsets.ISO_8859_1);
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
        try {
            wb.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 设置表头样式
     *
     * @param wb
     * @param tableHeader
     */
    private static void  setTableHeaderStyle(Workbook wb, Cell tableHeader) {
        CellStyle cs = wb.createCellStyle();
        Font font = wb.createFont();

        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        font.setFontHeightInPoints((short) 14);
        cs.setFont(font);
        cs.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cs.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        tableHeader.getRow().setHeightInPoints(20);
        tableHeader.setCellStyle(cs);
    }

    /**
     * 设置列标题样式
     *
     * @param wb
     * @param columnTitle
     * @param columnType
     */
    private static void setColumnTitleStyle(Workbook wb, Cell columnTitle, ColumnType columnType) {
        CellStyle cs = wb.createCellStyle();
        Font font = wb.createFont();

        font.setColor(IndexedColors.BLACK.getIndex());
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        cs.setFont(font);
        cs.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cs.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        switch (columnType) {
            case INTEGER:
                cs.setDataFormat(wb.createDataFormat().getFormat("#,#0"));
                break;
            case DOUBLE:
                cs.setDataFormat(wb.createDataFormat().getFormat("#,##0.00"));
                break;
            default:
                break;
        }

        columnTitle.getRow().setHeightInPoints(20);
        columnTitle.setCellStyle(cs);
    }

    /**
     * 设置行样式
     *
     * @param wb
     * @param cell
     * @param columnType
     */
    private static void setRowStyle(Workbook wb, Cell cell, ColumnType columnType) {
        CellStyle cs = wb.createCellStyle();

        switch (columnType) {
            case INTEGER:
                cs.setAlignment(HSSFCellStyle.ALIGN_LEFT);
                break;
            case DOUBLE:
                cs.setAlignment(HSSFCellStyle.ALIGN_LEFT);
                cs.setDataFormat(wb.createDataFormat().getFormat("#,##0.00"));
                break;
            default:
                break;
        }

        cell.getRow().setHeightInPoints(15);
        cell.setCellStyle(cs);
    }

    /**
     * 获取表格数据
     *
     * @param column
     * @param row
     * @param <T>
     * @return
     */
    private static <T> Object getCellValue(Excel.Column column, T row) {
        Object value;
        if (row instanceof Map) {
            value = ((Map)row).get(column.getField());
        } else {
            try {
                value = ReflectHelper.getProperty(row, column.getField());
            } catch (Exception e) {
                throw new RuntimeException("请检查类是否包含字段:" + column.getField());
            }
        }

        return value;
    }

    /**
     * Created with IntelliJ IDEA.
     * @description:  列的类型
     * @author: FS
     * @date: 2018-03-12 14:05
     */
    public enum ColumnType {
        STRING, INTEGER, DOUBLE, DATE, TIMESTAMP
    }


    /**
     * Created with IntelliJ IDEA.
     * @description: excel对象
     * @author: FS
     * @date: 2018-03-12 14:05
     */
    public static class Excel {

        private List<Column> columns = new ArrayList<>();

        /**
         * 文件名
         */
        private String fileName;
        /**
         * 表头名
         */
        private String tableName;


        public List<Column> getColumns() {
            return columns;
        }

        public void setColumn(String title, String field, int width, ColumnType columnType) {
            Column column = new Column();
            column.setTitle(title);
            column.setField(field);
            column.setWidth(width);
            column.setColumnType(columnType);
            this.columns.add(column);
        }

        public void setColumns(List<Column> columns) {
            this.columns.addAll(columns);
        }


        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getTableName() {
            return tableName;
        }

        public void setTableName(String tableName) {
            this.tableName = tableName;
        }

        /**
         * 列对象
         */
        private class Column {

            private String title;

            private String field;

            private Integer width;

            private ColumnType columnType;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getField() {
                return field;
            }

            public void setField(String field) {
                this.field = field;
            }

            public Integer getWidth() {
                return width;
            }

            public void setWidth(Integer width) {
                this.width = width;
            }

            public ColumnType getColumnType() {
                return columnType;
            }

            public void setColumnType(ColumnType columnType) {
                this.columnType = columnType;
            }
        }

    }
}

