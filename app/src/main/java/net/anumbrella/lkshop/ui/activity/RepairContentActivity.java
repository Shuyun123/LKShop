package net.anumbrella.lkshop.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.umeng.message.PushAgent;

import net.anumbrella.lkshop.R;
import net.anumbrella.lkshop.config.Config;
import net.anumbrella.lkshop.ui.fragment.RepairContentFragment;
import net.anumbrella.lkshop.ui.fragment.RepairScreenTypeFragment;

/**
 * author：Anumbrella
 * Date：16/6/4 下午12:49
 */
public class RepairContentActivity extends BaseThemeSettingActivity {

    private String type = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repaircontent);
        PushAgent.getInstance(this).onAppStart();
        type = getIntent().getStringExtra("repairType");
        if (type != null) {

            if (type.equals(Config.repairTexts[3])) {
                RepairContentFragment fragment = RepairContentFragment.newInstance(type);
                getSupportFragmentManager().beginTransaction().replace(R.id.repair_container, fragment).commit();
            }else{

                for(int i=0;i<Config.repairTexts.length;i++){
                    if(type.equals(Config.repairTexts[i])){
                        RepairContentFragment fragment = RepairContentFragment.newInstance(type);
                        getSupportFragmentManager().beginTransaction().replace(R.id.repair_container, fragment).commit();
                        return;
                    }
                }

                for(int i=0;i<Config.repairScreenPhoneTypes.length-1;i++){
                    if(type.equals(Config.repairScreenPhoneTypes[i])){
                        RepairScreenTypeFragment fragment = RepairScreenTypeFragment.newInstance(type);
                        getSupportFragmentManager().beginTransaction().replace(R.id.repair_container, fragment).commit();
                        return;
                    }
                }

            }
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
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
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
