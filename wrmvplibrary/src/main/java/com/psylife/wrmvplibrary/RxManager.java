package com.psylife.wrmvplibrary;

import java.lang.reflect.Method;
import java.util.Map;

import rx.Subscription;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

/**
 * 用于管理RxBus的事件和Rxjava相关代码的生命周期处理
 * Created by hpw on 16/10/27.
 */

public class RxManager {

//    public RxBus mRxBus = RxBus.$();
//    public RxBus mRxBus = RxBus.getInstance();
//    private Map<String, Observable<?>> mObservables = new HashMap<>();// 管理观察源
    private CompositeSubscription mCompositeSubscription = new CompositeSubscription();// 管理订阅者者
//    private static final LocalVariableTableParameterNameDiscoverer parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();


//    public void on(String eventName, Action1<Object> action1) {
//        Observable<?> mObservable = mRxBus.register(eventName);
//        mObservables.put(eventName, mObservable);
//        mCompositeSubscription.add(mObservable.observeOn(AndroidSchedulers.mainThread())
//                .subscribe(action1, (e) -> e.printStackTrace()));
//    }

    public void add(Subscription m) {
        mCompositeSubscription.add(m);
    }

    public void clear() {
        mCompositeSubscription.unsubscribe();// 取消订阅
//        for (Map.Entry<String, Observable<?>> entry : mObservables.entrySet())
//            mRxBus.unregister(entry.getKey(), entry.getValue());// 移除观察
    }

//    public void post(Object tag, Object content) {
//        mRxBus.post(tag, content);
//    }

    public void packAdd(Map<String, String> paraMap,Class clazz,String method_names, final Action1 onNext, final Action1<Throwable> onError){
        //通过方法名获取对应的方法
        Method method= null;
//        try {
            Method[] methods = clazz.getMethods();
            for (Method m : methods) {
                String methodName = m.getName();
                System.out.println("方法名称:" + methodName);
                if(methodName.equals(method_names)){
                    method = m;
                    break;
                }
            }
//            method = clazz.getDeclaredMethod(method_names);
            //获取方法的所有参数
//            String[] parameterNames = getParameterNames(method);
//            StringBuilder sb = new StringBuilder();
//            sb.append("方法："+method.getName() + " ");
//            if (parameterNames == null || parameterNames.length <1) {
//                sb.append("无参");
//            } else {
//                for (int i = 0; i < parameterNames.length ; i++) {
//                    sb.append(parameterNames[i]);
//                    sb.append(",");
//                }
//                sb.append("]");
//            }
//            LogUtil.i(sb.toString());
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//        }

    }

    /**
     * 获取方法所有参数名
     * @param method
     * @return
     */
//    public static String[] getParameterNames(Method method) {
//        return parameterNameDiscoverer.getParameterNames(method);
//    }




}