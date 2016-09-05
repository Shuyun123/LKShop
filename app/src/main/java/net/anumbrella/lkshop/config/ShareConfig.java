package net.anumbrella.lkshop.config;

import android.app.Activity;
import android.content.Context;

import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.QQShareContent;
import com.umeng.socialize.media.QZoneShareContent;
import com.umeng.socialize.media.SinaShareContent;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.sso.QZoneSsoHandler;
import com.umeng.socialize.sso.SinaSsoHandler;
import com.umeng.socialize.sso.UMQQSsoHandler;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.weixin.media.CircleShareContent;
import com.umeng.socialize.weixin.media.WeiXinShareContent;

import net.anumbrella.lkshop.R;

/**
 * author：Anumbrella
 * Date：16/6/25 下午9:24
 */
public class ShareConfig {
    private Context context;
    private UMSocialService mController;
    private String appID = "wx7f1384031fed9710";
    private String appSecret = "29c5d2ffcfb7bd39c29d329ebefbf965";

    public UMSocialService init(Context context, Activity activity) {
        this.context = context;
        UMWXHandler wxHandler = new UMWXHandler(context, appID, appSecret);
        wxHandler.addToSocialSDK();
        // 添加微信朋友圈
        UMWXHandler wxCircleHandler = new UMWXHandler(context, appID, appSecret);
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();
        //qq
        UMQQSsoHandler qqSsoHandler = new UMQQSsoHandler(activity, "1104882277",
                "lSwsVt4trqpxuWXB");
        qqSsoHandler.addToSocialSDK();
        //qqZone
        QZoneSsoHandler qZoneSsoHandler = new QZoneSsoHandler(activity, "100424468",
                "c7394704798a158208a74ab60104f0ba");
        qZoneSsoHandler.addToSocialSDK();
        mController = UMServiceFactory.getUMSocialService("com.umeng.share");
        mController.getConfig().setSsoHandler(new SinaSsoHandler());
        initSina();
        initQQ();
        initQQzone();
        initWeixin();
        initWXCircle();
        return mController;
    }

    public void initSina() {
        SinaShareContent sinaShareContent = new SinaShareContent();
        sinaShareContent.setShareContent("洛克商城app,为你提供良心品质的iphone");
        sinaShareContent.setTitle("洛克良品");
        sinaShareContent.setShareImage(new UMImage(context, R.mipmap.ic_launcher));
        sinaShareContent.setTargetUrl("https://www.anumbrella.net/App/AppShopService/index.php");
        mController.setShareMedia(sinaShareContent);
    }

    public void initQQ() {
        QQShareContent qqShareContent = new QQShareContent();
        qqShareContent.setShareContent("洛克商城app,为你提供良心品质的iphone");
        qqShareContent.setTitle("洛克良品");
        qqShareContent.setShareImage(new UMImage(context, R.mipmap.ic_launcher));
        qqShareContent.setTargetUrl("https://www.anumbrella.net/App/AppShopService/index.php");
        mController.setShareMedia(qqShareContent);
    }

    public void initQQzone() {
        QZoneShareContent qzone = new QZoneShareContent();
        qzone.setShareContent("洛克商城app,为你提供良心品质的iphone");
        qzone.setTargetUrl("https://www.anumbrella.net/App/AppShopService/index.php");
        qzone.setTitle("洛克良品");
        qzone.setShareImage(new UMImage(context, R.mipmap.ic_launcher));
        mController.setShareMedia(qzone);
    }

    public void initWeixin() {
        WeiXinShareContent weiXinShareContent = new WeiXinShareContent();
        weiXinShareContent.setShareContent("洛克商城app,为你提供良心品质的iphone");
        weiXinShareContent.setTargetUrl("https://www.wandoujia.com/apps/net.anumbrella.lkshop");
        weiXinShareContent.setTitle("洛克良品");
        weiXinShareContent.setShareImage(new UMImage(context, R.mipmap.ic_launcher));
        mController.setShareMedia(weiXinShareContent);
    }

    public void initWXCircle() {
        CircleShareContent circleShareContent = new CircleShareContent();
        circleShareContent.setShareContent("洛克商城app,为你提供良心品质的iphone");
        circleShareContent.setTargetUrl("https://www.wandoujia.com/apps/net.anumbrella.lkshop");
        circleShareContent.setTitle("洛克良品");
        circleShareContent.setShareImage(new UMImage(context, R.mipmap.ic_launcher));
        mController.setShareMedia(circleShareContent);
    }
}
