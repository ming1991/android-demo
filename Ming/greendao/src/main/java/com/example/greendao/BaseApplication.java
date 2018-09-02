package com.example.greendao;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.example.greendao.dao.DaoMaster;
import com.example.greendao.dao.DaoSession;
import com.example.greendao.dao.manager.GreenDaoManager;


/**
 * Created by Android-mwb on 2018/8/2.
 */
public class BaseApplication extends Application {
    private static DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        initGreenDao();

        //数据库初始化
        GreenDaoManager.getInstance(this);

    }

    /**
     * 初始化数据库
     *
     * DevOpenHelper：创建SQLite数据库的SQLiteOpenHelper的具体实现
     * DaoMaster：GreenDao的顶级对象，作为数据库对象、用于创建表和删除表
     * DaoSession：管理所有的Dao对象，Dao对象中存在着增删改查等API
     */
    private void initGreenDao() {
        //创建数据库shop.db
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "shop.db", null);
        //获取可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        //获取数据库对象
        DaoMaster daoMaster = new DaoMaster(db);
        //获取dao对象管理者
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoInstant() {
        return daoSession;
    }
}
