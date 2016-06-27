package net.anumbrella.lkshop.model;

import net.anumbrella.lkshop.http.RetrofitHttp;

import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Callback;

/**
 * author：Anumbrella
 * Date：16/6/20 下午9:01
 */
public class UserSettingModel {

    public static void uploadPricture(Callback<ResponseBody> callback, Map<String, RequestBody> data) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        RetrofitHttp.getRetrofit(builder.build()).uploadPicture("uploadPicture", data)
                .enqueue(callback);
    }


    public static void updateUserSettingName(Callback<ResponseBody> callback, String value,String type,String uid) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        RetrofitHttp.getRetrofit(builder.build()).updateUserSettingName("updateUserSettingName", value,type,uid)
                .enqueue(callback);

    }


}
