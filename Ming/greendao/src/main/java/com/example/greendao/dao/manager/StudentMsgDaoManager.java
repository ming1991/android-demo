package com.example.greendao.dao.manager;

import android.util.Log;

import com.example.greendao.BaseApplication;
import com.example.greendao.dao.ScoreBeanDao;
import com.example.greendao.dao.entity.ScoreBean;
import com.example.greendao.dao.entity.StudentMsgBean;

import java.util.List;

/**
 * Created by Android-mwb on 2018/8/2.
 *
 * 1对1表格查询
 *
 * https://blog.csdn.net/zone_/article/details/69054997
 */
public class StudentMsgDaoManager {

    public static void insertStudentMsg(){
        StudentMsgBean studentMsgBean = new StudentMsgBean();
        studentMsgBean.setName("zone");
        studentMsgBean.setStudentNum("123456");
        ScoreBean scoreBean = new ScoreBean();
        scoreBean.setEnglishScore("120");
        scoreBean.setMathScore("100");
        ScoreBeanDao scoreBeanDao = BaseApplication.getDaoInstant().getScoreBeanDao();
        scoreBeanDao.insert(scoreBean);
        //TODO 有问题
        ScoreBean scoreBean1 = scoreBeanDao.queryBuilder().unique();
        if (scoreBean1 != null) {
            studentMsgBean.setScoreId(scoreBean1.getId());
            studentMsgBean.setMScoreBean(scoreBean);
            BaseApplication.getDaoInstant().getStudentMsgBeanDao().insert(studentMsgBean);
        }
    }


    /**
     * 查询数据
     */
    public static List<StudentMsgBean> queryAll(){
        return BaseApplication.getDaoInstant().getStudentMsgBeanDao().queryBuilder().list();
    }
}
