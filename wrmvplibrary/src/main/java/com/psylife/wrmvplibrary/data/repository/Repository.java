package com.psylife.wrmvplibrary.data.repository;

import android.database.Observable;


import com.psylife.wrmvplibrary.base.WRBaseRepository;

import java.util.Map;

/**
 * Created by hpw on 16/11/1.
 */
public abstract class Repository<T> extends WRBaseRepository {
    public T data;

    public Map<String, String> param;

    public abstract Observable<Data<T>> getPageAt(int page);
}
