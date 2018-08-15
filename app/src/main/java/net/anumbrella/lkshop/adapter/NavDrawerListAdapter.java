package net.anumbrella.lkshop.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import net.anumbrella.lkshop.R;
import net.anumbrella.lkshop.model.bean.NavDrawerItemModel;

import java.util.List;

/**
 * author：Anumbrella
 * Date：16/5/25 下午7:19
 */
public class NavDrawerListAdapter extends BaseAdapter {

    private Context context;
    private List<NavDrawerItemModel> navDrawerItems;

    public NavDrawerListAdapter(Context context, List<NavDrawerItemModel> navDrawerItems) {
        this.context = context;
        this.navDrawerItems = navDrawerItems;
    }

    @Override
    public int getCount() {
        return navDrawerItems.size();
    }

    @Override
    public Object getItem(int position) {
        return navDrawerItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.drawer_list_item, null);
        }
        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.drawer_icon);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.drawer_title);

        imgIcon.setImageResource(navDrawerItems.get(position).getIcon());
        imgIcon.setColorFilter(context.getResources().getColor(navDrawerItems.get(position).getTint()));
        txtTitle.setText(navDrawerItems.get(position).getTitle());
        return convertView;
    }


}
