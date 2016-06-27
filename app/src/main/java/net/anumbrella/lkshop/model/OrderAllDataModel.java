package net.anumbrella.lkshop.model;

import net.anumbrella.lkshop.http.RetrofitHttp;
import net.anumbrella.lkshop.model.bean.CommentOrderDataModel;
import net.anumbrella.lkshop.model.bean.OrderDataModel;
import net.anumbrella.lkshop.utils.BaseUtils;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Callback;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * author：Anumbrella
 * Date：16/6/9 下午8:56
 */
public class OrderAllDataModel {

    public static void getOrderDataFromNet(Subscriber<List<OrderDataModel>> subscriber, String uid) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        RetrofitHttp.getRetrofit(builder.build()).getAllOrderData("getAllOrderData", uid)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }


    public static void deleteOrderData(Callback<ResponseBody> responseBodyCallback, String uid, String pid, String oid) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        RetrofitHttp.getRetrofit(builder.build()).deleteOrderData("deleteOrderData", BaseUtils.encrypt(uid), BaseUtils.encrypt(pid), BaseUtils.encrypt(oid))
                .enqueue(responseBodyCallback);

    }

    public static void publishCommentData(Callback<ResponseBody> responseBodyCallback, CommentOrderDataModel data) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        RetrofitHttp.getRetrofit(builder.build()).publishComment("publishCommentData", data).enqueue(responseBodyCallback);
    }


}
