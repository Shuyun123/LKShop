package net.anumbrella.lkshop.config;

import net.anumbrella.lkshop.R;
import net.anumbrella.lkshop.api.Api;
import net.anumbrella.lkshop.ui.fragment.CategorizeFragment;
import net.anumbrella.lkshop.ui.fragment.IndexFragment;
import net.anumbrella.lkshop.ui.fragment.ShoppingFragment;
import net.anumbrella.lkshop.ui.fragment.UserFragment;

/**
 * author：Anumbrella
 * Date：16/5/23 下午11:45
 */
public final class Config {

    /**
     * 引导界面本地图片
     */
    public static final int[] pics = {R.mipmap.guide1, R.mipmap.guide2, R.mipmap.guide3, R.mipmap.guide4};


    /**
     * 引导界面服务器地址上的图片
     */
    public static final String[] pricUrls = {
            Api.baseUrl + "/AppShopService/Guide/guide1.jpg",
            Api.baseUrl + "/AppShopService/Guide/guide2.jpg",
            Api.baseUrl + "/AppShopService/Guide/guide3.jpg",
            Api.baseUrl + "/AppShopService/Guide/guide4.jpg"
    };


    /**
     * 底部导航菜单项
     */
    public static final String[] tabs = {
            "首页", "分类", "购物车", "我的"
    };


    /**
     * 底部导航菜单项对应的图片
     */
    public static final int[] tabImgs = {
            R.drawable.home, R.drawable.categorize, R.drawable.shop, R.drawable.user
    };


    /**
     * 底部导航菜单点击事件对应的fragment
     */
    public static final Class[] tabClass = {
            IndexFragment.class, CategorizeFragment.class, ShoppingFragment.class, UserFragment.class
    };


    /**
     * 商品分类左边的目录菜单
     */
    public static final String[] categorizeTools = {
            "iPhone 6s", "iPhone 6", "iPhone 5", "iPhone 5s", "iPhone 5c"
    };

    /**
     * 商品分类左边的目录菜单图片
     */
    public static final int[] categorizeToolsImg = {
            R.mipmap.iphone_6s, R.mipmap.iphone_6, R.mipmap.iphone_5, R.mipmap.iphone_5s, R.mipmap.iphone_5c
    };


    /**
     * 推荐列表快捷导航栏图片
     */
    public static final int[] recommdendListImg = {
            R.mipmap.lk_icon, R.mipmap.sceeen, R.mipmap.facility, R.mipmap.repair, R.mipmap.conta_us, R.mipmap.found_us
    };

    /**
     * 推荐列表快捷导航栏文本
     */
    public static final String[] recommdendListTexts = {
            "全新手机", "手机配件", "保护壳/膜", "手机维修", "联系我们", "找到我们"
    };

    /**
     * 推荐列tip导航栏文本
     */
    public static final String[] recommdendTips = {
            "全新手机", "二手良品", "手机配件", "保护壳/膜"
    };


    /**
     * 手机维修背景图片
     */
    public static final int[] repairImgs = {
            R.mipmap.repair1, R.mipmap.repair2, R.mipmap.repair3, R.mipmap.repair4
    };


    /**
     * 手机碎屏可维修手机类型
     */
    public static final String[] repairScreenPhoneTypes = {
            "三星", "iPhone", "魅族", "小米", "华为", "其他"
    };

    /**
     * 手机碎屏可维修手机类型图片
     */
    public static final int[] repairScreenPhoneImgs = {
            R.mipmap.samsung, R.mipmap.iphone, R.mipmap.meizu, R.mipmap.mi, R.mipmap.huawei, R.mipmap.other
    };


    /**
     * 手机碎屏可维修屏幕类型图片
     */
    public static final int[] repairScreenTypePhoneImgs = {
            R.mipmap.samsung_screen_type, R.mipmap.iphone_screen_type, R.mipmap.meizu_screen_type, R.mipmap.mi_screen_type, R.mipmap.huawei_screen_type

    };


    /**
     * 手机维修标题
     */
    public static final String[] repairTexts = {
            "碎屏", "无法开机", "进水", "其他"
    };


    /**
     * 手机类型对应数字值
     */
    public static final String[][] productTypeArrays = {
            {"type", "phoneType"},
            {"0", "全新手机"},
            {"1", "二手良品"},
            {"2", "手机配件"},
            {"3", "手机壳/膜"}

    };


    /**
     * 手机运营商对应数字值
     */
    public static final String[][] productCarrieroperatorArrays = {
            {"type", "carrieroperator"},
            {"0", "移动4G"},
            {"1", "联通4G"},
            {"2", "电信4G"},
            {"3", "全网通"}

    };


    /**
     * 手机内存对应数字值
     */
    public static final String[][] productStorageArrays = {
            {"type", "storage"},
            {"0", "16G"},
            {"1", "32G"},
            {"2", "64G"},
            {"3", "128G"}
    };


    /**
     * 手机颜色对应数字值
     */
    public static final String[][] productColorArrays = {
            {"type", "color"},
            {"0", "深空灰"},
            {"1", "银色"},
            {"2", "香槟金"},
            {"3", "玫瑰金"},
            {"4", "黑色"},
            {"5", "白色"}

    };

    public static final String[] numberText = {
            "①", "②", "③", "④", "⑤", "⑥", "⑦", "⑧", "⑨", "⑩", "⑪", "⑫", "⑬", "⑭", "⑮", "⑯"
    };


}
