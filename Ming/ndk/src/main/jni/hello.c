//
// Created by Administrator on 2018/8/7.
//
#include <jni.h>
jstring Java_com_example_ndk_MainActivity_hello(JNIEnv *env,jobject obj){
    char *buf = "hello";
    return (*env)->NewStringUTF(env,buf);
}

