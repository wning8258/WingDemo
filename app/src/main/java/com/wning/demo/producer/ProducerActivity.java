package com.wning.demo.producer;

import android.os.Bundle;

import com.guagua.modules.utils.LogUtils;
import com.wning.demo.BaseActivity;
import com.wning.demo.R;

/**
 * Created by wning on 2018/4/2.
 */

public class ProducerActivity extends BaseActivity{
    @Override
    protected int getLayoutId() {
        return R.layout.activity_producer;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        NetworkProducer networkProducer=new NetworkProducer();
        DiskCacheProducer diskCacheProducer=new DiskCacheProducer(networkProducer);
        MemoryCacheProducer memoryCacheProducer=new MemoryCacheProducer(diskCacheProducer);
        memoryCacheProducer.produceResult(new Consumer() {
            @Override
            public void onNewResult() {
                LogUtils.i("wn","Producer onNewResult");

            }

            @Override
            public void onFailResult() {

            }
        });
    }
}
