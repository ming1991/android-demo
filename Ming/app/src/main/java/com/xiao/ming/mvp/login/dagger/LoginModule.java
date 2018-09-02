package com.xiao.ming.mvp.login.dagger;

import com.xiao.ming.mvp.login.view.ILoginView;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginModule {

    private final ILoginView mView;

    public LoginModule(ILoginView view) {
        mView = view;
    }

    @Provides
    ILoginView provideLoginView() {
        return mView;
    }
}

