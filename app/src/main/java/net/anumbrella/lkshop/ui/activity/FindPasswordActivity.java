package net.anumbrella.lkshop.ui.activity;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.umeng.message.PushAgent;

import net.anumbrella.customedittext.FloatLabelView;
import net.anumbrella.lkshop.R;
import net.anumbrella.lkshop.model.FindPassDataModel;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * author：Anumbrella
 * Date：16/5/29 上午9:03
 */
public class FindPasswordActivity extends BaseThemeSettingActivity {


    /**
     * 短信验证码等待时间
     */
    private int time = 60;

    private String phone;

    private String code;

    private String password;

    private boolean flag = true;

    private ProgressDialog mDialog;

    @BindView(R.id.btn_back)
    Button btn_back;


    @BindView(R.id.find_pass_phone)
    FloatLabelView passPhone;


    @BindView(R.id.find_pass_code)
    FloatLabelView passCode;


    @BindView(R.id.send_code)
    Button sendCode;


    @BindView(R.id.find_pass_new)
    FloatLabelView passNew;


    @BindView(R.id.find_pass_upadate_pass)
    Button updatePass;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);
        ButterKnife.bind(this);
        PushAgent.getInstance(this).onAppStart();
        passNew.getEditText().setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        initSMS();
        mDialog = new ProgressDialog(this);
        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mDialog.setMessage("请稍等");
        mDialog.setIndeterminate(false);
        // 设置ProgressDialog 是否可以按退回按键取消
        mDialog.setCancelable(false);

    }


    @Override
    protected void onResume() {
        initSMS();
        super.onResume();
    }

    private void initSMS() {
        EventHandler eventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }
        };
        // 注册回调监听接口
        SMSSDK.registerEventHandler(eventHandler);
    }


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;
            if (result == SMSSDK.RESULT_COMPLETE) {
                //短信注册成功后，返回MainActivity,然后提示新好友
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//提交验证码成功,验证通过
                    handlerText.sendEmptyMessage(2);
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {//服务器验证码发送成功
                    Toast.makeText(getApplicationContext(), "验证码已经发送", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (flag) {
                    Toast.makeText(FindPasswordActivity.this, "验证码获取失败，请重新获取", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(FindPasswordActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                }

            }

        }

    };


    @OnClick({R.id.btn_back, R.id.find_pass_upadate_pass, R.id.send_code})
    public void clickBtn(View view) {
        phone = passPhone.getEditText().getText().toString().trim();
        code = passCode.getEditText().getText().toString().trim();
        password = passNew.getEditText().getText().toString().trim();
        switch (view.getId()) {
            case R.id.btn_back:
                finish();
                break;
            case R.id.find_pass_upadate_pass:
                if (!TextUtils.isEmpty(phone)) {
                    if (TextUtils.isEmpty(code)) {
                        Toast.makeText(this, "请输入验证码", Toast.LENGTH_SHORT).show();
                    } else {
                        if (code.length() == 4) {
                            if (TextUtils.isEmpty(password)) {
                                Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
                            } else {
                                //对验证码进行校验
                                SMSSDK.submitVerificationCode("86", phone, code);
                                flag = false;
                            }
                        } else {
                            Toast.makeText(this, "验证码格式不正确", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.send_code:
                //发送验证码
                if (!TextUtils.isEmpty(phone)) {
                    if (checkPhoneNumber(phone)) {
                        checkPhoneExist();
                    } else {
                        Toast.makeText(this, "手机号格式不正确", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    private void checkPhoneExist() {
        FindPassDataModel.checkPhoneExist(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Response<ResponseBody> response) {
                try {
                    String resultResponse = response.body().string().toString();
                    if (resultResponse.equals("0200")) {
                        SMSSDK.getVerificationCode("86", phone);
                        handlerText.sendEmptyMessageDelayed(1, 1000);
                    } else {
                        Toast.makeText(FindPasswordActivity.this, "该手机号还没注册", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(FindPasswordActivity.this, "网络不给力", Toast.LENGTH_SHORT).show();
            }
        }, phone);
    }


    Handler handlerText = new Handler() {
        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                if (time > 0) {
                    sendCode.setClickable(false);
                    sendCode.setText("还剩" + time + "秒");
                    sendCode.setBackground(getResources().getDrawable(R.drawable.send_code_button_click_bg));
                    sendCode.setTextColor(getResources().getColor(R.color.milk_white));
                    time--;
                    handlerText.sendEmptyMessageDelayed(1, 1000);
                } else {
                    sendCode.setText("发送验证码");
                    sendCode.setClickable(true);
                    sendCode.setBackground(getResources().getDrawable(R.drawable.button_register));
                    sendCode.setTextColor(getResources().getColor(R.color.login_btn_text_color));
                    time = 60;
                    flag = false;
                }
            } else if (msg.what == 2) {
                mDialog.show();
                updatePassword();
            }
        }

    };

    private void updatePassword() {
        FindPassDataModel.updatePassword(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Response<ResponseBody> response) {
                try {
                    String resultResponse = response.body().string().toString();
                    if (resultResponse.equals("0200")) {
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(FindPasswordActivity.this, "更改密码成功", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }, 1000);

                    } else {
                        Toast.makeText(FindPasswordActivity.this, "更改密码失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                mDialog.hide();
                Toast.makeText(FindPasswordActivity.this, "网络不给力", Toast.LENGTH_SHORT).show();
            }
        }, phone, password);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }

    public static boolean checkPhoneNumber(String mobiles) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$"); // 验证手机号
        m = p.matcher(mobiles);
        b = m.matches();
        return b;
    }

}

