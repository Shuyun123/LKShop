package net.anumbrella.lkshop.adapter;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import net.anumbrella.lkshop.model.bean.RecommendContentModel;
import net.anumbrella.lkshop.ui.activity.MainActivity;
import net.anumbrella.lkshop.ui.viewholder.RecommendContentViewHolder;
import net.anumbrella.lkshop.ui.viewholder.RecommendListViewHolder;
import net.anumbrella.lkshop.ui.viewholder.RecommendTipVewHolder;

/**
 * author：Anumbrella
 * Date：16/5/25 下午9:23
 */
public class RecommendAdapter extends RecyclerArrayAdapter<RecommendContentModel> {

    private int tip = 0;
    private int content = 1;
    private int list = 2;
    private FloatingActionButton fab;

    public RecommendAdapter(Context context) {
        super(context);
        this.fab = MainActivity.floatBtn;
    }


    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == tip) {
            return new RecommendTipVewHolder(parent);
        } else if (viewType == content) {
            return new RecommendContentViewHolder(parent);
        } else if (viewType == list) {
            return new RecommendListViewHolder(parent);
        }
        return null;
    }


    @Override
    public int getViewType(int position) {

        if (position == getCount() - 1) {
            if(fab.isShown()){
                fab.hide();
            }
        }

        if (getItem(position).isJudgeType()) {
            return tip;
        } else if(getItem(position).isListType()){
            return list;
        }else{
            return content;
        }

    }


    public class TipSpanSizeLookUp extends GridSpanSizeLookup {

        public TipSpanSizeLookUp() {
            //列数默认为2
            super(2);
        }

        @Override
        public int getSpanSize(int position) {
            //在头部和尾部默认占2格
            if (position < getHeaderCount() || position >= getCount() + getHeaderCount()) {
                return 2;
            } else {
                if (getItem(position - 1).isJudgeType() || getItem(position-1).isListType()) {
                    //该Tip item占2格
                    return 2;
                } else {
                    //默认该Content item占1格
                    return 1;
                }
            }
        }
    }

    public TipSpanSizeLookUp obtainTipSpanSizeLookUp() {
        return new TipSpanSizeLookUp();
    }

}
