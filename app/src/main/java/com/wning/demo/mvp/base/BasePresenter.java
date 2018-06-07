package com.wning.demo.mvp.base;

/**
 * Created by wning on 16/7/17.
 */

public abstract class BasePresenter <T extends BaseView>{

    public T mView;

    public void attach(T mView){
        this.mView = mView;
    }

    public void detach(){
        mView = null;
    }
}
