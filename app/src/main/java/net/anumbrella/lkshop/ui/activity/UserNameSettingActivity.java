package net.anumbrella.lkshop.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.jude.utils.JUtils;
import com.umeng.message.PushAgent;

import net.anumbrella.customedittext.MyEditText;
import net.anumbrella.lkshop.R;
import net.anumbrella.lkshop.model.UserSettingModel;
import net.anumbrella.lkshop.model.bean.LocalUserDataModel;
import net.anumbrella.lkshop.utils.BaseUtils;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * author：Anumbrella
 * Date：16/6/21 下午11:12
 */
public class UserNameSettingActivity extends BaseThemeSettingActivity {


    @BindView(R.id.edit_name_text)
    MyEditText editText;


    @BindView(R.id.user_name_setting_toolbar)
    Toolbar toolbar;

    @BindView(R.id.save_edittext)
    TextView saveEidtText;

    private int type;

    private int uid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_name_setting);
        ButterKnife.bind(this);
        PushAgent.getInstance(this).onAppStart();
        if (getIntent().getIntExtra("type", -1) != -1) {
            type = getIntent().getIntExtra("type", -1);
            if (getIntent().getIntExtra("type", -1) == 1) {
                toolbar.setTitle("昵称");
            } else {
                toolbar.setTitle("签名");
            }
        }
        uid = BaseUtils.readLocalUser(UserNameSettingActivity.this).getUid();
        setToolbar(toolbar);
        if (getIntent().getStringExtra("setting") != null) {
            editText.setText(getIntent().getStringExtra("setting"));
        }
    }


    @OnClick({R.id.save_edittext})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.save_edittext:
                String result = editText.getText().toString().trim();
                String signName = BaseUtils.readLocalUser(UserNameSettingActivity.this).getSignName();
                String userName = BaseUtils.readLocalUser(UserNameSettingActivity.this).getUserName();
                if (type > 0) {
                    if (type == 1) {
                        if (!result.equals(userName)) {
                            if (TextUtils.isEmpty(result)) {
                                Toast.makeText(UserNameSettingActivity.this, "内容不能为空", Toast.LENGTH_SHORT).show();
                            } else {
                                updateValue(result);
                            }
                        }else{
                            JUtils.Toast("新的昵称不能和之前相同");
                        }

                    } else if (type == 2) {
                        if (!result.equals(signName)) {
                            if (TextUtils.isEmpty(result)) {
                                Toast.makeText(UserNameSettingActivity.this, "内容不能为空", Toast.LENGTH_SHORT).show();
                            } else {
                                updateValue(result);
                            }
                        }else{
                            JUtils.Toast("新的昵称不能和之前相同");
                        }
                    }
                }

                break;
        }
    }

    private void updateValue(final String result) {
        if (result != null && type > 0 && uid > 0) {
            UserSettingModel.updateUserSettingName(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Response<ResponseBody> response) {
                    try {
                        String resultResponse = response.body().string().toString();
                        if (resultResponse.equals("0200")) {
                            LocalUserDataModel data = BaseUtils.readLocalUser(UserNameSettingActivity.this);
                            if (type == 1) {
                                data.setUserName(result);
                            } else if (type == 2) {
                                data.setSignName(result);
                            }
                            BaseUtils.saveLocalUser(UserNameSettingActivity.this, data);
                            Toast.makeText(UserNameSettingActivity.this, "更改成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(UserNameSettingActivity.this, "更改失败", Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Throwable t) {
                    Toast.makeText(UserNameSettingActivity.this, "网络不给力", Toast.LENGTH_SHORT).show();

                }
            }, result, String.valueOf(type), String.valueOf(uid));

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

}
