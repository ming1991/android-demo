package com.xiao.ming.javaweb.bean;

public class UserInfoSuccess {

    /**
     * status : 200
     * userInfo : {"name":"小明","age":26}
     * msg : 成功
     */

    private int status;
    private UserInfoBean userInfo;
    private String msg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public UserInfoBean getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfoBean userInfo) {
        this.userInfo = userInfo;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class UserInfoBean {
        /**
         * name : 小明
         * age : 26
         */

        private String name;
        private int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
