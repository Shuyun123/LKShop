package net.anumbrella.lkshop.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.jude.utils.JUtils;

import net.anumbrella.lkshop.R;

/**
 * author：anumbrella
 * Date:16/9/5 上午10:00
 */
public class BaseThemeSettingActivity extends AppCompatActivity {



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCustomTheme();
    }



    private void setCustomTheme() {
        int theme = JUtils.getSharedPreference().getInt("THEME", 0);
        switch (theme) {
            case 1:
                setTheme(R.style.AppTheme1);
                break;
            case 2:
                setTheme(R.style.AppTheme2);
                break;
            case 3:
                setTheme(R.style.AppTheme3);
                break;
            case 4:
                setTheme(R.style.AppTheme4);
                break;
            case 5:
                setTheme(R.style.AppTheme5);
                break;
            default:
                setTheme(R.style.AppThemeDefault);
                break;
        }
    }
}
