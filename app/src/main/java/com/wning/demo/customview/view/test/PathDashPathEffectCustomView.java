package com.wning.demo.customview.view.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.util.AttributeSet;

/*************************************************************************************
 * Module Name:印章路径效果
 * File Name: PathDashPathEffectView.java
 * Description:
 * Author: 王宁
 * 版权 2008-2016，浙江齐聚科技有限公司
 * 所有版权保护
 * 这是浙江齐聚科技有限公司未公开的私有源代码, 本文件及相关内容未经浙江齐聚科技有限公司
 * 事先书面同意，不允许向任何第三方透露，泄密部分或全部; 也,不允许任何形式的私自备份。
 *************************************************************************************/
public class PathDashPathEffectCustomView extends BaseCustomView {

    int dx;


    public PathDashPathEffectCustomView(Context context) {
        super(context);
        init();
    }

    public PathDashPathEffectCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = getPaint();

        //画出原始路径
        Path path  = new Path();
        path.moveTo(100,600);
        path.lineTo(400,100);
        path.lineTo(700,900);
        canvas.drawPath(path,paint);

        //构建印章路径
        Path stampPath  = new Path();
        stampPath.moveTo(0,20);
        stampPath.lineTo(10,0);
        stampPath.lineTo(20,20);
        stampPath.close();
        stampPath.addCircle(0,0,3, Path.Direction.CCW);

        //使用印章路径效果
        canvas.translate(0,200);
        paint.setPathEffect(new PathDashPathEffect(stampPath,35,dx, PathDashPathEffect.Style.TRANSLATE));
        canvas.drawPath(path,paint);

        dx++;
        invalidate();
    }

    @Override
    protected String setInfo() {
        return "PathDashPathEffect印章路径效果";
    }



}
