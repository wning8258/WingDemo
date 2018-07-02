package com.wning.demo.customview.view.test;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.wning.demo.R;

/**
 * 橡皮擦效果
 */
public class EraserCustomView_SRCOUT extends BaseCustomView {
    private Paint mBitPaint;
    private Bitmap BmpDST,BmpSRC;
    private Path mPath;
    private float mPreX,mPreY;
    public EraserCustomView_SRCOUT(Context context, AttributeSet attrs) {
        super(context, attrs);

        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mBitPaint = new Paint();
        mBitPaint.setColor(Color.RED);
        mBitPaint.setStyle(Paint.Style.STROKE);
        mBitPaint.setStrokeWidth(45);

        BmpSRC = BitmapFactory.decodeResource(getResources(), R.mipmap.dog1,null);
        BmpDST = Bitmap.createBitmap(BmpSRC.getWidth(), BmpSRC.getHeight(), Bitmap.Config.ARGB_8888);
        mPath = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mBitPaint.setXfermode(null);
        int layerId = canvas.saveLayer(0, 100, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);

        //先把手指轨迹画到目标Bitmap上
        Canvas c = new Canvas(BmpDST);
        c.drawPath(mPath,mBitPaint);

        //然后把目标图像画到画布上
        canvas.drawBitmap(BmpDST,0,0,mBitPaint);

        //计算源图像区域
        mBitPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        canvas.drawBitmap(BmpSRC,0,0,mBitPaint);

        mBitPaint.setXfermode(null);
        canvas.restoreToCount(layerId);
    }

    @Override
    protected String setInfo() {
        return "橡皮擦效果，擦一下试试";
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                mPath.moveTo(event.getX(),event.getY());
                mPreX = event.getX();
                mPreY = event.getY();
                return true;
            case MotionEvent.ACTION_MOVE:
                getParent().requestDisallowInterceptTouchEvent(true);
                float endX = (mPreX+event.getX())/2;
                float endY = (mPreY+event.getY())/2;
                mPath.quadTo(mPreX,mPreY,endX,endY);
                mPreX = event.getX();
                mPreY =event.getY();
                break;
            case MotionEvent.ACTION_UP:
                getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }
        postInvalidate();
        return super.onTouchEvent(event);
    }
}
