package net.anumbrella.lkshop.ui.viewholder;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Xml;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.utils.JUtils;
import com.vanniktech.emoji.EmojiTextView;

import net.anumbrella.lkshop.R;
import net.anumbrella.lkshop.model.CommentDataModel;
import net.anumbrella.lkshop.model.bean.CommentProductDataModel;
import net.anumbrella.lkshop.ui.activity.SubCommentActivity;
import net.anumbrella.lkshop.utils.BaseUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * author：Anumbrella
 * Date：16/6/2 上午9:02
 */
public class CommentProductViewHolder extends BaseViewHolder<CommentProductDataModel> implements View.OnClickListener {

    private TextView likeNumber;
    private ImageView like_icon;

    private TextView commentFollow;
    private ImageView commentFollow_icon;
    private CommentProductDataModel data;
    private TextView comment_user_name;
    private SimpleDraweeView comment_user_icon;
    private EmojiTextView comment_detail_content;
    private TextView comment_time;
    private LinearLayout service_comment_layout;
    private LinearLayout deliver_speed_comment_layout;
    private LinearLayout describe_comment_layout;
    private TextView describe_score;
    private TextView deliver_score;
    private TextView service_score;
    private TextView order_product_detail_describe;
    private boolean isNotLike = false;

    public CommentProductViewHolder(ViewGroup parent) {
        super(parent, R.layout.itemview_comment_content);
        likeNumber = $(R.id.likeNumber);
        like_icon = $(R.id.like_icon);
        commentFollow = $(R.id.commentFollow);
        commentFollow_icon = $(R.id.commentFollow_icon);
        comment_user_icon = $(R.id.comment_user_icon);
        comment_user_name = $(R.id.comment_user_name);
        comment_detail_content = $(R.id.comment_detail_content);
        comment_time = $(R.id.comment_time);
        service_comment_layout = $(R.id.detail_product_service_comment_layout);
        deliver_speed_comment_layout = $(R.id.detail_product_deliver_speed_comment_layout);
        describe_comment_layout = $(R.id.detail_product_describe_comment_layout);
        order_product_detail_describe = $(R.id.order_product_detail_describe);
        describe_score = $(R.id.describe_score);
        deliver_score = $(R.id.deliver_score);
        service_score = $(R.id.service_score);
    }


    @Override
    public void setData(CommentProductDataModel data) {
        super.setData(data);
        this.data = data;
        try {
            String result = URLDecoder.decode(data.getCommentContent(), Xml.Encoding.UTF_8.name());
            comment_detail_content.setText(result);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        comment_user_name.setText(data.getUserName());
        comment_time.setText(data.getCommentTime());
        comment_user_icon.setImageURI(Uri.parse(data.getUserIcon()));
        service_score.setText(String.valueOf(data.getService()));
        deliver_score.setText(String.valueOf(data.getDeliver()));
        describe_score.setText(String.valueOf(data.getDescribe()));
        setCommentDataIcon(data.getDescribe(), data.getService(), data.getDeliver());
        if (data.getType() > 1) {
            order_product_detail_describe.setText(data.getProductName());
        } else {
            String result = "网络类型:" + BaseUtils.transform("carrieroperator", String.valueOf(data.getCarrieroperator()))
                    + " 机身颜色:" + BaseUtils.transform("color", String.valueOf(data.getColor()))
                    + " 机身内存:" + BaseUtils.transform("storage", String.valueOf(data.getStorage()));
            order_product_detail_describe.setText(result);
        }

        if (data.getSubCount() > 0) {
            commentFollow.setVisibility(View.VISIBLE);
            commentFollow.setText(String.valueOf(data.getSubCount()));
        } else {
            commentFollow.setVisibility(View.GONE);
        }

        if (data.getLikeNumber() > 0) {
            likeNumber.setVisibility(View.VISIBLE);
            likeNumber.setText(String.valueOf(data.getLikeNumber()));
        } else {
            likeNumber.setVisibility(View.GONE);
        }
        commentFollow_icon.setOnClickListener(this);
        like_icon.setOnClickListener(this);

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setCommentDataIcon(int describeIndex, int serviceIndex, int deliverSpeedIndex) {
        for (int i = 0; i < describe_comment_layout.getChildCount(); i++) {
            ImageView imageView = (ImageView) describe_comment_layout.getChildAt(i);
            if (i < describeIndex) {
                imageView.setBackground(getContext().getResources().getDrawable(R.mipmap.comment_like));
            }
        }

        for (int i = 0; i < service_comment_layout.getChildCount(); i++) {
            ImageView imageView = (ImageView) service_comment_layout.getChildAt(i);
            if (i < serviceIndex) {
                imageView.setBackground(getContext().getResources().getDrawable(R.mipmap.comment_like));
            }
        }

        for (int i = 0; i < deliver_speed_comment_layout.getChildCount(); i++) {
            ImageView imageView = (ImageView) deliver_speed_comment_layout.getChildAt(i);
            if (i < deliverSpeedIndex) {
                imageView.setBackground(getContext().getResources().getDrawable(R.mipmap.comment_like));
            }
        }

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.commentFollow_icon:
                Intent intent = new Intent();
                intent.putExtra(SubCommentActivity.ARG_ITEM_INFO_SUB_COMMENT_DATA, data);
                intent.setClass(getContext(), SubCommentActivity.class);
                getContext().startActivity(intent);
                break;
            case R.id.like_icon:
                if (isNotLike) {
                    if (Integer.parseInt(likeNumber.getText().toString()) > 0) {
                        likeNumber.setVisibility(View.VISIBLE);
                        likeNumber.setText(String.valueOf(Integer.parseInt(likeNumber.getText().toString()) - 1));
                        like_icon.setBackground(getContext().getResources().getDrawable(R.mipmap.like));
                        if (Integer.parseInt(likeNumber.getText().toString()) == 0) {
                            likeNumber.setVisibility(View.GONE);
                        }
                    }
                    isNotLike = false;
                } else {
                    likeNumber.setText(String.valueOf(Integer.parseInt(likeNumber.getText().toString()) + 1));
                    like_icon.setBackground(getContext().getResources().getDrawable(R.mipmap.like_click));
                    if (Integer.parseInt(likeNumber.getText().toString()) > 0) {
                        likeNumber.setVisibility(View.VISIBLE);
                    }
                    isNotLike = true;
                }
                updateLikes(Integer.parseInt(likeNumber.getText().toString()), isNotLike);
                break;

        }
    }

    private void updateLikes(int likes, final boolean isNotLike) {
        if (JUtils.isNetWorkAvilable()) {
            CommentDataModel.updateLikes(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Response<ResponseBody> response) {
                    try {
                        String result = response.body().string().toString();
                        if (result.equals("0200")) {
                            if (isNotLike) {
                                Toast.makeText(getContext(), "点赞成功", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getContext(), "已取消点赞", Toast.LENGTH_SHORT).show();
                            }
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    Toast.makeText(getContext(), "网络不给力", Toast.LENGTH_SHORT).show();
                }
            }, String.valueOf(data.getCid()), String.valueOf(likes));
        }
    }


}
