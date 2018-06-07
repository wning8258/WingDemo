package com.wning.demo.customview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

import com.guagua.anim.util.AnimBitmapLoader;
import com.guagua.modules.utils.LogUtils;
import com.guagua.modules.utils.Utils;

/**
 * Created by Administrator on 2016/7/20.
 */
public class VideoLoadingSpringView extends View {
    private static final String TAG = "VideoLoadingSpringView";
    private Thread spriteThread;
    private long sleepTime = 150;
    private static int SCREEN_WIDTH = 720;
    private int state = 0;
    private final int START = 1;
    private final int FINISH = 4;
    /**
     * 以宽为基准的比例（如宽为720，opt=720/720=1,宽为360则为opt=720/360=2）
     */
    private float opt = 1;
    private boolean running = false;
    private OnGiftListener mGiftListener;
    private Paint paint;
    private int mWidth, mHeight;
    private int mChickenWidth, mChickenHeight;
    private int mHouseWidth,mHouseHeight;
    private int x_chicken = 0, y_chicken = 0;
    private int x_house = 0, y_house = 0;
    private Bitmap[] bmp_chickens;
    private Bitmap bmp_bg;
    private Bitmap bmp_house_l,bmp_house_r;
    private int index_chicken;
    private int offsetCount = -1;
    private int totalCount = 7;
    private static int FRAME_CHICHEN = 14;
    private static int FRAME_HOUSE = 70;
    private static int FRAME_FWS = 4;
    private static int FRAME_FW_BOMB = 3;
    private static int FRAME_FW_ALPHA = 4;
    private static int FRAME_FW_WAIT = 3;
    private float offset_house_current;   //房子当前移动的距离
    private float offset_house_per; //房子每次移动的单位距离
    private float offset_house_total;  //房子可以移动的总距离
    private FW fw_yellow1;
    private FW fw_red;
    private FW fw_blue;
    private FW fw_yellow2;
    private  Rect srcHouseRect_r, desHouseRect_r;
    //private  Rect srcHouseRect_l, desHouseRect_l;
    private final int HANDLER_DRAW = 1;
    private final int HANDLER_LISTENER = 2;
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case HANDLER_DRAW:
                    invalidate();
                    break;
                case HANDLER_LISTENER:
                    if (mGiftListener != null) {
                        mGiftListener.onGiftAnimFinish();
                    }
                    break;
            }
        }
    };

    public VideoLoadingSpringView(Context context) {
        super(context);
        init();
    }

    public VideoLoadingSpringView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    private void init() {
        // 适配参数
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        float width = wm.getDefaultDisplay().getWidth();
        opt = width / SCREEN_WIDTH;
        paint = new Paint();
        bmp_chickens = new Bitmap[FRAME_CHICHEN];
        try {
            for (int i = 0; i < bmp_chickens.length; i++) {
                bmp_chickens[i] = AnimBitmapLoader.getInstance().getBitmap(
                        getContext(), "loading/qiqi_room_video_loading_spring" + i + ".png", opt);
                if (bmp_chickens[i] == null) {
                    if (mGiftListener != null) {
                        mGiftListener.onError("动画 init Exception");
                    }
                    state = FINISH;// 解决内存不足无法解析图片而导致动画无法运行
                    break;
                }
            }
            bmp_bg = AnimBitmapLoader.getInstance().getBitmap(getContext(), "loading/qiqi_room_video_loading_spring_bg.png", opt);
            bmp_house_r = AnimBitmapLoader.getInstance().getBitmap(getContext(), "loading/qiqi_room_video_loading_spring_house.png", opt);
            // bmp_house_l=bmp_house_r;
            Bitmap bmp_yellow = AnimBitmapLoader.getInstance().getBitmap(getContext(), "loading/qiqi_room_video_loading_spring_fw_yellow.png", opt);
            Bitmap bmp_yellow_bomb = AnimBitmapLoader.getInstance().getBitmap(getContext(), "loading/qiqi_room_video_loading_spring_fw_yellow_bomb.png", opt);
            Bitmap bmp_red = AnimBitmapLoader.getInstance().getBitmap(getContext(), "loading/qiqi_room_video_loading_spring_fw_red.png", opt);
            Bitmap bmp_red_bomb = AnimBitmapLoader.getInstance().getBitmap(getContext(), "loading/qiqi_room_video_loading_spring_fw_red_bomb.png", opt);
            Bitmap bmp_blue = AnimBitmapLoader.getInstance().getBitmap(getContext(), "loading/qiqi_room_video_loading_spring_fw_blue.png", opt);
            Bitmap bmp_blue_bomb = AnimBitmapLoader.getInstance().getBitmap(getContext(), "loading/qiqi_room_video_loading_spring_fw_blue_bomb.png", opt);

            fw_yellow1 = new FW(bmp_yellow,bmp_yellow_bomb);
            fw_red = new FW(bmp_red,bmp_red_bomb);
            fw_blue = new FW(bmp_blue,bmp_blue_bomb);
            fw_yellow2 = new FW(bmp_yellow,bmp_yellow_bomb);

            srcHouseRect_r = new Rect();
            desHouseRect_r = new Rect();
            // srcHouseRect_l = new Rect();
            //desHouseRect_l = new Rect();
        } catch (Exception e) {
            e.printStackTrace();
        } catch (OutOfMemoryError oe) {
            if (mGiftListener != null) {
                mGiftListener.onError("SRP Boom");
            }
            state = FINISH;//如果内存不够导致解析图片没有，则不会播放动画
            System.gc();
//            if (mGiftListener != null) {
//                mGiftListener.end();
//            }
        }
    }


    private Runnable run = new Runnable() {
        public void run() {
            long curTime = 0;
            while (running && state != FINISH) {
                curTime = System.currentTimeMillis();
                move();
                mHandler.sendEmptyMessage(HANDLER_DRAW);
                curTime = System.currentTimeMillis() - curTime;
                try {
                    if (curTime < sleepTime) {
                        Thread.sleep(sleepTime - curTime);
                    }
                } catch (InterruptedException e) {
                    break;
                }
            }
            // 添加动画结束回调
            mHandler.sendEmptyMessage(HANDLER_LISTENER);
        }
    };

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        LogUtils.i(TAG,"onSizeChanged w :"+w+",h :"+h);
        mWidth = w;
        mHeight = h;
    }

    private void move() {
        index_chicken++;
        if (index_chicken >= bmp_chickens.length) {
            index_chicken = 0;
        }
        offsetCount++;
        if (offsetCount >= totalCount) {
            offsetCount = 0;
        }
        fw_yellow1.move();
        fw_red.move();
        fw_blue.move();
        fw_yellow2.move();

        offset_house_current += offset_house_per;
        if (offset_house_current >= offset_house_total) {
            offset_house_current = 0;
        }
        srcHouseRect_r.left=mHouseWidth-SCREEN_WIDTH-(int)offset_house_current;
        srcHouseRect_r.right=srcHouseRect_r.left+SCREEN_WIDTH;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //背景颜色
//        Rect srcRect=new Rect(0,0, bmp_bg.getWidth(), bmp_bg.getHeight());
//        Rect desRect=new Rect(0,0,mWidth,mHeight);
//        canvas.drawBitmap(bmp_bg,srcRect,desRect, null);
        if(bmp_bg!=null){
            canvas.drawBitmap(bmp_bg, 0, 0, null);  //背景
        }
        if(fw_yellow1!=null) {
            fw_yellow1.draw(canvas);
        }
        if(fw_red!=null) {
            fw_red.draw(canvas);
        }
        if(fw_blue!=null) {
            fw_blue.draw(canvas);
        }
        if(fw_yellow2!=null) {
            fw_yellow2.draw(canvas);
        }
        if(index_chicken>=0&&index_chicken<=bmp_chickens.length-1&&bmp_chickens[index_chicken]!=null) {
            canvas.drawBitmap(bmp_chickens[index_chicken], x_chicken, y_chicken, null);  //帧动画
        }
        if(srcHouseRect_r !=null&& desHouseRect_r !=null&& bmp_house_r !=null) {
            canvas.drawBitmap(bmp_house_r, srcHouseRect_r, desHouseRect_r, null);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (state != 0) {
            return;
        }
//        init();
        if (state == FINISH) {
            return;
        }
        mChickenWidth = bmp_chickens[0].getWidth();
        mChickenHeight = bmp_chickens[0].getHeight();
        mHouseWidth= bmp_house_r.getWidth();
        mHouseHeight= bmp_house_r.getHeight();
        x_chicken = (mWidth - mChickenWidth) / 2;
        //y_chicken = y_house + bmp_house_r.getHeight() + 2 - Utils.dip2px(getContext(), 10);
        y_chicken = (mHeight - mChickenHeight) / 2+Utils.dip2px(getContext(),20);
        x_house = 0;
        //  y_house = (mHeight - bmp_house_r.getHeight()) / 2;
        y_house=y_chicken- mHouseHeight+Utils.dip2px(getContext(), 10);
        offset_house_total = mHouseWidth - SCREEN_WIDTH;
        offset_house_per = offset_house_total / FRAME_HOUSE;

        srcHouseRect_r.set(mHouseWidth - SCREEN_WIDTH , 0, mHouseWidth, mHouseHeight);
        desHouseRect_r.set(0, y_house, mWidth, y_house + mHouseHeight);

        //srcHouseRect_l.set(srcHouseRect_r);
        //desHouseRect_l.set(desHouseRect_r);
        fw_yellow1.setFwMoveOffset(Utils.dip2px(getContext(), 40),Utils.dip2px(getContext(), 40)/ FRAME_FWS);
        fw_yellow1.setX(mWidth * 0.1f);
        fw_red.setFwMoveOffset(Utils.dip2px(getContext(), 50),Utils.dip2px(getContext(), 40)/ FRAME_FWS);
        fw_red.setX(mWidth * 0.4f);
        fw_blue.setFwMoveOffset(Utils.dip2px(getContext(), 30),Utils.dip2px(getContext(), 40)/ FRAME_FWS);
        fw_blue.setX(mWidth * 0.7f);
        fw_yellow2.setFwMoveOffset(Utils.dip2px(getContext(), 20),Utils.dip2px(getContext(), 40)/ FRAME_FWS);
        fw_yellow2.setX(mWidth * 0.9f);
    }

    public void startLoading() {
        state = 0;
        running = true;
        if (spriteThread == null) {
            spriteThread = new Thread(run);
            spriteThread.start();
        }
    }

    public void stopLoading() {
        running = false;
        mHandler.removeCallbacksAndMessages(null);
        try {
            if (spriteThread != null) {
                spriteThread.interrupt();
                spriteThread = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            state = 0;
        }
    }

    public interface OnGiftListener {
        void onGiftAnimFinish();

        /**
         * 由内存不够活内存溢出导致的动画没有播放错误
         */
        void onError(String errorInfo);
    }

    private class FW {
        private int state = 1;
        private final int TRANSLATE = 1;
        private final int SCALE = 2;
        private final int ALPHA = 3;
        private final int STAY = 4;
        private Matrix matrix;
        Bitmap bmp_fw;
        Bitmap bmp_fw_bomb;
        float x;
        private float y;
        private float y_current_offset;
        float y_per_offset; //烟花每次移动的单位距离
        float y_total_offset;//各种烟花，y轴可以移动的总距离
        private float scale_current;
        private float scale_per;
        private final float scale_total = 1f;
        float x_bomb;
        private float alpha_current;
        private float alpha_per;
        private final float alpha_total = 1f;
        int waitCount = 0;

        public FW() {
            init();
        }

        public FW(Bitmap fwBitmap, Bitmap fwBombBitmap) {
            init();
            setFwBitmap(fwBitmap,fwBombBitmap);
        }

        private void init() {
            matrix = new Matrix();
            x = y = y_current_offset = y_per_offset = y_total_offset = 0;
            bmp_fw = null;
            bmp_fw_bomb = null;
            scale_per = scale_total / FRAME_FW_BOMB;
            alpha_per = alpha_total / FRAME_FW_ALPHA;
            x_bomb = 0;
        }

        /**
         * 设置bitmap
         * @param fwBitmap
         * @param fwBombBitmap
         */
        public void setFwBitmap(Bitmap fwBitmap, Bitmap fwBombBitmap){
            bmp_fw=fwBitmap;
            bmp_fw_bomb=fwBombBitmap;
        }

        public void setFwMoveOffset(float totalOffset,float perOffset){
            y_total_offset=totalOffset;
            y_per_offset=perOffset;
        }

        public void setX(float x){
            this.x=x;
        }

        void move() {
            switch (state) {
                case TRANSLATE:
                    translate();
                    break;
                case SCALE:
                    scale();
                    break;
                case ALPHA:
                    alpha();
                    break;
                case STAY:
                    stay();
                    break;
                default:
                    break;
            }
        }

        private void translate() {
            y_current_offset -= y_per_offset;
            LogUtils.i(TAG, "translate y_current_offset :" + y_current_offset);
            if (y_current_offset < -y_total_offset) {  //移动到指定位置了，开始爆炸
                state = SCALE;
                scale_current = 0;
                y_current_offset = 0;
                waitCount = 0;
                alpha_current = alpha_total;
                state = SCALE;
            }
        }

        private void scale() {
            scale_current += scale_per;
            LogUtils.i(TAG, "scale scale_current :" + scale_current);
            if (scale_current > scale_total) {
                scale_current = 0;
                y_current_offset = 0;
                waitCount = 0;
                alpha_current = alpha_total;
                state = ALPHA;
            }

        }

        public void alpha() {
            alpha_current -= alpha_per;
            LogUtils.i(TAG, "alpha alpha_current :" + alpha_current);
            if (alpha_current < 0) {
                alpha_current = alpha_total;
                y_current_offset = 0;
                waitCount = 0;
                alpha_current = 0;
                state = STAY;
            }
        }

        private void stay() {
            waitCount += 1;
            if (waitCount > FRAME_FW_WAIT) {
                scale_current = 0;
                y_current_offset = 0;
                waitCount = 0;
                alpha_current = alpha_total;
                state = TRANSLATE;
            }
        }

        void draw(Canvas canvas) {
            // LogUtils.i(TAG,"x :"+x+",y :"+y+",y_current_offset : "+y_current_offset);
            if(canvas==null||bmp_fw==null||bmp_fw_bomb==null||matrix==null||paint==null){
                return;
            }
            if (y == 0) {
                // y = (mHeight - bmp.getHeight()) / 2;
                y=y_house;
            }
            if (state == TRANSLATE) {
                matrix.reset();
                matrix.postTranslate(x, y + y_current_offset);
                canvas.drawBitmap(bmp_fw, matrix, null);
            } else if (state == SCALE) {
                LogUtils.i(TAG, "draw x_bomb :" + x_bomb + " ,y_total_offset :" + y_total_offset);
                matrix.reset();
                matrix.setScale(scale_current, scale_current, bmp_fw_bomb.getWidth() / 2, bmp_fw_bomb.getHeight() / 2);
                if (x_bomb == 0) {
                    x_bomb = x + (bmp_fw.getWidth() - bmp_fw_bomb.getWidth()) / 2;
                }
                matrix.postTranslate(x_bomb, y - y_total_offset);
                canvas.drawBitmap(bmp_fw_bomb, matrix, null);
            } else if (state == ALPHA) {
                LogUtils.i(TAG, "draw x_bomb :" + x_bomb + " ,y_total_offset :" + y_total_offset);
                matrix.reset();
                matrix.postTranslate(x_bomb, y - y_total_offset);
                paint.setAlpha((int) (255 * alpha_current));
                canvas.drawBitmap(bmp_fw_bomb, matrix, paint);
            } else {
            }
        }
    }
}
