package net.anumbrella.lkshop.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.umeng.message.PushAgent;

import net.anumbrella.lkshop.R;
import net.anumbrella.lkshop.adapter.CategorizeDetailProductAdapter;
import net.anumbrella.lkshop.db.DBManager;
import net.anumbrella.lkshop.model.bean.ListProductContentModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author：Anumbrella
 * Date：16/5/25 下午8:08
 */
public class SearchActivity extends BaseThemeSettingActivity {

    private CategorizeDetailProductAdapter adapter;

    private GridLayoutManager girdLayoutManager;

    private String searchkeyWord;


    @BindView(R.id.search_all_toolbar)
    Toolbar toolbar;

    @BindView(R.id.search_all_data)
    EasyRecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        PushAgent.getInstance(this).onAppStart();
        toolbar.setTitle("搜索商品");
        setToolbar(toolbar);
        if (getIntent().getBundleExtra("search") != null) {
            searchkeyWord = getIntent().getBundleExtra("search").getString("search");
        }
        adapter = new CategorizeDetailProductAdapter(this);
        girdLayoutManager = new GridLayoutManager(this, 2);
        girdLayoutManager.setSpanSizeLookup(adapter.obtainTipSpanSizeLookUp());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(girdLayoutManager);
        recyclerView.setAdapterWithProgress(adapter);
        recyclerView.setErrorView(R.layout.search_no_data);
        adapter.addAll(setData(searchkeyWord));
        if (adapter.getCount() == 0) {
            recyclerView.showError();
        }

    }

    private ArrayList<ListProductContentModel> setData(String searchKeyWord) {
        ArrayList<ListProductContentModel> data = new ArrayList<ListProductContentModel>();
        data = (ArrayList<ListProductContentModel>) DBManager.getManager(this).getProductDataBySearch(searchKeyWord);
        return data;
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
