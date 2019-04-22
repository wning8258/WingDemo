package com.wning.demo.customview.view.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import androidx.annotation.Nullable;
import android.util.AttributeSet;

import com.wning.demo.R;

/**
 * Created by wning on 2017/3/7.
 */

public class LightingColorFilterCustomView extends BaseCustomView {

    private Paint mPaint;
     private Bitmap mBmp;

    public LightingColorFilterCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mBmp = BitmapFactory.decodeResource(getResources(), R.drawable.btn);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.reset();
        mPaint.setAntiAlias(true);

        int width  = 500;
        int height = width * mBmp.getHeight()/mBmp.getWidth();

        canvas.drawBitmap(mBmp,null,new Rect(0,100,width,height),mPaint);

//        int color = mBmp.getPixel(100, 100);//x、y为bitmap所对应的位置
//        int r = Color.red(color);
//        int g = Color.green(color);
//        int b = Color.blue(color);
//        int a = Color.alpha(color);
//
//        LogUtils.i("wn","r :"+r+",g :"+g+",b :"+b+",a :"+a);

        canvas.translate(500,0);
        mPaint.setColorFilter(new LightingColorFilter(0x00ff00,0x000000));
        canvas.drawBitmap(mBmp,null,new Rect(0,100,width,height),mPaint);

    }


    @Override
    protected String setInfo() {
        return "LightingColorFilter";
    }
}
