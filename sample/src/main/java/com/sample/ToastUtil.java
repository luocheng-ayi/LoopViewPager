package com.sample;

import android.widget.Toast;

public class ToastUtil {

    private static Toast mToast;

    static {
        mToast = Toast.makeText(Application.getContext(), "", Toast.LENGTH_SHORT);
    }

    public static void showToast(CharSequence s) {
        mToast.setText(s);
        mToast.show();
    }
}
