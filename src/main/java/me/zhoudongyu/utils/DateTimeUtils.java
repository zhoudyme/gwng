package me.zhoudongyu.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 时间处理工具类
 */
public class DateTimeUtils {

    /**
     * 获得最近yearNumber个学年，格式为yyyy-yyyy
     *
     * @param enrollmentYear 入学年份
     */
    public static synchronized List<String> getRecentYear(String enrollmentYear) {
        if (StringUtils.isBlank(enrollmentYear)) {
            return null;
        }
        List<String> yearList = new ArrayList<>();
        List<String> resultList = new ArrayList<>();
        Calendar cal = Calendar.getInstance();
        int nowYear = cal.get(Calendar.YEAR);
        int yearNumber = nowYear - Integer.parseInt(enrollmentYear);
        for (int i = yearNumber; i >= 1; i--) {
            int lastYear = nowYear - i;
            yearList.add(String.valueOf(lastYear));
        }
        yearList.add(String.valueOf(nowYear));
        for (int i = 0; i < yearList.size() - 1; i++) {
            resultList.add(yearList.get(i) + "-" + yearList.get(i + 1));
        }
        return resultList;
    }
}
