package com.wning.demo.customview.view;

import android.content.Context;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by wning on 2018/3/2.
 */

public class TouchMoveView extends View {

    private int lastx,lasty;

    public TouchMoveView(Context context) {
        super(context);
    }

    public TouchMoveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int x= (int) event.getX();
        int y= (int) event.getY();

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                lastx=x;
                lasty=y;
                break;
            case MotionEvent.ACTION_MOVE:
                getParent().requestDisallowInterceptTouchEvent(true);
                int offsetx=x-lastx;
                int offsety=y-lasty;
                //1
                //layout(getLeft()+offsetx,getTop()+offsety,getRight()+offsetx,getBottom()+offsety);

                //2
               // offsetLeftAndRight(offsetx);
                //offsetTopAndBottom(offsety);

                //3
                ((View)getParent()).scrollBy(-offsetx,-offsety);  //这里值为负数
                break;
            case MotionEvent.ACTION_UP:
                getParent().requestDisallowInterceptTouchEvent(false);
                break;

        }

        return true;
    }
}
