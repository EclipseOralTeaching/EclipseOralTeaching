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

    //��������Ϊ��ʱ���ͼ�ʱ��ʱ����
    @Override
    public void onTick(long millisUntilFinished) {//��ʱ������ʾ
        btnms.setEnabled(false);
        btnms.setText((millisUntilFinished / 1000) + "��");

    }

    @Override
    public void onFinish() {//��ʱ���ʱ����
        btnms.setText("������֤��");
        btnms.setEnabled(true);

    }
}
