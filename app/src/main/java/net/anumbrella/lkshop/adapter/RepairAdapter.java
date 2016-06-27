package net.anumbrella.lkshop.adapter;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import net.anumbrella.lkshop.model.bean.RepairDataModel;
import net.anumbrella.lkshop.ui.activity.MainActivity;
import net.anumbrella.lkshop.ui.viewholder.RepairViewHolder;

/**
 * author：Anumbrella
 * Date：16/5/31 下午10:54
 */
public class RepairAdapter extends RecyclerArrayAdapter<RepairDataModel> {

    private FloatingActionButton fab;

    public RepairAdapter(Context context) {
        super(context);
        this.fab = MainActivity.floatBtn;

    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new RepairViewHolder(parent);
    }

    @Override
    public int getViewType(int position) {

        if (position == getCount() - 1) {
            if (fab.isShown()) {
                fab.hide();
            }
        }

        return super.getViewType(position);
    }

    public class TipSpanSizeLookUp extends GridSpanSizeLookup {

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
