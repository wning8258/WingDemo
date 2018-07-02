package com.wning.demo.customview.view.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;
import android.graphics.RegionIterator;
import android.util.AttributeSet;

/*************************************************************************************
 * Module Name:
 * File Name: RegionView.java
 * Description:
 * Author: 王宁
 * 版权 2008-2016，浙江齐聚科技有限公司
 * 所有版权保护
 * 这是浙江齐聚科技有限公司未公开的私有源代码, 本文件及相关内容未经浙江齐聚科技有限公司
 * 事先书面同意，不允许向任何第三方透露，泄密部分或全部; 也,不允许任何形式的私自备份。
 *************************************************************************************/
public class RegionCustomView extends BaseCustomView {

    private Paint mPaint;

    public RegionCustomView(Context context) {
        super(context);
        init();
    }

    public RegionCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint=new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(5);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Path ovalPath=new Path();
        RectF rectf=new RectF(50,100,100,300);
        ovalPath.addOval(rectf, Path.Direction.CCW);
     //   canvas.drawPath(ovalPath,mPaint);   //画出一个椭圆

        Region region=new Region();
        region.setPath(ovalPath,new  Region(50, 100, 100, 200));  //使用小的矩形去裁剪椭圆，取交集

        drawRegion(canvas,region);
    }

    @Override
    protected String setInfo() {
        return "drawRegion setPath RegionIterator";
    }

    /**
     * 绘制region
     * @param canvas
     * @param region
     */
    private void drawRegion(Canvas canvas,Region region) {
        //所有的形状其实都可以分解成很小的矩形
        RegionIterator iterator=new RegionIterator(region);
        Rect rect=new Rect();
        while(iterator.next(rect)){
            canvas.drawRect(rect,mPaint);
        }
    }


}
