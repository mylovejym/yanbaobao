package com.zhizhen.ybb.lanya;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Binder;
import android.os.IBinder;

public class MsgAidlService extends Service {
	//http://blog.csdn.net/Listening_music/article/details/7217571
	//http://blog.csdn.net/leewokan/article/details/51331941

//	@Override
//	public IBinder onBind(Intent arg0) {
//		// TODO Auto-generated method stub
//		return null;
//	}
	private static final String TAG = MsgAidlService.class.getSimpleName();
	  private PendingIntent mPendingIntent;
	  private MyBinder mBinder;
	  private MyServiceConnection mServiceConnection;
	 
	  @Override
	  public IBinder onBind(Intent intent) {
	    return mBinder;
	  }
	 
	  @Override
	  public void onCreate() {
	    super.onCreate();
	    if (mBinder == null) {
	      mBinder = new MyBinder();
	    }
	 
	    mServiceConnection = new MyServiceConnection();
	  }
	 
	  @SuppressLint("NewApi") @Override
	  public int onStartCommand(Intent intent, int flags, int startId) {
	    this.bindService(new Intent(MsgAidlService.this, UartService.class), mServiceConnection, Context.BIND_IMPORTANT);
//	    mPendingIntent = PendingIntent.getService(this, 0, intent, 0);
//	    Notification.Builder builder = new Notification.Builder(this);
//	 
//	    builder.setTicker("守护服务B启动中")
//	        .setContentText("我是来守护服务A的")
//	        .setContentTitle("守护服务B")
//	        .setSmallIcon(R.drawable.ic_launcher)
//	        .setContentIntent(mPendingIntent)
//	        .setWhen(System.currentTimeMillis());
//	    Notification notification = builder.build();
//	    startForeground(startId, notification);
	 
	    return START_STICKY;
	  }
	 
	  public class MyBinder extends Binder {
	 

	  }
	 
	  class MyServiceConnection implements ServiceConnection {
	 
	    @Override
	    public void onServiceConnected(ComponentName componentName, IBinder iBinder) {

//	      Toast.makeText(MsgAidlService.this, name + "连接成功", Toast.LENGTH_SHORT).show();
	    }
	 
	    @Override
	    public void onServiceDisconnected(ComponentName componentName) {
//	      Toast.makeText(MsgAidlService.this, TAG + "断开连接", Toast.LENGTH_SHORT).show();
	 
	      MsgAidlService.this.startService(new Intent(MsgAidlService.this, UartService.class));
	      MsgAidlService.this.bindService(new Intent(MsgAidlService.this, UartService.class), mServiceConnection, Context.BIND_IMPORTANT);
	    }
	  }

}
