package net.anumbrella.lkshop.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.umeng.message.PushAgent;

import net.anumbrella.customedittext.FloatLabelView;
import net.anumbrella.lkshop.R;
import net.anumbrella.lkshop.api.ServiceApi;
import net.anumbrella.lkshop.http.RetrofitHttp;
import net.anumbrella.lkshop.model.bean.UserDataModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * author：Anumbrella
 * Date：16/5/28 下午8:56
 */
public class RegisterActivity extends BaseThemeSettingActivity {

    private String phone;

    private String password;

    private String password_again;

    private String userName;

    private boolean prompt = false;

    private boolean checkUpResult = true;


    private ProgressDialog mDialog;

    @BindView(R.id.btn_back)
    Button btn_back;

    @BindView(R.id.service_text)
    TextView service_text;

    @BindView(R.id.btn_ok)
    Button btn_ok;

    @BindView(R.id.register_phone)
    FloatLabelView register_phone;

    @BindView(R.id.register_username)
    FloatLabelView register_username;

    @BindView(R.id.register_password)
    FloatLabelView register_password;

    @BindView(R.id.register_password_again)
    FloatLabelView register_password_again;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        PushAgent.getInstance(this).onAppStart();
        register_password.getEditText().setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        register_password_again.getEditText().setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        mDialog = new ProgressDialog(this);
        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mDialog.setMessage("请稍等");
        mDialog.setIndeterminate(false);

        // 设置ProgressDialog 是否可以按退回按键取消
        mDialog.setCancelable(false);

    }

    private void getData() {
        phone = register_phone.getEditText().getText().toString().trim();
        password = register_password.getEditText().getText().toString().trim();
        password_again = register_password_again.getEditText().getText().toString().trim();
        userName = register_username.getEditText().getText().toString().trim();
        if (phone.equals("") && prompt) {
            Toast.makeText(this, "手机号不能为空", Toast.LENGTH_SHORT).show();
            checkUpResult = false;
            prompt = false;
        }

        if (!checkPhoneNumber(phone) && prompt) {
            Toast.makeText(this, "手机号格式不正确", Toast.LENGTH_SHORT).show();
            checkUpResult = false;
            prompt = false;
        }


        if (userName.equals("") && prompt) {
            Toast.makeText(this, "昵称不能为空", Toast.LENGTH_SHORT).show();
            checkUpResult = false;
            prompt = false;
        }

        if (password.equals("") && prompt) {
            Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
            checkUpResult = false;
            prompt = false;
        }

        if ((password_again.equals("") || !password.equals(password_again)) && prompt) {
            Toast.makeText(this, "两次输入密码不一致", Toast.LENGTH_SHORT).show();
            checkUpResult = false;
            prompt = false;
        }

        doRegister();
    }

    private void doRegister() {
        if (checkUpResult) {
            UserDataModel newUser = new UserDataModel();
            newUser.setPassword(password);
            newUser.setPhoneNumber(phone);
            newUser.setUserName(userName);
            newUser.setPassword_again(password_again);
            mDialog.show();
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            prompt(RetrofitHttp.getRetrofit(builder.build()).registerUser("register", newUser));

        }
    }

    private void prompt(Call<ResponseBody> register) {
        register.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Response<ResponseBody> response) {
                mDialog.hide();
                try {
                    String result = response.body().string().toString();
                    for (int i = 0; i < ServiceApi.RegisterApi.length; i++) {
                        if (ServiceApi.RegisterApi[i][0].equals(result)) {
                            Toast.makeText(RegisterActivity.this, ServiceApi.RegisterApi[i][1], Toast.LENGTH_SHORT).show();
                            //注册成功跳转到登录
                            if (ServiceApi.RegisterApi[i][0].equals("0200")) {
                                Intent intent = new Intent();
                                intent.setClass(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                            break;
                        }
                    }
                } catch (Exception io) {

                }
            }

            @Override
            public void onFailure(Throwable t) {
                mDialog.hide();
                Toast.makeText(RegisterActivity.this, "网络不给力", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @OnClick({R.id.service_text, R.id.btn_back, R.id.btn_ok})
    public void clickBtn(View view) {
        switch (view.getId()) {
            case R.id.service_text:
//                Intent intent = new Intent();
//                intent.setClass(this, ServiceTextActivity.class);
//                startActivity(intent);
                break;
            case R.id.btn_back:
                finish();
                break;
            case R.id.btn_ok:
                prompt = true;
                if (checkUpResult == false) {
                    checkUpResult = true;
                }
                getData();
                break;
            default:
                break;
        }
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
