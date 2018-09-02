package com.xiao.ming;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.xiao.ming.StatusBarlDesign.ToolBarActivity;
import com.xiao.ming.customView.CustomerViewActivity;
import com.xiao.ming.h5Live.H5LiveActivity;
import com.xiao.ming.javaweb.JavaWebTest;
import com.xiao.ming.jniTest.NDKDemo01;
import com.xiao.ming.mvp.login.view.LoginActivity;
import com.xiao.ming.utils.NotificationUtils;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button mBtnGetHtmlContent = findViewById(R.id.btn_get_html_content);
        mBtnGetHtmlContent.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_get_html_content :
                /*Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);*/

                //sendNotification();
                //Intent intent = new Intent(this, JavaWebTest.class);

                Intent intent = new Intent(this, H5LiveActivity.class);
                startActivity(intent);

                break;
        }
    }

    private void sendNotification() {
        NotificationUtils notificationUtils = new NotificationUtils(this);
        notificationUtils.sendNotification("测试标题", "测试内容");

    }
}
