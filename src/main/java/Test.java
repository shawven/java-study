import com.alibaba.fastjson.JSONObject;
import web.supports.utils.ExcelUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author FS
 * @since 2018-12-26
 */
public class Test {

    public static void main(String[] args) throws IOException {
        String[] headers = {"单位名称", "IP", "IP分类", "危险程度"};
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();

        Map<String, String> map = new HashMap<String, String>();
        map.put("单位名称","excel");
        map.put("IP","10.130.138.96");
        map.put("IP分类","主机IP");
        map.put("危险程度","高危");
        list.add(map);
        map = new HashMap<String, String>();
        map.put("单位名称","excel");
        map.put("IP","10.130.138.90");
        map.put("IP分类","主机IP");
        map.put("危险程度","高危2");
        list.add(map);
        map = new HashMap<String, String>();
        map.put("单位名称","test1");
        map.put("IP","10.130.138.90");
        map.put("IP分类","主机IP");
        map.put("危险程度","高危2");
        list.add(map);
        map = new HashMap<String, String>();
        map.put("单位名称","test111");
        map.put("IP","10.130.138.96");
        map.put("IP分类","主机IP");
        map.put("危险程度","高危");
        list.add(map);
        list.add(map);
        list.add(map);
        list.add(map);
        String[] regions = new String[]{"单位名称", "IP", "IP分类", "危险程度"};

        Excel<Map<String, String>> excel = new Excel<>();
        excel.setData(list);
        excel.setHeaders(regions);
        excel.setMergeColumns(new Integer[]{0, 1, 2, 3});


        ExcelUtil<Map<String, String>> excelUtil = new ExcelUtil<>();
        excelUtil.setColumn("单位名称", "单位名称", 50, ExcelUtil.ColumnType.STRING, true);
        excelUtil.setColumn("IP", "IP", 50, ExcelUtil.ColumnType.STRING, true);
        excelUtil.setColumn("IP分类", "IP分类", 50, ExcelUtil.ColumnType.STRING, true);
        excelUtil.setColumn("危险程度", "危险程度", 50, ExcelUtil.ColumnType.STRING, true);
        String path = excelUtil.setFileName("123").setTableName("测试数据").setData(list).createExcel().toFile();
        System.out.println(path);


    }

}
