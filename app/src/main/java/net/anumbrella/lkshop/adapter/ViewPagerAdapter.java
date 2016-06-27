package net.anumbrella.lkshop.adapter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

/**
 * author：Anumbrella
 * Date：16/5/24 下午5:02
 */
public class ViewPagerAdapter extends PagerAdapter {

    private ArrayList<SimpleDraweeView> list;


    public ViewPagerAdapter(ArrayList<SimpleDraweeView> views) {
        this.list = views;
    }


    @Override
    public int getCount() {
        if (list.size() > 0) {
            return list.size();
        }
        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }



    /**
     * 销毁position位置的界面
     *
     * @param container
     * @param position
     * @param object
     */
    @Override
    public void destroyItem(View container, int position, Object object) {
        ((ViewPager) container).removeView(list.get(position));

    }


    /**
     * 初始化position位置的界面
     *
     * @param container
     * @param position
     * @return
     */
    public Object instantiateItem(View container, int position) {
        ((ViewPager) container).addView(list.get(position), 0);
        return list.get(position);
    }

}
