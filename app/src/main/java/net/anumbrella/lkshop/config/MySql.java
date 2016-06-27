package net.anumbrella.lkshop.config;

/**
 * author：Anumbrella
 * Date：16/5/31 下午12:52
 */
public class MySql {

    public static final String DATABASE_NAME = "lkshop.db";
    public static final String ProductTable = "product";
    public static final String UserTable = "user";
    public static final String ShoppingTable = "shopping";
    public static final String CollectTable = "collect";
    public static final int DATABASE_VERSION = 1;

    /**
     * 创建商品表
     */
    public static final String createProductTable = "create table if not exists " + ProductTable +
            "(id integer primary key autoincrement," +
            "productName text," +
            "imgPath text," +
            "price float," +
            "productType integer," +
            "pid integer," +
            "color integer," +
            "storage integer," +
            "carrieroperator integer)";



    /**
     * 创建用户表
     */
    public static final String createUserTable = "create table if not exists " + UserTable +
            "(id integer primary key autoincrement," +
            "userName text," +
            "login boolean default false," +
            "uid integer)";


    /**
     * 创建购物车表
     */
    public static final String createShoppingTable = "create table if not exists " + ShoppingTable +
            "(id integer primary key autoincrement," +
            "pid integer," +
            "uid integer," +
            "sum integer)";

    /**
     * 创建收藏表
     */
    public static final String createCollectTable = "create table if not exists " + CollectTable +
            "(id integer primary key autoincrement," +
            "pid integer," +
            "uid integer)";


}
