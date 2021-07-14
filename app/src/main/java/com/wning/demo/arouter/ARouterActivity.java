package com.wning.demo.arouter;

import androidx.viewbinding.ViewBinding;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.wning.demo.BaseActivity;
import com.wning.demo.R;
import com.wning.demo.databinding.ActivityArouterBinding;
import com.wning.demo.databinding.ActivityLooperBinding;

@Route(path = "/test/activity")
public class ARouterActivity extends BaseActivity<ActivityArouterBinding>{

    @Autowired
    String key;



    //Arouter Master

}
