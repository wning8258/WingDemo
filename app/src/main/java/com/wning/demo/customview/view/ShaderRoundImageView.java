package com.wning.demo.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

import com.wning.demo.R;


/**
 * Created by Administrator on 2016/6/15.
 */
public class ShaderRoundImageView extends ImageView{

    /**
     * 图片的类型，圆形or圆角
     */
    private int type;
    public static final int TYPE_CIRCLE = 0;
    public static final int TYPE_ROUND = 1;

    /**
     * 绘图的Paint
     */
    private Paint mBitmapPaint;
    /**
     * 圆角的半径
     */
    private int mRadius;
    /**
     * 3x3 矩阵，主要用于缩小放大
     */
    private Matrix mMatrix;
    /**
     * 渲染图像，使用图像为绘制图形着色
     */
    private BitmapShader mBitmapShader;

    /**
     * view的宽度
     */
    private int mWidth;
    private RectF mRoundRect;

    /**
     * 圆角的大小
     */
    private int mBorderRadius;


    public ShaderRoundImageView(Context context) {
        super(context,null);
    }

    public ShaderRoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);

        mMatrix = new Matrix();
        mBitmapPaint = new Paint();
        mBitmapPaint.setAntiAlias(true);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomImageView);

        type = typedArray.getInt(R.styleable.CustomImageView_type, TYPE_CIRCLE);

        mBorderRadius=typedArray.getDimensionPixelSize(R.styleable.CustomImageView_borderRadius,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20f,
                        getResources().getDisplayMetrics()));

        typedArray.recycle();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        /**
         * 如果类型是圆形，则强制改变view的宽高一致，以小值为准
         */
        if (type == TYPE_CIRCLE)
        {
            mWidth = Math.min(getMeasuredWidth(), getMeasuredHeight());
            mRadius = mWidth / 2;
            setMeasuredDimension(mWidth, mWidth);
        }

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh)
    {
        super.onSizeChanged(w, h, oldw, oldh);

        // 圆角图片的范围
        if (type == TYPE_ROUND)
            mRoundRect = new RectF(0, 0, w, h);
    }


    private void setUpShader() {
        if (getDrawable() == null)
        {
            return;
        }
        Bitmap bmp=drawableToBitmap(getDrawable());
        mBitmapShader=new BitmapShader(bmp, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);

        //如果bitmap的大小小于view的大小，需要进行放大
        float scale = 1.0f;
        if (type == TYPE_CIRCLE)
        {
            // 拿到bitmap宽或高的小值
            int bSize = Math.min(bmp.getWidth(), bmp.getHeight());
            scale = mWidth * 1.0f / bSize;

        } else if (type == TYPE_ROUND)
        {
            if (!(bmp.getWidth() == getWidth() && bmp.getHeight() == getHeight()))
            {
                // 如果图片的宽或者高与view的宽高不匹配，计算出需要缩放的比例；缩放后的图片的宽高，一定要大于我们view的宽高；所以我们这里取大值；
                scale = Math.max(getWidth() * 1.0f / bmp.getWidth(),
                        getHeight() * 1.0f / bmp.getHeight());
            }

        }
        // shader的变换矩阵，我们这里主要用于放大或者缩小
        mMatrix.setScale(scale, scale);
        mBitmapShader.setLocalMatrix(mMatrix);
        mBitmapPaint.setShader(mBitmapShader);
    }

    private Bitmap drawableToBitmap(Drawable drawable) {
        if(drawable instanceof BitmapDrawable){
            return ((BitmapDrawable)drawable).getBitmap();
        }
        int width=drawable.getIntrinsicWidth();
        int height=drawable.getIntrinsicHeight();
        Bitmap bmp=Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(bmp);

        drawable.setBounds(0,0,width,height);
        drawable.draw(canvas);
        return bmp;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (getDrawable() == null)
        {
            return;
        }
        setUpShader();

        if (type == TYPE_ROUND)
        {
            canvas.drawRoundRect(mRoundRect, mBorderRadius, mBorderRadius,
                    mBitmapPaint);
        } else
        {
            canvas.drawCircle(mRadius, mRadius, mRadius, mBitmapPaint);
            // drawSomeThing(canvas);
        }
    }




}
