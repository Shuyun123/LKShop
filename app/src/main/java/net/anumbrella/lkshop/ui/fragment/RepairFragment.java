package net.anumbrella.lkshop.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.EasyRecyclerView;

import net.anumbrella.lkshop.R;
import net.anumbrella.lkshop.adapter.RepairAdapter;
import net.anumbrella.lkshop.config.Config;
import net.anumbrella.lkshop.model.bean.RepairDataModel;

import java.util.ArrayList;
import java.util.List;

/**
 * author：Anumbrella
 * Date：16/5/31 下午10:48
 */
public class RepairFragment extends Fragment {
    public EasyRecyclerView recyclerView;

    private RepairAdapter adapter;

    private GridLayoutManager girdLayoutManager;

    private Handler handler = new Handler();


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new RepairAdapter(getActivity());
        girdLayoutManager = new GridLayoutManager(getActivity(), 2);
        girdLayoutManager.setSpanSizeLookup(adapter.obtainTipSpanSizeLookUp());

    }

    private ArrayList<RepairDataModel> setData() {
        List<RepairDataModel> dataModelList = new ArrayList<RepairDataModel>();
        for (int i = 0; i < Config.repairImgs.length; i++) {
            RepairDataModel repairDataModel = new RepairDataModel();
            repairDataModel.setImg(Config.repairImgs[i]);
            repairDataModel.setTitle(Config.repairTexts[i]);
            dataModelList.add(repairDataModel);
        }
        return (ArrayList<RepairDataModel>) dataModelList;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        recyclerView = (EasyRecyclerView) view.findViewById(R.id.easy_recyclerview);
        recyclerView.setErrorView(R.layout.view_net_error);
        recyclerView.setLayoutManager(girdLayoutManager);
        recyclerView.setAdapterWithProgress(adapter);
        recyclerView.setRefreshing(false);
        onRefresh();
        return view;
    }

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
