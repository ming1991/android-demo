package com.xiao.ming.javaweb.net;

import android.content.Context;

import com.xiao.ming.javaweb.utils.ContantsUtil;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

public class OkHttpUtils {
    private static OkHttpUtils okHttpUtils;

    private OkHttpClient mOkHttpClient;

    public static OkHttpClient getClient(Context context) {
        if (okHttpUtils == null) {
            okHttpUtils = new OkHttpUtils(context);
        }
        return okHttpUtils.mOkHttpClient;
    }

    private OkHttpUtils() {
    }

    private OkHttpUtils(Context context) {
        mOkHttpClient = new OkHttpClient.Builder()
                .readTimeout(ContantsUtil.CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .connectTimeout(ContantsUtil.CONNECT_TIME_OUT, TimeUnit.SECONDS)
                .build();
    }
}
