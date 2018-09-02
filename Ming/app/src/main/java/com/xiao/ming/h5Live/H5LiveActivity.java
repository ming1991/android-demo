package com.xiao.ming.h5Live;

import android.graphics.PixelFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.tencent.smtt.sdk.TbsVideo;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebSettings;


import com.xiao.ming.R;

public class H5LiveActivity extends AppCompatActivity {


    private String mUrl = "http://192.168.2.137:8080/view-stream.html";
    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h5_live);

        //getWindow().setFormat(PixelFormat.TRANSLUCENT);

        initWebView();

        //mUrl = "rtmp://live.hkstv.hk.lxdns.com/live/hks";
        //mUrl = "rtmp://wv1.tp33.net/sat/jp011";

        //mUrl = "http://9890.vod.myqcloud.com/9890_4e292f9a3dd011e6b4078980237cc3d3.f30.mp4";
        //TbsVideo.openVideo(this, mUrl);


    }

    private void initWebView() {
        mWebView = findViewById(R.id.webView);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(false);
        webSettings.setSupportZoom(true);
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        //自适应屏幕
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);

        mWebView.loadUrl(mUrl);
    }
}
