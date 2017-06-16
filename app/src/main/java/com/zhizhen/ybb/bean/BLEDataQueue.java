package com.zhizhen.ybb.bean;

import java.util.LinkedList;

/**
 * Created by psylife00 on 2017/6/15.
 */

public class BLEDataQueue {
    private LinkedList<String> measure_degree = new LinkedList();
    private LinkedList<String> measure_time = new LinkedList();
    private LinkedList<String> duration = new LinkedList();
    public void clear()//销毁队列
    {
        measure_degree.clear();
        measure_time.clear();
        duration.clear();
    }
    public boolean QueueEmpty()//判断队列是否为空
    {
        return measure_degree.isEmpty();
    }
    public void enQueueMeasure_degree(String o)//进队
    {
        measure_degree.addLast(o);
    }
    public void enQueueMeasure_time(String o)//进队
    {
        measure_time.addLast(o);
    }
    public void enQueueDuration(String o)//进队
    {
        duration.addLast(o);
    }
    public String deQueueMeasure_degree()//出队
    {
        if(!measure_degree.isEmpty())
        {
            return measure_degree.removeFirst();
        }
        return "";
    }
    public String deQueueMeasure_time()//出队
    {
        if(!measure_time.isEmpty())
        {
            return measure_time.removeFirst();
        }
        return "";
    }
    public String deQueueDuration()//出队
    {
        if(!duration.isEmpty())
        {
            return duration.removeFirst();
        }
        return "";
    }
    public int QueueLength()//获取队列长度
    {
        return measure_degree.size();
    }
    public String QueuePeek()//查看队首元素
    {
        return measure_degree.getFirst();
    }
}
