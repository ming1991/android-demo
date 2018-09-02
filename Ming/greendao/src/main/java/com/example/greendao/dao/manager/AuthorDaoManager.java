package com.example.greendao.dao.manager;

import android.util.Log;

import com.example.greendao.BaseApplication;
import com.example.greendao.dao.AuthorDao;
import com.example.greendao.dao.PostDao;
import com.example.greendao.dao.entity.Author;
import com.example.greendao.dao.entity.Post;
import com.example.greendao.dao.entity.StudentMsgBean;

import org.greenrobot.greendao.rx.RxDao;

import java.util.List;

/**
 * Created by Android-mwb on 2018/8/2.
 *
 * 1对n表格查询
 *
 * https://blog.csdn.net/zone_/article/details/69054997
 */
public class AuthorDaoManager {

    public static void insertAuthor(){
        AuthorDao authorDao= BaseApplication.getDaoInstant().getAuthorDao();
        PostDao postDao = BaseApplication.getDaoInstant().getPostDao();

        Author author = new Author();//存贮一个作者
        author.setName("zone");
        author.setSex("boy");
        authorDao.insert(author);
        Author authorByQuery = authorDao.queryBuilder().where(AuthorDao.Properties.Name.eq("zone"), AuthorDao.Properties.Sex.eq("boy")).unique();

        Post firstPost = new Post();//写一篇文章
        firstPost.setAuthorId(authorByQuery.getId());
        firstPost.setContent("第一篇文章！");
        Post secondPost = new Post();//写一篇文章
        secondPost.setAuthorId(authorByQuery.getId());
        secondPost.setContent("第二篇文章！");
        postDao.insertInTx(firstPost,secondPost);//存储文章

    }

    /**
     * 查询数据
     */
    public static void queryAll(){
        AuthorDao authorDao= BaseApplication.getDaoInstant().getAuthorDao();
        Author authorResult = authorDao.queryBuilder().where(AuthorDao.Properties.Name.eq("zone"), AuthorDao.Properties.Sex.eq("boy")).unique();//查询存储的结果
        Log.d("ming007", authorResult.getName());
        Log.d("ming007", authorResult.getSex());
        for (int i = 0; i < authorResult.getPosts().size(); i++) {
            Log.d("ming007", authorResult.getPosts().get(i).getContent());
        }
    }
}
