package com.shen.shenbanner;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 定制的ViewPager
 */
public class CustomViewPager extends ViewPager {

    /** 两子控件横坐标的绝对值？？？ */
    private ArrayList<Integer> mChildCenterXAbs = new ArrayList<>();
    private SparseArray<Integer> mChildIndex = new SparseArray<>();


    public CustomViewPager(Context context) {
        super(context);
        init();
    }

    public CustomViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        // 控件的绘制区域是否在padding里面的，true的情况下如果你设置了padding那么绘制的区域就往里缩，
        setClipToPadding(false);

        /**
         * setOverScrollMode(View.OVER_SCROLL_NEVER) 设置此模式，滑到边界后继续滑动也不会出现弧形光晕
         * setOverScrollMode(View.OVER_SCROLL_ALWAYS) 设置此模式，滑到边界后继续滑动也总是会出现弧形光晕
         * setOverScrollMode(View.OVER_SCROLL_IF_CONTENT_SCROLLS)
         * 设置此模式，如果recycleview里面的内容可以滑动，那么滑到边界后继续滑动会出现弧形光晕；
         * 如果recycleview里面的内容不可以滑动，那么滑到边界后继续滑动不会出现弧形光晕.
         */
        setOverScrollMode(OVER_SCROLL_NEVER);
    }


    /**
     * 返回迭代的绘制子类索引。如果你想改变子类的绘制顺序就要重写该方法。默认返回 n 值。
     *  在调用draw（）方法时，将会调用getChildDrawingOrder（int childCount ，int n）方法
     *
     * @param childCount    子类个数
     * @param n             当前迭代顺序
     * @return 第n个位置的child 的绘制索引
     */
    @Override
    protected int getChildDrawingOrder(int childCount, int n) {

        if (n == 0 || mChildIndex.size() != childCount) {
            mChildCenterXAbs.clear();
            mChildIndex.clear();

            int viewCenterX = getViewCenterX(this);

            for (int i = 0; i < childCount; ++i) {
                int indexAbs = Math.abs(viewCenterX - getViewCenterX(getChildAt(i)));

                if (mChildIndex.get(indexAbs) != null) { // 两个距离相同，后来的那个做自增，从而保持abs不同
                    ++indexAbs;
                }

                mChildCenterXAbs.add(indexAbs);
                mChildIndex.append(indexAbs, i);
            }
            Collections.sort(mChildCenterXAbs);//1,0,2  0,1,2   排序从小到大
        }

        //那个item距离中心点远一些，就先draw它。（最近的就是中间放大的item,最后draw）
        return mChildIndex.get(mChildCenterXAbs.get(childCount - 1 - n));
    }


    /**
     * 获取 -- 控件的中心点 (横坐标)
     * @param view
     * @return
     */
    private int getViewCenterX(View view) {
        int[] array = new int[2];
        view.getLocationOnScreen(array);        // 获取控件在屏幕中的 (x,y)
        return array[0] + view.getWidth() / 2;
    }
}
