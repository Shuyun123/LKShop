package net.anumbrella.lkshop.utils;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.jude.utils.JUtils;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.Holder;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.ViewHolder;

import net.anumbrella.lkshop.R;
import net.anumbrella.lkshop.model.UpdateAppModel;
import net.anumbrella.lkshop.widget.DownloadProgressHandler;
import net.anumbrella.lkshop.widget.ProgressHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * author：Anumbrella
 * Date：16/7/6 下午10:17
 */
public class UpdateUtils {


    private static Context mContext;

    private  String fileName;

    private  String path;

    private static UpdateUtils singleton;


    public static UpdateUtils init(Context context) {
        mContext = context;
        if (singleton == null) {
            synchronized (UpdateUtils.class) {
                singleton = new UpdateUtils();
            }
        }
        return singleton;
    }

    public void getAppInfo(final int updateType) {
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
                        if (updateType == 1) {
                            android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(mContext);
                            builder.setTitle("检查更新");
                            builder.setMessage("已是最新版本~");
                            builder.setPositiveButton("确定", null);
                            builder.show();
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                JUtils.Toast("网络不给力");
            }
        });
    }

    /**
     * 更新App
     */
    public  void updateApp(final String versionCode, String versionName, String appPath, String updateContent) {


        final ProgressDialog dialog = new ProgressDialog(mContext);
        dialog.setProgressNumberFormat("%1d KB/%2d KB");
        dialog.setTitle("下载");
        dialog.setMessage("正在下载，请稍后...");
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setCancelable(true);
        dialog.show();


        ProgressHelper.setProgressHandler(new DownloadProgressHandler() {
            @Override
            protected void onProgress(long bytesRead, long contentLength, boolean done) {
                dialog.setMax((int) (contentLength / 1024));
                dialog.setProgress((int) (bytesRead / 1024));
                if (done) {
                    dialog.dismiss();
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
                }finally {
                    installApk(null);
                }

            }

            @Override
            public void onFailure(Throwable t) {
                JUtils.Toast("网络不给力");
            }
        }, appPath);
    }

    public  void installApk(String filePath) {
        if (filePath == null) {
            filePath = Environment.getExternalStorageDirectory() + "/LKShop/Apk/"+fileName;
        }
        File file = new File(filePath);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        mContext.startActivity(intent);
    }

    public void createDialog(final String versionCode, final String versionName, final String appPath, final String updateContent) {

        View rootView = LayoutInflater.from(mContext).inflate(R.layout.setting_update_dialog, null);
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
                            android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(mContext);
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
                        view.setBackground(mContext.getResources().getDrawable(R.color.textColor_gray));
                        break;
                    case R.id.update_cancel:
                        dialog.dismiss();
                        view.setBackground(mContext.getResources().getDrawable(R.color.textColor_gray));
                        break;

                }

            }
        };

        DialogPlus dialogPlus = DialogPlus.newDialog(mContext)
                .setContentHolder(holder)
                .setHeader(R.layout.update_app_dialog_header)
                .setGravity(Gravity.CENTER)
                .setCancelable(true)
                .setOnClickListener(clickListener)
                .create();
        dialogPlus.show();

    }


}
