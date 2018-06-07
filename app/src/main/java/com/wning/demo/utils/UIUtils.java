package com.wning.demo.utils;

import android.app.Dialog;
import android.content.Context;

import com.guagua.modules.utils.LogUtils;
import com.wning.demo.widget.AnimLoadingDialog;

/**
 * Created by wning on 16/7/17.
 */

public class UIUtils {

    private static final String TAG="UIUtils";

    /**
     * 设置dialog
     */
    public static Dialog showAnimLoading(Context context, boolean canWatchOutsideTouch, boolean dimBehindEnabled) {
        AnimLoadingDialog dialog = new AnimLoadingDialog(context).setWatchOutsideTouch(canWatchOutsideTouch).setDimBehindEnabled(dimBehindEnabled)
                .setCentered(true);
        dialog.show();
        LogUtils.i(TAG, "loadingDialog show context = " + context + ", dialog = " + dialog);
        return dialog;
    }

    public static void dismissAnimLoading(Dialog dialog) {
        LogUtils.i(TAG, "loadingDialog dismiss dialog = " + dialog);
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
