package net.anumbrella.lkshop.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.EasyRecyclerView;

import net.anumbrella.lkshop.R;
import net.anumbrella.lkshop.adapter.ListProductAdapter;
import net.anumbrella.lkshop.db.DBManager;
import net.anumbrella.lkshop.model.bean.ListProductContentModel;

import java.util.ArrayList;

/**
 * author：Anumbrella
 * Date：16/5/26 上午10:29
 */
public class ListProductFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    public EasyRecyclerView recyclerView;

    private ListProductAdapter adapter;

    private GridLayoutManager girdLayoutManager;

    private Handler handler = new Handler();

    private int type;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ListProductAdapter(getActivity());
        girdLayoutManager = new GridLayoutManager(getActivity(), 2);
        girdLayoutManager.setSpanSizeLookup(adapter.obtainTipSpanSizeLookUp());
        this.type = getArguments().getInt("type");

    }

    private ArrayList<ListProductContentModel> setData() {
        ArrayList<ListProductContentModel> list = new ArrayList<ListProductContentModel>();
        list = (ArrayList<ListProductContentModel>) DBManager.getManager(getContext()).getAllProductsFromDB(type);
        return list;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = (EasyRecyclerView) view.findViewById(R.id.easy_recyclerview);
        recyclerView.setErrorView(R.layout.view_net_error);
        recyclerView.setLayoutManager(girdLayoutManager);
        recyclerView.setAdapterWithProgress(adapter);
        recyclerView.setRefreshListener(this);
        onRefresh();
        return view;
    }

    @Override
    public void onRefresh() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.clear();
                adapter.addAll(setData());
            }
        }, 1000);

    }
}
