package net.anumbrella.lkshop.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import net.anumbrella.lkshop.model.bean.ListProductContentModel;
import net.anumbrella.lkshop.ui.viewholder.ShoppingDataViewHolder;

import java.util.HashMap;

/**
 * author：Anumbrella
 * Date：16/6/4 下午9:57
 */
public class ShoppingDataAdapter extends RecyclerArrayAdapter<ListProductContentModel> {

    private static HashMap<Integer, Boolean> isCheckList = new HashMap<>();

    public ShoppingDataAdapter(Context context) {
        super(context);
    }

    private static boolean display = false;


    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ShoppingDataViewHolder(parent);
    }


    @Override
    public int getPosition(ListProductContentModel item) {
        return super.getPosition(item);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
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

    public static void setCheckBoolean(int pid, boolean bool) {
        isCheckList.put(pid, bool);
    }

    public static HashMap<Integer, Boolean> getIsCheckList() {
        return isCheckList;
    }

    public static boolean getIsCheck(int pid) {
        return isCheckList.get(pid);
    }

    public TipSpanSizeLookUp obtainTipSpanSizeLookUp() {
        return new TipSpanSizeLookUp();
    }

    public static boolean getDisplay() {
        return display;
    }

    public static void setDisplay(boolean isDisplay) {
        display = isDisplay;
    }
}
