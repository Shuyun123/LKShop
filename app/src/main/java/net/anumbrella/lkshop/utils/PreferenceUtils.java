package net.anumbrella.lkshop.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * author：Anumbrella
 * Date：16/5/29 上午9:35
 */
public class PreferenceUtils {
    /**
     * 写入数据(int类型)
     *
     * @param context
     * @param finleName
     * @param k
     * @param v
     */
    public static void write(Context context, String finleName, String k, int v) {
        SharedPreferences preferences = context.getSharedPreferences(finleName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(k, v);
        editor.commit();
    }


    /**
     * 写入数据（boolean数据类型）
     *
     * @param context
     * @param finleName
     * @param k
     * @param v
     */
    public static void write(Context context, String finleName, String k, boolean v) {
        SharedPreferences preferences = context.getSharedPreferences(finleName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(k, v);
        editor.commit();
    }


    /**
     * 写入数据(String数据类型)
     *
     * @param context
     * @param finleName
     * @param k
     * @param v
     */
    public static void write(Context context, String finleName, String k, String v) {
        SharedPreferences preferences = context.getSharedPreferences(finleName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(k, v);
        editor.commit();
    }


    /**
     * 阅读Int数据
     *
     * @param context
     * @param fileName
     * @param k
     * @param defV
     * @return
     */
    public static int readInt(Context context, String fileName, String k, int defV) {
        SharedPreferences preferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return preferences.getInt(k, defV);
    }


    /**
     * 阅读Int数据(默认为0)
     *
     * @param context
     * @param fileName
     * @param k
     * @return
     */
    public static int readInt(Context context, String fileName, String k) {
        SharedPreferences preferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return preferences.getInt(k, 0);
    }


    /**
     * 阅读String数据
     *
     * @param context
     * @param fileName
     * @param k
     * @param defV
     * @return
     */
    public static String readString(Context context, String fileName, String k, String defV) {
        SharedPreferences preferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return preferences.getString(k, defV);
    }

    /**
     * 阅读String数据(默认为null)
     *
     * @param context
     * @param fileName
     * @param k
     * @return
     */
    public static String readString(Context context, String fileName, String k) {
        SharedPreferences preferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return preferences.getString(k, null);
    }


    /**
     * 阅读Boolean数据
     *
     * @param context
     * @param fileName
     * @param k
     * @param defV
     * @return
     */
    public static boolean readBoolean(Context context, String fileName, String k, boolean defV) {
        SharedPreferences preferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return preferences.getBoolean(k, defV);
    }


    /**
     * 阅读Boolean数据(默认为false)
     *
     * @param context
     * @param fileName
     * @param k
     * @return
     */
    public static boolean readBoolean(Context context, String fileName, String k) {
        SharedPreferences preferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        return preferences.getBoolean(k, false);
    }


    /**
     * 删除文件
     *
     * @param context
     * @param fileName
     * @param k
     */
    public static void remove(Context context, String fileName, String k) {
        SharedPreferences preferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(k);
        editor.commit();
    }


    /**
     * 清除sharedPreferences
     *
     * @param context
     * @param fileName
     */
    public static void clear(Context context, String fileName) {
        SharedPreferences preferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }
}
