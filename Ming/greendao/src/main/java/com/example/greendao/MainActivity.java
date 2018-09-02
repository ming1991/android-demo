package com.example.greendao;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.greendao.dao.DaoMaster;
import com.example.greendao.dao.DaoSession;
import com.example.greendao.dao.entity.Author;
import com.example.greendao.dao.entity.ShopBean;
import com.example.greendao.dao.entity.StudentMsgBean;
import com.example.greendao.dao.manager.AuthorDaoManager;
import com.example.greendao.dao.manager.ShopDaoManager;
import com.example.greendao.dao.manager.StudentMsgDaoManager;

import org.greenrobot.greendao.query.QueryBuilder;
import org.greenrobot.greendao.rx.RxDao;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.btn_insert)
    Button mBtnInsert;
    @BindView(R.id.btn_delete)
    Button mBtnDelete;
    @BindView(R.id.btn_update)
    Button mBtnUpdate;
    @BindView(R.id.btn_query)
    Button mBtnQuery;
    @BindView(R.id.tv_result)
    TextView mTvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        init();
    }

    private void init() {

    }

    @OnClick({R.id.btn_insert, R.id.btn_delete, R.id.btn_update, R.id.btn_query})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_insert:
                //insertShopBean();
                //insertStudentMsg();
                insertAuthor();


                break;
            case R.id.btn_delete:
                //deleteShopBean();


                break;
            case R.id.btn_update:
                //updateShopBean();


                break;
            case R.id.btn_query:
                //queryShopBean();
                //queryStudentMsg();
                queryAuthor();

                break;
        }
    }





    /**
     * 1对n表格 查询
     */
    private void queryAuthor() {
        AuthorDaoManager.queryAll();
    }

    /**
     * 1对n表格 插入
     */
    private void insertAuthor() {
        AuthorDaoManager.insertAuthor();
    }

    /**
     * 1对1表格  查询
     */
    private void queryStudentMsg() {
        List<StudentMsgBean> studentMsgBeans = StudentMsgDaoManager.queryAll();
        printStudentMsgBeans(studentMsgBeans);
    }

    /**
     * 1对1表格 插入
     */
    private void insertStudentMsg() {
        StudentMsgDaoManager.insertStudentMsg();
    }

    /**
     * 简单查询
     */
    private void queryShopBean() {
        List<ShopBean> shopBeansList = ShopDaoManager.queryAll();
        printShopBeans(shopBeansList);
    }

    /**
     * 简单更新
     */
    private void updateShopBean() {
        ShopBean shopBean = new ShopBean(4L, "小明", "6.5", 11, "www.shuxue", "深圳", ShopBean.TYPE_CART);
        ShopDaoManager.updateShop(shopBean);
    }

    /**
     * 简单删除
     */
    private void deleteShopBean() {
        ShopDaoManager.deleteShop(3L);
    }

    /**
     * 简单插入
     */
    private void insertShopBean() {
        ShopBean shopBean1 = new ShopBean(null, "英语", "5.5", 10, "www.yingyu", "武汉", ShopBean.TYPE_LOVE);
        ShopBean shopBean2 = new ShopBean(null, "数学", "6.5", 11, "www.shuxue", "深圳", ShopBean.TYPE_CART);
        ShopDaoManager.insertShop(shopBean1);
        ShopDaoManager.insertShop(shopBean2);
    }

    private void printShopBeans(List<ShopBean> shopBeansList) {
        StringBuffer stringBuffer = new StringBuffer();
        for (ShopBean shopBean : shopBeansList) {
            stringBuffer.append(shopBean.getId()).append(" ").append(shopBean.getName()).append(" ").append(shopBean.getPrice()).append(" ")
                    .append(shopBean.getSell_num()).append(" ").append(shopBean.getImage_url()).append(" ").append(shopBean.getAddress()).append(" ")
                    .append(shopBean.getType()).append("\n");
        }
        mTvResult.setText(stringBuffer);

    }

    private void printStudentMsgBeans(List<StudentMsgBean> studentMsgBeans) {
        StringBuffer stringBuffer = new StringBuffer();
        for (StudentMsgBean studentMsgBean : studentMsgBeans) {
            stringBuffer.append(studentMsgBean.getId()).append(" ").append(studentMsgBean.getStudentNum()).append(" ")
                    .append(studentMsgBean.getName()).append(" ").append(studentMsgBean.getScoreId()).append(" ")
                    .append(studentMsgBean.getMScoreBean().getEnglishScore()).append(" ").append(studentMsgBean.getMScoreBean().getMathScore()).append("\n");
        }
        mTvResult.setText(stringBuffer);

    }
}
