package net.anumbrella.lkshop.ui.viewholder;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import net.anumbrella.lkshop.R;
import net.anumbrella.lkshop.model.bean.RepairDataModel;
import net.anumbrella.lkshop.ui.activity.RepairContentActivity;

/**
 * author：Anumbrella
 * Date：16/5/31 下午11:15
 */
public class RepairViewHolder extends BaseViewHolder<RepairDataModel> implements View.OnClickListener {


    private SimpleDraweeView simpleDraweeView;
    private CardView cardView;
    private TextView title;
    private RepairDataModel data;

    public RepairViewHolder(ViewGroup parent) {
        super(parent, R.layout.itemview_repair_content);
        simpleDraweeView = $(R.id.repair_img);
        title = $(R.id.repair_title);
        cardView = $(R.id.repair_cardview);
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void setData(RepairDataModel data) {
        super.setData(data);
        this.data = data;
        simpleDraweeView.setBackground(getContext().getResources().getDrawable(data.getImg()));
        title.setText(data.getTitle());
        cardView.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.repair_cardview:
                Intent intent = new Intent();
                intent.putExtra("repairType", data.getTitle());
                intent.setClass(getContext(), RepairContentActivity.class);
                getContext().startActivity(intent);
                break;
        }
    }


}


