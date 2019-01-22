package com.wning.demo.mvvm;

import android.app.Activity;
import android.os.Bundle;

import com.wning.demo.R;
import com.wning.demo.databinding.ActivityDatabindingBindingImpl;

import androidx.databinding.DataBindingUtil;


public class DataBindingActivity extends Activity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityDatabindingBindingImpl binding = DataBindingUtil.setContentView(this, R.layout.activity_databinding);

        UserBean user=new UserBean("张三",26);
        binding.setUser(user);

//        LeakThread thread=new LeakThread();
//        thread.start();
    }

//    class LeakThread extends Thread {
//        @Override
//        public void run() {
//            try {
//                Thread.sleep(60 * 60 * 1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }

}
