package net.anumbrella.lkshop.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

import net.anumbrella.lkshop.model.bean.CommentProductDataModel;
import net.anumbrella.lkshop.ui.viewholder.CommentProductViewHolder;
import net.anumbrella.lkshop.ui.viewholder.NoCommentViewHolder;
import net.anumbrella.lkshop.ui.viewholder.ProductDetailViewHoler;

/**
 * author：Anumbrella
 * Date：16/6/2 上午8:52
 */
public class CommentProductAdapter extends RecyclerArrayAdapter<CommentProductDataModel> {

    private int prodectDetail = 0;
    private int commentDetail = 1;
    private int noComment = 2;


    public CommentProductAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == commentDetail) {
            return new CommentProductViewHolder(parent);
        } else if (viewType == prodectDetail) {
            return new ProductDetailViewHoler(parent);
        } else if (viewType == noComment) {
            return new NoCommentViewHolder(parent);
        }
        return null;
    }


    @Override
    public int getViewType(int position) {

        if (position == 0) {
            return prodectDetail;
        } else {
            if (getItem(1) != null) {
                return commentDetail;
            } else {
                return noComment;
            }
        }

    }

}
