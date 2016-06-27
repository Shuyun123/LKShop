package net.anumbrella.lkshop.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.jude.utils.JUtils;

import net.anumbrella.lkshop.R;
import net.anumbrella.lkshop.utils.BaseUtils;
import net.anumbrella.lkshop.widget.BrowserLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author：Anumbrella
 * Date：16/6/25 下午11:47
 */
public class BaseWebActivity extends AppCompatActivity {

    public static final String BUNDLE_KEY_URL = "BUNDLE_KEY_URL";
    public static final String BUNDLE_KEY_TITLE = "BUNDLE_KEY_TITLE";

    private String mWebUrl = null;
    private String mWebTitle = null;


    @BindView(R.id.base_web_toolbar)
    Toolbar toolbar;

    @BindView(R.id.base_web_browser_layout)
    BrowserLayout browserLayout;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_web);
        ButterKnife.bind(this);

        Bundle extras = getIntent().getExtras();
        if (null != extras) {
            mWebTitle = extras.getString(BUNDLE_KEY_TITLE);
            mWebUrl = extras.getString(BUNDLE_KEY_URL);
        }

        if (!BaseUtils.isEmpty(mWebTitle)) {
            toolbar.setTitle(mWebTitle);
        } else {
            toolbar.setTitle("网页");
        }

        if (!BaseUtils.isEmpty(mWebUrl)) {
            browserLayout.loadUrl(mWebUrl);
        } else {
            JUtils.Toast("获取URL地址失败");
        }
        setToolbar(toolbar);
        browserLayout.showBrowserController();
    }


    /**
     * 建立toolbar
     *
     * @param toolbar
     */
    public void setToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @OnClick({})
    public void click(View view) {

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


}
