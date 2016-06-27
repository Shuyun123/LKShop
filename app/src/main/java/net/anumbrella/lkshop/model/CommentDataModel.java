package net.anumbrella.lkshop.model;

import net.anumbrella.lkshop.http.RetrofitHttp;
import net.anumbrella.lkshop.model.bean.CommentProductDataModel;
import net.anumbrella.lkshop.model.bean.SubCommentDataModel;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Callback;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * author：Anumbrella
 * Date：16/6/15 下午1:07
 */
public class CommentDataModel {
    public static void getCommentData(Subscriber<List<CommentProductDataModel>> subscriber) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        RetrofitHttp.getRetrofit(builder.build()).getCommentData("getCommentData")
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);

    }


    public static void updateLikes(Callback<ResponseBody> callback, String cid, String likes) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        RetrofitHttp.getRetrofit(builder.build()).updateLikes("updateLikes", cid, likes)
                .enqueue(callback);

    }


    public static void uploadSubCommentData(Callback<ResponseBody> callback, SubCommentDataModel dataModel) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        RetrofitHttp.getRetrofit(builder.build()).uploadSubComment("uploadSubComment", dataModel)
                .enqueue(callback);

    }

    public static void getSubCommentData(Subscriber<List<SubCommentDataModel>> subscriber, String cid) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        RetrofitHttp.getRetrofit(builder.build()).getSubCommentData("getSubCommentData", cid)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }


    public static void updateSubLikes(Callback<ResponseBody> callback, String sid, String likes) {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        RetrofitHttp.getRetrofit(builder.build()).updateSubLikes("updateSubLikes", sid, likes)
                .enqueue(callback);

    }


}
