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
public class PathDashPathEffectStyleCustomView extends BaseCustomView {


    public PathDashPathEffectStyleCustomView(Context context) {
        super(context);
        init();
    }

    public PathDashPathEffectStyleCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawPathDashPathEffect(canvas);
    }

    private void drawPathDashPathEffect(Canvas canvas){
        Paint paint = getPaint();
        Path path = getPath();
        canvas.drawPath(path,paint);

        canvas.translate(0,200);
        paint.setPathEffect(new PathDashPathEffect(getStampPath(),35,0, PathDashPathEffect.Style.MORPH));
        canvas.drawPath(path,paint);

        canvas.translate(0,200);
        paint.setPathEffect(new PathDashPathEffect(getStampPath(),35,0, PathDashPathEffect.Style.ROTATE));
        canvas.drawPath(path,paint);

        canvas.translate(0,200);
        paint.setPathEffect(new PathDashPathEffect(getStampPath(),35,0, PathDashPathEffect.Style.TRANSLATE));
        canvas.drawPath(path,paint);
    }

    private Path getPath() {
        Path path = new Path();
        // 定义路径的起点
        path.moveTo(0, 100);

        // 定义路径的各个点
        for (int i = 0; i <= 40; i++) {
            path.lineTo(i * 35, (float) (Math.random() * 150)+100);
        }
        return path;
    }

    private Path getStampPath(){
        Path path  = new Path();
        path.moveTo(0,20);
        path.lineTo(10,0);
        path.lineTo(20,20);
        path.close();

        path.addCircle(0,0,3, Path.Direction.CCW);
        return path;
    }

    @Override
    protected String setInfo() {
        return "PathDashPathEffect印章路径多种style效果";
    }



}
