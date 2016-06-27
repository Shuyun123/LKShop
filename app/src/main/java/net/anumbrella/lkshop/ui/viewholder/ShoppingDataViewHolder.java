package net.anumbrella.lkshop.ui.viewholder;

import android.app.Dialog;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import net.anumbrella.lkshop.R;
import net.anumbrella.lkshop.adapter.ShoppingDataAdapter;
import net.anumbrella.lkshop.model.bean.ListProductContentModel;
import net.anumbrella.lkshop.ui.fragment.ShoppingFragment;
import net.anumbrella.lkshop.widget.PromptDialog;

import java.util.HashMap;

/**
 * author：Anumbrella
 * Date：16/6/4 下午10:01
 */
public class ShoppingDataViewHolder extends BaseViewHolder<ListProductContentModel> implements View.OnClickListener {

    private ListProductContentModel data;

    private static HashMap<Integer, Integer> hashMapValue = new HashMap<>();

    private SimpleDraweeView icon;

    private CardView cardview;

    private CheckBox checkbox;

    private TextView title;

    private TextView price;

    private TextView sum;

    private LinearLayout shopping_delete_layout;


    private LinearLayout shopping_data_action;


    private LinearLayout shopping_data_sum;

    private ImageView actionSub;

    private ImageView actionAdd;

    private TextView productSum;

    private TextView actionDelete;


    public ShoppingDataViewHolder(ViewGroup parent) {
        super(parent, R.layout.itemview_shopping_data);
        icon = $(R.id.shopping_data_icon);
        cardview = $(R.id.shopping_product_cardview);
        checkbox = $(R.id.checkbox);
        title = $(R.id.shopping_data_title);
        price = $(R.id.shopping_data_price);
        sum = $(R.id.shopping_data_add_sum);
        shopping_delete_layout = $(R.id.shopping_delete_layout);
        shopping_data_action = $(R.id.shopping_data_action);
        shopping_data_sum = $(R.id.shopping_data_sum);
        actionAdd = $(R.id.shopping_product_sum_add);
        actionSub = $(R.id.shopping_product_sum_sub);
        actionDelete = $(R.id.shopping_action_delete);
        productSum = $(R.id.shopping_product_sum);

    }

    @Override
    public void setData(final ListProductContentModel listData) {
        super.setData(data);
        this.data = listData;
        icon.setImageURI(Uri.parse(data.getImageUrl()));
        cardview.setOnClickListener(this);
        checkbox.setOnClickListener(this);
        actionAdd.setOnClickListener(this);
        actionSub.setOnClickListener(this);
        actionDelete.setOnClickListener(this);
        title.setText(data.getTitle());
        price.setText("￥" + String.valueOf(data.getPrice()));
        sum.setText(String.valueOf(data.getSum()));
        productSum.setText(String.valueOf(data.getSum()));
        final boolean isCheck = ShoppingDataAdapter.getIsCheck(data.getPid());
        if (ShoppingDataAdapter.getDisplay()) {
            shopping_delete_layout.setVisibility(View.VISIBLE);
            shopping_data_action.setVisibility(View.VISIBLE);
            shopping_data_sum.setVisibility(View.GONE);
        } else {
            shopping_delete_layout.setVisibility(View.GONE);
            shopping_data_action.setVisibility(View.GONE);
            shopping_data_sum.setVisibility(View.VISIBLE);
        }

        checkbox.setChecked(isCheck);
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                ShoppingDataAdapter.setCheckBoolean(data.getPid(), isChecked);
                if (ShoppingFragment.ischeckAllState() && isChecked) {
                    ShoppingFragment.checkBoxStateAll.setChecked(true);
                } else {
                    if (ShoppingFragment.checkBoxStateAll.isChecked()) {
                        ShoppingFragment.isCheckSingle = true;
                        ShoppingFragment.checkBoxStateAll.setChecked(false);
                    } else if (ShoppingFragment.ischeckAllState()) {
                        ShoppingFragment.isCheckSingle = false;
                        ShoppingFragment.checkBoxStateAll.setChecked(true);
                    }

                    ShoppingFragment.shopping_spend.setText(String.valueOf(ShoppingFragment.countAllPrice()));
                    ShoppingFragment.shopping_data_count_sum.setText(String.valueOf(ShoppingFragment.getTotalSum()));
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shopping_product_sum_add:
                productSum.setText(String.valueOf(Integer.parseInt(productSum.getText().toString()) + 1));
                addHashMapValue(data.getPid(), Integer.parseInt(productSum.getText().toString()));
                break;
            case R.id.shopping_product_sum_sub:
                if (Integer.parseInt(productSum.getText().toString()) > 1) {
                    productSum.setText(String.valueOf(Integer.parseInt(productSum.getText().toString()) - 1));
                }
                addHashMapValue(data.getPid(), Integer.parseInt(productSum.getText().toString()));
                break;
            case R.id.shopping_action_delete:

                new PromptDialog.Builder(getContext())
                        .setTitle("提示")
                        .setTitleColor(R.color.white)
                        .setViewStyle(PromptDialog.VIEW_STYLE_TITLEBAR_SKYBLUE)
                        .setMessage("确定删除该商品吗?")
                        .setMessageSize(20f)
                        .setButton1("确定", new PromptDialog.OnClickListener() {
                            @Override
                            public void onClick(Dialog dialog, int which) {
                                ShoppingFragment.deleteProduct(data);
                                dialog.dismiss();
                            }
                        })
                        .setButton2("取消", new PromptDialog.OnClickListener() {
                            @Override
                            public void onClick(Dialog dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .show();

                break;

        }
    }

    public void addHashMapValue(int pid, int sum) {
        if (hashMapValue.get(pid) == null || hashMapValue.get(pid) != sum) {
            hashMapValue.put(pid, sum);
        }
    }

    public static HashMap<Integer, Integer> getHashMap() {
        return hashMapValue;
    }

}
