package com.example.lesson;

import android.os.CountDownTimer;
import android.widget.Button;

/**
 * Created by Administrator on 2016/7/6 0006.
 */
    public class TimeCount extends CountDownTimer {
    private Button btnms;

    public TimeCount(long millisInFuture, long countDownInterval, Button btnms) {
        super(millisInFuture, countDownInterval);
        this.btnms = btnms;
    }

    //参数依次为总时长和计时的时间间隔
    @Override
    public void onTick(long millisUntilFinished) {//计时过程显示
        btnms.setEnabled(false);
        btnms.setText((millisUntilFinished / 1000) + "秒");

    }

    @Override
    public void onFinish() {//计时完毕时触发
        btnms.setText("发送验证码");
        btnms.setEnabled(true);

    }
}
