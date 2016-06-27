package net.anumbrella.lkshop.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import net.anumbrella.lkshop.adapter.ShoppingDataAdapter;
import net.anumbrella.lkshop.config.Config;
import net.anumbrella.lkshop.config.MySql;
import net.anumbrella.lkshop.model.bean.ListProductContentModel;
import net.anumbrella.lkshop.model.bean.ProductDataModel;
import net.anumbrella.lkshop.model.bean.RecommendContentModel;
import net.anumbrella.lkshop.utils.BaseUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * author：Anumbrella
 * Date：16/5/31 下午1:10
 * 单例模式
 */
public class DBManager {

    private static DBManager singleton;
    private DBHelper helper;
    private SQLiteDatabase db;

    /**
     * 私有构造器
     *
     * @param context
     */
    private DBManager(Context context) {
        helper = new DBHelper(context);
        db = helper.getWritableDatabase();
    }

    public static DBManager getManager(Context context) {
        if (singleton == null) {
            synchronized (DBManager.class) {
                singleton = new DBManager(context);
            }
        }
        return singleton;
    }


    /**
     * 删除所有商品
     */
    public void removeAllProducts() {
        db.beginTransaction();
        db.execSQL("delete from " + MySql.ProductTable + "");
        db.setTransactionSuccessful();
        db.endTransaction();
    }


    /**
     * 添加所有商品
     */
    public void addAllProducts(List<ProductDataModel> listDatas) {
        db.beginTransaction();
        for (ProductDataModel data : listDatas) {
            addProduct(data);
        }
        db.setTransactionSuccessful();
        db.endTransaction();

    }

    /**
     * 添加商品
     */
    public void addProduct(ProductDataModel data) {
        db.execSQL("insert into " + MySql.ProductTable + " values(null,?,?,?,?,?,?,?,?)",
                new Object[]{BaseUtils.tranLowCase(data.getName()), data.getImg(), data.getPrice(), data.getType(), data.getId(), data.getColor(), data.getStorage(), data.getCarrieroperator()});
    }


    /**
     * 从数据库获取推荐商品列表
     *
     * @return
     */
    public List<RecommendContentModel> getRecommendContentsFromDB() {
        List<RecommendContentModel> listDatas = new ArrayList<RecommendContentModel>();
        Cursor cursor = db.rawQuery("select * from " + MySql.ProductTable + " order by productType asc", null);
        if (cursor != null && cursor.moveToFirst()) {

            for (int i = 0; i < Config.recommdendTips.length; i++) {
                RecommendContentModel recommendTip = new RecommendContentModel();
                recommendTip.setJudgeType(true);
                recommendTip.setTip(Config.recommdendTips[i]);
                listDatas.add(recommendTip);
                for (int j = 0; j < 4; j++) {
                    int type = cursor.getInt(cursor.getColumnIndex("productType"));
                    if (i == type) {
                        RecommendContentModel recommendContent = new RecommendContentModel();
                        recommendContent.setPid(cursor.getInt(cursor.getColumnIndex("pid")));
                        recommendContent.setUid(0);
                        recommendContent.setPrice(cursor.getFloat(cursor.getColumnIndex("price")));
                        recommendContent.setImageUrl(cursor.getString(cursor.getColumnIndex("imgPath")));
                        recommendContent.setTitle(cursor.getString(cursor.getColumnIndex("productName")));
                        recommendContent.setType(cursor.getInt(cursor.getColumnIndex("productType")));
                        recommendContent.setColor(cursor.getInt(cursor.getColumnIndex("color")));
                        recommendContent.setStorage(cursor.getInt(cursor.getColumnIndex("storage")));
                        recommendContent.setCarrieroperator(cursor.getInt(cursor.getColumnIndex("carrieroperator")));
                        listDatas.add(recommendContent);
                        if (!cursor.isLast()) {
                            cursor.moveToNext();
                        } else {
                            break;
                        }
                    } else if (i > type) {
                        if (!cursor.isLast()) {
                            cursor.moveToNext();
                        }
                    }

                }

            }

        }

        cursor.close();
        RecommendContentModel recommendListContent = new RecommendContentModel();
        recommendListContent.setListType(true);
        listDatas.add(0, recommendListContent);
        return listDatas;
    }


    /**
     * 从数据库获得所有的商品
     *
     * @return
     */
    public List<ListProductContentModel> getAllProductsFromDB(int type) {

        List<ListProductContentModel> listDatas = new ArrayList<ListProductContentModel>();
        Cursor cursor = null;
        switch (type) {
            case 0:
                cursor = db.rawQuery("select * from " + MySql.ProductTable + " where productType = 0", null);
                break;
            case 1:
                cursor = db.rawQuery("select * from " + MySql.ProductTable + " where productType = 1", null);
                break;
            case 2:
                cursor = db.rawQuery("select * from " + MySql.ProductTable + " where productType = 2", null);
                break;
            case 3:
                cursor = db.rawQuery("select * from " + MySql.ProductTable + " where productType = 3", null);
                break;
            default:
                cursor = db.rawQuery("select * from " + MySql.ProductTable + "", null);
                break;

        }
        if (cursor != null && cursor.moveToFirst()) {
            do {
                ListProductContentModel listProductContentModel = new ListProductContentModel();
                listProductContentModel.setUid(0);
                listProductContentModel.setPid(cursor.getInt(cursor.getColumnIndex("pid")));
                listProductContentModel.setPrice(Float.parseFloat(String.valueOf(cursor.getFloat(cursor.getColumnIndex("price")))));
                listProductContentModel.setImageUrl(cursor.getString(cursor.getColumnIndex("imgPath")));
                listProductContentModel.setTitle(cursor.getString(cursor.getColumnIndex("productName")));
                listProductContentModel.setType(cursor.getInt(cursor.getColumnIndex("productType")));
                listProductContentModel.setColor(cursor.getInt(cursor.getColumnIndex("color")));
                listProductContentModel.setStorage(cursor.getInt(cursor.getColumnIndex("storage")));
                listProductContentModel.setCarrieroperator(cursor.getInt(cursor.getColumnIndex("carrieroperator")));
                listDatas.add(listProductContentModel);
            } while (cursor.moveToNext());
        }
        return listDatas;
    }


    /**
     * 添加用户
     *
     * @param UserName
     * @param isLogin
     * @param uid
     */
    public void addUser(String UserName, boolean isLogin, int uid) {
        db.beginTransaction();
        db.execSQL("insert into " + MySql.UserTable + " values(null,?,?,?)",
                new Object[]{UserName, isLogin, uid});
        db.setTransactionSuccessful();
        db.endTransaction();
    }


    /**
     * 根据分类显示获取商品的信息
     *
     * @param name
     * @param type
     * @return
     */
    public List<ListProductContentModel> getCategorizeDetailProduct(String name, int type) {
        List<ListProductContentModel> listDatas = new ArrayList<ListProductContentModel>();
        Cursor cursor = db.rawQuery("select * from " + MySql.ProductTable + " where productName = ? and productType = ?",
                new String[]{BaseUtils.tranLowCase(name), String.valueOf(type)});
        if (cursor != null && cursor.moveToFirst()) {
            do {
                ListProductContentModel listProductContentModel = new ListProductContentModel();
                listProductContentModel.setPid(cursor.getInt(cursor.getColumnIndex("pid")));
                listProductContentModel.setUid(0);
                listProductContentModel.setPrice(Float.parseFloat(String.valueOf(cursor.getFloat(cursor.getColumnIndex("price")))));
                listProductContentModel.setImageUrl(cursor.getString(cursor.getColumnIndex("imgPath")));
                listProductContentModel.setTitle(cursor.getString(cursor.getColumnIndex("productName")));
                listProductContentModel.setType(cursor.getInt(cursor.getColumnIndex("productType")));
                listProductContentModel.setColor(cursor.getInt(cursor.getColumnIndex("color")));
                listProductContentModel.setStorage(cursor.getInt(cursor.getColumnIndex("storage")));
                listProductContentModel.setCarrieroperator(cursor.getInt(cursor.getColumnIndex("carrieroperator")));
                listDatas.add(listProductContentModel);
            } while (cursor.moveToNext());
        }
        return listDatas;
    }


    /**
     * 获取用户添加到购物车中的商品
     *
     * @param uid
     * @return
     */
    public List<ListProductContentModel> getShoppingListData(int uid) {
        List<ListProductContentModel> listDatas = new ArrayList<ListProductContentModel>();
        Cursor cursor = db.rawQuery("select * from " + MySql.ShoppingTable + "," + MySql.ProductTable + " where  product.pid = shopping.pid and shopping.uid = ?", new String[]{String.valueOf(uid)});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                ListProductContentModel listProductContentModel = new ListProductContentModel();
                listProductContentModel.setPid(cursor.getInt(cursor.getColumnIndex("pid")));
                listProductContentModel.setSum(cursor.getInt(cursor.getColumnIndex("sum")));
                listProductContentModel.setUid(uid);
                ShoppingDataAdapter.setCheckBoolean(cursor.getInt(cursor.getColumnIndex("pid")), false);
                listProductContentModel.setPrice(Float.parseFloat(String.valueOf(cursor.getFloat(cursor.getColumnIndex("price")))));
                listProductContentModel.setImageUrl(cursor.getString(cursor.getColumnIndex("imgPath")));
                listProductContentModel.setTitle(cursor.getString(cursor.getColumnIndex("productName")));
                listProductContentModel.setType(cursor.getInt(cursor.getColumnIndex("productType")));
                listProductContentModel.setColor(cursor.getInt(cursor.getColumnIndex("color")));
                listProductContentModel.setStorage(cursor.getInt(cursor.getColumnIndex("storage")));
                listProductContentModel.setCarrieroperator(cursor.getInt(cursor.getColumnIndex("carrieroperator")));
                listDatas.add(listProductContentModel);
            } while (cursor.moveToNext());
        }

        return listDatas;
    }


    /**
     * 根据搜索获取商品详情
     *
     * @param productName
     * @return
     */
    public List<ListProductContentModel> getProductDataBySearch(String productName) {
        List<ListProductContentModel> listDatas = new ArrayList<ListProductContentModel>();
        Cursor cursor = db.rawQuery("select * from " + MySql.ProductTable + " where productName = ?", new String[]{BaseUtils.tranLowCase(productName)});
        if (cursor != null && cursor.moveToFirst()) {
            do {
                ListProductContentModel listProductContentModel = new ListProductContentModel();
                listProductContentModel.setPid(cursor.getInt(cursor.getColumnIndex("pid")));
                listProductContentModel.setUid(0);
                listProductContentModel.setPrice(Float.parseFloat(String.valueOf(cursor.getFloat(cursor.getColumnIndex("price")))));
                listProductContentModel.setImageUrl(cursor.getString(cursor.getColumnIndex("imgPath")));
                listProductContentModel.setTitle(cursor.getString(cursor.getColumnIndex("productName")));
                listProductContentModel.setType(cursor.getInt(cursor.getColumnIndex("productType")));
                listProductContentModel.setColor(cursor.getInt(cursor.getColumnIndex("color")));
                listProductContentModel.setStorage(cursor.getInt(cursor.getColumnIndex("storage")));
                listProductContentModel.setCarrieroperator(cursor.getInt(cursor.getColumnIndex("carrieroperator")));
                listDatas.add(listProductContentModel);
            } while (cursor.moveToNext());
        }
        return listDatas;

    }


    /**
     * 获取用户收藏的商品列表
     *
     * @param uid
     * @return
     */
    public List<ListProductContentModel> getCollectListData(int uid) {
        List<ListProductContentModel> listDatas = new ArrayList<ListProductContentModel>();
        Cursor cursor = db.rawQuery("select * from " + MySql.CollectTable + "," + MySql.ProductTable + " where  product.pid = collect.pid and collect.uid = ?", new String[]{String.valueOf(uid)});
        if (cursor != null && cursor.moveToFirst()) {
            do {
                ListProductContentModel listProductContentModel = new ListProductContentModel();
                listProductContentModel.setPid(cursor.getInt(cursor.getColumnIndex("pid")));
                listProductContentModel.setUid(uid);
                ShoppingDataAdapter.setCheckBoolean(cursor.getInt(cursor.getColumnIndex("pid")), false);
                listProductContentModel.setPrice(Float.parseFloat(String.valueOf(cursor.getFloat(cursor.getColumnIndex("price")))));
                listProductContentModel.setImageUrl(cursor.getString(cursor.getColumnIndex("imgPath")));
                listProductContentModel.setTitle(cursor.getString(cursor.getColumnIndex("productName")));
                listProductContentModel.setType(cursor.getInt(cursor.getColumnIndex("productType")));
                listProductContentModel.setColor(cursor.getInt(cursor.getColumnIndex("color")));
                listProductContentModel.setStorage(cursor.getInt(cursor.getColumnIndex("storage")));
                listProductContentModel.setCarrieroperator(cursor.getInt(cursor.getColumnIndex("carrieroperator")));
                listDatas.add(listProductContentModel);
            } while (cursor.moveToNext());
        }

        return listDatas;
    }


    /**
     * 删除购物车中的商品
     *
     * @param uid
     * @param pid
     */
    public void deleteShoppingListData(int uid, int pid) {
        db.beginTransaction();
        db.execSQL("delete from " + MySql.ShoppingTable + " where uid = ? and pid = ?", new Object[]{String.valueOf(uid
        ), String.valueOf(pid)});
        db.setTransactionSuccessful();
        db.endTransaction();
    }


    /**
     * 删除用户
     */
    public void deleteUser(int uid) {
        db.beginTransaction();
        db.execSQL("delete from " + MySql.UserTable + " where uid = ?", new Object[]{String.valueOf(uid
        )});
        db.setTransactionSuccessful();
        db.endTransaction();

    }

    /**
     * 添加收藏
     *
     * @param pid
     * @param uid
     */
    public void addCollect(int pid, int uid) {
        db.beginTransaction();
        if (!checkCollect(pid, uid)) {
            db.execSQL("insert into " + MySql.CollectTable + " values(null,?,?)",
                    new Object[]{pid, uid});
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }


    /**
     * 更新购物车商品信息
     *
     * @param pid
     * @param uid
     * @param sum
     */
    public void updateShoppingListData(int pid, int uid, int sum) {
        db.beginTransaction();
        db.execSQL(
                "update " + MySql.ShoppingTable + " set sum = ? where uid = ? and pid = ?",
                new Object[]{String.valueOf(sum), String.valueOf(uid), String.valueOf(pid)});
        db.setTransactionSuccessful();
        db.endTransaction();
    }


    /**
     * 删除收藏的商品
     *
     * @param pid
     * @param uid
     */
    public void deleteCollect(int pid, int uid) {
        db.beginTransaction();
        db.execSQL("delete from " + MySql.CollectTable + " where  pid = ? and uid =?", new String[]{String.valueOf(pid), String.valueOf(uid
        )});
        db.setTransactionSuccessful();
        db.endTransaction();
    }




    /**
     * 添加商品到购物车
     *
     * @param pid
     * @param uid
     * @param num
     */
    public void addShopping(int pid, int uid, int num) {
        db.beginTransaction();
        if (!checkShopping(pid, uid)) {
            db.execSQL("insert into " + MySql.ShoppingTable + " values(null,?,?,?)",
                    new Object[]{pid, uid, num});
        }
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    /**
     * 检查购物车中是否已经添加
     *
     * @param pid
     * @param uid
     * @return
     */
    public boolean checkShopping(int pid, int uid) {
        Cursor cursor = db.rawQuery("select * from " + MySql.ShoppingTable + " where pid = ? and uid = ?",
                new String[]{String.valueOf(pid), String.valueOf(uid)});
        if (cursor != null && cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 确认用户是否已经收藏
     *
     * @param pid
     * @param uid
     * @return
     */
    public boolean checkCollect(int pid, int uid) {
        Cursor cursor = db.rawQuery("select * from " + MySql.CollectTable + " where pid = ? and uid = ?",
                new String[]{String.valueOf(pid), String.valueOf(uid)});
        if (cursor != null && cursor.moveToFirst()) {
            return true;
        } else {
            return false;
        }
    }


}
