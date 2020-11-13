package com.sakura.ydhyfinal.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.viewpager.widget.ViewPager;

public class NoScrollViewPager extends ViewPager {
    private boolean isScroll;

    public NoScrollViewPager(Context context) {
        super(context);
    }

    public NoScrollViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    /**v
     * 1.dispatchTouchEvent一般情况不做处理
     *,如果修改了默认的返回值,子孩子都无法收到事件
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);   // return true;不行
    }
    /**
     * 是否拦截
     * 拦截:会走到自己的onTouchEvent方法里面来
     * 不拦截:事件传递给子孩子
     */

    // 1.禁掉viewpager左右滑动事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return false;
    }

    //2.禁掉viewpager左右滑动事件
    @Override
    public boolean onInterceptTouchEvent(MotionEvent arg0) {
        return false;
    }

    public void setScroll(boolean scroll) {
        isScroll = scroll;
    }
}