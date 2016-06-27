package net.anumbrella.lkshop.utils;

import android.content.Context;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.gson.Gson;

import net.anumbrella.lkshop.app.App;
import net.anumbrella.lkshop.config.Config;
import net.anumbrella.lkshop.model.bean.LocalUserDataModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 * author：Anumbrella
 * Date：16/5/25 下午7:28
 */
public class BaseUtils {


    /**
     * 判断链表第某个位置是否为空
     *
     * @param list
     * @param index
     * @return true:为Null或者size为0
     */
    public static boolean isEmpty(List<?> list, int index) {
        return list == null || list.size() == 0 || list.size() <= index;
    }


    public static String transform(String type, String value) {

        if (type.equals(Config.productColorArrays[0][1])) {

            for (int i = 1; i < Config.productColorArrays.length; i++) {
                if (value.equals(Config.productColorArrays[i][0])) {
                    return Config.productColorArrays[i][1];
                }
            }
        } else if (type.equals(Config.productCarrieroperatorArrays[0][1])) {
            for (int i = 1; i < Config.productCarrieroperatorArrays.length; i++) {
                if (value.equals(Config.productCarrieroperatorArrays[i][0])) {
                    return Config.productCarrieroperatorArrays[i][1];
                }
            }

        } else if (type.equals(Config.productStorageArrays[0][1])) {
            for (int i = 1; i < Config.productStorageArrays.length; i++) {
                if (value.equals(Config.productStorageArrays[i][0])) {
                    return Config.productStorageArrays[i][1];
                }
            }

        } else if (type.equals(Config.productTypeArrays[0][1])) {
            for (int i = 1; i < Config.productTypeArrays.length; i++) {
                if (value.equals(Config.productTypeArrays[i][0])) {
                    return Config.productTypeArrays[i][1];
                }
            }
        }
        return null;
    }

    public static String transformState(int isPayState, int isDeliverState, int isAccessState) {

        if (isPayState == 0 && isDeliverState == 0 && isAccessState == 0) {
            return "待付款";
        } else if (isPayState == 1 && isDeliverState == 0 && isAccessState == 0) {
            return "待发货";
        } else if (isPayState == 1 && isDeliverState == 1 && isAccessState == 0) {
            return "待评价";
        } else if (isPayState == 1 && isDeliverState == 1 && isAccessState == 1) {
            return "订单交易成功";
        } else if (isPayState == 404 && isDeliverState == 404 && isAccessState == 404) {
            return "404";
        }

        return "待付款";
    }


    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        display.getMetrics(dm);

        return dm.widthPixels;
    }

    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        display.getMetrics(dm);

        return dm.heightPixels;
    }


    /**
     * 加密
     *
     * @param str
     * @return
     */
    public static String encrypt(String str) {
        String valueBinary = Integer.toBinaryString(Integer.parseInt(str));
        int doubleValue = Integer.parseInt(valueBinary) * 2;
        return String.valueOf(doubleValue);
    }


    /**
     * 去掉空格并把字符串变为小写
     *
     * @param str
     * @return
     */
    public static String tranLowCase(String str) {
        String string = str.replaceAll(" ", "");
        return string.toLowerCase();
    }


    /**
     * 是否存在sdcard
     *
     * @return
     */
    public static boolean hasSdcard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 把对象转换为json
     *
     * @param dataModel
     * @return
     */
    public static JSONObject convertJson(LocalUserDataModel dataModel) {
        Gson gson = new Gson();
        String result = gson.toJson(dataModel);
        JSONObject json = null;
        try {
            json = new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }


    /**
     * 把用户数据保存起来
     *
     * @param context
     * @param data
     */
    public static void saveLocalUser(Context context, LocalUserDataModel data) {
        Writer writer = null;
        try {
            OutputStream out = context.openFileOutput(App.FILENAME, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(convertJson(data).toString());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }


    /**
     * 读取用户数据
     *
     * @param context
     * @return
     */
    public static LocalUserDataModel readLocalUser(Context context) {
        BufferedReader reader = null;
        try {
            InputStream in = context.openFileInput(App.FILENAME);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }
            Gson gson = new Gson();
            LocalUserDataModel result = gson.fromJson(jsonString.toString(), LocalUserDataModel.class);
            return result;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static boolean checkLogin(Context context) {
        int uid = readLocalUser(context).getUid();
        boolean isLogin = readLocalUser(context).isLogin();
        if (uid > 0 && isLogin) {
            return true;

        } else {
            Toast.makeText(context, "请先登录", Toast.LENGTH_SHORT).show();
        }
        return false;
    }


    /**
     * md5加密
     *
     * @param content
     * @return
     */
    public static String Md5(String content) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(content.getBytes());
            byte b[] = md.digest();

            int i;

            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            //32位加密
            return buf.toString();
            // 16位的加密
            //return buf.toString().substring(8, 24);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static boolean isEmpty(String str) {
        if (str == null || str.length() == 0 || str.equalsIgnoreCase("null") || str.isEmpty() || str.equals("")) {
            return true;
        } else {
            return false;
        }
    }
}
