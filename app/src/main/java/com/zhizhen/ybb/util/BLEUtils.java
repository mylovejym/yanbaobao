package com.zhizhen.ybb.util;

import com.psylife.wrmvplibrary.utils.LogUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.zhizhen.ybb.util.Utils.hexStringToBytes;

/**
 * Created by songxiang on 2017/5/30.
 */

public class BLEUtils {
    static SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd");//系统时间format

    /**
     * 系统时间转换成UTC时间的16进制命令字符串
     * @return
     */
    public static byte[] getTimeString(String time)  {
        Date date = null;
        try {
            date = timeFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //1、取得本地时间：
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTime(date);

        //2、取得时间偏移量：
        int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);

        //3、取得夏令时差：
        int dstOffset = cal.get(java.util.Calendar.DST_OFFSET);
        //4、从本地时间里扣除这些差量，即可以取得UTC时间：
        cal.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        long t = cal.getTimeInMillis();
        String str = Utils.longToHexString(t/1000,8);
        String command = "AA0602"+str.substring(str.length()-8)+"55";
        LogUtil.e("command:"+command);

//				try {
        //send data to service
        byte[]  value = hexStringToBytes(command);

        return value;
    }


}
