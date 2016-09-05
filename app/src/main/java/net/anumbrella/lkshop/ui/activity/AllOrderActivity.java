package net.anumbrella.lkshop.ui.activity;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.umeng.message.PushAgent;

import net.anumbrella.lkshop.R;
import net.anumbrella.lkshop.adapter.OrderAllAdapter;
import net.anumbrella.lkshop.model.OrderAllDataModel;
import net.anumbrella.lkshop.model.bean.OrderDataModel;
import net.anumbrella.lkshop.utils.BaseUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Response;
import rx.Subscriber;

/**
 * author：Anumbrella
 * Date：16/6/9 下午5:31
 */
public class AllOrderActivity extends BaseThemeSettingActivity implements SwipeRefreshLayout.OnRefreshListener {

    public final static String ARG_ITEM_INFO_ORDER_LOOK_TYPE = "item_info_order_type";

    private static String type = "all";

    private static int uid;

    public static OrderAllAdapter adapter;

    private static Context mContext;

    private GridLayoutManager girdLayoutManager;

    private static EasyRecyclerView recyclerView;

    @BindView(R.id.order_all_toolbar)
    Toolbar toolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_order);
        ButterKnife.bind(this);
        mContext = this;
        PushAgent.getInstance(this).onAppStart();
        if (getIntent().getStringExtra(ARG_ITEM_INFO_ORDER_LOOK_TYPE) != null) {
            type = getIntent().getStringExtra(ARG_ITEM_INFO_ORDER_LOOK_TYPE);
        }

        if (type.equals("comment")) {
            toolbar.setTitle("待评价订单");
        } else if (type.equals("deliver")) {
            toolbar.setTitle("待发货订单");
        } else if (type.equals("pay")) {
            toolbar.setTitle("待付款订单");
        } else if (type.equals("all")) {
            toolbar.setTitle("全部订单");
        } else if (type.equals("done")) {
            toolbar.setTitle("已完成的交易");
        }
        uid = BaseUtils.readLocalUser(AllOrderActivity.this).getUid();
        setToolbar(toolbar);
        adapter = new OrderAllAdapter(this);
        recyclerView = (EasyRecyclerView) findViewById(R.id.order_all_data);
        girdLayoutManager = new GridLayoutManager(this, 2);
        girdLayoutManager.setSpanSizeLookup(adapter.obtainTipSpanSizeLookUp());
        recyclerView.setLayoutManager(girdLayoutManager);
        recyclerView.setRefreshListener(this);
        recyclerView.setErrorView(R.layout.view_net_error);
        recyclerView.setAdapterWithProgress(adapter);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onRefresh();
            }
        }, 1000);

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


    @Override
    public void onRefresh() {
        getData(type);
    }

    private void getData(final String type) {
        if (uid > 0) {
            OrderAllDataModel.getOrderDataFromNet(new Subscriber<List<OrderDataModel>>() {
                @Override
                public void onCompleted() {
                    recyclerView.setRefreshing(false);

                }

                @Override
                public void onError(Throwable e) {
                    Toast.makeText(AllOrderActivity.this, "网络不给力", Toast.LENGTH_SHORT).show();
                    if (adapter.getCount() == 0) {
                        recyclerView.showError();
                    }
                    recyclerView.setRefreshing(false);

                }

                @Override
                public void onNext(List<OrderDataModel> orderDataModels) {

                    if (orderDataModels.size() == 1 && orderDataModels.get(0).getImg().equals("404")) {
                        recyclerView.setErrorView(R.layout.order_no_data_error);
                        recyclerView.showError();
                    } else {
                        recyclerView.setErrorView(R.layout.view_net_error);
                        if (adapter.getCount() > 0) {
                            adapter.clear();
                            adapter.addAll(dealData(type, orderDataModels));
                            if (adapter.getCount() == 0) {
                                LinearLayout view = (LinearLayout) LayoutInflater.from(AllOrderActivity.this).inflate(R.layout.order_no_data_error, null);
                                ((TextView) view.findViewById(R.id.order_no_data_text)).setText("暂无数据");
                                recyclerView.setErrorView(view);
                                recyclerView.showError();
                            }
                        } else {
                            adapter.addAll(dealData(type, orderDataModels));
                            if (adapter.getCount() == 0) {
                                LinearLayout view = (LinearLayout) LayoutInflater.from(AllOrderActivity.this).inflate(R.layout.order_no_data_error, null);
                                ((TextView) view.findViewById(R.id.order_no_data_text)).setText("暂无数据");
                                recyclerView.setErrorView(view);
                                recyclerView.showError();
                            }
                        }
                    }

                }
            }, String.valueOf(uid));
        }
    }


    public List<OrderDataModel> dealData(String type, List<OrderDataModel> orderDataModels) {
        List<OrderDataModel> data = new ArrayList<>();
        switch (type) {
            case "comment":
                for (int i = 0; i < orderDataModels.size(); i++) {
                    String result = BaseUtils.transformState(orderDataModels.get(i).getIsPay(), orderDataModels.get(i).getIsDeliver(), orderDataModels.get(i).getIsComment());
                    if (result.equals("待评价")) {
                        data.add(orderDataModels.get(i));
                    }
                }
                break;
            case "deliver":
                for (int i = 0; i < orderDataModels.size(); i++) {
                    String result = BaseUtils.transformState(orderDataModels.get(i).getIsPay(), orderDataModels.get(i).getIsDeliver(), orderDataModels.get(i).getIsComment());
                    if (result.equals("待发货")) {
                        data.add(orderDataModels.get(i));
                    }
                }
                break;
            case "pay":
                for (int i = 0; i < orderDataModels.size(); i++) {
                    String result = BaseUtils.transformState(orderDataModels.get(i).getIsPay(), orderDataModels.get(i).getIsDeliver(), orderDataModels.get(i).getIsComment());
                    if (result.equals("待付款")) {
                        data.add(orderDataModels.get(i));
                    }
                }
                break;
            case "done":
                for (int i = 0; i < orderDataModels.size(); i++) {
                    String result = BaseUtils.transformState(orderDataModels.get(i).getIsPay(), orderDataModels.get(i).getIsDeliver(), orderDataModels.get(i).getIsComment());
                    if (result.equals("订单交易成功")) {
                        data.add(orderDataModels.get(i));
                    }
                }
                break;
            default:
                data = orderDataModels;
        }

        return data;
    }


    public void deleteOrder(OrderDataModel data) {
        if (uid > 0) {
            int pid = data.getPid();
            int oid = data.getBid();
            OrderAllDataModel.deleteOrderData(
                    new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Response<ResponseBody> response) {
                            try {
                                String result = response.body().string().toString();
                                if (result.equals("0200")) {
                                    onRefresh();
                                    adapter.notifyDataSetChanged();
                                } else {
                                    Toast.makeText(mContext, "删除失败", Toast.LENGTH_SHORT).show();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            Toast.makeText(mContext, "删除失败", Toast.LENGTH_SHORT).show();

                        }
                    }
                    , String.valueOf(uid), String.valueOf(pid), String.valueOf(oid));
        }
    }
}
