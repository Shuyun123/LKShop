package net.anumbrella.lkshop.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.umeng.message.PushAgent;

import net.anumbrella.lkshop.R;
import net.anumbrella.lkshop.http.RetrofitHttp;
import net.anumbrella.lkshop.model.bean.ListProductContentModel;
import net.anumbrella.lkshop.ui.fragment.ShoppingFragment;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * author：Anumbrella
 * Date：16/6/9 上午10:59
 */
public class ProductPayDetailActivity extends BaseThemeSettingActivity {

    private ArrayList<ListProductContentModel> data;

    private ProgressDialog mDialog;

    @BindView(R.id.pay_detail_screen_toolbar)
    Toolbar toolbar;

    @BindView(R.id.btn_link_seller)
    Button btn_link_seller;


    @BindView(R.id.btn_upload_order)
    Button btn_upload_order;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_pay_detail);
        ButterKnife.bind(this);
        PushAgent.getInstance(this).onAppStart();
        toolbar.setTitle("结算");
        setToolbar(toolbar);
        mDialog = new ProgressDialog(this);
        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mDialog.setMessage("请稍等");
        mDialog.setIndeterminate(false);

        // 设置ProgressDialog 是否可以按退回按键取消
        mDialog.setCancelable(false);

        if (getIntent().getParcelableArrayListExtra("data") != null) {
            data = getIntent().getParcelableArrayListExtra("data");
        }

    }

    private void prompt(Call<ResponseBody> uploadOrder, final int position) {
        uploadOrder.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Response<ResponseBody> response) {
                try {
                    String result = response.body().string().toString();

                    if (position == data.size() - 1) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mDialog.hide();
                            }
                        }, 2000);

                        if (result.equals("0200")) {
                            Toast.makeText(ProductPayDetailActivity.this, "上传成功", Toast.LENGTH_SHORT).show();
                            ShoppingFragment.deleteCheckShoppingData();
                            Intent all_order = new Intent();
                            all_order.putExtra(AllOrderActivity.ARG_ITEM_INFO_ORDER_LOOK_TYPE, "all");
                            all_order.setClass(ProductPayDetailActivity.this, AllOrderActivity.class);
                            startActivity(all_order);
                            finish();
                        } else {
                            Toast.makeText(ProductPayDetailActivity.this, "上传失败,请稍后重试", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(ProductPayDetailActivity.this, "网络不给力", Toast.LENGTH_SHORT).show();
                mDialog.hide();
            }
        });

    }

    @OnClick({R.id.btn_link_seller, R.id.btn_upload_order})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.btn_upload_order:
                mDialog.show();
                for (int i = 0; i < data.size(); i++) {
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    prompt(RetrofitHttp.getRetrofit(builder.build()).uploadOrder("uploadOrder", data.get(i)), i);
                }

                break;
            case R.id.btn_link_seller:
                String url = "mqqwpa://im/chat?chat_type=wpa&uin=466097381";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                break;
        }
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
