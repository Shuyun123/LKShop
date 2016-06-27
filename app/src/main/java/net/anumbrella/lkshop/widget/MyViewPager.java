package net.anumbrella.lkshop.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * author：Anumbrella
 * Date：16/5/27 下午8:57
 */
public class MyViewPager extends ViewPager {

    private boolean scrollble = true;

    public MyViewPager(Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (scrollble) {
            return super.onTouchEvent(ev);
        }else {
            return false;
        }

    }

    public boolean isScrollble() {
        return scrollble;
    }

    public void setScrollble(boolean scrollble) {
        this.scrollble = scrollble;
    }
}