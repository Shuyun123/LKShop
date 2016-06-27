package net.anumbrella.lkshop.ui.viewholder;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import net.anumbrella.lkshop.R;
import net.anumbrella.lkshop.model.bean.ListProductContentModel;
import net.anumbrella.lkshop.ui.activity.DetailContentActivity;
import net.anumbrella.lkshop.ui.fragment.DetailContentFragment;
import net.anumbrella.lkshop.utils.BaseUtils;

/**
 * author：Anumbrella
 * Date：16/6/4 下午7:17
 */
public class CategorizeDetailProductViewHolder extends BaseViewHolder<ListProductContentModel> implements View.OnClickListener {


    private SimpleDraweeView simpleDraweeView;
    private CardView cardView;
    private TextView name;
    private TextView price;
    private TextView color;
    private TextView storage;
    private TextView carrieroperator;
    private TextView phoneType;
    private ListProductContentModel data;

    public CategorizeDetailProductViewHolder(ViewGroup parent) {
        super(parent, R.layout.itemview_categorize_detail_product);
        simpleDraweeView = $(R.id.categorize_detail_product_img);
        name = $(R.id.categorize_detail_product_name);
        price = $(R.id.categorize_detail_product_price);
        cardView = $(R.id.categorize_detail_product_cardview);
        color = $(R.id.categorize_phone_color);
        carrieroperator = $(R.id.categorize_phone_carrieroperator);
        storage = $(R.id.categorize_phone_stroage);
        phoneType = $(R.id.categorize_phone_type);
    }


    @Override
    public void setData(ListProductContentModel data) {
        super.setData(data);
        this.data = data;
        simpleDraweeView.setImageURI(Uri.parse(data.getImageUrl()));
        name.setText(data.getTitle());
        price.setText("￥ " + data.getPrice());
        color.setText(BaseUtils.transform("color", String.valueOf(data.getColor())));
        carrieroperator.setText(BaseUtils.transform("carrieroperator", String.valueOf(data.getCarrieroperator())));
        storage.setText(BaseUtils.transform("storage", String.valueOf(data.getStorage())));
        phoneType.setText(BaseUtils.transform("phoneType", String.valueOf(data.getType())));
        cardView.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.categorize_detail_product_cardview:
                intent.putExtra(DetailContentFragment.ARG_ITEM_INFO_LISTPRODUCT, data);
                break;
        }
        intent.setClass(getContext(), DetailContentActivity.class);
        getContext().startActivity(intent);

    }
}
