package net.anumbrella.lkshop.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import net.anumbrella.lkshop.R;
import net.anumbrella.lkshop.adapter.GridViewAdapter;
import net.anumbrella.lkshop.config.Config;
import net.anumbrella.lkshop.model.bean.ProductTypeModel;
import net.anumbrella.lkshop.ui.activity.CategorizeDetailProductActivity;

import java.util.ArrayList;

/**
 * author：Anumbrella
 * Date：16/5/26 下午7:13
 */
public class CategorizeListContentFragment extends Fragment {

    public ArrayList<ProductTypeModel> productListType;

    /**
     * widget网格view
     */
    private GridView gridView;


    private GridViewAdapter adapter;


    private ProductTypeModel productType;

    private String productName;

    private int icon;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.productlist_layout, null);
        gridView = (GridView) view.findViewById(R.id.GridViewList);
        int index = getArguments().getInt("index");
        productName = Config.categorizeTools[index];
        icon = Config.categorizeToolsImg[index];
        ((TextView) view.findViewById(R.id.productName)).setText(productName);

        //临时数据
        productListType = new ArrayList<ProductTypeModel>();
        // 这里可以根据数据设定要填充的资源

        productType = new ProductTypeModel(0, icon, productName, "全新 " + productName, Integer.parseInt(Config.productTypeArrays[1][0]));
        productListType.add(productType);

        productType = new ProductTypeModel(1, icon, productName, "二手 " + productName, Integer.parseInt(Config.productTypeArrays[2][0]));
        productListType.add(productType);

        adapter = new GridViewAdapter(getActivity(), productListType);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                ProductTypeModel data = productListType.get(position);
                Intent intent = new Intent();
                intent.putExtra(CategorizeDetailProductActivity.INTENT_PRODUCT_ITEM_INFO, data);
                intent.setClass(getContext(), CategorizeDetailProductActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
