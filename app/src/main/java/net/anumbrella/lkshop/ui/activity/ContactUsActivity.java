package net.anumbrella.lkshop.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import net.anumbrella.lkshop.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * author：Anumbrella
 * Date：16/6/27 下午8:13
 */
public class ContactUsActivity extends BaseThemeSettingActivity {


    @BindView(R.id.contact_us_toolbar)
    Toolbar toolbar;

    @BindView(R.id.contact_us_number1)
    TextView number1;

    @BindView(R.id.contact_us_number2)
    TextView number2;


    @BindView(R.id.contact_us_qq)
    TextView qq;


    @BindView(R.id.contact_us_weixin)
    TextView weixin;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        ButterKnife.bind(this);
        toolbar.setTitle("联系我们");
        setToolbar(toolbar);
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


    @OnClick({R.id.contact_us_number1, R.id.contact_us_number2, R.id.contact_us_qq, R.id.contact_us_weixin})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.contact_us_number1:
                String num1 = number1.getText().toString();
                Intent intent1 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + num1));
                startActivity(intent1);
                break;
            case R.id.contact_us_number2:
                String num2 = number2.getText().toString();
                Intent intent2 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + num2));
                startActivity(intent2);
                break;
            case R.id.contact_us_qq:
                String url = "mqqwpa://im/chat?chat_type=wpa&uin=" + qq.getText().toString();
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                break;
            case R.id.contact_us_weixin:
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

}
