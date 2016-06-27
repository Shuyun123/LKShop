package net.anumbrella.lkshop.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import net.anumbrella.lkshop.model.bean.OrderDataModel;
import net.anumbrella.lkshop.ui.viewholder.OrderAllDataViewHolder;

/**
 * author：Anumbrella
 * Date：16/6/9 下午6:36
 */
public class OrderAllAdapter extends RecyclerArrayAdapter<OrderDataModel> {
    public OrderAllAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new OrderAllDataViewHolder(parent);
    }

    public class TipSpanSizeLookUp extends RecyclerArrayAdapter.GridSpanSizeLookup {

        public TipSpanSizeLookUp() {
            //列数默认为2
            super(2);
        }

        @Override
        public int getSpanSize(int position) {
            return 2;
        }
    }

    public TipSpanSizeLookUp obtainTipSpanSizeLookUp() {
        return new TipSpanSizeLookUp();
    }


}
