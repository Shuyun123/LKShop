package net.anumbrella.lkshop.ui.viewholder;

import android.annotation.TargetApi;
import android.net.Uri;
import android.os.Build;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.RoundingParams;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;

import net.anumbrella.lkshop.R;
import net.anumbrella.lkshop.config.Config;
import net.anumbrella.lkshop.model.bean.RecommendContentModel;
import net.anumbrella.lkshop.ui.activity.MainActivity;

/**
 * author：Anumbrella
 * Date：16/5/26 下午4:02
 */
public class RecommendListViewHolder extends BaseViewHolder<RecommendContentModel> implements View.OnClickListener {


    private LinearLayout linearLayout_one;

    private LinearLayout linearLayout_two;

    public RecommendListViewHolder(ViewGroup parent) {
        super(parent, R.layout.itemview_recommend_list);
        linearLayout_one = $(R.id.recommend_list_menu_one);
        linearLayout_two = $(R.id.recommend_list_menu_two);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void setData(RecommendContentModel data) {
        super.setData(data);
        int length = getContext().getResources().getStringArray(R.array.tab).length;
        addImgViews(linearLayout_one, length / 2);
        addImgViews(linearLayout_two, length / 2);

    }


    private void addImgViews(LinearLayout linearLayout, int length) {
        for (int i = 0; i < length; i++) {
            LinearLayout layout = new LinearLayout(getContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layout.setGravity(Gravity.CENTER_VERTICAL & Gravity.CENTER_HORIZONTAL);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setLayoutParams(layoutParams);
            layout.setOnClickListener(this);
            SimpleDraweeView view = new SimpleDraweeView(getContext());
            GenericDraweeHierarchyBuilder builder =
                    new GenericDraweeHierarchyBuilder(getContext().getResources());
            GenericDraweeHierarchy hierarchy = builder
                    // .setPlaceholderImage(getContext().getResources().getDrawable(R.mipmap.default_icon))
                    .setRoundingParams(RoundingParams.fromCornersRadius(10f))
                    .build();
            LinearLayout.LayoutParams lps = new LinearLayout.LayoutParams(300, 200);
            lps.setMargins(30, 0, 30, 0);
            view.setLayoutParams(lps);
            view.setHierarchy(hierarchy);
            TextView textView = new TextView(getContext());
            textView.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams lps_text = new LinearLayout.LayoutParams(300, ViewGroup.LayoutParams.WRAP_CONTENT);
            lps_text.setMargins(30, 15, 0, 0);
            if (linearLayout == linearLayout_two) {
                layout.setTag(i + length);
                textView.setText(Config.recommdendListTexts[i + length]);
                view.setBackgroundResource(Config.recommdendListImg[i + length]);
            } else {
                layout.setTag(i);
                textView.setText(Config.recommdendListTexts[i]);
                view.setBackgroundResource(Config.recommdendListImg[i]);
            }

            textView.setTextColor(getContext().getResources().getColor(R.color.textColor_black));
            textView.setLayoutParams(lps_text);
            layout.addView(view);
            layout.addView(textView);
            linearLayout.addView(layout);
        }
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        switch (position) {
            case 0:
                MainActivity.staticViewPager.setCurrentItem(1);
                break;
            case 1:
                MainActivity.staticViewPager.setCurrentItem(3);
                break;
            case 2:
                MainActivity.staticViewPager.setCurrentItem(4);
                break;
            case 3:
                MainActivity.staticViewPager.setCurrentItem(5);
                break;
            case 4:
                Toast.makeText(getContext(),"下一版本推出",Toast.LENGTH_SHORT).show();
                break;
            case 5:
                Toast.makeText(getContext(),"下一版本推出",Toast.LENGTH_SHORT).show();
                break;

        }


    }
}
