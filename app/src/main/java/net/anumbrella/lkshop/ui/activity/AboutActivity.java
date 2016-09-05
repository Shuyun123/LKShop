package net.anumbrella.lkshop.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jude.utils.JUtils;

import net.anumbrella.lkshop.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author：Anumbrella
 * Date：16/6/25 下午10:44
 */
public class AboutActivity extends BaseThemeSettingActivity {


    @BindView(R.id.about_toolbar)
    Toolbar toolbar;


    @BindView(R.id.about_app)
    LinearLayout aboutApp;


    @BindView(R.id.about_app_version)
    TextView aboutAppVersion;


    @BindView(R.id.about_github)
    LinearLayout aboutGithub;

    @BindView(R.id.about_me)
    LinearLayout aboutMe;

    @BindView(R.id.setting_app_version)
    LinearLayout appVersion;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        toolbar.setTitle("关于");
        setToolbar(toolbar);
        aboutAppVersion.setText("version:" + JUtils.getAppVersionName());

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


    @OnClick({R.id.about_app, R.id.about_github, R.id.about_me, R.id.setting_app_version})
    public void click(View view) {

        Bundle extras = new Bundle();
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(AboutActivity.this);
        switch (view.getId()) {
            case R.id.about_app:
                builder.setTitle("洛克商城");
                String describeApp = getResources().getString(R.string.describe_app);
                builder.setMessage(describeApp);
                builder.show();
                break;
            case R.id.setting_app_version:
                builder.setTitle("洛克商城");
                String appVersion = "当前版本: " + JUtils.getAppVersionName();
                builder.setMessage(appVersion);
                builder.show();
                break;
            case R.id.about_me:
                extras.putString(BaseWebActivity.BUNDLE_KEY_URL, "https://www.anumbrella.net");
                extras.putString(BaseWebActivity.BUNDLE_KEY_TITLE, "关于我");
                Intent intent = new Intent(this, BaseWebActivity.class);
                intent.putExtras(extras);
                startActivity(intent);
                break;
            case R.id.about_github:
                extras.putString(BaseWebActivity.BUNDLE_KEY_URL, "https://github.com/Shuyun123/LKShop");
                extras.putString(BaseWebActivity.BUNDLE_KEY_TITLE, "Github");
                Intent intentGithub = new Intent(this, BaseWebActivity.class);
                intentGithub.putExtras(extras);
                startActivity(intentGithub);
                break;
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }


}
