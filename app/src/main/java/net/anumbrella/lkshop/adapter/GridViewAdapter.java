package net.anumbrella.lkshop.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import net.anumbrella.lkshop.R;
import net.anumbrella.lkshop.model.bean.ProductTypeModel;

import java.util.ArrayList;

/**
 * author：Anumbrella
 * Date：16/5/26 下午7:21
 */
public class GridViewAdapter extends BaseAdapter {

    private ProductTypeModel productType;

    private ArrayList<ProductTypeModel> listProductType;

    private Context context;

    private Hoder hoder;


    public GridViewAdapter(Context context, ArrayList<ProductTypeModel> listProductType) {
        this.context = context;
        this.listProductType = listProductType;
    }


    @Override
    public int getCount() {
        if (listProductType != null && listProductType.size() > 0) {
            return listProductType.size();
        } else {
            return 0;
        }
    }

    @Override
    public Object getItem(int position) {
        return listProductType.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = convertView.inflate(context, R.layout.itemview_gridview, null);
            hoder = new Hoder(convertView);
            convertView.setTag(hoder);
        } else {
            hoder = (Hoder) convertView.getTag();
        }
        if (listProductType != null && listProductType.size() > 0) {
            productType = listProductType.get(position);
            hoder.imageView.setBackgroundResource(productType.getIcon());
            hoder.textView.setText(productType.getTitleName());
        }
        return convertView;
    }

    private static class Hoder {
        private TextView textView;
        private ImageView imageView;
        public Hoder(View view) {
            textView = (TextView) view.findViewById(R.id.productName_gridView);
            imageView = (ImageView) view.findViewById(R.id.icon_gridView);
        }
    }
}
