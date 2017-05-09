package com.psylife.wrmvplibrary.base;


import com.psylife.wrmvplibrary.data.repository.Repository;

/**
 * Created by hpw on 16/11/1.
 */

public class WRBaseRepository {
    @Override
    public Object clone() {
        Repository stu = null;
        try {
            stu = (Repository) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return stu;
    }
}
