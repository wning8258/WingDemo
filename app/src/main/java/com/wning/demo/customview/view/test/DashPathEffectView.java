package com.wning.demo.customview.view.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;

import com.nineoldandroids.animation.ValueAnimator;

/*************************************************************************************
 * Module Name:虚线效果
 * File Name: PathView.java
 * Description:
 * Author: 王宁
 * 版权 2008-2016，浙江齐聚科技有限公司
 * 所有版权保护
 * 这是浙江齐聚科技有限公司未公开的私有源代码, 本文件及相关内容未经浙江齐聚科技有限公司
 * 事先书面同意，不允许向任何第三方透露，泄密部分或全部; 也,不允许任何形式的私自备份。
 *************************************************************************************/
public class DashPathEffectView extends BaseView{

    ValueAnimator valueAnimator;

    public DashPathEffectView(Context context) {
        super(context);
        init();
    }

    public DashPathEffectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = getPaint();
        Path path = new Path();
        path.moveTo(100,600);
        path.lineTo(400,100);
        path.lineTo(700,900);

        canvas.drawPath(path,paint);
        paint.setColor(Color.RED);

//使用DashPathEffect画线段
        paint.setPathEffect(new DashPathEffect(new float[]{20,10,100,100},0));
        canvas.translate(0,100);
        canvas.drawPath(path,paint);

//画同一条线段,偏移值为15
        paint.setPathEffect(new DashPathEffect(new float[]{20,10,100,100},dashDx));
        paint.setColor(Color.YELLOW);
        canvas.translate(0,100);
        canvas.drawPath(path,paint);

    }

    @Override
    protected String setInfo() {
        return "DashPathEffect虚线效果";
    }




    float dashDx;

    public void startAnim() {
         valueAnimator = ValueAnimator.ofInt(0, 230);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(1000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                dashDx = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        valueAnimator.start();
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startAnim();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if(valueAnimator!=null&&valueAnimator.isRunning()){
            valueAnimator.cancel();
        }
    }
}
