package net.anumbrella.lkshop.ui.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jude.easyrecyclerview.EasyRecyclerView;

import net.anumbrella.lkshop.R;
import net.anumbrella.lkshop.adapter.ShoppingDataAdapter;
import net.anumbrella.lkshop.db.DBManager;
import net.anumbrella.lkshop.model.bean.ListProductContentModel;
import net.anumbrella.lkshop.ui.activity.ProductPayDetailActivity;
import net.anumbrella.lkshop.ui.viewholder.ShoppingDataViewHolder;
import net.anumbrella.lkshop.utils.BaseUtils;
import net.anumbrella.lkshop.widget.PromptDialog;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author：Anumbrella
 * Date：16/5/24 下午8:04
 */
public class ShoppingFragment extends Fragment {


    public static ShoppingFragment object = new ShoppingFragment();


    private static ArrayList<ListProductContentModel> arrayList = new ArrayList<>();

    private static Context mContext;

    private static ShoppingDataAdapter adapter;

    private GridLayoutManager girdLayoutManager;

    public static CheckBox checkBoxStateAll;

    public static boolean isCheckSingle = false;

    private boolean isEditState = false;

    private static int uid;

    private static TextView shopping_edit;

    private static TextView shopping_toal_data;

    private static EasyRecyclerView recyclerView;

    private static LinearLayout action_layout;

    public static TextView shopping_spend;

    public static TextView shopping_data_count_sum;

    private static LinearLayout shopping_calculate_layout;

    private static LinearLayout shopping_delete_all_layout;


    @BindView(R.id.check_all)
    CheckBox check_all;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        adapter = new ShoppingDataAdapter(mContext);
        uid = BaseUtils.readLocalUser(mContext).getUid();
    }


    @Override
    public void onResume() {
        if (adapter.getCount() == 0) {
            recyclerView.showError();
        }
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shop, container, false);
        ButterKnife.bind(this, view);
        setRetainInstance(true);
        checkBoxStateAll = check_all;
        shopping_toal_data = (TextView) view.findViewById(R.id.shopping_toal_data);
        shopping_edit = (TextView) view.findViewById(R.id.shopping_edit);
        recyclerView = (EasyRecyclerView) view.findViewById(R.id.shopping_list_data);
        action_layout = (LinearLayout) view.findViewById(R.id.shop_end_action);
        shopping_spend = (TextView) view.findViewById(R.id.shopping_spend);
        shopping_data_count_sum = (TextView) view.findViewById(R.id.shopping_data_count_sum);
        shopping_delete_all_layout = (LinearLayout) view.findViewById(R.id.shopping_delete_all_layout);
        shopping_calculate_layout = (LinearLayout) view.findViewById(R.id.shopping_calculate_layout);
        girdLayoutManager = new GridLayoutManager(getActivity(), 2);
        girdLayoutManager.setSpanSizeLookup(adapter.obtainTipSpanSizeLookUp());
        recyclerView.setLayoutManager(girdLayoutManager);
        recyclerView.setAdapterWithProgress(adapter);
        recyclerView.setErrorView(R.layout.shopping_no_data_error);
        check_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    checkAllState();
                    shopping_spend.setText(String.valueOf(countAllPrice()));
                    shopping_data_count_sum.setText(String.valueOf(getTotalSum()));
                } else if (!isChecked && isCheckSingle) {
                    isCheckSingle = false;
                } else {
                    unCheckAll();
                    shopping_spend.setText(String.valueOf("0"));
                    shopping_data_count_sum.setText(String.valueOf("0"));
                }
            }
        });
        if (adapter.getCount() != 0) {
            adapter.clear();
            adapter.addAll(setData());
            if (action_layout.getVisibility() == View.GONE) {
                action_layout.setVisibility(View.VISIBLE);
            }
        } else {
            adapter.addAll(setData());
            if (adapter.getCount() == 0) {
                recyclerView.showError();
                action_layout.setVisibility(View.GONE);
            }
        }
        shopping_toal_data.setText("(" + String.valueOf(adapter.getCount()) + ")");
        return view;
    }


    public void updateShoppingTotalSum() {
        adapter.notifyDataSetChanged();
        shopping_toal_data.setText("(" + String.valueOf(adapter.getCount()) + ")");
        if (adapter.getCount() == 0) {
            editActionState();
            recyclerView.showError();
            action_layout.setVisibility(View.GONE);
        } else {
            if (action_layout.getVisibility() == View.GONE) {
                action_layout.setVisibility(View.VISIBLE);
            }
        }
    }

    public static float countAllPrice() {
        float totalPrice = 0;
        List<ListProductContentModel> data = adapter.getAllData();
        Iterator iterator = adapter.getIsCheckList().entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            Integer key = (Integer) entry.getKey();
            boolean val = (boolean) entry.getValue();
            for (int i = 0; i < data.size(); i++) {
                int pid = data.get(i).getPid();
                if (pid == key && val) {
                    totalPrice = totalPrice + data.get(i).getPrice() * data.get(i).getSum();
                    break;
                }
            }
        }
        return totalPrice;
    }


    public static int getTotalSum() {
        List<ListProductContentModel> data = adapter.getAllData();
        Iterator iterator = adapter.getIsCheckList().entrySet().iterator();
        int totalSum = 0;
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            Integer key = (Integer) entry.getKey();
            boolean val = (boolean) entry.getValue();
            for (int i = 0; i < data.size(); i++) {
                int pid = data.get(i).getPid();
                if (pid == key && val) {
                    totalSum++;
                    break;
                }
            }
        }
        return totalSum;
    }


    public void checkAllState() {

        Iterator iterator = adapter.getIsCheckList().entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            Integer key = (Integer) entry.getKey();
            adapter.setCheckBoolean(key, true);
        }
        adapter.notifyDataSetChanged();
    }

    public void unCheckAll() {
        Iterator iterator = adapter.getIsCheckList().entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            Integer key = (Integer) entry.getKey();
            adapter.setCheckBoolean(key, false);
        }
        adapter.notifyDataSetChanged();
    }


    public static boolean ischeckAllState() {
        Iterator iterator = adapter.getIsCheckList().entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            boolean val = (Boolean) entry.getValue();
            if (val != true) {
                return false;
            }
        }
        return true;
    }


    @OnClick({R.id.check_all, R.id.shopping_edit, R.id.shopping_delete_all_layout, R.id.shopping_pay})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.check_all:
                break;
            case R.id.shopping_edit:
                if (adapter.getCount() > 0) {
                    if (isEditState) {
                        editActionState();
                    } else {
                        finishActionState();
                    }
                }
                break;
            case R.id.shopping_delete_all_layout:
                if (getTotalSum() > 0) {
                    new PromptDialog.Builder(mContext)
                            .setTitle("提示")
                            .setTitleColor(R.color.white)
                            .setViewStyle(PromptDialog.VIEW_STYLE_TITLEBAR_SKYBLUE)
                            .setMessage("确定删除这" + String.valueOf(getTotalSum()) + "件商品吗?")
                            .setMessageSize(20f)
                            .setButton1("确定", new PromptDialog.OnClickListener() {
                                @Override
                                public void onClick(Dialog dialog, int which) {
                                    deleteCheckShoppingData();
                                    dialog.dismiss();
                                    adapter.notifyDataSetChanged();
                                    if (adapter.getCount() == 0) {
                                        recyclerView.showError();
                                    }
                                }
                            })
                            .setButton2("取消", new PromptDialog.OnClickListener() {
                                @Override
                                public void onClick(Dialog dialog, int which) {
                                    dialog.dismiss();
                                }
                            })
                            .show();
                } else {
                    Toast.makeText(mContext, "没有选择商品", Toast.LENGTH_SHORT).show();
                }

                break;

            case R.id.shopping_pay:
                if (getTotalSum() > 0) {
                    setUploadOrderData();
                    Intent intent = new Intent();
                    intent.putParcelableArrayListExtra("data", arrayList);
                    intent.setClass(getContext(), ProductPayDetailActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(mContext, "没有选择商品", Toast.LENGTH_SHORT).show();
                }
                break;

        }

    }


    private static void setUploadOrderData() {
        List<ListProductContentModel> data = adapter.getAllData();
        Iterator iterator = adapter.getIsCheckList().entrySet().iterator();
        if (arrayList.size() > 0) {
            arrayList.clear();
        }
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            Integer key = (Integer) entry.getKey();
            boolean val = (boolean) entry.getValue();
            for (int i = 0; i < data.size(); i++) {
                int pid = data.get(i).getPid();
                if (pid == key && val) {
                    arrayList.add(data.get(i));
                    break;
                }
            }
        }

    }


    public static void deleteCheckShoppingData() {
        if(adapter != null){
            List<ListProductContentModel> data = adapter.getAllData();
            if(data != null && adapter.getIsCheckList() != null){
                Iterator iterator = adapter.getIsCheckList().entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry entry = (Map.Entry) iterator.next();
                    Integer key = (Integer) entry.getKey();
                    boolean val = (boolean) entry.getValue();
                    for (int i = 0; i < data.size(); i++) {
                        int pid = data.get(i).getPid();
                        if (pid == key && val) {
                            deleteProduct(data.get(i));
                            break;
                        }
                    }
                }
            }
            shopping_spend.setText(String.valueOf("0"));
            shopping_data_count_sum.setText(String.valueOf("0"));
        }
    }


    public void editActionState() {
        ShoppingDataAdapter.setDisplay(false);
        shopping_edit.setText("编辑");
        isEditState = false;
        shopping_calculate_layout.setVisibility(View.VISIBLE);
        shopping_delete_all_layout.setVisibility(View.GONE);
        updateDataSum();
    }

    public void finishActionState() {
        ShoppingDataAdapter.setDisplay(true);
        shopping_edit.setText("完成");
        isEditState = true;
        shopping_calculate_layout.setVisibility(View.GONE);
        shopping_delete_all_layout.setVisibility(View.VISIBLE);
        adapter.notifyDataSetChanged();
    }

    public void updateDataSum() {

        Iterator iterator = ShoppingDataViewHolder.getHashMap().entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            Integer key = (Integer) entry.getKey();
            Integer val = (Integer) entry.getValue();
            if (uid > 0 && key > 0 && val > 0) {
                DBManager.getManager(mContext).updateShoppingListData(key, uid, val);
            }
        }
        adapter.clear();
        adapter.addAll(setData());
        adapter.notifyDataSetChanged();
    }


    private ArrayList<ListProductContentModel> setData() {
        ArrayList<ListProductContentModel> data = new ArrayList<ListProductContentModel>();
        data = (ArrayList<ListProductContentModel>) DBManager.getManager(mContext).getShoppingListData(uid);
        if (data != null && data.size() > 0) {
            return data;
        }
        return null;
    }


    public static void deleteProduct(ListProductContentModel data) {
        if (data != null) {
            int index = adapter.getPosition(data);
            adapter.remove(index);
            if (uid > 0) {
                DBManager.getManager(mContext).deleteShoppingListData(uid, data.getPid());
            }
        }
        object.updateShoppingTotalSum();
    }


    @Override
    public void onPause() {
        ShoppingDataAdapter.setDisplay(false);
        isEditState = false;
        super.onPause();
    }
}
