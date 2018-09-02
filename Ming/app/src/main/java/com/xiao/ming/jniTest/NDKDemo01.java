package com.xiao.ming.jniTest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.VideoView;

import com.xiao.ming.R;

public class NDKDemo01 extends AppCompatActivity {
    private TextView mTvString;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ndkdemo01);

        mTvString = findViewById(R.id.tv_string);



    }



}
