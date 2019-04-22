package com.wning.demo.customview.view.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;

import com.wning.demo.R;

/**
 * Created by wning on 2017/3/3.
 */

public class ColorMatrixColorFilterCustomView extends BaseCustomView {

    private Bitmap bitmap;

    private Paint mPaint = new Paint();

    public ColorMatrixColorFilterCustomView(Context context) {
        super(context);
        init();
    }

    public ColorMatrixColorFilterCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        bitmap= BitmapFactory.decodeResource(getResources(), R.drawable.dog);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected String setInfo() {
        return "ColorMatrixColorFilter色彩矩阵";
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.reset();
        canvas.drawBitmap(bitmap, null, new Rect(0, 100, 500, 500 * bitmap.getHeight() / bitmap.getWidth()), mPaint);
        canvas.translate(510, 0);
        // 生成色彩矩阵
        ColorMatrix colorMatrix = new ColorMatrix(new float[]{
                1, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                0, 0, 0, 0, 0,
                0, 0, 0, 1, 0,
        });
        mPaint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));
        canvas.drawBitmap(bitmap, null, new Rect(0, 100, 500, 500 * bitmap.getHeight() / bitmap.getWidth()), mPaint);
    }
}
