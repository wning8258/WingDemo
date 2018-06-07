package com.wning.demo.customview.activity;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.Choreographer;

import com.wning.demo.BaseActivity;
import com.wning.demo.R;
import com.wning.demo.logprint.LogPrinter;

public class ChoreographerActivity extends BaseActivity {


   // private Choreographer mChoreographer;
    private ChoreographerFrameCallback mFrameCallback;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_looper;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       // mChoreographer= Choreographer.getInstance();

        mFrameCallback=new ChoreographerFrameCallback();

        Choreographer.getInstance().postFrameCallback(mFrameCallback);

    }

    @Override
    protected void onDestroy() {
        Choreographer.getInstance().removeFrameCallback(mFrameCallback);
        super.onDestroy();

    }

    @Override
    protected boolean isNeedLogPrinter() {
        return true;
    }


    private class ChoreographerFrameCallback implements Choreographer.FrameCallback{
        @Override
        public void doFrame(long frameTimeNanos) {
            long currentTime = SystemClock.uptimeMillis();


            LogPrinter.log("frameTimeNanos :"+frameTimeNanos/1000000+",currentTime :"+currentTime);

            Choreographer.getInstance().postFrameCallback(this);
        }
    }
}
