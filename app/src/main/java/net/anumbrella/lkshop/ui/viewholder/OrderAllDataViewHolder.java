package net.anumbrella.lkshop.ui.viewholder;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import net.anumbrella.lkshop.R;
import net.anumbrella.lkshop.model.bean.OrderDataModel;
import net.anumbrella.lkshop.ui.activity.AllOrderActivity;
import net.anumbrella.lkshop.ui.activity.CommentActivity;
import net.anumbrella.lkshop.utils.BaseUtils;
import net.anumbrella.lkshop.widget.PromptDialog;

/**
 * author：Anumbrella
 * Date：16/6/9 下午6:37
 */
public class OrderAllDataViewHolder extends BaseViewHolder<OrderDataModel> implements View.OnClickListener {


    private OrderDataModel data;

    private TextView orderState;

    private static AllOrderActivity object = new AllOrderActivity();


    private SimpleDraweeView icon;

    private TextView title;

    private TextView price;

    private TextView sum;

    private TextView comment;

    private TextView delete;

    private TextView totalSum;

    private TextView item_order_price;

    private LinearLayout order_data_type_layout;

    private TextView type;

    private TextView color;

    private TextView carrieroperator;

    private TextView storage;


    public OrderAllDataViewHolder(ViewGroup parent) {
        super(parent, R.layout.itemview_order_all_data);
        icon = $(R.id.order_data_icon);
        title = $(R.id.order_data_title);
        price = $(R.id.order_data_single_price);
        sum = $(R.id.order_data_item_sum);
        comment = $(R.id.order_comment);
        delete = $(R.id.order_delete);
        type = $(R.id.order_phone_type);
        carrieroperator = $(R.id.order_phone_carrieroperator);
        color = $(R.id.order_phone_color);
        storage = $(R.id.order_phone_stroage);
        totalSum = $(R.id.order_item_toatal_sum);
        order_data_type_layout = $(R.id.order_data_type_layout);
        orderState = $(R.id.order_state);
        item_order_price = $(R.id.item_order_price);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void setData(OrderDataModel orderData) {
        super.setData(orderData);
        this.data = orderData;
        icon.setImageURI(Uri.parse(data.getImg()));
        title.setText(data.getProductName());
        price.setText("￥" + data.getPrice());
        color.setText(BaseUtils.transform("color", String.valueOf(data.getColor())));
        type.setText(BaseUtils.transform("phoneType", String.valueOf(data.getType())));
        if (data.getType() > 1) {
            order_data_type_layout.setVisibility(View.GONE);
        } else {
            order_data_type_layout.setVisibility(View.VISIBLE);
        }
        if (data.getIsComment() > 0) {
            comment.setText("已评价");
            comment.setBackground(getContext().getResources().getDrawable(R.drawable.comment_ok_bg));
            comment.setClickable(false);
        }
        item_order_price.setText(String.valueOf(data.getTotal()));
        storage.setText(BaseUtils.transform("storage", String.valueOf(data.getStorage())));
        sum.setText(String.valueOf(data.getSum()));
        totalSum.setText(String.valueOf(data.getSum()));
        carrieroperator.setText(BaseUtils.transform("carrieroperator", String.valueOf(data.getCarrieroperator())));
        orderState.setText(BaseUtils.transformState(data.getIsPay(), data.getIsDeliver(), data.getIsComment()));
        comment.setOnClickListener(this);
        delete.setOnClickListener(this);

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.order_comment:

                if (data.getIsComment() == 0) {
                    String result = BaseUtils.transformState(data.getIsPay(), data.getIsDeliver(), data.getIsComment());
                    if (!result.equals("待评价") && !result.equals("订单交易成功")) {
                        Toast.makeText(getContext(), "交易还没完成,不能评价", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent access_intent = new Intent();
                        access_intent.putExtra(CommentActivity.ARG_ITEM_INFO_COMMENT_ORDER, data);
                        access_intent.setClass(getContext(), CommentActivity.class);
                        getContext().startActivity(access_intent);
                    }
                }

                break;
            case R.id.order_delete:
                String result = BaseUtils.transformState(data.getIsPay(), data.getIsDeliver(), data.getIsComment());
                if (result.equals("待付款") || result.equals("订单交易成功")) {
                    new PromptDialog.Builder(getContext())
                            .setTitle("提示")
                            .setTitleColor(R.color.white)
                            .setViewStyle(PromptDialog.VIEW_STYLE_TITLEBAR_SKYBLUE)
                            .setMessage("确定删除该订单吗?")
                            .setMessageSize(20f)
                            .setButton1("确定", new PromptDialog.OnClickListener() {
                                @Override
                                public void onClick(Dialog dialog, int which) {
                                    object.deleteOrder(data);
                                    dialog.dismiss();

                                }
                            })
                            .setButton2("取消", new PromptDialog.OnClickListener() {
                                @Override
                                public void onClick(Dialog dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .show();

                } else {
                    Toast.makeText(getContext(), "交易还没完成,不能删除", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }
}
