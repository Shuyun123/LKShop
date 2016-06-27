package net.anumbrella.lkshop.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import net.anumbrella.lkshop.ui.fragment.CategorizeListContentFragment;

/**
 * author：Anumbrella
 * Date：16/5/26 下午7:08
 */
public class CategorizeProductAdapter extends FragmentStatePagerAdapter{


    private String[] list;

    public CategorizeProductAdapter(FragmentManager fm, String[] array) {
        super(fm);
        list = array;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new CategorizeListContentFragment();
        Bundle bundle = new Bundle();
        // 把选中的index指针传入过去
        bundle.putInt("index", position);
        // 设定在fragment当中去
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return list.length;
    }
}
