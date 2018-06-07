package com.wning.demo.customview.view.test;

/*************************************************************************************
 * Module Name:
 * File Name: RegionOPView.java
 * Description:
 * Author: 王宁
 * 版权 2008-2016，浙江齐聚科技有限公司
 * 所有版权保护
 * 这是浙江齐聚科技有限公司未公开的私有源代码, 本文件及相关内容未经浙江齐聚科技有限公司
 * 事先书面同意，不允许向任何第三方透露，泄密部分或全部; 也,不允许任何形式的私自备份。
 *************************************************************************************/
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.Region.Op;
import android.graphics.RegionIterator;
import android.util.AttributeSet;

public class RegionOPView extends BaseView {

    public RegionOPView(Context context) {
        super(context);
    }

    public RegionOPView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);

        //构造两个矩形
        Rect rect1 = new Rect(100,200,400,300);
        Rect rect2 = new Rect(200,100,300,400);

        //构造一个画笔，画出矩形轮廓
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Style.STROKE);
        paint.setStrokeWidth(2);

        canvas.drawRect(rect1, paint);
        canvas.drawRect(rect2, paint);


        //构造两个Region
        Region region = new Region(rect1);
        Region region2= new Region(rect2);

        /**
         *  DIFFERENCE(0),
         INTERSECT(1),
         UNION(2),
         XOR(3),
         REVERSE_DIFFERENCE(4),
         REPLACE(5);
         */
        //取两个区域的交集
        region.op(region2, Op.INTERSECT);

        //再构造一个画笔,填充Region操作结果
        Paint paint_fill = new Paint();
        paint_fill.setColor(Color.GREEN);
        paint_fill.setStyle(Style.FILL);
        drawRegion(canvas, region, paint_fill);

    }

    @Override
    protected String setInfo() {
        return "region.op";
    }


    private void drawRegion(Canvas canvas,Region rgn,Paint paint)
    {
        RegionIterator iter = new RegionIterator(rgn);
        Rect r = new Rect();

        while (iter.next(r)) {
            canvas.drawRect(r, paint);
        }
    }
}