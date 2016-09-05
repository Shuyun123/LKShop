package net.anumbrella.lkshop.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.umeng.message.PushAgent;

import net.anumbrella.lkshop.R;
import net.anumbrella.lkshop.adapter.CategorizeDetailProductAdapter;
import net.anumbrella.lkshop.db.DBManager;
import net.anumbrella.lkshop.model.bean.ListProductContentModel;
import net.anumbrella.lkshop.model.bean.ProductTypeModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author：Anumbrella
 * Date：16/6/4 下午6:04
 */
public class CategorizeDetailProductActivity extends BaseThemeSettingActivity {

    public static final String INTENT_PRODUCT_ITEM_INFO = "item_product_info";

    private ProductTypeModel productTypeModel;

    private CategorizeDetailProductAdapter adapter;

    private GridLayoutManager girdLayoutManager;

    @BindView(R.id.categorize_detail_recycleview)
    EasyRecyclerView recyclerView;


    @BindView(R.id.categorize_detail_toobar)
    Toolbar toolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_detail_product);
        ButterKnife.bind(this);
        PushAgent.getInstance(this).onAppStart();
        if (getIntent().getParcelableExtra(INTENT_PRODUCT_ITEM_INFO) != null) {
            productTypeModel = getIntent().getParcelableExtra(INTENT_PRODUCT_ITEM_INFO);

        }
        toolbar.setTitle("商品分类");
        adapter = new CategorizeDetailProductAdapter(this);
        girdLayoutManager = new GridLayoutManager(this, 2);
        girdLayoutManager.setSpanSizeLookup(adapter.obtainTipSpanSizeLookUp());
        setToolbar(toolbar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(girdLayoutManager);
        recyclerView.setAdapterWithProgress(adapter);
        recyclerView.setErrorView(R.layout.categorize_detail_product_no_data_error);
        adapter.addAll(setData());
        if (adapter.getCount() == 0) {
            recyclerView.showError();
        }
    }

    private ArrayList<ListProductContentModel> setData() {
        ArrayList<ListProductContentModel> data = new ArrayList<ListProductContentModel>();
        if (productTypeModel != null) {
            data = (ArrayList<ListProductContentModel>) DBManager.getManager(this).getCategorizeDetailProduct(productTypeModel.getProductName(), productTypeModel.getPhoneType());
        }

        if (data != null && data.size() > 0) {
            return data;
        }
        return null;
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
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
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
