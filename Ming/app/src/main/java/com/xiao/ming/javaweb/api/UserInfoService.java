package com.xiao.ming.javaweb.api;


import com.xiao.ming.javaweb.bean.UserInfoSuccess;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;

import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface UserInfoService {
    @GET("S001JsonServlet")
    Observable<UserInfoSuccess> getUserInfo();

    @Multipart
    @POST("Upload")
    Call<ResponseBody> upload(@Part MultipartBody.Part file);
}
