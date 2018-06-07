package com.wning.demo.mvp.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by wning on 16/7/17.
 */

public abstract class BaseMVPActivity<V extends BaseView,T extends BasePresenter<V>> extends AppCompatActivity {


    public T presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = initPresenter();
        presenter.attach((V)this);
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        presenter.attach((V)this);
//    }




    @Override
    protected void onDestroy() {
        presenter.detach();
        super.onDestroy();
    }

    public abstract T initPresenter();
}
