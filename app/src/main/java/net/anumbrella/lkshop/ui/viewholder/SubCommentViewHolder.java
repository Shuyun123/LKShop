package net.anumbrella.lkshop.ui.viewholder;

import android.annotation.TargetApi;
import android.net.Uri;
import android.os.Build;
import android.util.Xml;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.vanniktech.emoji.EmojiTextView;

import net.anumbrella.lkshop.R;
import net.anumbrella.lkshop.adapter.SubCommentAdapter;
import net.anumbrella.lkshop.model.CommentDataModel;
import net.anumbrella.lkshop.model.bean.SubCommentDataModel;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * author：Anumbrella
 * Date：16/6/17 下午10:12
 */
public class SubCommentViewHolder extends BaseViewHolder<SubCommentDataModel> implements View.OnClickListener {

    private SubCommentDataModel data;

    private EmojiTextView commentContent;

    private boolean isNotLike = false;

    private TextView likeNumber;

    private ImageView likeIcon;

    private TextView levels;

    private TextView time;

    private TextView userName;

    private SimpleDraweeView userIcon;

    public SubCommentViewHolder(ViewGroup parent) {
        super(parent, R.layout.itemview_sub_comment);
        commentContent = $(R.id.sub_comment_detail_content);
        likeNumber = $(R.id.likeNumber);
        likeIcon = $(R.id.like_icon);
        levels = $(R.id.sub_comment_level);
        time = $(R.id.sub_comment_time);
        userName = $(R.id.sub_comment_user_name);
        userIcon = $(R.id.sub_comment_user_icon);

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void setData(SubCommentDataModel data) {
        super.setData(data);
        this.data = data;
        try {
            String result = URLDecoder.decode(data.getSubCommentContent(), Xml.Encoding.UTF_8.name());
            commentContent.setText(result);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        userName.setText(data.getUserName());
        userIcon.setImageURI(Uri.parse(data.getUserImg()));
        time.setText(data.getSubTime());
        if (data.getLikeNumber() == 0) {
            likeNumber.setText("点赞");
        } else {
            likeNumber.setText(String.valueOf(data.getLikeNumber()));
        }
        levels.setText(String.valueOf(getAdapterPosition() + 1) + "楼");

        if (SubCommentAdapter.isNotLike.get(data.getCid()) == null) {
            HashMap<Integer, Boolean> hashMap = new HashMap<>();
            hashMap.put(getAdapterPosition(), false);
            ArrayList<HashMap<Integer, Boolean>> list = new ArrayList<>();
            list.add(getAdapterPosition(), hashMap);
            SubCommentAdapter.isNotLike.put(data.getCid(), list);
            likeIcon.setBackground(getContext().getResources().getDrawable(R.mipmap.like));
            isNotLike = false;
        } else {
            if (getAdapterPosition() == SubCommentAdapter.isNotLike.get(data.getCid()).size()) {
                HashMap<Integer, Boolean> hashMap = new HashMap<>();
                hashMap.put(getAdapterPosition(), false);
                SubCommentAdapter.isNotLike.get(data.getCid()).add(getAdapterPosition(), hashMap);
            }
            likeIcon.setBackground(getContext().getResources().getDrawable(R.mipmap.like));
            isNotLike = false;
        }

        if (SubCommentAdapter.isNotLike.get(data.getCid()) != null && SubCommentAdapter.isNotLike.get(data.getCid()).get(getAdapterPosition()) != null) {
            if (SubCommentAdapter.isNotLike.get(data.getCid()).get(getAdapterPosition()).get(getAdapterPosition()) != null) {
                if (SubCommentAdapter.isNotLike.get(data.getCid()).get(getAdapterPosition()).get(getAdapterPosition())) {
                    likeIcon.setBackground(getContext().getResources().getDrawable(R.mipmap.like_click));
                    isNotLike = true;
                } else {
                    likeIcon.setBackground(getContext().getResources().getDrawable(R.mipmap.like));
                    isNotLike = false;
                }
            }
        }

        likeIcon.setOnClickListener(this);
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.like_icon:
                if (isNotLike) {
                    if (isNumeric(likeNumber.getText().toString())) {
                        likeIcon.setBackground(getContext().getResources().getDrawable(R.mipmap.like));
                        if ((Integer.parseInt(likeNumber.getText().toString()) - 1) > 0) {
                            likeNumber.setText(String.valueOf(Integer.parseInt(likeNumber.getText().toString()) - 1));
                        } else if ((Integer.parseInt(likeNumber.getText().toString()) - 1) == 0) {
                            likeNumber.setText("点赞");
                        }
                        isNotLike = false;
                        HashMap<Integer, Boolean> hashMap = new HashMap<>();
                        hashMap.put(getAdapterPosition(), isNotLike);
                        SubCommentAdapter.isNotLike.get(data.getCid()).add(getAdapterPosition(), hashMap);
                    }
                } else {
                    if (isNumeric(likeNumber.getText().toString())) {
                        likeNumber.setText(String.valueOf(Integer.parseInt(likeNumber.getText().toString()) + 1));
                    } else {
                        likeNumber.setText("1");
                    }
                    likeIcon.setBackground(getContext().getResources().getDrawable(R.mipmap.like_click));
                    isNotLike = true;
                    HashMap<Integer, Boolean> hashMap = new HashMap<>();
                    hashMap.put(getAdapterPosition(), isNotLike);
                    SubCommentAdapter.isNotLike.get(data.getCid()).add(getAdapterPosition(), hashMap);
                }
                if (isNumeric(likeNumber.getText().toString())) {
                    updateSubLikes(Integer.parseInt(likeNumber.getText().toString()), isNotLike);
                } else {
                    updateSubLikes(0, isNotLike);
                }

                break;
        }

    }

    private void updateSubLikes(int likeNumbers, final boolean isNotLike) {
        CommentDataModel.updateSubLikes(new Callback<ResponseBody>() {
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
                    } else {
                        if (isNotLike) {
                            Toast.makeText(getContext(), "点赞失败", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "取消点赞失败", Toast.LENGTH_SHORT).show();
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Throwable t) {
                if (isNotLike) {
                    Toast.makeText(getContext(), "点赞失败", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "取消点赞失败", Toast.LENGTH_SHORT).show();
                }

            }
        }, String.valueOf(data.getSid()), String.valueOf(likeNumbers));
    }


    /**
     * 判断字符串是否为数字
     *
     * @param str
     * @return
     */
    public static boolean isNumeric(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (!Character.isDigit(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
