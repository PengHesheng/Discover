package com.example.a14512.discover.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.example.a14512.discover.R;


/**
 * @author 14512 on 2017/9/5.
 */

public class SlidingView extends HorizontalScrollView {
    private LinearLayout mWapper;

    /**
     * 菜单区
     */
    private ViewGroup mMenu;

    /**
     * 内容区
     */
    private ViewGroup mContent;
    private int mScreenWidth;
    private int mMenuWidth;

    /**
     * 分别记录上次滑动的坐标（onInterceptTouchEvent）
     */
    private int lastXIntercept = 0;
    private int lastYIntercept = 0;

    private int mMenuRightPadding;

    private boolean once = false;
    private boolean isOpen;


    public SlidingView(Context context) {
        this(context, null);
    }

    /**
     * 未使用自定义View时调用
     *
     * @param  context
     * @param attrs
     * */
    public SlidingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    /**
     * 使用自定义View时才调用
     *
     * @param context
     * @param attrs
     * @param defStyleAttr
     * */
    public SlidingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setHorizontalScrollBarEnabled(false);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SlidingMenu, defStyleAttr, 0);
        int initSize = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, displayMetrics);
        mMenuRightPadding = a.getDimensionPixelSize(R.styleable.SlidingMenu_rightPadding, initSize);
        a.recycle();
        mScreenWidth = displayMetrics.widthPixels;
    }

    /**
     * 设置自己的宽和高
     * 设置子view的宽和高
     * */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!once) {
            mWapper = (LinearLayout) getChildAt(0);
            mMenu = (ViewGroup) mWapper.getChildAt(0);
            mContent = (ViewGroup) mWapper.getChildAt(1);
            mMenuWidth = mMenu.getLayoutParams().width = mScreenWidth - mMenuRightPadding;
            mContent.getLayoutParams().width = mScreenWidth;
            once = true;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 通过设置偏移量来将menu隐藏
     * */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            this.scrollTo(mMenuWidth, 0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_UP:
                //隐藏在左边的宽度
                int scrollX= getScrollX();
                if (scrollX >= mMenuWidth / 2) {
                    //隐藏时有动画
                    this.smoothScrollTo(mMenuWidth, 0);
                    isOpen = false;
                } else {
                    this.smoothScrollTo(0, 0);
                    isOpen = true;
                }
                return true;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 事件分发，冲突处理，外部拦截
     * */
//    @Override
//    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        boolean intercepted = false;
//        int x = (int) ev.getX();
//        int y = (int) ev.getY();
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                //处理内部拦截时
//                onTouchEvent(ev);
//                intercepted = false;
//                break;
//            case MotionEvent.ACTION_MOVE:
//                //外部拦截ScrollView的上下滑动，当ScrollView上下滑动时，事件给ScrollView
//                if (Math.abs(x - lastXIntercept) > Math.abs(y - lastYIntercept)) {
//                    intercepted = true;
//                } else {
//                    intercepted = false;
//                }
//                break;
//            case MotionEvent.ACTION_UP:
//                intercepted = false;
//                break;
//            default:
//                break;
//        }
//        lastXIntercept = x;
//        lastYIntercept = y;
//        return intercepted;
//    }

    /**
     * 滚动事调用
     * */
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        //1~0
        float scale = l * 1.0f / mMenuWidth;
        //透明度
        float leftAlpha = 0.6f + 0.4f * (1.0f - scale);

        //属性动画TranslationX，默认动画有时间限制，需要自己去设置时间
        mMenu.animate().translationX(mMenuWidth * scale * 0.8f).alpha(leftAlpha).setDuration(0).start();
    }

    /**
     * 打开菜单
     */
    public void openMenu() {
        if (isOpen) {
            return;
        }
        this.smoothScrollTo(0, 0);
        isOpen = true;
    }

    /**
     * 关闭菜单
     * */
    public void closeMenu() {
        if (!isOpen) {
            return;
        }
        this.smoothScrollTo(mMenuWidth, 0);
        isOpen = false;
    }

    /**
     * 切换菜单
     * */
    public void changeMenu() {
        if (isOpen) {
            closeMenu();
        } else {
            openMenu();
        }
    }

    /**
     * 获取当前菜单栏的状态
     * @return isOpen
     */
    public boolean getMenu() {
        return isOpen;
    }

}
