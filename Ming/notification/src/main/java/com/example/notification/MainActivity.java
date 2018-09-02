package com.example.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.SystemClock;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    public static final String id = "channel_1";
    public static final String name = "channel_name_1";

    private NotificationCompat.Builder builder;
    private NotificationManager manager;

    private Button mBtnProgressbarNotification;
    private ScheduledFuture<?> future;

    private int count;//模似进度
    private NotificationUtil mNotificationUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //发送带进度条的通知
        mBtnProgressbarNotification = findViewById(R.id.btn_progressbarNotification);
        mBtnProgressbarNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                mNotificationUtil = new NotificationUtil(MainActivity.this);
                mNotificationUtil.showNotification(100);// 测试发出通知

                manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
                future = executor.scheduleAtFixedRate(new MyThread(), 0, 100, TimeUnit.MILLISECONDS);
            }
        });

    }


    private Notification customNotification(int progress, String text) {//自定义View通知
        if (builder == null){

            if (Build.VERSION.SDK_INT>=26){
                NotificationChannel channel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH);
                manager.createNotificationChannel(channel);
                builder = new NotificationCompat.Builder(this, id);
            }else{
                builder = new NotificationCompat.Builder(this);

            }
        }

        RemoteViews view = new RemoteViews(getPackageName(), R.layout.notification_upgrade);
        view.setProgressBar(R.id.bar, 100, progress, false);
        view.setTextViewText(R.id.tv_des, text);
        view.setTextViewText(R.id.tv_progress, String.format(Locale.getDefault(), "%d%%", progress));

        builder.setContent(view)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setDefaults(Notification.DEFAULT_LIGHTS)
                .setAutoCancel(true);
        return builder.build();
    }

    //这里为了方便阅读就放在Activity里,实际情况放在Service里更好
    private class MyThread implements Runnable {
        @Override
        public void run() {
            //manager.notify("Download", 0, customNotification(++count, "下载中"));
            mNotificationUtil.updateProgress(100,++count);

            if (count == 100) {
                //manager.notify("Download", 0, customNotification(count, "下载完成了"));

                SystemClock.sleep(3000);

                //mNotificationUtil.cancel(100);

                if (future != null)
                    future.cancel(false);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (future != null)
            future.cancel(true);
    }
}
