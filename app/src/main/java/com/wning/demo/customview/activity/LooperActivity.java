package com.wning.demo.customview.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import com.wning.demo.BaseActivity;
import com.wning.demo.R;
import com.wning.demo.logprint.LogPrinter;

import butterknife.BindView;
import butterknife.OnClick;

public class LooperActivity extends BaseActivity {

    @BindView(R.id.test)
    Button test;


    private Handler mMainHandler,mChildHandler;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_looper;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LogPrinter.enable(this);

        mMainHandler =new Handler(){
            @Override
            public void handleMessage(Message msg) {
                LogPrinter.log("Main Thread Got an incoming message from the child thread - "
                        + (String) msg. obj);
            }
        };

        new ChildThead().start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogPrinter.disable();
        mChildHandler.getLooper().quit();
    }

    @OnClick(R.id.test)
    public void Onclick(View v){
        Message childMsg = mChildHandler.obtainMessage();
        childMsg. obj = mMainHandler.getLooper().getThread().getName() + " says Hello";
        mChildHandler.sendMessage(childMsg);

        LogPrinter. log("Main Thead Send a message to the child thread - " + (String)childMsg. obj);
    }

    private class ChildThead extends Thread{
        @Override
        public void run() {

            Looper.prepare();

            mChildHandler=new Handler(){
                @Override
                public void handleMessage(Message msg) {
                    LogPrinter.log("Child Thread Got an incoming message from the main thread - " + (String)msg.obj);


                    LogPrinter.log("Child Thread do something......................");

                    try {

                        //在子线程中可以做一些耗时的工作
                        sleep(2000);


                        Message toMain = mMainHandler.obtainMessage();
                        toMain. obj = "This is " + this.getLooper().getThread().getName() +
                                ".  Did you send me \"" + (String)msg.obj + "\"?";

                        mMainHandler.sendMessage(toMain);

                        LogPrinter.log("Child Thread Send a message to the main thread - " + (String)toMain.obj);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            };

            Looper.loop();

        }
    }
}
