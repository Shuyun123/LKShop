package net.anumbrella.lkshop.model.http;

import net.anumbrella.lkshop.model.bean.BannerDataModel;
import net.anumbrella.lkshop.model.bean.CommentOrderDataModel;
import net.anumbrella.lkshop.model.bean.CommentProductDataModel;
import net.anumbrella.lkshop.model.bean.ListProductContentModel;
import net.anumbrella.lkshop.model.bean.OrderDataModel;
import net.anumbrella.lkshop.model.bean.ProductDataModel;
import net.anumbrella.lkshop.model.bean.SubCommentDataModel;
import net.anumbrella.lkshop.model.bean.UserDataModel;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * author：Anumbrella
 * Date：16/5/29 下午3:52
 */

public interface HttpInterface {

    /**
     * 创建用户
     *
     * @param action
     * @param userObject
     * @return
     */
    @POST("/AppShopService/index.php/Home/Index/{action}")
    Call<ResponseBody> registerUser(@Path("action") String action, @Body UserDataModel userObject);

    /**
     * 用户登录
     *
     * @param action
     * @param phoneNumber
     * @return
     */
    @GET("/AppShopService/index.php/Home/Index/{action}")
    Call<ResponseBody> login(@Path("action") String action, @Query("phone") String phoneNumber, @Query("password") String password);


    /**
     * 获取商品
     *
     * @param action
     * @return
     */
    @GET("/AppShopService/index.php/Home/Index/{action}")
    Observable<List<ProductDataModel>> getProducts(@Path("action") String action);


    /**
     * 获取推荐轮播图片
     *
     * @param action
     * @return
     */
    @GET("/AppShopService/index.php/Home/Index/{action}")
    Observable<List<BannerDataModel>> getBanners(@Path("action") String action);

    /**
     * 上传用户订单
     *
     * @param action
     * @param data
     * @return
     */
    @POST("/AppShopService/index.php/Home/Index/{action}")
    Call<ResponseBody> uploadOrder(@Path("action") String action, @Body ListProductContentModel data);


    /**
     * 获取用户订单信息
     *
     * @param action
     * @return
     */
    @GET("/AppShopService/index.php/Home/Index/{action}")
    Observable<List<OrderDataModel>> getAllOrderData(@Path("action") String action, @Query("uid") String uid);


    /**
     * 删除用户订单信息
     *
     * @param action
     * @return
     */
    @GET("/AppShopService/index.php/Home/Index/{action}")
    Call<ResponseBody> deleteOrderData(@Path("action") String action, @Query("uid") String uid, @Query("pid") String pid, @Query("oid") String orderId);


    /**
     * 发布评价信息
     *
     * @param action
     * @param commentData
     * @return
     */
    @POST("/AppShopService/index.php/Home/Index/{action}")
    Call<ResponseBody> publishComment(@Path("action") String action, @Body CommentOrderDataModel commentData);


    /**
     * 获取用户评价信息
     *
     * @param action
     * @return
     */
    @GET("/AppShopService/index.php/Home/Index/{action}")
    Observable<List<CommentProductDataModel>> getCommentData(@Path("action") String action);


    /**
     * 点赞更新
     *
     * @param action
     * @param cid
     * @param likes
     * @return
     */
    @GET("/AppShopService/index.php/Home/Index/{action}")
    Call<ResponseBody> updateLikes(@Path("action") String action, @Query("cid") String cid, @Query("likeNumber") String likes);


    /**
     * 发布评论信息
     *
     * @param action
     * @param data
     * @return
     */
    @POST("/AppShopService/index.php/Home/Index/{action}")
    Call<ResponseBody> uploadSubComment(@Path("action") String action, @Body SubCommentDataModel data);


    /**
     * 获取评论信息
     *
     * @param action
     * @return
     */
    @GET("/AppShopService/index.php/Home/Index/{action}")
    Observable<List<SubCommentDataModel>> getSubCommentData(@Path("action") String action, @Query("cid") String cid);

    /**
     * 点赞更新
     *
     * @param action
     * @param sid
     * @param likes
     * @return
     */
    @GET("/AppShopService/index.php/Home/Index/{action}")
    Call<ResponseBody> updateSubLikes(@Path("action") String action, @Query("sid") String sid, @Query("likeNumber") String likes);


    /**
     * 上传用户图片
     *
     * @param action
     * @param params
     * @return
     */
    @Multipart
    @POST("/AppShopService/index.php/Home/Index/{action}")
    Call<ResponseBody> uploadPicture(@Path("action") String action, @PartMap Map<String, RequestBody> params);


    /**
     * 用户名称和签名更改
     *
     * @param action
     * @param value
     * @return
     */
    @GET("/AppShopService/index.php/Home/Index/{action}")
    Call<ResponseBody> updateUserSettingName(@Path("action") String action, @Query("value") String value, @Query("type") String type, @Query("uid") String uid);


    /**
     * 验证用户输入的手机号是否注册过
     *
     * @param action
     * @param phone
     * @return
     */
    @GET("/AppShopService/index.php/Home/Index/{action}")
    Call<ResponseBody> findPassCheckPhoneExsit(@Path("action") String action, @Query("phone") String phone);


    /**
     * 更改密码
     *
     * @param action
     * @param phone
     * @param password
     * @return
     */
    @GET("/AppShopService/index.php/Home/Index/{action}")
    Call<ResponseBody> findPassUpdatePass(@Path("action") String action, @Query("phone") String phone, @Query("password") String password);


    /**
     * 获取app版本信息
     *
     * @param action
     * @return
     */
    @GET("/AppShopService/index.php/Home/Index/{action}")
    Call<ResponseBody> getUpdateAppInfo(@Path("action") String action);


    /**
     * 更新app
     *
     * @param action
     * @return
     */
    @GET("/AppShopService/Uploads/App/{action}")
    Call<ResponseBody> updateApp(@Path("action") String action);


}
