package net.anumbrella.lkshop.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.jude.easyrecyclerview.EasyRecyclerView;

import net.anumbrella.lkshop.R;
import net.anumbrella.lkshop.adapter.RepairAdapter;
import net.anumbrella.lkshop.config.Config;
import net.anumbrella.lkshop.model.bean.RepairDataModel;
import net.anumbrella.lkshop.ui.activity.RepairContentActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author：Anumbrella
 * Date：16/6/4 下午12:53
 */
public class RepairContentFragment extends Fragment {

    public static final String ARG_ITEM_INFO_TYPE = "item_info_type";

    private String type = null;

    private Context mContext;

    private RepairAdapter adapter;

    private GridLayoutManager girdLayoutManager;

    @BindView(R.id.repair_toolbar)
    Toolbar toolbar;

    @BindView(R.id.repair_recycleview)
    EasyRecyclerView recyclerView;

    @BindView(R.id.recycleview_layout)
    LinearLayout recycleview_layout;

    @BindView(R.id.repair_link_content)
    ScrollView repair_link_content;

    @BindView(R.id.link_us_number1)
    TextView number1;

    @BindView(R.id.link_us_number2)
    TextView number2;


    @BindView(R.id.link_us_qq)
    TextView qq;


    @BindView(R.id.link_us_weixin)
    TextView weixin;


    public static RepairContentFragment newInstance(String type) {
        RepairContentFragment fragment = new RepairContentFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ITEM_INFO_TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        adapter = new RepairAdapter(getActivity());
        girdLayoutManager = new GridLayoutManager(getActivity(), 2);
        girdLayoutManager.setSpanSizeLookup(adapter.obtainTipSpanSizeLookUp());


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_repaircontent, container, false);
        ButterKnife.bind(this, view);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setLayoutManager(girdLayoutManager);
        recyclerView.setAdapterWithProgress(adapter);
        if (getArguments().containsKey(ARG_ITEM_INFO_TYPE)) {
            type = getArguments().getString(ARG_ITEM_INFO_TYPE);
        }
        if (type != null && !type.equals(Config.repairTexts[0])) {
            recycleview_layout.setVisibility(View.GONE);
            repair_link_content.setVisibility(View.VISIBLE);
        } else {
            adapter.addAll(setData());
            repair_link_content.setVisibility(View.GONE);
            recycleview_layout.setVisibility(View.VISIBLE);
        }
        setHasOptionsMenu(true);
        toolbar.setTitle(type);
        ((RepairContentActivity) getActivity()).setToolbar(toolbar);
        TypedArray array = getActivity().getTheme().obtainStyledAttributes(new int[] {
                android.R.attr.colorPrimary,
        });
        int color = array.getColor(0, 0xFF00FF);
        toolbar.setBackgroundColor(color);
        return view;
    }

    private ArrayList<RepairDataModel> setData() {
        List<RepairDataModel> dataModelList = new ArrayList<RepairDataModel>();
        for (int i = 0; i < Config.repairScreenPhoneTypes.length; i++) {
            RepairDataModel repairDataModel = new RepairDataModel();
            repairDataModel.setImg(Config.repairScreenPhoneImgs[i]);
            repairDataModel.setTitle(Config.repairScreenPhoneTypes[i]);
            dataModelList.add(repairDataModel);
        }
        return (ArrayList<RepairDataModel>) dataModelList;
    }


    @OnClick({R.id.link_us_number1, R.id.link_us_number2, R.id.link_us_qq, R.id.link_us_weixin})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.link_us_number1:
                String num1 = number1.getText().toString();
                Intent intent1 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + num1));
                startActivity(intent1);
                break;
            case R.id.link_us_number2:
                String num2 = number2.getText().toString();
                Intent intent2 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + num2));
                startActivity(intent2);
                break;
            case R.id.link_us_qq:
                String url = "mqqwpa://im/chat?chat_type=wpa&uin=" + qq.getText().toString();
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                break;
            case R.id.link_us_weixin:
                break;
        }
    }


}