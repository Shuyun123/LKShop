package net.anumbrella.lkshop.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jude.utils.JUtils;
import com.umeng.message.PushAgent;

import net.anumbrella.lkshop.R;
import net.anumbrella.lkshop.utils.UpdateUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author：Anumbrella
 * Date：16/6/24 下午6:12
 */
public class SettingActivity extends AppCompatActivity {


    @BindView(R.id.setting_toolbar)
    Toolbar toolbar;

    @BindView(R.id.setting_update)
    LinearLayout updateSetting;

    @BindView(R.id.setting_theme)
    LinearLayout themeSetting;


    @BindView(R.id.setting_switch)
    SwitchCompat switchCompatSetting;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        if (JUtils.getSharedPreference().getBoolean("shouldPush", true)) {
            switchCompatSetting.setChecked(true);
        } else {
            switchCompatSetting.setChecked(false);
        }
        toolbar.setTitle("系统设置");
        setToolbar(toolbar);
    }

    @OnClick({R.id.setting_update, R.id.setting_theme, R.id.setting_switch})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.setting_update:
                //手动更新
                int hand = 1;
                UpdateUtils.init(this).getAppInfo(hand);
                break;
            case R.id.setting_theme:
                Toast.makeText(SettingActivity.this,"下一版本退出",Toast.LENGTH_SHORT).show();
                break;
            case R.id.setting_switch:
                PushAgent mPushAgent = PushAgent.getInstance(this);
                if (!switchCompatSetting.isChecked()) {
                    mPushAgent.disable();
                    JUtils.Toast("已关闭接收消息推送功能");
                    JUtils.getSharedPreference().edit().putBoolean("shouldPush", false).commit();
                } else {
                    mPushAgent.enable();
                    JUtils.Toast("已开启接收消息推送功能");
                    JUtils.getSharedPreference().edit().putBoolean("shouldPush", true).commit();
                }
                break;
        }
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


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
