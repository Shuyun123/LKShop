package net.anumbrella.lkshop.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import net.anumbrella.lkshop.R;
import net.anumbrella.lkshop.adapter.CategorizeProductAdapter;
import net.anumbrella.lkshop.config.Config;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author：Anumbrella
 * Date：16/5/24 下午8:04
 */
public class CategorizeFragment extends Fragment {

    private LayoutInflater linflater;

    private View[] shopViews;

    private String[] listMenus;

    private CategorizeProductAdapter categorizeProductAdapter;

    private TextView[] listMenuTextViews;

    private Bundle savedState;


    /**
     * 默认的ViewPager选中的项
     */
    private int currentItem = 0;

    @BindView(R.id.goods)
    ViewPager viewPager;

    @BindView(R.id.tools_scrollView)
    ScrollView tools_scrollView;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categorize, container, false);
        ButterKnife.bind(this, view);
        setHasOptionsMenu(true);
        linflater = LayoutInflater.from(getContext());
        initTools(view);
        initViewPager();
        return view;
    }


    private void initViewPager() {
        // 由于使用了支持包所以最终必须确保所有的导入包都是来自支持包
        categorizeProductAdapter = new CategorizeProductAdapter(getChildFragmentManager(), listMenus);
        viewPager.setAdapter(categorizeProductAdapter);
        // 为ViewPager设置页面变化的监控
        viewPager.addOnPageChangeListener(onPageChangeListener);
    }

    ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            if (viewPager.getCurrentItem() != position) {
                viewPager.setCurrentItem(position);
            }
            // 通过ViewPager监听点击字体颜色和背景的改变
            if (currentItem != position) {
                changeTextColor(position);
                changeTextLocation(position);
            }
            currentItem = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {


        }
    };

    /**
     * 初始化左边目录
     */
    private void initTools(View layoutView) {
        listMenus = Config.categorizeTools;
        shopViews = new View[Config.categorizeToolsImg.length];
        listMenuTextViews = new TextView[listMenus.length];
        LinearLayout toolsLayout = (LinearLayout) layoutView.findViewById(R.id.tools);
        for (int i = 0; i < listMenus.length; i++) {
            View view = linflater.inflate(R.layout.itemview_categorize_listmenus, null);
            // 给每个View设定唯一标识
            view.setId(i);
            // 给每个view添加点击监控事件
            view.setOnClickListener(ListItemMenusClickListener);
            // 获取到左侧栏的的TextView的组件
            TextView textView = (TextView) view.findViewById(R.id.textView);
            textView.setText(listMenus[i]);
            toolsLayout.addView(view);
            // 传入的是地址不是复制的值
            listMenuTextViews[i] = textView;
            shopViews[i] = view;
        }
        changeTextColor(0);
    }

    private void changeTextColor(int position) {
        for (int i = 0; i < listMenus.length; i++) {
            if (position != i) {
                listMenuTextViews[i].setBackgroundColor(0x00000000);
                listMenuTextViews[i].setTextColor(0xFF000000);
            }
        }
        listMenuTextViews[position].setBackgroundColor(0xFFFFFFFF);
        listMenuTextViews[position].setTextColor(0xFFFF5D5E);
    }

    private void changeTextLocation(int clickPosition) {
        int y = (shopViews[clickPosition].getTop());
        // 如果滑动条可以滑动的情况下就把点击的视图移动到顶部
        tools_scrollView.smoothScrollTo(0, y);

    }

    View.OnClickListener ListItemMenusClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            viewPager.setCurrentItem(v.getId());
        }
    };


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        restoreState();
        changeTextColor(currentItem);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        savedState = saveState();
    }


    private void restoreState() {
        if (savedState != null) {
            currentItem = savedState.getInt("index");
        }
    }

    private Bundle saveState() {
        Bundle state = new Bundle();
        state.putInt("index", currentItem);
        return state;
    }
}
