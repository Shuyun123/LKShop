package net.anumbrella.lkshop.ui.viewholder;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import net.anumbrella.lkshop.R;
import net.anumbrella.lkshop.model.bean.ListProductContentModel;
import net.anumbrella.lkshop.ui.activity.CollectActivity;
import net.anumbrella.lkshop.ui.activity.DetailContentActivity;
import net.anumbrella.lkshop.ui.fragment.DetailContentFragment;

/**
 * author：Anumbrella
 * Date：16/6/10 下午11:21
 */
public class CollectDataViewHolder extends BaseViewHolder<ListProductContentModel> implements View.OnClickListener {


    private SimpleDraweeView simpleDraweeView;
    private CardView cardView;
    private TextView title;
    private TextView price;
    private ListProductContentModel data;
    private ImageView collectIcon;
    private static CollectActivity object = new CollectActivity();


    public CollectDataViewHolder(ViewGroup parent) {
        super(parent, R.layout.itemview_collect_data);
        simpleDraweeView = $(R.id.collect_product_img);
        title = $(R.id.collect_product_title);
        price = $(R.id.collect_product_price);
        cardView = $(R.id.collect_product_cardview);
        collectIcon = $(R.id.collectIcon);
    }

    @Override
    public void setData(ListProductContentModel contentData) {
        super.setData(contentData);
        this.data = contentData;
        simpleDraweeView.setImageURI(Uri.parse(data.getImageUrl()));
        title.setText(data.getTitle());
        price.setText("￥ " + data.getPrice());
        simpleDraweeView.setOnClickListener(this);
        collectIcon.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.collect_product_img:
                Intent intent = new Intent();
                intent.putExtra(DetailContentFragment.ARG_ITEM_INFO_LISTPRODUCT, data);
                intent.setClass(getContext(), DetailContentActivity.class);
                getContext().startActivity(intent);
                break;
            case R.id.collectIcon:
                object.deleteCollect(data);
                break;
        }
    }
}
