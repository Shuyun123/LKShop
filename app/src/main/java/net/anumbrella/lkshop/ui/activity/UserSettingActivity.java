package net.anumbrella.lkshop.ui.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.utils.JUtils;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.Holder;
import com.orhanobut.dialogplus.OnClickListener;
import com.orhanobut.dialogplus.ViewHolder;
import com.umeng.common.message.Log;
import com.umeng.message.PushAgent;

import net.anumbrella.lkshop.R;
import net.anumbrella.lkshop.model.UserSettingModel;
import net.anumbrella.lkshop.model.bean.LocalUserDataModel;
import net.anumbrella.lkshop.utils.BaseUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * author：Anumbrella
 * Date：16/6/9 下午4:25
 */
public class UserSettingActivity extends BaseThemeSettingActivity {

    private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照

    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择

    private static final int PHOTO_REQUEST_CUT = 3;// 结果

    private static final String PHOTO_FILE_NAME = "temp_photo.jpg";

    private Bitmap bitmap;

    private File tempFile;

    private int uid;

    @BindView(R.id.user_setting_toolbar)
    Toolbar user_setting_toolbar;

    @BindView(R.id.user_setting_icon)
    SimpleDraweeView userIcon;

    @BindView(R.id.user_setting_icon_right)
    ImageView iconRight;

    @BindView(R.id.user_setting_name_right)
    ImageView nameRight;

    @BindView(R.id.user_setting_signName_right)
    ImageView singleNameRight;

    @BindView(R.id.user_setting_userName)
    TextView userName;

    @BindView(R.id.user_setting_signName)
    TextView signName;


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_setting);
        ButterKnife.bind(this);
        PushAgent.getInstance(this).onAppStart();
        user_setting_toolbar.setTitle("用户设置");
        uid = BaseUtils.readLocalUser(UserSettingActivity.this).getUid();
        setToolbar(user_setting_toolbar);
        updateUserIcon();
    }

    private void updateUserIcon() {
        String img = BaseUtils.readLocalUser(UserSettingActivity.this).getUserImg();
        if (!img.equals("null")) {
            userIcon.setImageURI(Uri.parse(img));
        }
    }

    private void updateUserName() {
        String name = BaseUtils.readLocalUser(UserSettingActivity.this).getUserName();
        if (!name.equals("null")) {
            userName.setText(name);
        }
    }

    private void updateSignName() {
        String sign = BaseUtils.readLocalUser(UserSettingActivity.this).getSignName();
        if (!sign.equals("null")) {
            signName.setText(sign);
        }
    }


    @Override
    protected void onResume() {
        updateUserName();
        updateSignName();
        super.onResume();
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


    @OnClick({R.id.user_setting_icon_right, R.id.user_setting_name_right, R.id.user_setting_signName_right})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.user_setting_icon_right:
                createDialog();
                break;
            case R.id.user_setting_name_right:
                Intent userNameIntent = new Intent();
                userNameIntent.putExtra("setting", userName.getText().toString());
                userNameIntent.putExtra("type", 1);
                userNameIntent.setClass(this, UserNameSettingActivity.class);
                startActivity(userNameIntent);
                break;
            case R.id.user_setting_signName_right:
                Intent singleNameIntent = new Intent();
                singleNameIntent.putExtra("setting", signName.getText().toString());
                singleNameIntent.putExtra("type", 2);
                singleNameIntent.setClass(this, UserNameSettingActivity.class);
                startActivity(singleNameIntent);
                break;
        }


    }

    private void createDialog() {
        Holder holder = new ViewHolder(R.layout.user_icon_setting_dialog);
        OnClickListener clickListener = new OnClickListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(DialogPlus dialog, View view) {
                switch (view.getId()) {
                    case R.id.user_setting_cancel:
                        dialog.dismiss();
                        view.setBackground(getResources().getDrawable(R.color.textColor_gray));
                        break;
                    case R.id.user_setting_album:
                        fromAlubm();
                        dialog.dismiss();
                        view.setBackground(getResources().getDrawable(R.color.textColor_gray));
                        break;
                    case R.id.user_setting_photo:
                        fromCamera();
                        dialog.dismiss();
                        view.setBackground(getResources().getDrawable(R.color.textColor_gray));
                        break;

                }

            }
        };


        DialogPlus dialogPlus = DialogPlus.newDialog(this)
                .setContentHolder(holder)
                .setHeader(R.layout.user_setting_header)
                .setGravity(Gravity.CENTER)
                .setCancelable(true)
                .setOnClickListener(clickListener)
                .create();
        dialogPlus.show();

    }


    public void fromAlubm() {
        // 激活系统图库，选择一张图片
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }


    public void fromCamera() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        // 判断存储卡是否可以用，可用进行存储
        if (BaseUtils.hasSdcard()) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(new File(Environment
                            .getExternalStorageDirectory(), PHOTO_FILE_NAME)));
        }
        startActivityForResult(intent, PHOTO_REQUEST_CAMERA);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PHOTO_REQUEST_GALLERY) {
            if (data != null) {
                // 得到图片的全路径
                Uri uri = data.getData();
                crop(uri);
            }

        } else if (requestCode == PHOTO_REQUEST_CAMERA) {
            if (BaseUtils.hasSdcard()) {
                tempFile = new File(Environment.getExternalStorageDirectory(), PHOTO_FILE_NAME);
                if (tempFile.exists()) {
                    crop(Uri.fromFile(tempFile));
                } else {
                    JUtils.Toast("取消拍照");
                }
            } else {
                Toast.makeText(UserSettingActivity.this, "未找到存储卡，无法存储照片！", Toast.LENGTH_SHORT).show();
            }

        } else if (requestCode == PHOTO_REQUEST_CUT) {
            try {
                if (data != null) {
                    if (data.getParcelableExtra("data") != null) {
                        bitmap = data.getParcelableExtra("data");
                    }
                    if (tempFile != null) {
                        tempFile.delete();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            uploadPricture();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    private void uploadPricture() {

        if (uid > 0) {
            RequestBody userUid = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(uid));
            Map<String, RequestBody> map = new HashMap<>();
            map.put("uid", userUid);
            if (bitmap != null) {
                RequestBody fileBody = null;
                File tempfile = null;
                try {
                    tempfile = saveFile(bitmap);
                    fileBody = RequestBody.create(MediaType.parse("image/jpg"), tempfile);
                    map.put("image\"; filename=\"" + tempfile.getName(), fileBody);
                } catch (IOException e) {
                    e.printStackTrace();
                }


                final File finalTempfile = tempfile;
                UserSettingModel.uploadPricture(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Response<ResponseBody> response) {
                        try {
                            String resultStr = response.body().string().toString();
                            JSONObject jsonObj = new JSONObject(resultStr);
                            String iconImg = jsonObj.getString("iconImg");
                            String result = jsonObj.getString("result");
                            if (result.equals("0200")) {
                                //保存用户头像图片
                                LocalUserDataModel data = BaseUtils.readLocalUser(UserSettingActivity.this);
                                data.setUserImg(iconImg);
                                BaseUtils.saveLocalUser(UserSettingActivity.this, data);

                                userIcon.setImageURI(Uri.parse(iconImg));
                                Toast.makeText(UserSettingActivity.this, "上传头像成功", Toast.LENGTH_SHORT).show();
                                finalTempfile.delete();
                            } else {
                                Toast.makeText(UserSettingActivity.this, "上传头像失败", Toast.LENGTH_SHORT).show();
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Throwable t) {
                        Toast.makeText(UserSettingActivity.this, "网络不给力", Toast.LENGTH_SHORT).show();
                    }
                }, map);
            }

        }

    }


    /**
     * 图片剪裁
     *
     * @param uri
     */
    private void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        // 图片格式
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);// true:不返回uri，false：返回uri
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }


    public File saveFile(Bitmap bm) throws IOException {
        String path = Environment.getExternalStorageDirectory() + "/LKShop/HeadIcon/";
        File dirFile = new File(path);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        String fileName = new Date().getTime() + ".jpg";
        File myCaptureFile = new File(path + fileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
        bos.flush();
        bos.close();
        return myCaptureFile;
    }

}
