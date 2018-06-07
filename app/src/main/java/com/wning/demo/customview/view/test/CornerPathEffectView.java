package com.wning.demo.customview.view.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;

/*************************************************************************************
 * Module Name:圆形拐角效果
 * File Name: CornerPathEffectView.java
 * Description:
 * Author: 王宁
 * 版权 2008-2016，浙江齐聚科技有限公司
 * 所有版权保护
 * 这是浙江齐聚科技有限公司未公开的私有源代码, 本文件及相关内容未经浙江齐聚科技有限公司
 * 事先书面同意，不允许向任何第三方透露，泄密部分或全部; 也,不允许任何形式的私自备份。
 *************************************************************************************/
public class CornerPathEffectView extends BaseView{


    public CornerPathEffectView(Context context) {
        super(context);
    }

    public CornerPathEffectView(Context context, AttributeSet attrs) {
        super(context, attrs);
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
        paint.setPathEffect(new CornerPathEffect(100));
        canvas.drawPath(path,paint);

        paint.setColor(Color.YELLOW);
        paint.setPathEffect(new CornerPathEffect(200));
        canvas.drawPath(path,paint);

    }

    @Override
    protected String setInfo() {
        return "CornerPathEffect圆形拐角效果";
    }




}
