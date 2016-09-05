package net.anumbrella.lkshop.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.utils.JActivityManager;
import com.jude.utils.JUtils;
import com.search.material.library.MaterialSearchView;
import com.umeng.message.PushAgent;

import net.anumbrella.lkshop.R;
import net.anumbrella.lkshop.adapter.MyTabFragmentAdapter;
import net.anumbrella.lkshop.adapter.NavDrawerListAdapter;
import net.anumbrella.lkshop.config.Config;
import net.anumbrella.lkshop.config.ShareConfig;
import net.anumbrella.lkshop.model.TabModel;
import net.anumbrella.lkshop.model.bean.LocalUserDataModel;
import net.anumbrella.lkshop.model.bean.NavDrawerItemModel;
import net.anumbrella.lkshop.ui.fragment.ListProductFragment;
import net.anumbrella.lkshop.ui.fragment.RecommendFragment;
import net.anumbrella.lkshop.ui.fragment.RepairFragment;
import net.anumbrella.lkshop.utils.BaseUtils;
import net.anumbrella.lkshop.utils.ExitUtils;
import net.anumbrella.lkshop.utils.UpdateUtils;
import net.anumbrella.lkshop.widget.MyViewPager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author：Anumbrella
 * Date：16/5/23 下午6:12
 */
public class MainActivity extends BaseThemeSettingActivity {

    protected FragmentTabHost tabHost;

    private MyTabFragmentAdapter myTabFragmentAdapter;

    private TypedArray mNavMenuIconsTypeArray;

    private TypedArray mNavMenuIconTintTypeArray;

    private ListView mDrawerMenu;

    private String[] mNavMenuTitles;

    private ArrayList<NavDrawerItemModel> mDrawerItems;

    private NavDrawerListAdapter mNavDrawerAdapter;

    public static FloatingActionButton floatBtn;

    public static MyViewPager staticViewPager;

    public static boolean selectTab = false;

    private FragmentManager fragmentManager;

    private FragmentTransaction fragmentTransaction;


    private ExitUtils exit = new ExitUtils();


    @BindView(R.id.tab_toolbar)
    Toolbar tab_toolbar;

    @BindView(R.id.tab_bar_layout)
    RelativeLayout tab_bar_layout;


    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.nav_drawer_layout)
    LinearLayout nav_drawer_layout;


    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @BindView(R.id.content_viewPager)
    MyViewPager viewPager;


    @BindView(R.id.tab_AppBarLayout)
    AppBarLayout tab_AppBarLayout;

    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;

    @BindView(R.id.search_view)
    MaterialSearchView searchView;


    @BindView(R.id.fab)
    FloatingActionButton fab;


    @BindView(R.id.user_img)
    SimpleDraweeView user_img;

    @BindView(R.id.tv_name)
    TextView userName;

    @BindView(R.id.tv_signName)
    TextView signName;

    @BindView(R.id.login_tip)
    TextView loginTip;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        tabHost = (FragmentTabHost) super.findViewById(android.R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager()
                , R.id.contentLayout);
        tabHost.getTabWidget().setDividerDrawable(null);
        initTab();
        initPush();
        initSearchView();
        setSupportActionBar(toolbar);
        setDrawerLayout(toolbar);
        initAppBarSetting();
        initMyTab();
        floatBtn = fab;
        staticViewPager = viewPager;
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        UpdateUtils.init(this).getAppInfo(0);

    }


    @Override
    protected void onResume() {
        updateUserIcon();
        updateSignName();
        updateUserName();
        super.onResume();
    }


    private void updateUserName() {
        String name = BaseUtils.readLocalUser(MainActivity.this).getUserName();
        if (!name.equals("null")) {
            userName.setText(name);
        } else {
            userName.setText("昵称");
        }
    }

    private void updateSignName() {
        String sign = BaseUtils.readLocalUser(MainActivity.this).getSignName();
        if (!sign.equals("null")) {
            signName.setText(sign);
        } else {
            signName.setText("签名~");
        }
    }

    private void updateUserIcon() {
        String img = BaseUtils.readLocalUser(MainActivity.this).getUserImg();
        if (!img.equals("null")) {
            user_img.setImageURI(Uri.parse(img));
        } else {
            user_img.setImageURI(null);
        }
        if (!checkLogin()) {
            loginTip.setVisibility(View.VISIBLE);
        } else {
            loginTip.setVisibility(View.GONE);
        }
    }

    @OnClick({R.id.fab, R.id.user_img, R.id.tv_signName, R.id.tv_name, R.id.login_tip})
    public void clickFab(View view) {
        switch (view.getId()) {
            case R.id.fab:
                goToUp(0);
                break;
            case R.id.login_tip:
            case R.id.user_img:
                if (checkLogin()) {
                    Intent intent = new Intent();
                    intent.setClass(this, UserSettingActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("startUp", "main");
                    intent.setClass(this, LoginActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.tv_signName:
                break;
            case R.id.tv_name:
                break;
        }
    }


    private boolean checkLogin() {
        boolean isLogin = BaseUtils.readLocalUser(MainActivity.this).isLogin();
        return isLogin;
    }

    /**
     * 关闭抽屉菜单
     */
    private void closeDrawer() {
        if (drawerLayout == null) {
            return;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        if (fab.isShown()) {
            fab.hide();
        }
    }

    /**
     * 初始化消息推送
     */
    private void initPush() {
        if (JUtils.getSharedPreference().getBoolean("shouldPush", true)) {
            PushAgent mPushAgent = PushAgent.getInstance(this);
            mPushAgent.onAppStart();
            mPushAgent.enable();
        }
    }


    public void goToUp(int position) {
        if (myTabFragmentAdapter.getFragment(viewPager.getCurrentItem()) != null) {
            if (viewPager.getCurrentItem() == 0) {
                ((RecommendFragment) myTabFragmentAdapter.getFragment(0)).recyclerView.scrollToPosition(position);
            } else {

                if ((myTabFragmentAdapter.getFragment(viewPager.getCurrentItem())) instanceof ListProductFragment) {
                    ((ListProductFragment) myTabFragmentAdapter.getFragment(viewPager.getCurrentItem())).recyclerView.scrollToPosition(position);
                } else {
                    ((RepairFragment) myTabFragmentAdapter.getFragment(viewPager.getCurrentItem())).recyclerView.scrollToPosition(position);
                }

            }
        }
    }


    /**
     * 初始化搜索视图
     */
    private void initSearchView() {
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Bundle bundle = new Bundle();
                bundle.putString("search", query);
                Intent intent = new Intent();
                intent.putExtra("search", bundle);
                intent.setClass(MainActivity.this, SearchActivity.class);
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    private void initMyTab() {
        tabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.white));
        myTabFragmentAdapter = new MyTabFragmentAdapter(this, getSupportFragmentManager());
        viewPager.setAdapter(myTabFragmentAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (!fab.isShown()) {
                    fab.show();
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tabLayout.setupWithViewPager(viewPager);

    }


    /**
     * 初始化AppBar的设置
     */
    private void initAppBarSetting() {
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0 && !fab.isShown()) {
                    if (selectTab) {
                        fab.hide();
                    } else {
                        fab.show();
                    }

                }

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mean_search, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    /**
     * 抽屉菜单的初始化
     */
    private void setDrawerLayout(Toolbar toolbar) {
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                selectTab = false;
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                selectTab = true;
                if (fab.isShown()) {
                    fab.hide();
                }
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        mDrawerMenu = (ListView) findViewById(R.id.left_menu);
        mNavMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
        mNavMenuIconsTypeArray = getResources().obtainTypedArray(R.array.nav_drawer_icons);
        mNavMenuIconTintTypeArray = getResources().obtainTypedArray(R.array.nav_drawer_tint);
        mDrawerItems = new ArrayList<NavDrawerItemModel>();

        for (int i = 0; i < mNavMenuTitles.length; i++) {
            mDrawerItems.add(new NavDrawerItemModel(mNavMenuTitles[i], mNavMenuIconsTypeArray.getResourceId(i, -1), mNavMenuIconTintTypeArray.getResourceId(i, -1)));
        }

        mNavMenuIconsTypeArray.recycle();

        mNavDrawerAdapter = new NavDrawerListAdapter(this, mDrawerItems);

        mDrawerMenu.setAdapter(mNavDrawerAdapter);


        mDrawerMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!BaseUtils.isEmpty(mDrawerItems, position)) {
                    NavDrawerItemModel navItem = mDrawerItems.get(position);
                    if (navItem != null) {
                        selectItem(position, navItem.getTitle());
                    }

                }
            }
        });
    }

    /**
     * 菜单点击选中项
     *
     * @param position
     * @param title
     */
    private void selectItem(int position, String title) {
        switch (position) {
            case 0:
                //首页
                tabHost.setCurrentTab(0);
                viewPager.setCurrentItem(0);
                break;
            case 1:
                //设置
                Intent settingIntent = new Intent();
                settingIntent.setClass(MainActivity.this, SettingActivity.class);
                startActivity(settingIntent);
                break;
            case 2:
                //意见反馈
                Toast.makeText(MainActivity.this, "下一版本推出", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                //分享
                openShare();
                break;
            case 4:
                //关于
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, AboutActivity.class);
                startActivity(intent);
                break;
            case 5:
                //退出
                if(checkLogin()){
                    LocalUserDataModel data = new LocalUserDataModel();
                    data.setSignName("null");
                    data.setUserImg("null");
                    data.setUserName("null");
                    data.setUid(0);
                    data.setLogin(false);
                    BaseUtils.saveLocalUser(MainActivity.this, data);
                    JUtils.Toast("已经退出");
                }
                onResume();
                break;
        }
        closeDrawer();
    }

    public void openShare() {
        ShareConfig config = new ShareConfig();
        config.init(this, this).openShare(this, false);

    }

    /**
     * 初始化Tab
     */
    private void initTab() {
        String[] tabTexts = TabModel.getTabTexts();
        for (int i = 0; i < tabTexts.length; i++) {
            TabHost.TabSpec tabSpec = tabHost.newTabSpec(tabTexts[i]).setIndicator(getTabView(i));
            tabHost.addTab(tabSpec, TabModel.getFragments()[i], null);
            tabHost.setOnTabChangedListener(new OnTabChangeListener());
            tabHost.setTag(i);
        }

    }


    /**
     * 获取tab对应的视图
     *
     * @param position
     * @return
     */
    private View getTabView(int position) {
        View view = LayoutInflater.from(this).inflate(R.layout.tabs_footer, null);
        if (position == 0) {
            (view.findViewById(R.id.ivImg)).setEnabled(true);
        } else {
            ((ImageView) view.findViewById(R.id.ivImg)).setImageResource(TabModel.getTabImgs()[position]);
            (view.findViewById(R.id.ivImg)).setEnabled(false);
        }
        return view;
    }


    private final class OnTabChangeListener implements TabHost.OnTabChangeListener {
        @Override
        public void onTabChanged(String tabId) {

            if (tabId.equals(Config.tabs[0])) {
                selectTab = false;
                setSupportActionBar(toolbar);
                tab_bar_layout.setVisibility(View.GONE);
                appBarLayout.setVisibility(View.VISIBLE);
                setDrawerLayout(toolbar);
                addFragment(viewPager.getCurrentItem());
                viewPager.setScrollble(true);
            } else {
                tab_bar_layout.setVisibility(View.VISIBLE);
                setSupportActionBar(tab_toolbar);
                setDrawerLayout(tab_toolbar);
                selectTab = true;
                deleteFragment(viewPager.getCurrentItem());
                viewPager.setScrollble(false);
                if (tabId.equals(Config.tabs[2]) || tabId.equals(Config.tabs[3])) {
                    tab_bar_layout.setVisibility(View.GONE);
                    appBarLayout.setVisibility(View.GONE);
                }
            }
            closeDrawer();
            updateTabs();
        }
    }


    public void addFragment(int index) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (index == 0) {
            fragmentTransaction.add(MyTabFragmentAdapter.getFragment(index), "RecommendFragment");
        } else {
            fragmentTransaction.add(MyTabFragmentAdapter.getFragment(index), "ListProductFragment");
        }

        fragmentTransaction.commit();
        viewPager.setOffscreenPageLimit(0);
        myTabFragmentAdapter.notifyDataSetChanged();
    }


    public void deleteFragment(int index) {
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.remove(MyTabFragmentAdapter.getFragment(index));
        fragmentTransaction.commit();
    }


    private void updateTabs() {
        TabWidget tabw = tabHost.getTabWidget();
        for (int i = 0; i < tabw.getChildCount(); i++) {
            View view = tabw.getChildAt(i);
            ImageView iv = (ImageView) view.findViewById(R.id.ivImg);
            if (i == tabHost.getCurrentTab()) {
                iv.setEnabled(true);
                ((ImageView) view.findViewById(R.id.ivImg)).setImageResource(TabModel.getTabImgs()[i]);
            } else {
                ((ImageView) view.findViewById(R.id.ivImg)).setImageResource(TabModel.getTabImgs()[i]);
                iv.setEnabled(false);
            }
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            pressAgainExit();
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    /**
     * 双击返回键离开
     */
    private void pressAgainExit() {
        if (exit.isExit()) {
            for (Activity activity : JActivityManager.getActivityStack()) {
                activity.finish();
            }
        } else {
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            exit.doExitAction();
        }
    }

}
