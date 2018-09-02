package com.xiao.ming.javaweb.net;

import android.content.Context;

import com.xiao.ming.javaweb.utils.ContantsUtil;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtils {

    private static RetrofitUtils retrofitUtils;

    private Retrofit retrofit;

    public static Retrofit getRetrofit(Context context) {
        if (retrofitUtils == null) {
            retrofitUtils = new RetrofitUtils(context);
        }
        return retrofitUtils.retrofit;
    }

    private RetrofitUtils() {
    }

    private RetrofitUtils(Context context) {
        retrofit = new Retrofit.Builder()
                .baseUrl(ContantsUtil.BASE_URL)
                .client(OkHttpUtils.getClient(context))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
}
