package net.anumbrella.lkshop.ui.fragment;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.Holder;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.ViewHolder;

import net.anumbrella.lkshop.R;
import net.anumbrella.lkshop.config.Config;
import net.anumbrella.lkshop.model.OrderAllDataModel;
import net.anumbrella.lkshop.model.bean.OrderDataModel;
import net.anumbrella.lkshop.ui.activity.AllOrderActivity;
import net.anumbrella.lkshop.ui.activity.CollectActivity;
import net.anumbrella.lkshop.ui.activity.UserSettingActivity;
import net.anumbrella.lkshop.utils.BaseUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;

/**
 * author：Anumbrella
 * Date：16/5/24 下午8:04
 */
public class UserFragment extends Fragment {

    private Context mContext;

    private int uid;

    @BindView(R.id.user_detail_setting)
    TextView user_detail_setting;

    @BindView(R.id.user_look_all_order)
    LinearLayout user_look_all_order;

    @BindView(R.id.pay_order_num)
    TextView pay_oder_num;

    @BindView(R.id.deliver_order_num)
    TextView deliver_order_num;

    @BindView(R.id.comment_order_num)
    TextView comment_order_num;


    @BindView(R.id.collect_shopping_icon)
    ImageView collect_shopping;

    @BindView(R.id.pay_layout)
    FrameLayout pay_layout;


    @BindView(R.id.deliver_layout)
    FrameLayout deliver_layout;

    @BindView(R.id.comment_layout)
    FrameLayout comment_layout;

    @BindView(R.id.custom_service)
    ImageView custom_service;

    @BindView(R.id.trade_success)
    ImageView trade_success;

    @BindView(R.id.user_detail)
    TextView userName;


    @BindView(R.id.user_detail_icon)
    SimpleDraweeView userDetailIcon;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        uid = BaseUtils.readLocalUser(mContext).getUid();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);
        ButterKnife.bind(this, view);
        getOrderData();
        if (!BaseUtils.readLocalUser(mContext).isLogin()) {
            userName.setVisibility(View.GONE);
        } else {
            userName.setVisibility(View.VISIBLE);
        }

        return view;
    }

    private void getOrderData() {
        if (uid > 0) {
            OrderAllDataModel.getOrderDataFromNet(new Subscriber<List<OrderDataModel>>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onNext(List<OrderDataModel> orderDataModels) {
                    int paySum = 0, deliverSum = 0, accessSum = 0;
                    for (int i = 0; i < orderDataModels.size(); i++) {
                        String result = BaseUtils.transformState(orderDataModels.get(i).getIsPay(), orderDataModels.get(i).getIsDeliver(), orderDataModels.get(i).getIsComment());
                        if (result.equals("待付款")) {
                            paySum++;
                        } else if (result.equals("待发货")) {
                            deliverSum++;
                        } else if (result.equals("待评价")) {
                            accessSum++;
                        }

                    }
                    if (paySum == 0) {
                        pay_oder_num.setVisibility(View.GONE);
                        pay_layout.setClickable(false);
                    } else {
                        pay_layout.setClickable(true);
                        pay_oder_num.setVisibility(View.VISIBLE);
                        pay_oder_num.setText(Config.numberText[paySum - 1]);
                    }

                    if (deliverSum == 0) {
                        deliver_layout.setClickable(false);
                        deliver_order_num.setVisibility(View.GONE);
                    } else {
                        deliver_layout.setClickable(true);
                        deliver_order_num.setVisibility(View.VISIBLE);
                        deliver_order_num.setText(Config.numberText[deliverSum - 1]);
                    }

                    if (accessSum == 0) {
                        comment_layout.setClickable(false);
                        comment_order_num.setVisibility(View.GONE);
                    } else {
                        comment_layout.setClickable(true);
                        comment_order_num.setVisibility(View.VISIBLE);
                        comment_order_num.setText(Config.numberText[accessSum - 1]);
                    }
                }
            }, String.valueOf(uid));
        }

    }


    private void updateUserIcon() {
        String img = BaseUtils.readLocalUser(mContext).getUserImg();
        if (!img.equals("null")) {
            userDetailIcon.setImageURI(Uri.parse(img));
        }
    }


    private void updateUserName() {
        String name = BaseUtils.readLocalUser(mContext).getUserName();
        if (!name.equals("null")) {
            userName.setText(name);
        }
    }

    @Override
    public void onResume() {
        getOrderData();
        updateUserIcon();
        updateUserName();
        updateUserId();
        if (uid <= 0) {
            pay_oder_num.setVisibility(View.GONE);
            pay_layout.setClickable(false);
            deliver_layout.setClickable(false);
            deliver_order_num.setVisibility(View.GONE);
            comment_layout.setClickable(false);
            comment_order_num.setVisibility(View.GONE);
        }
        super.onResume();
    }

    private void updateUserId() {
        uid = BaseUtils.readLocalUser(mContext).getUid();
    }

    @OnClick({R.id.user_detail_setting, R.id.user_look_all_order, R.id.collect_shopping_icon, R.id.pay_layout, R.id.deliver_layout, R.id.comment_layout, R.id.custom_service, R.id.trade_success, R.id.user_detail_icon})
    public void onClick(View view) {
        if (BaseUtils.checkLogin(mContext)) {
            switch (view.getId()) {
                case R.id.user_detail_setting:
                    Intent intent = new Intent();
                    intent.setClass(mContext, UserSettingActivity.class);
                    startActivity(intent);
                    break;
                case R.id.custom_service:
                    break;
                case R.id.user_detail_icon:
                    String img = BaseUtils.readLocalUser(mContext).getUserImg();
                    if (!img.equals("null")) {
                        showDialogUserIcon(img);
                    } else {
                        Toast.makeText(mContext, "请上传头像", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case R.id.trade_success:
                    Intent done_order = new Intent();
                    done_order.putExtra(AllOrderActivity.ARG_ITEM_INFO_ORDER_LOOK_TYPE, "done");
                    done_order.setClass(mContext, AllOrderActivity.class);
                    startActivity(done_order);
                    break;
                case R.id.user_look_all_order:
                    Intent all_order = new Intent();
                    all_order.putExtra(AllOrderActivity.ARG_ITEM_INFO_ORDER_LOOK_TYPE, "all");
                    all_order.setClass(mContext, AllOrderActivity.class);
                    startActivity(all_order);
                    break;

                case R.id.collect_shopping_icon:
                    Intent collect = new Intent();
                    collect.setClass(mContext, CollectActivity.class);
                    startActivity(collect);
                    break;

                case R.id.comment_layout:
                    Intent comment_order = new Intent();
                    comment_order.putExtra(AllOrderActivity.ARG_ITEM_INFO_ORDER_LOOK_TYPE, "comment");
                    comment_order.setClass(mContext, AllOrderActivity.class);
                    startActivity(comment_order);
                    break;
                case R.id.deliver_layout:
                    Intent deliver_order = new Intent();
                    deliver_order.putExtra(AllOrderActivity.ARG_ITEM_INFO_ORDER_LOOK_TYPE, "deliver");
                    deliver_order.setClass(mContext, AllOrderActivity.class);
                    startActivity(deliver_order);
                    break;
                case R.id.pay_layout:
                    Intent pay_order = new Intent();
                    pay_order.putExtra(AllOrderActivity.ARG_ITEM_INFO_ORDER_LOOK_TYPE, "pay");
                    pay_order.setClass(mContext, AllOrderActivity.class);
                    startActivity(pay_order);
                    break;

            }
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void showDialogUserIcon(String img) {
        View viewLayout = LayoutInflater.from(mContext).inflate(R.layout.show_dialog_user_icon, null);
        SimpleDraweeView userImg = (SimpleDraweeView) viewLayout.findViewById(R.id.show_dialog_user_icon);
        userImg.setImageURI(Uri.parse(img));
        Holder holder = new ViewHolder(viewLayout);
        OnClickListener clickListener = new OnClickListener() {

            @Override
            public void onClick(DialogPlus dialog, View view) {
                dialog.dismiss();

            }
        };
        DialogPlus dialogPlus = DialogPlus.newDialog(mContext)
                .setContentHolder(holder)
                .setGravity(Gravity.CENTER)
                .setCancelable(true)
                .setOnClickListener(clickListener)
                .create();
        dialogPlus.show();


    }

}
