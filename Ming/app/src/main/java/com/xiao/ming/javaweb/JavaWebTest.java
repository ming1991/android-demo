package com.xiao.ming.javaweb;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xiao.ming.R;
import com.xiao.ming.bean.User;
import com.xiao.ming.javaweb.api.UserInfoService;
import com.xiao.ming.javaweb.bean.UserInfo;
import com.xiao.ming.javaweb.bean.UserInfoSuccess;
import com.xiao.ming.javaweb.net.RetrofitUtils;

import java.io.File;
import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class JavaWebTest extends AppCompatActivity implements View.OnClickListener {
    private TextView mTvName;
    private TextView mTvAge;
    private Button mBtnGetUserInfo;
    private Context mContext;
    private Disposable mSubscribe;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_web_test);
        mContext = this;

        initView();
        intEvent();

        requestPermissions();

    }

    private void intEvent() {
        mBtnGetUserInfo.setOnClickListener(this);

    }

    private void initView() {
        mTvName = findViewById(R.id.name);
        mTvAge = findViewById(R.id.age);
        mBtnGetUserInfo = findViewById(R.id.get_userInfo);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.get_userInfo:
                //initData();

                upload();
                break;

        }
    }

    private void requestPermissions() {
        if (ContextCompat.checkSelfPermission(JavaWebTest.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // 没有权限，申请权限。
            ActivityCompat.requestPermissions(JavaWebTest.this, PERMISSIONS_STORAGE, 1);
        }else{
            // 有权限了，去放肆吧。

        }
    }

    private void upload() {
        File file = new File(Environment.getExternalStorageDirectory(), "icon.jpg");
        if (file.exists()){
            RequestBody photoRequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part photo = MultipartBody.Part.createFormData("myfile", "icon.jpg", photoRequestBody);

            Call<ResponseBody> call = RetrofitUtils.getRetrofit(mContext).create(UserInfoService.class)
                    .upload(photo);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try {
                        if (response.isSuccessful()){
                            mTvName.setText(response.body().string());
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                        mTvName.setText(e.toString());
                    }

                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    mTvName.setText(t.toString());

                }
            });
        }else{
            mTvName.setText("文件不存在");
        }


    }

    private void initData() {
        mSubscribe = RetrofitUtils.getRetrofit(mContext).create(UserInfoService.class)
                .getUserInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<UserInfoSuccess>() {
                               @Override
                               public void accept(UserInfoSuccess userInfoSuccess) throws Exception {
                                    if (userInfoSuccess!=null){
                                        mTvName.setText(userInfoSuccess.getUserInfo().getName() + "");
                                        //int值--转换成string
                                        mTvAge.setText(userInfoSuccess.getUserInfo().getAge() + "");
                                    }

                               }
                           }, new Consumer<Throwable>() {
                               @Override
                               public void accept(Throwable throwable) throws Exception {
                                   mTvName.setText("404");
                                   mTvAge.setText("404");

                               }
                           }

                );

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSubscribe!=null&&!mSubscribe.isDisposed()){
            mSubscribe.dispose();
        }

    }
}
