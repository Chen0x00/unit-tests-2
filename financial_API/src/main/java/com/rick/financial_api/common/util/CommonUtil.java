package com.rick.financial_api.common.util;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public class CommonUtil {
    /**
     * 处理页数
     */
    public static Integer PageNo(Integer pageNo) {//pageNo=1 通常表示请求分页数据的第一页，而 pageNo=0 表示请求所有数据或者根本不使用分页

        if (pageNo == null || pageNo < 1) {
            pageNo = 1;
            return pageNo;
        }
        return pageNo;
    }

    /**
     * 处理页大小
     */
    public static Integer PageSize(Integer pageSize) {

        if (pageSize == null || pageSize < 1) {
            pageSize = 1;
            return pageSize;
        }
        return pageSize;
    }

    /**
     * 手机号脱敏
     */
    public static String desensitize(String phone) {
        String result = "***********";
        if (phone != null && phone.trim().length() == 11) {
            return phone.substring(0, 3) + "******" + phone.substring(9, 11);
        }
        return result;
    }

    /**
     * 检查手机号是否符合规范
     */
    public static boolean checkPhoneFormat(String phone) {
        boolean flag = false;
        if (Pattern.matches("^1[1-9]\\d{9}$", phone)) {
            flag = true;
        }
        return flag;
    }

    /**
     * 比较两个金额的大小
     */
    public static boolean compareTo(BigDecimal n1, BigDecimal n2) {

        if (n1 == null || n2 == null) {
            throw new RuntimeException("参数是NULL");
        }
        //0表示等于，1表示大于
        return n1.compareTo(n2) >= 0;

    }


}
