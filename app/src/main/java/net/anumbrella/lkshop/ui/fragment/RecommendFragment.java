package net.anumbrella.lkshop.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jude.easyrecyclerview.EasyRecyclerView;

import net.anumbrella.lkshop.R;
import net.anumbrella.lkshop.adapter.RecommendAdapter;
import net.anumbrella.lkshop.db.DBManager;
import net.anumbrella.lkshop.model.RecommendModel;
import net.anumbrella.lkshop.model.bean.ProductDataModel;
import net.anumbrella.lkshop.model.bean.RecommendContentModel;
import net.anumbrella.lkshop.ui.viewholder.RollViewPagerItemView;

import java.util.List;

import rx.Subscriber;
import rx.functions.Action1;

/**
 * author：Anumbrella
 * Date：16/5/25 下午8:55
 */
public class RecommendFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    public EasyRecyclerView recyclerView;

    private RecommendAdapter adapter;

    private GridLayoutManager girdLayoutManager;

    private Context mContext;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new RecommendAdapter(getActivity());
        girdLayoutManager = new GridLayoutManager(getActivity(), 2);
        girdLayoutManager.setSpanSizeLookup(adapter.obtainTipSpanSizeLookUp());
        mContext = getContext();

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = (EasyRecyclerView) view.findViewById(R.id.easy_recyclerview);
        recyclerView.setErrorView(R.layout.view_net_error);
        adapter.addHeader(new RollViewPagerItemView(recyclerView.getSwipeToRefresh()));
        recyclerView.setLayoutManager(girdLayoutManager);
        recyclerView.setAdapterWithProgress(adapter);
        recyclerView.setRefreshListener(this);
        //打开首先从缓存获取数据显示
        getDataFromCache(false);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                onRefresh();
            }
        }, 1000);
        return view;
    }

    /**
     * 先从数据库获取数据
     */
    private void getDataFromCache(final boolean isUpadate) {
        RecommendModel.getProductsFromDB(getContext()).subscribe(new Action1<List<RecommendContentModel>>() {
            @Override
            public void call(List<RecommendContentModel> listDatas) {
                if (listDatas.size() != 0 && listDatas != null) {
                    if (isUpadate && adapter.getCount() != 0) {
                        adapter.clear();
                    }
                    adapter.addAll(listDatas);
                    if (recyclerView != null && !isUpadate) {
                        recyclerView.setRefreshing(true);

                    }
                }
            }
        });
    }

    @Override
    public void onRefresh() {

        RecommendModel.getProductsDataFromNet(new Subscriber<List<ProductDataModel>>() {
            @Override
            public void onCompleted() {
                recyclerView.setRefreshing(false);
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(mContext, "网络不给力", Toast.LENGTH_SHORT).show();
                if (adapter.getCount() == 0) {
                    recyclerView.showError();
                }
                recyclerView.setRefreshing(false);
            }

            @Override
            public void onNext(final List<ProductDataModel> productDataModels) {
                DBManager.getManager(getContext()).removeAllProducts();
                DBManager.getManager(getContext()).addAllProducts(productDataModels);
                getDataFromCache(true);
            }
        });

    }
}



