package base;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * @author XW
 * @date 2018-09-29 23:37
 */

public class TimeUtil {
    private static String firstDay;
    private static String lastDay;
    public static void main(String[] args) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //获取前月的第一天
        Calendar cal_1=Calendar.getInstance();//获取当前日期
        cal_1.add(Calendar.MONTH, -1);
        cal_1.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
        firstDay = format.format(cal_1.getTime());
        System.out.println("-----1------firstDay:"+firstDay);
        //获取前月的最后一天
        Calendar cale = Calendar.getInstance();
        cale.set(Calendar.DAY_OF_MONTH,0);//设置为1号,当前日期既为本月第一天
        lastDay = format.format(cale.getTime());
        System.out.println("-----2------lastDay:"+lastDay);


        //获取当前月第一天：
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
        String first = format.format(c.getTime());
        System.out.println("===============first:"+first);

        //获取当前月最后一天
        Calendar ca = Calendar.getInstance();
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
        String last = format.format(ca.getTime());
        System.out.println("===============last:"+last);

        Calendar startOfWeek = Calendar.getInstance();
        startOfWeek.set(Calendar.DAY_OF_WEEK,  0);

        String startOfWeekStr = format.format(ca.getTime());
        System.out.println("===============startOfWeek:"+startOfWeekStr);

        Calendar endOfWeek = Calendar.getInstance();
        endOfWeek.set(Calendar.DAY_OF_WEEK, ca.getActualMaximum(Calendar.DAY_OF_WEEK));
        String endOfWeekStr = format.format(ca.getTime());
        System.out.println("===============endOfWeek:"+endOfWeekStr);
    }
}
