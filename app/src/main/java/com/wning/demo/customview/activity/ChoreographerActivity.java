package com.wning.demo.customview.activity;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.Choreographer;

import androidx.viewbinding.ViewBinding;

import com.guagua.modules.utils.LogUtils;
import com.wning.demo.BaseActivity;
import com.wning.demo.R;
import com.wning.demo.databinding.ActivityLooperBinding;

public class ChoreographerActivity extends BaseActivity<ActivityLooperBinding> {


   // private Choreographer mChoreographer;
    private ChoreographerFrameCallback mFrameCallback;




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



    private class ChoreographerFrameCallback implements Choreographer.FrameCallback{
        @Override
        public void doFrame(long frameTimeNanos) {
            long currentTime = SystemClock.uptimeMillis();


            LogUtils.i(TAG,"frameTimeNanos :"+frameTimeNanos/1000000+",currentTime :"+currentTime);

            Choreographer.getInstance().postFrameCallback(this);
        }
    }
}
