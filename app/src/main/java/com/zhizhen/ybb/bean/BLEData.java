package com.zhizhen.ybb.bean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by psylife00 on 2017/6/2.
 */

public class BLEData {
    ArrayList<String> measure_degree;
    ArrayList<String> measure_time;
    ArrayList<String> duration;
    private static  Lock lock = new ReentrantLock();// 锁对象

    public BLEData(){
        measure_degree = new ArrayList<>();
        measure_time = new ArrayList<>();
        duration = new ArrayList<>();
    }

    public void copy(BLEDataQueue data){
        int max =0;
        if(data.QueueLength()>800){
            max = 800;
        }else{
            max = data.QueueLength();
        }
        for(int i=0; i<max; i++){
            System.out.println(i);

            measure_degree.add(data.deQueueMeasure_degree());
            measure_time.add(data.deQueueMeasure_time());
            duration.add(data.deQueueDuration());

        }
    }

    public void copy(BLEData data){
        lock.lock();// 得到锁
        try {

            Iterator<String> it = data.getMeasure_degree().iterator();
            Iterator<String> it_time = data.getMeasure_time().iterator();
            Iterator<String> it_dur = data.getDuration().iterator();
            int max =0;
            if(data.getMeasure_degree().size()>100){
                max = 100;
            }else{
                max = data.getMeasure_degree().size();
            }
            for(int i=0; i<max; i++){
                System.out.println(i);
                String name = it.next();
                measure_degree.add(name);
                it.remove();
                String time = it_time.next();
                measure_time.add(time);
                it_time.remove();
                String dur = it_dur.next();
                duration.add(dur);
                it_dur.remove();

            }

//            if(data.getMeasure_degree().size()>10){
//                List l = data.getMeasure_degree().subList(0,10);
//                measure_degree.addAll(l);
//                l.clear();
//            }else{
//                measure_degree.addAll(data.getMeasure_degree());
//                data.getMeasure_degree().clear();
//            }
//
//            if(data.getMeasure_time().size()>10){
//                List l = data.getMeasure_time().subList(0,10);
//                measure_time.addAll(l);
//                l.clear();
//            }else{
//                measure_time.addAll(data.getMeasure_time());
//                data.getMeasure_time().clear();
//            }
//
//            if(data.getDuration().size()>10){
//                List l = data.getDuration().subList(0,10);
//                duration.addAll( l);
//                l.clear();
//            }else{
//                duration.addAll(data.getDuration());
//                data.getDuration().clear();
//            }

        } finally {
            lock.unlock();// 释放锁
        }
    }

    public void clear(){
        lock.lock();// 得到锁
        try {
            measure_degree.clear();
            measure_time.clear();;
            duration.clear();
        } finally {
            lock.unlock();// 释放锁
        }
    }

    public ArrayList<String> getMeasure_degree() {
        return measure_degree;
    }

    public void setMeasure_degree(ArrayList<String> measure_degree) {
        this.measure_degree = measure_degree;
    }
    public void addMeasure_degree(String v){
        lock.lock();// 得到锁
        try {
            measure_degree.add(v);
        } finally {
            lock.unlock();// 释放锁
        }
    }

    public ArrayList<String> getMeasure_time() {
        return measure_time;
    }

    public void setMeasure_time(ArrayList<String> measure_time) {
        this.measure_time = measure_time;
    }
    public void addMeasure_time(String v){
        lock.lock();// 得到锁
        try {
            measure_time.add(v);
        } finally {
            lock.unlock();// 释放锁
        }
    }

    public ArrayList<String> getDuration() {
        return duration;
    }

    public void setDuration(ArrayList<String> duration) {
        this.duration = duration;
    }
    public void addDuration(String v){
        lock.lock();// 得到锁
        try {
            duration.add(v);
        } finally {
            lock.unlock();// 释放锁
        }
    }
}
