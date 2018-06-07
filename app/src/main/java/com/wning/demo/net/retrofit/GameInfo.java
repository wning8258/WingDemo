package com.wning.demo.net.retrofit;

import android.text.TextUtils;

import com.wning.demo.gifteffect.BaseBean;

import org.json.JSONObject;

/**
 * Created by wning on 2016/10/18.
 */

public class GameInfo extends BaseBean {

    //豪车漂移初始化json参数的key
    public static final String MCAR_INIT_ROLL = "roll";
    public static final String MCAR_INIT_STATUS = "status";
    public static final String MCAR_INIT_INTERVAL = "interval";



    private boolean isRoll;
    private boolean isShow;
    private long rollInterval;

    private String message;
    private Object content;
    private int state;

    public GameInfo() {

    }

    public GameInfo(JSONObject json) {
    }

    public boolean isShow() {
//        if (mSwitch != 1) {
//            return false;
//        }
        return isShow;
    }

    public boolean isScroll() {
        if (!isShow()) {
            return false;
        }
        return isRoll;
    }

    public long getRollInterval() {
        return rollInterval;
    }





    class GameInfoItem{
        public String icon;
        public String actId;
        public String title;
        public String navshow;
        //public String init;
        public String from;
        public String type;
        public String showtype;
        public String url;
        public String jump;
    }
}

