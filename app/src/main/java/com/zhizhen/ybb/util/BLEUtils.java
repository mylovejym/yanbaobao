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
    static SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//系统时间format

    /**
     * 系统时间转换成UTC时间的16进制命令字符串
     * @return
     */
    public static byte[] getTimeString(String time)  {
        Date date = null;
        Date date2 = null;
//        timeFormat.setTimeZone(TimeZone.getTimeZone("Etc/GMT+0"));
        try {
            date = timeFormat.parse(time);
            date2 = timeFormat.parse("2000-01-01 00:00:00");
            long t = date.getTime()-date2.getTime();
            String aa = Utils.intToHexString((int) (t/1000),4);
            LogUtil.e("aaaa:"+aa);
//        LogUtil.e("aaaast:"+((int)t));
            String str = Utils.longToHexString(t/1000,8);
            String command = "AA0602"+Utils.intToHexString((int) (t/1000),4)+"55";
            LogUtil.e("command:"+command);

//				try {
            //send data to service
            byte[]  value = hexStringToBytes(command);

            return value;

        } catch (ParseException e) {
            e.printStackTrace();
        }
//        long t = date.getTime();

//        //美国洛杉矶时区
//        TimeZone tz=TimeZone.getTimeZone("America/Los_Angeles");
//        //时区转换
//
//
//        //1、取得本地时间：
//        java.util.Calendar cal = java.util.Calendar.getInstance();
//        cal.setTime(date);
//        cal.setTimeZone(tz);
//
//        //2、取得时间偏移量：
//        int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);
//
//        //3、取得夏令时差：
//        int dstOffset = cal.get(java.util.Calendar.DST_OFFSET);
//        //4、从本地时间里扣除这些差量，即可以取得UTC时间：
//        cal.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));
//        long t = cal.getTimeInMillis();


//        String aa = Utils.intToHexString((int) (t/1000),4);
//        LogUtil.e("aaaa:"+aa);
////        LogUtil.e("aaaast:"+((int)t));
//        String str = Utils.longToHexString(t/1000,8);
//        String command = "AA0602"+Utils.intToHexString((int) (t/1000),4)+"55";
//        LogUtil.e("command:"+command);
//
////				try {
//        //send data to service
//        byte[]  value = hexStringToBytes(command);

        return new byte[0];
    }

    public static byte[] getACTime(int startH, int startm, int endH, int endm){
//        //美国洛杉矶时区
//        TimeZone tz=TimeZone.getTimeZone("America/Los_Angeles");
//        //时区转换
//
//
//        //1、取得本地时间：
//        Calendar cal = Calendar.getInstance();
//        cal.setTimeZone(tz);
//
//        //2、取得时间偏移量：
//        int zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);
//
//        //3、取得夏令时差：
//        int dstOffset = cal.get(java.util.Calendar.DST_OFFSET);
//        //4、从本地时间里扣除这些差量，即可以取得UTC时间：
//        cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
//        cal.set(Calendar.HOUR_OF_DAY, 0);
//        cal.set(Calendar.MINUTE,0);
//        cal.set(Calendar.SECOND,0);
//        long zt = cal.getTimeInMillis();
//        cal.set(Calendar.HOUR_OF_DAY, startH);
//        cal.set(Calendar.MINUTE,startm);
//        long startTime = cal.getTimeInMillis() - zt;
//        cal.set(Calendar.HOUR_OF_DAY, endH);
//        cal.set(Calendar.MINUTE,endm);
//        long endTime = cal.getTimeInMillis() - zt;

        int startTime = startH*3600+ startm *60;
        int endTime = endH*3600+ endm*60;

        LogUtil.e("aaaast:"+((int)startTime/1000));
//        String startStr = Utils.intToHexString((int)(startTime/1000),4);
        String startStr = Utils.intToHexString(startTime,4);
        LogUtil.e("aaaa:"+startStr);

//        String endStr = Utils.intToHexString((int) (endTime/1000),4);
        String endStr = Utils.intToHexString(endTime/1000,4);
        LogUtil.e("bbbb:"+endStr);

        String command = "AA0A08" + startStr + endStr + "55";
        LogUtil.e("command:"+command);
        byte[]  value = hexStringToBytes(command);

        return value;

    }

    public static byte[] getHz(String hz){
        String command =null;
        if(hz.equals("每1秒一次")){
            command = "AA03010155";
        }else if(hz.equals("每2秒一次")){
            command = "AA03010255";
        }else{
            command = "AA03010455";
        }
        byte[]  value = hexStringToBytes(command);

        return value;
    }

    public static byte[] getNum(String Num){
        String command =null;
        if(Num.equals("震动3次")){
            command = "AA03050355";
        }else if(Num.equals("震动2次")){
            command = "AA03050255";
        }else{
            command = "AA03050155";
        }
        byte[]  value = hexStringToBytes(command);

        return value;
    }

    public static byte[] getDelay(String delay){
        String command =null;
        if(delay.equals("60s")){
            command = "AA03073c55";
        }else if(delay.equals("30s")){
            command = "AA03071E55";
        }else{
            command = "AA03070555";
        }
        byte[]  value = hexStringToBytes(command);

        return value;
    }

    public static byte[] getPos(String Pos){
        String command =null;
        if(Pos.equals("10°")){
            command = "AA0400810A55";
        }else if(Pos.equals("20°")){
            command = "AA0400811455";
        }else{
            command = "AA0400810F55";
        }
        byte[]  value = hexStringToBytes(command);

        return value;
    }

    public static String bytesToHexString(byte[] src){
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }


}
