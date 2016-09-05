package net.anumbrella.lkshop.ui.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.umeng.message.PushAgent;

import net.anumbrella.lkshop.R;
import net.anumbrella.lkshop.adapter.CollectAdapter;
import net.anumbrella.lkshop.db.DBManager;
import net.anumbrella.lkshop.model.bean.ListProductContentModel;
import net.anumbrella.lkshop.utils.BaseUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author：Anumbrella
 * Date：16/6/10 下午11:09
 */
public class CollectActivity extends BaseThemeSettingActivity implements SwipeRefreshLayout.OnRefreshListener {

    private static CollectAdapter adapter;

    private static EasyRecyclerView recyclerView;

    private GridLayoutManager girdLayoutManager;

    private Handler handler = new Handler();

    private static int uid;


    @BindView(R.id.collect_all_toolbar)
    Toolbar toolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_collect);
        recyclerView = (EasyRecyclerView) findViewById(R.id.collect_all_data);
        ButterKnife.bind(this);
        PushAgent.getInstance(this).onAppStart();
        uid = BaseUtils.readLocalUser(CollectActivity.this).getUid();
        toolbar.setTitle("我的收藏");
        setToolbar(toolbar);
        adapter = new CollectAdapter(this);
        girdLayoutManager = new GridLayoutManager(this, 2);
        girdLayoutManager.setSpanSizeLookup(adapter.obtainTipSpanSizeLookUp());
        recyclerView.setLayoutManager(girdLayoutManager);
        recyclerView.setErrorView(R.layout.collect_no_data_error);
        recyclerView.setAdapterWithProgress(adapter);
        recyclerView.setRefreshListener(this);
        recyclerView.setRefreshing(false);
        onRefresh();

    }


    private ArrayList<ListProductContentModel> setData() {
        ArrayList<ListProductContentModel> list = new ArrayList<ListProductContentModel>();
        list = (ArrayList<ListProductContentModel>) DBManager.getManager(this).getCollectListData(uid);
        return list;
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
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.clear();
                adapter.addAll(setData());
                if (adapter.getCount() == 0) {
                    recyclerView.showError();
                }
            }
        }, 1000);

    }

    public void deleteCollect(ListProductContentModel data) {
        int pid = data.getPid();
        if (pid > 0 && uid > 0) {
            DBManager.getManager(this).deleteCollect(pid, uid);
        }
        adapter.clear();
        adapter.addAll(setData());
        adapter.notifyDataSetChanged();
        if (adapter.getCount() == 0) {
            recyclerView.showError();
        }
    }

    @Override
    protected void onResume() {
        adapter.clear();
        adapter.addAll(setData());
        if (adapter.getCount() == 0) {
            recyclerView.showError();
        }
        super.onResume();
    }
}
