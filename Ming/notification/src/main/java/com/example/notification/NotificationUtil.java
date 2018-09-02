package com.example.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Android-mwb on 2018/8/1.
 */
public class NotificationUtil {

    public static final String id = "channel_1";
    public static final String name = "channel_name_1";

    private Context mContext;
    // NotificationManager ： 是状态栏通知的管理类，负责发通知、清楚通知等。
    private NotificationManager manager;
    // 定义Map来保存Notification对象
    private Map<Integer, Notification> map;

    public NotificationUtil(Context context) {
        this.mContext = context;
        // NotificationManager 是一个系统Service，必须通过 getSystemService()方法来获取。
        manager = (NotificationManager) mContext
                .getSystemService(Context.NOTIFICATION_SERVICE);
        map = new HashMap<Integer, Notification>();
    }

    public void showNotification(int notificationId) {
        // 判断对应id的Notification是否已经显示， 以免同一个Notification出现多次
        if (!map.containsKey(notificationId)) {

            NotificationCompat.Builder builder = null;

            if (Build.VERSION.SDK_INT >= 26) {
                NotificationChannel channel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_DEFAULT);

                //设置不震动
                // 设置通知出现时不震动
                channel.enableVibration(false);
                channel.setVibrationPattern(new long[]{0});

                //设置无声音
                channel.setSound(null, null);

                //无灯光
                channel.enableLights(false);

                manager.createNotificationChannel(channel);
                builder = new NotificationCompat.Builder(mContext, id);

            }else{//8.0以下

                builder = new NotificationCompat.Builder(mContext);
                //取消震动
                builder.setVibrate(new long[]{0});
                //无声音
                builder.setSound(null);
                //无灯光

            }

            // 设置通知栏滚动显示文字
            builder.setTicker("开始下载xx文件");

            // 设置显示时间
            builder.setWhen(System.currentTimeMillis());

            // 设置状态栏通知显示的图标
            builder.setSmallIcon(R.mipmap.ic_launcher);

            //设置通知栏下拉的大图片
            builder.setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher));

            // 设置通知的特性: 通知被点击后，自动消失
            builder.setAutoCancel(true);

            //设置通知方式
            //builder.setDefaults(Notification.DEFAULT_LIGHTS);
            //builder.setDefaults(NotificationCompat.FLAG_ONLY_ALERT_ONCE);

            // 设置点击通知栏操作
            Intent in = new Intent(mContext, MainActivity.class);// 点击跳转到指定页面
            PendingIntent pIntent = PendingIntent.getActivity(mContext, 0, in, 0);
            builder.setContentIntent(pIntent);

            // 设置通知的显示视图
            RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.notification_contentview);

            // 设置暂停按钮的点击事件
            Intent pause = new Intent(mContext, MainActivity.class);// 设置跳转到对应界面
            PendingIntent pauseIn = PendingIntent.getActivity(mContext, 0, pause, 0);
            // 这里可以通过Bundle等传递参数
            remoteViews.setOnClickPendingIntent(R.id.pause, pauseIn);

            // 设置取消按钮的点击事件
            Intent stop = new Intent(mContext, MainActivity.class);// 设置跳转到对应界面
            PendingIntent stopIn = PendingIntent.getActivity(mContext, 0, stop, 0);
            // 这里可以通过Bundle等传递参数
            remoteViews.setOnClickPendingIntent(R.id.cancel, stopIn);

            // 设置通知的显示视图
            builder.setContent(remoteViews);

            Notification notification = builder.build();
            // 发出通知
            manager.notify(notificationId, notification);
            map.put(notificationId, notification);// 存入Map中
        }
    }

    /**
     * 取消通知操作
     */
    public void cancel(int notificationId) {
        manager.cancel(notificationId);
        map.remove(notificationId);
    }

    /**
     * 更新进度条
     */
    public void updateProgress(int notificationId, int progress) {
        Notification notify = map.get(notificationId);
        if (null != notify) {
            // 修改进度条
            notify.contentView.setProgressBar(R.id.pBar, 100, progress, false);
            manager.notify(notificationId, notify);
        }
    }
}
