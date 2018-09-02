package com.xiao.ming.mvp.login.dagger;

import com.xiao.ming.mvp.login.view.LoginActivity;

import dagger.Component;

@Component(modules = LoginModule.class)
public interface LoginComponent {
    void inject(LoginActivity activity);
}
