package net.anumbrella.lkshop.ui.activity;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jude.utils.JUtils;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.Holder;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.ViewHolder;
import com.umeng.message.PushAgent;

import net.anumbrella.lkshop.R;
import net.anumbrella.lkshop.model.UpdateAppModel;
import net.anumbrella.lkshop.utils.BaseUtils;
import net.anumbrella.lkshop.widget.DownloadProgressHandler;
import net.anumbrella.lkshop.widget.ProgressHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * author：Anumbrella
 * Date：16/6/24 下午6:12
 */
public class SettingActivity extends AppCompatActivity {

    private String fileName;

    private String path;

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
                getAppInfo();
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


    private void getAppInfo() {
        UpdateAppModel.getUpdateAppInfo(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Response<ResponseBody> response) {
                try {
                    String resultStr = response.body().string().toString();
                    JSONObject jsonObj = new JSONObject(resultStr);
                    String appPath = jsonObj.getString("appPath");
                    String versionCode = jsonObj.getString("versionCode");
                    String versionName = jsonObj.getString("versionName");
                    String updateContent = jsonObj.getString("updateContent");
                    int nowVersionCode = JUtils.getAppVersionCode();
                    if (Integer.parseInt(versionCode) > nowVersionCode) {
                        createDialog(versionCode, versionName, appPath, updateContent);
                    } else {
                        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(SettingActivity.this);
                        builder.setTitle("检查更新");
                        builder.setMessage("已是最新版本~");
                        builder.setPositiveButton("确定", null);
                        builder.show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(SettingActivity.this, "网络不给力", Toast.LENGTH_SHORT).show();

            }
        });
    }

    /**
     * 更新App
     */
    private void updateApp(final String versionCode, String versionName, String appPath, String updateContent) {


        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setProgressNumberFormat("%1d KB/%2d KB");
        dialog.setTitle("下载");
        dialog.setMessage("正在下载，请稍后...");
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setCancelable(false);
        dialog.show();


        ProgressHelper.setProgressHandler(new DownloadProgressHandler() {
            @Override
            protected void onProgress(long bytesRead, long contentLength, boolean done) {
                dialog.setMax((int) (contentLength / 1024));
                dialog.setProgress((int) (bytesRead / 1024));
                if (done) {
                    dialog.dismiss();
                    installApk(null);
                }

            }
        });

        UpdateAppModel.updateApp(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Response<ResponseBody> response) {

                try {
                    path = Environment.getExternalStorageDirectory() + "/LKShop/Apk/";
                    InputStream is = response.body().byteStream();
                    File dirFile = new File(path);
                    if (!dirFile.exists()) {
                        dirFile.mkdir();
                    }
                    fileName = BaseUtils.Md5(versionCode) + ".apk";
                    File file = new File(path, fileName);
                    FileOutputStream fos = new FileOutputStream(file);
                    BufferedInputStream bis = new BufferedInputStream(is);
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = bis.read(buffer)) != -1) {
                        fos.write(buffer, 0, len);
                        fos.flush();
                    }
                    fos.close();
                    bis.close();
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(SettingActivity.this, "网络不给力", Toast.LENGTH_SHORT).show();
            }
        }, appPath);


    }

    private void installApk(String filePath) {
        if (filePath == null) {
            filePath = fileName;
        }
        File file = new File(filePath);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        startActivity(intent);
    }

    private void createDialog(final String versionCode, final String versionName, final String appPath, final String updateContent) {

        View rootView = LayoutInflater.from(SettingActivity.this).inflate(R.layout.setting_update_dialog, null);
        TextView update_content = (TextView) rootView.findViewById(R.id.update_content);
        TextView update_tip = (TextView) rootView.findViewById(R.id.update_tip);
        update_content.setText(updateContent);
        update_tip.setText("最新版本:" + versionName);
        Holder holder = new ViewHolder(rootView);
        OnClickListener clickListener = new OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(DialogPlus dialog, View view) {
                switch (view.getId()) {
                    case R.id.update_now:
                        dialog.dismiss();
                        final String filePath = Environment.getExternalStorageDirectory() + "/LKShop/Apk/" + BaseUtils.Md5(versionCode) + ".apk";
                        File file = new File(filePath);
                        if (!file.exists()) {
                            updateApp(versionCode, versionName, appPath, updateContent);
                        } else {
                            android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(SettingActivity.this);
                            builder.setTitle("检查更新");
                            builder.setMessage("最新版已经下载，是否安装？");
                            builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    installApk(filePath);
                                }
                            });
                            builder.setNegativeButton("取消", null);
                            builder.show();

                        }
                        view.setBackground(getResources().getDrawable(R.color.textColor_gray));
                        break;
                    case R.id.update_cancel:
                        dialog.dismiss();
                        view.setBackground(getResources().getDrawable(R.color.textColor_gray));
                        break;

                }

            }
        };


        DialogPlus dialogPlus = DialogPlus.newDialog(this)
                .setContentHolder(holder)
                .setHeader(R.layout.update_app_dialog_header)
                .setGravity(Gravity.CENTER)
                .setExpanded(true, 1200)
                .setCancelable(true)
                .setOnClickListener(clickListener)
                .create();
        dialogPlus.show();

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
