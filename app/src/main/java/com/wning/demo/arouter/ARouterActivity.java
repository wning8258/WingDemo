package com.wning.demo.arouter;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.wning.demo.BaseActivity;
import com.wning.demo.R;

@Route(path = "/test/activity")
public class ARouterActivity extends BaseActivity{

    @Autowired
    String key;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_arouter;
    }

    //Arouter Master

}
