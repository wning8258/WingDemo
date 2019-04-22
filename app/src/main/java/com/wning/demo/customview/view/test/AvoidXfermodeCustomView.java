package com.wning.demo.customview.view.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.wning.demo.R;

/**
 * Created by wning on 2017/3/8.
 */

/**
 * @deprecated
 */
public class AvoidXfermodeCustomView extends BaseCustomView {

    private Paint mPaint;
    private Bitmap mBmp;

    public AvoidXfermodeCustomView(Context context) {
        super(context);
        init();
    }

    public AvoidXfermodeCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mBmp = BitmapFactory.decodeResource(getResources(), R.drawable.dog);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.reset();

        int width  = 500;
        int height = width * mBmp.getHeight()/mBmp.getWidth();
        mPaint.setColor(Color.RED);

        int layerID = canvas.saveLayer(0,100,width,height,mPaint,Canvas.ALL_SAVE_FLAG);

        canvas.drawBitmap(mBmp,null,new Rect(0,0,width,height),mPaint);
        //把图片中与白色容差100以内的区域替换成红色
       // mPaint.setXfermode(new AvoidXfermode(Color.WHITE,100, AvoidXfermode.Mode.TARGET));
        canvas.drawRect(0,0,width,height,mPaint);
        canvas.restoreToCount(layerID);
    }

    @Override
    protected String setInfo() {
        return "AvoidXfermode";
    }
}
