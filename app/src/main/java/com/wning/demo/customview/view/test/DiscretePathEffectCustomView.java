package com.wning.demo.customview.view.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;

/*************************************************************************************
 * Module Name:离散路径效果
 * File Name: PathView.java
 * Description:
 * Author: 王宁
 * 版权 2008-2016，浙江齐聚科技有限公司
 * 所有版权保护
 * 这是浙江齐聚科技有限公司未公开的私有源代码, 本文件及相关内容未经浙江齐聚科技有限公司
 * 事先书面同意，不允许向任何第三方透露，泄密部分或全部; 也,不允许任何形式的私自备份。
 *************************************************************************************/
public class DiscretePathEffectCustomView extends BaseCustomView {


    public DiscretePathEffectCustomView(Context context) {
        super(context);
        init();
    }

    public DiscretePathEffectCustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = getPaint();
        Path path = getPath();
//第一条原生Path
        canvas.translate(0, 100);
        canvas.drawPath(path, paint);
//第二条Path
        canvas.translate(0, 200);
        paint.setPathEffect(new DiscretePathEffect(2, 5));
        canvas.drawPath(path, paint);
//第三条Path
        canvas.translate(0, 200);
        paint.setPathEffect(new DiscretePathEffect(6, 5));
        canvas.drawPath(path, paint);
//第四条Path
        canvas.translate(0, 200);
        paint.setPathEffect(new DiscretePathEffect(6, 15));
        canvas.drawPath(path, paint);

    }

    @Override
    protected String setInfo() {
        return "DiscretePathEffect离散路径效果";
    }

    private Path getPath() {
        Path path = new Path();
        // 定义路径的起点
        path.moveTo(0, 0);

        // 定义路径的各个点
        for (int i = 0; i <= 40; i++) {
            path.lineTo(i * 35, (float) (Math.random() * 150));
        }
        return path;
    }



}
