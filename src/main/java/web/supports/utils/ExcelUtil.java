package web.supports.utils;


import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.*;
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
public class ExcelUtil<T> {

    private static final String DEFAULT_FONT_NAME = "宋体";
    private static final int DEFAULT_HEADER_HEIGHT = 20;
    private static final int DEFAULT_COLUMN_HEIGHT = 20;
    private static final short DEFAULT_HEADER_FONT_SIZE = 14;
    private static final short DEFAULT_ROW_FONT_SIZE= 11;

    /**
     * 预留的偏移量 头部和列名
     */
    private int reserveOffset = 2;

    /**
     * 列名
     */
    private List<Column> columns = new ArrayList<>();

    /**
     * 记录列的信息用于合并
     */
    private List<ColumnRecord> columnRecords = new ArrayList<>();

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 表头名
     */
    private String tableName;

    /**
     * 数据
     */
    private List<T> data;

    /**
     * 扩展名
     */
    private String ext = "xlsx";

    private Workbook wb = new HSSFWorkbook();

    private OutputStream out;


    /**
     * 创建excel表格
     */
    public ExcelUtil<T> createExcel() throws IOException {
        if (CollectionUtils.isEmpty(columns) || CollectionUtils.isEmpty(data)) {
            throw new IllegalArgumentException();
        }
        if (fileName == null) {
            fileName = UUID.randomUUID().toString();
        }

        // 创建第一个sheet（页），并命名
        Sheet sheet = wb.createSheet(fileName);

        // 设置表头
        setHeader(sheet);

        // 设置列标题
        setColumns(sheet);

        //设置行数据
        setRows(sheet);

        return this;
    }


    /**
     * 设置行和单元格
     *
     * @param sheet
     */
    private void setRows(Sheet sheet) {
        int dataSize = data.size();
        int columnsSize = getColumns().size();

        for (int i = 0, rowIndex = reserveOffset; i < dataSize; i++, rowIndex ++) {
            T elem = data.get(i);
            Row row = sheet.createRow(rowIndex);

            for(int columnIndex = 0; columnIndex < columnsSize; columnIndex++) {
                Column column = columns.get(columnIndex);
                Object cellValue = getElemValue(elem, column.getField());

                if (rowIndex == reserveOffset) {
                    initMergeNodes(columnIndex, cellValue);
                } else {
                    mergeColumns(sheet, elem, rowIndex, columnIndex);
                }

                Cell cell = row.createCell(columnIndex);
                setCellStyle(cell, column.getColumnType());
                setCellData(cell, column.getColumnType(), cellValue);
            }
        }
    }


    /**
     * 初始化合并节点信息
     *
     * @param columnIndex
     * @param cellValue
     */
    private void initMergeNodes(int columnIndex, Object cellValue) {
        ColumnRecord columnRecord = new ColumnRecord();
        columnRecord.setOldValue(cellValue);
        columnRecord.setValue(cellValue);
        columnRecord.setRowIndex(reserveOffset);
        columnRecord.setCellIndex(columnIndex);
        columnRecords.add(columnRecord);
    }

    /**
     * 行合并的基础是行首的值一样时相邻的行才能合并
     * 简称（值相等）：当前单元格的值与最近一次合并记录的值相等时
     * 简称（值不相等）：当前单元格的值与最近一次合并记录的值不相等时
     *
     * @param sheet
     * @param elem
     * @param rowIndex
     * @param columnIndex
     */
    private void mergeColumns(Sheet sheet, T elem, int rowIndex, int columnIndex) {
        Object oldValue = columnRecords.get(columnIndex).getValue();
        Object cellValue = getElemValue(elem, columns.get(columnIndex).getField());

        Column column = columns.get(columnIndex);
        if (column.needMerge) {
            ColumnRecord columnRecord = columnRecords.get(columnIndex);

            Object value = columnRecord.getValue();
            Object headValue = columnRecords.get(0).getOldValue();
            Object oldHeadValue = getElemValue(elem, columns.get(0).getField());

            // 最后一次做合并操作是是记录的行索引
            int mergeStartIndex = columnRecord.getRowIndex();
            // 合并前一行（合并只针对之前的单元格）
            int mergeEndIndex = rowIndex - 1;
            int cellIndex = columnRecord.getCellIndex();

            // 如果合并的开始行和结束行相等没必要合并
            boolean necessaryMerged = mergeStartIndex != mergeEndIndex;

            // 处于行首，值不相等时
            // 说明：行首值不相等，说明当前是另一条记录， 应该把先前的所有行首合并为一个单元格
            if (columnIndex == 0 && !value.equals(cellValue)) {
                if (necessaryMerged) {
                    sheet.addMergedRegion(new CellRangeAddress(mergeStartIndex, mergeEndIndex, cellIndex, cellIndex));
                }
                // 从当前开始重新记录单元格的内容、单前行索引（下次合并从这里开始）、当前的合并列索引
                columnRecord.setValue(cellValue);
                columnRecord.setRowIndex(rowIndex);
                columnRecord.setCellIndex(columnIndex);
                // 非行首
            } else if (columnIndex > 0) {
                // 满足 值不相等 或 值相等但行首的值不相等
                // 说明：1、不相等应该把同一列先前的所有单元格合并为一个单元格
                //       2、相等但行首不相等，还是要合并之前的
                if (!value.equals(cellValue)
                        || (value.equals(cellValue) && !headValue.equals(oldHeadValue))) {
                    if (necessaryMerged) {
                        sheet.addMergedRegion(new CellRangeAddress(mergeStartIndex, mergeEndIndex, cellIndex, cellIndex));
                    }
                    columnRecord.setValue(cellValue);
                    columnRecord.setRowIndex(rowIndex);
                    columnRecord.setCellIndex(columnIndex);
                }
            }

            // 最后一行时，
            if (rowIndex == data.size()) {
                // 处于行首，行首的值相等时，把当前行首往上合并，
                if (columnIndex == 0 && value.equals(cellValue)) {
                    sheet.addMergedRegion(new CellRangeAddress(mergeStartIndex, rowIndex, cellIndex, cellIndex));
                    // 非行首，行首相等时，也往上合并
                } else if (value.equals(cellValue) && headValue.equals(oldHeadValue)) {
                    sheet.addMergedRegion(new CellRangeAddress(mergeStartIndex, rowIndex, cellIndex, cellIndex));
                }
            }
        }

        // 每遍历一次行的所有列时，都把新值设为旧值
        columnRecords.get(columnIndex).setOldValue(oldValue);
    }

    /**
     * 设置表头
     *
     * @param sheet
     */
    private void setHeader(Sheet sheet) {
        if (getTableName() != null) {
            Cell tableHeader = sheet.createRow(0).createCell(0);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, getColumns().size() - 1));
            tableHeader.setCellValue(getTableName());
            setTableHeaderStyle(tableHeader);
        }
    }


    /**
     * 设置列
     *
     * @param sheet
     */
    private void setColumns(Sheet sheet) {
        Row columnTitleRow = sheet.createRow(1);
        int columnsSize = getColumns().size();
        for (int i = 0; i < columnsSize; i++) {
            // 创建列
            Column column = getColumns().get(i);
            Cell columnTitle = columnTitleRow.createCell(i);
            columnTitle.setCellValue(column.getTitle());

            //设置列宽高
            if (column.getWidth() != null) {
                sheet.setColumnWidth(i, 256 * column.getWidth());
            } else {
                sheet.autoSizeColumn(i);
            }

            setColumnStyle(columnTitle, column.getColumnType());
        }
    }

    /**
     * 设置数据单元格
     *
     * @param cell
     * @param columnType
     * @param originalValue
     */
    private void setCellData(Cell cell, ColumnType columnType, Object originalValue) {
        switch (columnType) {
            case INTEGER:
                if (originalValue != null ) {
                    cell.setCellValue(Integer.valueOf(originalValue.toString()));
                } else {
                    cell.setCellValue(0);
                }
                break;
            case DOUBLE:
                if (originalValue != null && originalValue.toString().length() > 0
                        && Character.isWhitespace(originalValue.toString().charAt(0))) {
                    cell.setCellValue(Double.parseDouble(originalValue.toString()));
                } else {
                    cell.setCellValue(0d);
                }
                break;
            case DATE:
                if (originalValue != null) {
                    try {
                        Date date = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH)
                                .parse(originalValue.toString());
                        cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));
                        return;
                    } catch (ParseException ignored) {}
                }
                cell.setCellValue("");
                break;
            case TIMESTAMP:
                if (originalValue != null) {
                    cell.setCellValue(
                            new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(originalValue.toString()));
                } else {
                    cell.setCellValue("");
                }
                break;
            case STRING:
            default:
                cell.setCellValue(originalValue == null ? "" : originalValue.toString());
        }
    }

    /**
     * 设置表头样式
     *
     * @param tableHeader
     */
    private void setTableHeaderStyle(Cell tableHeader) {
        Font font = wb.createFont();
        font.setFontHeightInPoints(DEFAULT_HEADER_FONT_SIZE);
        font.setFontName(DEFAULT_FONT_NAME);

        CellStyle cs = wb.createCellStyle();
        cs.setFont(font);
        cs.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cs.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        tableHeader.getRow().setHeightInPoints(DEFAULT_HEADER_HEIGHT);
        tableHeader.setCellStyle(cs);
    }

    /**
     * 设置列样式
     *
     * @param columnTitle
     * @param columnType
     */
    private  void setColumnStyle(Cell columnTitle, ColumnType columnType) {
        CellStyle cs = wb.createCellStyle();
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

        Font font = wb.createFont();
        font.setFontHeightInPoints(DEFAULT_ROW_FONT_SIZE);
        font.setFontName(DEFAULT_FONT_NAME);

        cs.setFont(font);
        cs.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cs.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        columnTitle.getRow().setHeightInPoints(DEFAULT_COLUMN_HEIGHT);
        columnTitle.setCellStyle(cs);
    }

    /**
     * 设置数据单元格样式
     *
     * @param cell
     * @param columnType
     */
    private void setCellStyle(Cell cell, ColumnType columnType) {
        CellStyle cs = wb.createCellStyle();
        switch (columnType) {
            case DOUBLE:
                cs.setDataFormat(wb.createDataFormat().getFormat("#,##0.00"));
                break;
            default:
        }

        Font font = wb.createFont();
        font.setFontHeightInPoints(DEFAULT_ROW_FONT_SIZE);
        font.setFontName(DEFAULT_FONT_NAME);

        cs.setFont(font);
        cs.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cs.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        cell.setCellStyle(cs);
    }


    /**
     * 获取表格数据
     *
     * @param row
     * @param field
     * @return
     */
    private Object getElemValue(T row, String field) {
        if (row instanceof Map) {
            return ((Map)row).get(field);
        }
        try {
            return ReflectHelper.getProperty(row, field);
        } catch (Exception e) {
            throw new IllegalArgumentException("请检查类是否包含字段:" + field);
        }
    }

//    public void toDownload(HttpServletResponse response) throws IOException {
//        if (getFileName() == null) {
//            setFileName(getTableName() != null ?
//                    getTableName() : new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//        }
//
//        String fileName = new String((getFileName() + "." + ext).getBytes(), StandardCharsets.ISO_8859_1);
//
//        response.reset();
//        response.setContentType("application/vnd.ms-struct;charset=utf-8");
//        response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
//
//        ServletOutputStream out = response.getOutputStream();
//        wb.write(out);
//        out.flush();
//        out.close();
//    }

    public void write() throws IOException {
        wb.write(out);
        out.flush();
        out.close();
    }

    /**
     * 写到文件
     *
     * @return
     * @throws IOException
     */
    public String toFile() throws IOException {
        File file = new File(fileName + "." + ext);
        out = new FileOutputStream(file);
        wb.write(out);
        out.flush();
        out.close();
        return file.getAbsolutePath();
    }


    public List<Column> getColumns() {
        return columns;
    }

    public ExcelUtil<T> setColumn(String title, String field, int width, ColumnType columnType) {
        setColumn(title, field, width, columnType, false);
        return this;
    }

    public ExcelUtil<T> setColumn(String title, String field, int width, ColumnType columnType, boolean needMerge) {
        Column column = new Column();
        column.setTitle(title);
        column.setField(field);
        column.setWidth(width);
        column.setColumnType(columnType);
        column.setNeedMerge(needMerge);
        this.columns.add(column);
        return this;
    }

    public ExcelUtil<T> setColumns(List<Column> columns) {
        this.columns.addAll(columns);
        return this;
    }

    public String getFileName() {
        return fileName;
    }

    public ExcelUtil<T> setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public String getTableName() {
        return tableName;
    }

    public ExcelUtil<T> setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    public List<T> getData() {
        return data;
    }

    public ExcelUtil<T> setData(List<T> data) {
        this.data = data;
        return this;
    }

    public String getExt() {
        return ext;
    }

    public ExcelUtil<T> setExt(String ext) {
        this.ext = ext;
        return this;
    }

    public int getReserveOffset() {
        return reserveOffset;
    }

    public ExcelUtil<T> setReserveOffset(int reserveOffset) {
        this.reserveOffset = reserveOffset;
        return this;
    }

    public OutputStream getOut() {
        return out;
    }

    public ExcelUtil<T> setOut(OutputStream out) {
        this.out = out;
        return this;
    }

    private class Column {

        private String title;

        private String field;

        private Integer width;

        private ColumnType columnType;

        private boolean needMerge;

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

        public boolean isNeedMerge() {
            return needMerge;
        }

        public void setNeedMerge(boolean needMerge) {
            this.needMerge = needMerge;
        }
    }

    private class ColumnRecord {
        private Object value;

        private Object oldValue;

        private int rowIndex;

        private int cellIndex;

        public Object getOldValue() {
            return oldValue;
        }

        public void setOldValue(Object oldValue) {
            this.oldValue = oldValue;
        }

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }

        public int getRowIndex() {
            return rowIndex;
        }

        public void setRowIndex(int rowIndex) {
            this.rowIndex = rowIndex;
        }

        public int getCellIndex() {
            return cellIndex;
        }

        public void setCellIndex(int cellIndex) {
            this.cellIndex = cellIndex;
        }
    }

    public enum ColumnType {
        /**
         * 字符串
         */
        STRING,
        /**
         * 整数
         */
        INTEGER,
        /**
         * 小数
         */
        DOUBLE,
        /**
         * 时间
         */
        DATE,
        /**
         * 时间戳
         */
        TIMESTAMP
    }
}

