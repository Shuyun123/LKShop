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
import net.anumbrella.lkshop.model.bean.RecommendContentModel;
import net.anumbrella.lkshop.ui.activity.DetailContentActivity;
import net.anumbrella.lkshop.ui.fragment.DetailContentFragment;

/**
 * author：Anumbrella
 * Date：16/5/25 下午9:54
 */
public class RecommendContentViewHolder extends BaseViewHolder<RecommendContentModel> implements View.OnClickListener {

    private SimpleDraweeView simpleDraweeView;
    private CardView cardView;
    private TextView title;
    private TextView content;
    private RecommendContentModel data;

    public RecommendContentViewHolder(ViewGroup parent) {
        super(parent, R.layout.itemview_recommend_content);
        simpleDraweeView = $(R.id.recomend_img);
        title = $(R.id.recommend_title);
        content = $(R.id.recommend_content);
        cardView = $(R.id.recommend_cardview);
    }

    @Override
    public void setData(RecommendContentModel data) {
        super.setData(data);
        this.data = data;
        simpleDraweeView.setImageURI(Uri.parse(data.getImageUrl()));
        title.setText(data.getTitle());
        content.setText("￥ " + data.getPrice());
        cardView.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.recommend_cardview:
                intent.putExtra(DetailContentFragment.ARG_ITEM_INFO_RECOMMEND, data);
                break;
        }

        intent.setClass(getContext(), DetailContentActivity.class);
        getContext().startActivity(intent);
    }
}
