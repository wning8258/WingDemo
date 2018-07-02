package com.wning.demo.customview.view.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.wning.demo.R;

/**
 * Created by wning on 2017/3/7.
 */

public class PorterDuffColorFilterCustomView extends BaseCustomView {

    private Paint mPaint;
     private Bitmap mBmp;

    public PorterDuffColorFilterCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mBmp = BitmapFactory.decodeResource(getResources(), R.mipmap.dog);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.reset();
        mPaint.setAntiAlias(true);

        drawPorterDuffFilter(canvas);
    }

    private void drawPorterDuffFilter(Canvas canvas){
        int width  = 500;
        int height = width * mBmp.getHeight()/mBmp.getWidth();

        canvas.drawBitmap(mBmp,null,new Rect(0,100,width,height),mPaint);

        canvas.translate(550,0);
        mPaint.setColorFilter(new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.LIGHTEN));
       // mPaint.setColorFilter(new PorterDuffColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY));//变暗
        canvas.drawBitmap(mBmp,null,new Rect(0,100,width,height),mPaint);
    }


    @Override
    protected String setInfo() {
        return "PorterDuffColorFilter";
    }
}
