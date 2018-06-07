package com.wning.demo.customview.view.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by wning on 2017/4/11.
 */


public class MATRIX_SAVE_FLAG_View extends View {
    private Paint mPaint;
    public MATRIX_SAVE_FLAG_View(Context context, AttributeSet attrs) {
        super(context, attrs);
        setLayerType(LAYER_TYPE_SOFTWARE,null);
        mPaint = new Paint();

        mPaint.setColor(Color.GREEN);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.GREEN);

        //HAS_ALPHA_LAYER_SAVE_FLAG 在新建的画布在合成到上一个画布上时，直接覆盖，不清空所在区域原图像

        canvas.saveLayer(0,0,getWidth(),getHeight(),mPaint,Canvas.MATRIX_SAVE_FLAG
                |Canvas.HAS_ALPHA_LAYER_SAVE_FLAG
        );
        canvas.clipRect(100,0,200,100);
        canvas.restore();

        canvas.drawColor(Color.YELLOW);
    }
}