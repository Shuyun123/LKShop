package net.anumbrella.lkshop.ui.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Xml;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.message.PushAgent;

import net.anumbrella.lkshop.R;
import net.anumbrella.lkshop.model.OrderAllDataModel;
import net.anumbrella.lkshop.model.bean.CommentOrderDataModel;
import net.anumbrella.lkshop.model.bean.OrderDataModel;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * author：Anumbrella
 * Date：16/6/13 下午7:25
 */
public class CommentActivity extends BaseThemeSettingActivity {

    public final static String ARG_ITEM_INFO_COMMENT_ORDER = "item_info_comment_order";

    private HashMap<String, Integer> commentHasMap = new HashMap<>();

    private static AllOrderActivity object = new AllOrderActivity();

    private OrderDataModel orderData;

    @BindView(R.id.comment_all_toolbar)
    Toolbar toolbar;

    @BindView(R.id.comment_content)
    EditText comment_content;

    @BindView(R.id.deliver_speed_comment_layout)
    LinearLayout deliver_speed_comment_layout;

    @BindView(R.id.service_comment_layout)
    LinearLayout service_comment_layout;


    @BindView(R.id.describe_product_comment_layout)
    LinearLayout describe_product_comment_layout;


    @BindView(R.id.publish_comment)
    TextView publish_comment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        ButterKnife.bind(this);
        PushAgent.getInstance(this).onAppStart();
        if (getIntent().getParcelableExtra(ARG_ITEM_INFO_COMMENT_ORDER) != null) {
            this.orderData = getIntent().getParcelableExtra(ARG_ITEM_INFO_COMMENT_ORDER);
        }
        toolbar.setTitle("发布评价");
        setToolbar(toolbar);
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @OnClick({R.id.publish_comment})
    public void click(View view) {

        if (view.getTag() != null) {
            String tag = (String) view.getTag();
            int index = Integer.parseInt(tag);
            LinearLayout layout = (LinearLayout) view.getParent();
            switch (layout.getId()) {
                case R.id.describe_product_comment_layout:
                    for (int i = 0; i < describe_product_comment_layout.getChildCount(); i++) {
                        ImageView imageView = (ImageView) describe_product_comment_layout.getChildAt(i);
                        if (i < index) {
                            commentHasMap.put("describe", index);
                            imageView.setBackground(getResources().getDrawable(R.mipmap.comment_like));
                        } else {
                            imageView.setBackground(getResources().getDrawable(R.mipmap.comment_no_like));
                        }
                    }
                    break;
                case R.id.deliver_speed_comment_layout:
                    for (int i = 0; i < deliver_speed_comment_layout.getChildCount(); i++) {
                        ImageView imageView = (ImageView) deliver_speed_comment_layout.getChildAt(i);
                        if (i < index) {
                            commentHasMap.put("deliver", index);
                            imageView.setBackground(getResources().getDrawable(R.mipmap.comment_like));
                        } else {
                            imageView.setBackground(getResources().getDrawable(R.mipmap.comment_no_like));
                        }
                    }
                    break;
                case R.id.service_comment_layout:
                    for (int i = 0; i < service_comment_layout.getChildCount(); i++) {
                        ImageView imageView = (ImageView) service_comment_layout.getChildAt(i);
                        if (i < index) {
                            commentHasMap.put("service", index);
                            imageView.setBackground(getResources().getDrawable(R.mipmap.comment_like));
                        } else {
                            imageView.setBackground(getResources().getDrawable(R.mipmap.comment_no_like));
                        }
                    }

                    break;
            }

        }

        switch (view.getId()) {
            case R.id.publish_comment:
                String commentContent = comment_content.getText().toString().trim();
                if (commentContent.equals("")) {
                    Toast.makeText(this, "评论内容不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    if (commentHasMap.get("deliver") == null || commentHasMap.get("service") == null || commentHasMap.get("describe") == null) {
                        Toast.makeText(this, "请打评分", Toast.LENGTH_SHORT).show();
                    } else {
                        publishCommentAction(commentContent);
                    }
                }

                break;
        }


    }

    private void publishCommentAction(String commentContent) {

        CommentOrderDataModel data = new CommentOrderDataModel();
        data.setBid(orderData.getBid());
        try {
            String result = URLEncoder.encode(commentContent, Xml.Encoding.UTF_8.name());
            data.setCommentContent(result);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        data.setPid(orderData.getPid());
        data.setUid(orderData.getUid());
        data.setDescribe(commentHasMap.get("describe"));
        data.setService(commentHasMap.get("service"));
        data.setDeliver(commentHasMap.get("deliver"));
        OrderAllDataModel.publishCommentData(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Response<ResponseBody> response) {
                try {
                    String result = response.body().string().toString();
                    if (result.equals("0200")) {
                        Toast.makeText(CommentActivity.this, "发布评价成功", Toast.LENGTH_SHORT).show();
                        object.onRefresh();
                        object.adapter.notifyDataSetChanged();

                        finish();
                    } else {
                        Toast.makeText(CommentActivity.this, "发布评价失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
            @Override
            public void onFailure(Throwable t) {

            }
        }, data);

    }


    /**
     * 建立toolbar
     *
     * @param toolbar
     */
    public void setToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
