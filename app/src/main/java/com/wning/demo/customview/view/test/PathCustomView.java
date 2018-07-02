package com.wning.demo.customview.view.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

/*************************************************************************************
 * Module Name:
 * File Name: PathView.java
 * Description:
 * Author: 王宁
 * 版权 2008-2016，浙江齐聚科技有限公司
 * 所有版权保护
 * 这是浙江齐聚科技有限公司未公开的私有源代码, 本文件及相关内容未经浙江齐聚科技有限公司
 * 事先书面同意，不允许向任何第三方透露，泄密部分或全部; 也,不允许任何形式的私自备份。
 *************************************************************************************/
public class PathCustomView extends BaseCustomView {

    private Paint mPaint;


    public PathCustomView(Context context) {
        super(context);
        init();
    }

    public PathCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint=new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(5);

       // mPathHeight= Utils.dip2px(getContext(),50);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Path path=new Path();
        path.addCircle(200,200,100, Path.Direction.CCW);
        path.addOval(new RectF(400,100,800,200), Path.Direction.CCW);
        canvas.drawPath(path,mPaint);

        Paint paint=new Paint();
        paint.setColor(Color.RED);  //设置画笔颜色
        paint.setStrokeWidth (5);//设置画笔宽度
        paint.setAntiAlias(true); //指定是否使用抗锯齿功能，如果使用，会使绘图速度变慢
        paint.setTextSize(80);//设置文字大小
//绘图样式，设置为填充
        paint.setStyle(Paint.Style.FILL);
        canvas.drawText("欢迎光临", 10,380, paint);
//绘图样式设置为描边
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawText("欢迎光临", 10,480, paint);
//绘图样式设置为填充且描边
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawText("欢迎光临", 10,580, paint);

    }

    @Override
    protected String setInfo() {
        return "drawPath(addCircle addOval), drawText ";
    }
}
