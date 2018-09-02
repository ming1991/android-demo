package com.example.greendao.dao.manager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.greendao.dao.DaoMaster;
import com.example.greendao.dao.DaoSession;

/**
 * Created by Android-mwb on 2018/8/3.
 */
public class GreenDaoManager {
    // 是否加密
    public static final boolean ENCRYPTED = true;

    private static final String DB_NAME = "test.db";
    private static GreenDaoManager mGreenDaoManager;
    private static DaoMaster.DevOpenHelper mDevOpenHelper;
    private static DaoMaster mDaoMaster;
    private static DaoSession mDaoSession;

    private Context mContext;

    private GreenDaoManager(Context context) {
        this.mContext = context;
        // 初始化数据库信息
        mDevOpenHelper = new DaoMaster.DevOpenHelper(context, DB_NAME, null);
        getDaoMaster(context);
        getDaoSession(context);
    }

    public static GreenDaoManager getInstance(Context context) {
        if (null == mGreenDaoManager) {
            synchronized (GreenDaoManager.class) {
                if (null == mGreenDaoManager) {
                    mGreenDaoManager = new GreenDaoManager(context);
                }
            }
        }
        return mGreenDaoManager;
    }

    /**
     * @desc 获取可读数据库
     **/
    public static SQLiteDatabase getReadableDatabase(Context context) {
        if (null == mDevOpenHelper) {
            getInstance(context);
        }
        return mDevOpenHelper.getReadableDatabase();
    }

    /**
     * @desc 获取可写数据库
     **/
    public static SQLiteDatabase getWritableDatabase(Context context) {
        if (null == mDevOpenHelper) {
            getInstance(context);
        }
        return mDevOpenHelper.getWritableDatabase();
    }

    /**
     * @desc 获取DaoMaster
     **/
    public static DaoMaster getDaoMaster(Context context) {
        if (null == mDaoMaster) {
            synchronized (GreenDaoManager.class) {
                if (null == mDaoMaster) {
                    mDaoMaster = new DaoMaster(getWritableDatabase(context));
                }
            }
        }
        return mDaoMaster;
    }

    /**
     * @desc 获取DaoSession
     **/
    public static DaoSession getDaoSession(Context context) {
        if (null == mDaoSession) {
            synchronized (GreenDaoManager.class) {
                mDaoSession = getDaoMaster(context).newSession();
            }
        }
        return mDaoSession;
    }
}