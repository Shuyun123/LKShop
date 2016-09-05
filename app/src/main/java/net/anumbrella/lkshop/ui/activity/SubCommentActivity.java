package net.anumbrella.lkshop.ui.activity;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Xml;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.utils.JUtils;
import com.umeng.message.PushAgent;
import com.vanniktech.emoji.EmojiEditText;
import com.vanniktech.emoji.EmojiPopup;
import com.vanniktech.emoji.listeners.OnEmojiPopupDismissListener;
import com.vanniktech.emoji.listeners.OnEmojiPopupShownListener;
import com.vanniktech.emoji.listeners.OnSoftKeyboardCloseListener;

import net.anumbrella.lkshop.R;
import net.anumbrella.lkshop.adapter.SubCommentAdapter;
import net.anumbrella.lkshop.model.CommentDataModel;
import net.anumbrella.lkshop.model.bean.CommentProductDataModel;
import net.anumbrella.lkshop.model.bean.SubCommentDataModel;
import net.anumbrella.lkshop.utils.BaseUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;

/**
 * author：Anumbrella
 * Date：16/6/17 下午1:14
 */
public class SubCommentActivity extends BaseThemeSettingActivity implements SwipeRefreshLayout.OnRefreshListener {

    public final static String ARG_ITEM_INFO_SUB_COMMENT_DATA = "item_info_sub_comment_data";

    private SubCommentAdapter adapter;

    private CommentProductDataModel data;

    private EmojiPopup emojiPopup;

    private ProgressDialog mDialog;

    private int uid;

    @BindView(R.id.emoji_icon)
    ImageButton emojiBtn;

    @BindView(R.id.sub_comment_root_view)
    LinearLayout subCommentRootView;

    @BindView(R.id.sub_comment_edittext)
    EmojiEditText emojiEditText;

    @BindView(R.id.sub_comment_all_toolbar)
    Toolbar toolbar;

    @BindView(R.id.sub_comment_content)
    EasyRecyclerView recyclerView;

    @BindView(R.id.send_sub_comment)
    TextView sendSubComment;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!JUtils.isNetWorkAvilable()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        }
        setContentView(R.layout.activity_sub_comment);
        ButterKnife.bind(this);
        PushAgent.getInstance(this).onAppStart();
        adapter = new SubCommentAdapter(this);
        toolbar.setTitle("评论列表");
        mDialog = new ProgressDialog(this);
        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mDialog.setMessage("请稍等");
        mDialog.setIndeterminate(false);
        // 设置ProgressDialog 是否可以按退回按键取消
        mDialog.setCancelable(false);
        setToolbar(toolbar);
        uid = BaseUtils.readLocalUser(SubCommentActivity.this).getUid();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setErrorView(R.layout.view_net_comment_error);
        recyclerView.setRefreshListener(this);
        if (getIntent().getParcelableExtra(ARG_ITEM_INFO_SUB_COMMENT_DATA) != null) {
            data = getIntent().getParcelableExtra(ARG_ITEM_INFO_SUB_COMMENT_DATA);
        }
        setData();
        recyclerView.setAdapterWithProgress(adapter);
        setUpEmojiPopup();
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setUpEmojiPopup() {
        emojiPopup = EmojiPopup.Builder.fromRootView(subCommentRootView)
                .setOnEmojiPopupShownListener(new OnEmojiPopupShownListener() {
                    @Override
                    public void onEmojiPopupShown() {
                        emojiBtn.setBackground(getResources().getDrawable(R.mipmap.keyboard));
                    }
                }).setOnEmojiPopupDismissListener(new OnEmojiPopupDismissListener() {

                    @Override
                    public void onEmojiPopupDismiss() {
                        emojiBtn.setBackground(getResources().getDrawable(R.mipmap.emoji_icon));
                    }
                }).setOnSoftKeyboardCloseListener(new OnSoftKeyboardCloseListener() {
                    @Override
                    public void onKeyboardClose() {
                        emojiPopup.dismiss();
                    }
                }).build(emojiEditText);
    }


    @Override
    public void onBackPressed() {
        if (emojiPopup != null && emojiPopup.isShowing()) {
            emojiPopup.dismiss();
        } else {
            finish();
            super.onBackPressed();
        }
    }

    public void setData() {
        if (data != null) {
            CommentDataModel.getSubCommentData(new Subscriber<List<SubCommentDataModel>>() {
                @Override
                public void onCompleted() {
                    recyclerView.setRefreshing(false);
                }

                @Override
                public void onError(Throwable e) {
                    recyclerView.setRefreshing(false);
                    recyclerView.showError();
                }

                @Override
                public void onNext(List<SubCommentDataModel> subCommentDataModels) {

                    if (subCommentDataModels.size() == 1 && subCommentDataModels.get(0).getSubCommentContent().equals("404")) {
                        subCommentDataModels.clear();
                        subCommentDataModels.add(null);
                        adapter.addAll(subCommentDataModels);
                    } else {
                        recyclerView.setErrorView(R.layout.view_net_comment_error);
                        if (adapter.getCount() > 0) {
                            adapter.clear();
                            adapter.addAll(subCommentDataModels);
                        } else {
                            adapter.addAll(subCommentDataModels);
                        }
                    }
                }
            }, String.valueOf(data.getCid()));
        }

    }


    public void uploadCommentData(SubCommentDataModel data) {
        CommentDataModel.uploadSubCommentData(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Response<ResponseBody> response) {
                try {
                    String result = response.body().string().toString();
                    if (result.equals("0200")) {
                        Toast.makeText(SubCommentActivity.this, "评论成功", Toast.LENGTH_SHORT).show();
                        emojiEditText.setText("");
                        setData();
                        adapter.notifyDataSetChanged();
                        recyclerView.scrollToPosition(adapter.getCount() - 1);
                        mDialog.hide();

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(SubCommentActivity.this, "评论失败", Toast.LENGTH_SHORT).show();
                mDialog.hide();

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


    @OnClick({R.id.emoji_icon, R.id.send_sub_comment})
    public void click(View view) {
        if (BaseUtils.checkLogin(SubCommentActivity.this)) {
            switch (view.getId()) {
                case R.id.emoji_icon:
                    if (getWindow().getAttributes().softInputMode == WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED) {
                        //隐藏软键盘
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                        getWindow().getAttributes().softInputMode = WindowManager.LayoutParams.SOFT_INPUT_STATE_UNSPECIFIED;
                    }
                    emojiPopup.toggle();
                    break;
                case R.id.send_sub_comment:
                    String subCommentContent = emojiEditText.getText().toString().trim();
                    SubCommentDataModel dataModel = new SubCommentDataModel();
                    if (subCommentContent.equals("")) {
                        Toast.makeText(this, "评论内容不能为空", Toast.LENGTH_SHORT).show();
                    } else {
                        dataModel.setCid(data.getCid());
                        try {
                            String result = URLEncoder.encode(subCommentContent, Xml.Encoding.UTF_8.name());
                            dataModel.setSubCommentContent(result);
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        if (uid > 0) {
                            dataModel.setUid(uid);
                        }
                        mDialog.show();
                        uploadCommentData(dataModel);
                    }
                    break;
            }

        }
    }

    @Override
    public void onRefresh() {
        setData();
        adapter.notifyDataSetChanged();
    }
}
