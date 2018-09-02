package com.example.greendao.dao.manager;

import com.example.greendao.BaseApplication;
import com.example.greendao.dao.ShopBeanDao;
import com.example.greendao.dao.entity.ShopBean;

import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by Android-mwb on 2018/8/2.
 *
 * https://www.cnblogs.com/wjtaigwh/p/6394288.html
 *
 *
 * 使用GreenDao 实现简单的增删改查，下面是基本方法
 * 增加单个数据
 * getShopDao().insert(shop);
 * getShopDao().insertOrReplace(shop);
 * 增加多个数据
 * getShopDao().insertInTx(shopList);
 * getShopDao().insertOrReplaceInTx(shopList);
 * 查询全部
 * List< Shop> list = getShopDao().loadAll();
 * List< Shop> list = getShopDao().queryBuilder().list();
 * 查询附加单个条件
 * .where()
 * .where(StudentMsgBeanDao.Properties.Name.eq("zone"))//数据筛选，只获取 Name = "zone" 的数据。
 * .whereOr()
 * 查询附加多个条件
 * .where(, , ,)
 * .whereOr(, , ,)
 * 查询附加排序
 * .orderDesc()
 * .orderAsc()
 * orderAsc(StudentMsgBeanDao.Properties.StudentNum)//通过 StudentNum 这个属性进行正序排序
 * 偏移量，相当于 SQL 语句中的 skip
 * .offset(1)
 * 查询限制当页个数--只获取结果集的前 3 个数据
 * .limit()
 * 查询总个数
 * .count()
 * 修改单个数据
 * getShopDao().update(shop);
 * 修改多个数据
 * getShopDao().updateInTx(shopList);
 * 删除单个数据
 * getTABUserDao().delete(user);
 * 删除多个数据
 * getUserDao().deleteInTx(userList);
 * 删除数据ByKey
 * getTABUserDao().deleteByKey();
 * .deleteByKey(list.get(0).getId());//通过 Id 来删除数据
 *返回唯一结果或者 null
 * unique()
 */

public class ShopDaoManager {

    /**
     * 添加数据，如果有重复则覆盖
     *
     * @param shop
     */
    public static void insertShop(ShopBean shop) {
        BaseApplication.getDaoInstant().getShopBeanDao().insertOrReplace(shop);
    }

    /**
     * 删除数据
     *
     * @param id
     */
    public static void deleteShop(Long id) {
        BaseApplication.getDaoInstant().getShopBeanDao().deleteByKey(id);
    }

    /**
     * 更新数据
     */
    public static void updateShop(ShopBean shop) {
        BaseApplication.getDaoInstant().getShopBeanDao().update(shop);
    }

    /**
     * 查询Type为1的所有数据
     *
     * @return
     */
    public static List<ShopBean> queryShop() {
        return BaseApplication.getDaoInstant().getShopBeanDao().queryBuilder().where(ShopBeanDao.Properties.Type.eq(ShopBean.TYPE_CART)).list();

    }

    /**
     * 查询所有数据
     *
     * @return
     */
    public static List<ShopBean> queryAll() {
        return BaseApplication.getDaoInstant().getShopBeanDao().loadAll();
    }
}
