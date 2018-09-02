package com.xiao.ming.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.xiao.ming.R;


public class NotificationUtils extends ContextWrapper {

    private NotificationManager manager;
    public static final String id = "channel_1";
    public static final String name = "channel_name_1";

    public NotificationUtils(Context context){
        super(context);
    }

    public void createNotificationChannel(){
        NotificationChannel channel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH);
        getManager().createNotificationChannel(channel);
    }
    private NotificationManager getManager(){
        if (manager == null){
            manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        }
        return manager;
    }
    public Notification.Builder getChannelNotification(String title, String content){
        return new Notification.Builder(getApplicationContext(), id)
                .setContentTitle(title)
                .setContentText(content)
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.mipmap.app_icon))// 设置下拉列表中的图标(大图标)
                .setSmallIcon(R.mipmap.app_icon) // 设置状态栏内的小图标
                .setWhen(System.currentTimeMillis()) // 设置该通知发生的时间
                .setAutoCancel(true);
    }
    public NotificationCompat.Builder getNotification_25(String title, String content){
        return new NotificationCompat.Builder(getApplicationContext())
                .setContentTitle(title)
                .setContentText(content)
                .setLargeIcon(BitmapFactory.decodeResource(this.getResources(), R.mipmap.app_icon))// 设置下拉列表中的图标(大图标)
                .setSmallIcon(R.mipmap.app_icon) // 设置状态栏内的小图标
                .setWhen(System.currentTimeMillis()) // 设置该通知发生的时间
                .setAutoCancel(true);
    }
    public void sendNotification(String title, String content){
        if (Build.VERSION.SDK_INT>=26){
            createNotificationChannel();
            Notification notification = getChannelNotification
                    (title, content).build();
            //notification.defaults = Notification.DEFAULT_SOUND; //设置为默认的声音
            //notification.flags = Notification.FLAG_FOREGROUND_SERVICE;
            getManager().notify(1,notification);
        }else{
            Notification notification = getNotification_25(title, content).build();
            //notification.defaults = Notification.DEFAULT_SOUND; //设置为默认的声音
            //notification.flags = Notification.FLAG_FOREGROUND_SERVICE;
            getManager().notify(1,notification);
        }
    }
}