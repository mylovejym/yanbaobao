package com.zhizhen.ybb.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by songxiang on 2017/5/16.
 */

public class AllUtils {
    //手机正则
    public static boolean isMobileNO(String mobiles) {
        Pattern p = Pattern
                .compile("^((13[0-9])|(15[^4,//D])|(18[0,5-9]))//d{8}$");
        Matcher m = p.matcher(mobiles);
        System.out.println(m.matches() + "---");
        return m.matches();
    }
}
