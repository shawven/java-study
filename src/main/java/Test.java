import java.io.IOException;
import java.text.DecimalFormat;

/**
 * @author FS
 * @since 2018-12-26
 */
public class Test {

    public static void main(String[] args) throws IOException {
//        String[] headers = {"单位名称", "IP", "IP分类", "危险程度"};
//        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
//
//        Map<String, String> map = new HashMap<String, String>();
//        map.put("单位名称","excel");
//        map.put("IP","10.130.138.96");
//        map.put("IP分类","主机IP");
//        map.put("危险程度","高危");
//        list.add(map);
//        map = new HashMap<String, String>();
//        map.put("单位名称","excel");
//        map.put("IP","10.130.138.90");
//        map.put("IP分类","主机IP");
//        map.put("危险程度","高危2");
//        list.add(map);
//        map = new HashMap<String, String>();
//        map.put("单位名称","test1");
//        map.put("IP","10.130.138.90");
//        map.put("IP分类","主机IP");
//        map.put("危险程度","高危2");
//        list.add(map);
//        map = new HashMap<String, String>();
//        map.put("单位名称","test111");
//        map.put("IP","10.130.138.96");
//        map.put("IP分类","主机IP");
//        map.put("危险程度","高危");
//        list.add(map);
//        list.add(map);
//        list.add(map);
//        list.add(map);
//        String[] regions = new String[]{"单位名称", "IP", "IP分类", "危险程度"};
//
//        Excel<Map<String, String>> excel = new Excel<>();
//        excel.setData(list);
//        excel.setHeaders(regions);
//        excel.setMergeColumns(new Integer[]{0, 1, 2, 3});
//
//
//        ExcelUtil<Map<String, String>> excelUtil = new ExcelUtil<>();
//        excelUtil.setColumn("单位名称", "单位名称", 50, ExcelUtil.ColumnType.STRING, true);
//        excelUtil.setColumn("IP", "IP", 50, ExcelUtil.ColumnType.STRING, true);
//        excelUtil.setColumn("IP分类", "IP分类", 50, ExcelUtil.ColumnType.STRING, true);
//        excelUtil.setColumn("危险程度", "危险程度", 50, ExcelUtil.ColumnType.STRING, true);
//        String path = excelUtil.setFileName("123").setTableName("测试数据").setData(list).createExcel().toFile();
//        System.out.println(path);
        format("0", 112321321112223456.36d);
        format("#", 112321321112223456.36d);
        format("#,###.00", 222222222222.366);
        format("###,###.###", 111222.34567d);
        format("###,###.###", 111222.34567d);
        format("###,###.###", 111222.34567d);
        format("000,000.000", 11222.34567d);
        format("###,###.###$", 1222.34567d);
        format("000,000.000￥", 11222.34567d);
        format("##.###%", 0.345678d);        // 使用百分数形式
        format("00.###%", 0.0345678d);        // 使用百分数形式
        format("###.###\u2030", 0.345678d);    // 使用千分数形式

//        Workbook wb = new HSSFWorkbook();
//        //Workbook wb = new XSSFWorkbook();
//        CreationHelper createHelper = wb.getCreationHelper();
//        Sheet sheet = wb.createSheet("new sheet");
//
//        // Create a row and put some cells in it. Rows are 0 based.
//        Row row = sheet.createRow(0);
//
//        // Create a cell and put a date value in it.  The first cell is not styled
//        // as a date.
//        Cell cell = row.createCell(0);
//        cell.setCellValue(new Date());
//
//        // we style the second cell as a date (and time).  It is important to
//        // create a new cell style from the workbook otherwise you can end up
//        // modifying the built in style and effecting not only this cell but other cells.
//        CellStyle cellStyle = wb.createCellStyle();
//        cellStyle.setDataFormat(
//                wb.createDataFormat().getFormat("m/d/yy h:mm"));
//        cell = row.createCell(1);
//        cell.setCellValue(new Date());
//        cell.setCellStyle(cellStyle);
//
//        //you can also set date as java.util.Calendar
//        cell = row.createCell(2);
//        cell.setCellValue(Calendar.getInstance());
//        cell.setCellStyle(cellStyle);
//
//        // Write the output to a file
//        try (OutputStream fileOut = new FileOutputStream("workbook.xls")) {
//            wb.write(fileOut);
//        }

    }

    public static void format(String pattern,double value){
        DecimalFormat df=new DecimalFormat(pattern);
        String str=df.format(value);
        System.out.println("使用" + pattern+ "\t格式化数字"+value+"：\t" + str);
    }
}
