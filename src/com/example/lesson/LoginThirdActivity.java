package com.example.lesson;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lesson.R;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import static com.mob.tools.utils.R.getStringRes;

public class LoginThirdActivity extends Activity implements View.OnClickListener{

    private TimeCount time;
    private EditText phonenumber;
    private EditText password;
    private Button back;
    private EditText cord;
    private String iPhone;
    private String iCord;
    private String iPassword;
    private Button getcord;
    private Button queren;
    private boolean flag = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity_third);
        SMSSDK.initSDK(this, "14b94cda79db0", "68bb646b370a4c0889967f56afa8353c");
        initView();
        EventHandler eh=new EventHandler(){
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }
        };
        SMSSDK.registerEventHandler(eh);
        phonenumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()==0)
                    queren.setBackgroundColor(Color.parseColor("#d3d3d3"));
                else {queren.setBackgroundColor(Color.parseColor("#33ccff"));
                    queren.setEnabled(true);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    public void initView(){
        phonenumber=(EditText)findViewById(R.id.third_et_phonenumber);
        password=(EditText)findViewById(R.id.third_et_password);
        cord=(EditText)findViewById(R.id.third_et_cord) ;
        getcord=(Button)findViewById(R.id.third_btn_getcord);
        time=new TimeCount(60000,1000,getcord);
        queren=(Button)findViewById(R.id.third_btn_queren);
        queren.setBackgroundColor(Color.parseColor("#d3d3d3"));
        queren.setEnabled(false);
        back=(Button)findViewById(R.id.third_btn_back);
        back.setOnClickListener(this);
        queren.setOnClickListener(this);
        getcord.setOnClickListener(this);

    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            // TODO Auto-generated method stub
            super.handleMessage(msg);
            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;
            Log.e("event", "event="+event);
            if (result == SMSSDK.RESULT_COMPLETE) {
                //短信注册成功后，返回MainActivity,然后提示新好友
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//提交验证码成功,验证通过
                    Toast.makeText(getApplicationContext(), "修改密码成功", Toast.LENGTH_SHORT).show();
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){//服务器验证码发送成功
                    Toast.makeText(getApplicationContext(), "验证码已经发送", Toast.LENGTH_SHORT).show();
                }
                else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES)
                {//返回支持发送验证码的国家列表
                    Toast.makeText(getApplicationContext(), "获取国家列表成功", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                if(flag){
                    Toast.makeText(LoginThirdActivity.this, "验证码获取失败，请重新获取", Toast.LENGTH_SHORT).show();
                    phonenumber.requestFocus();
                }else{
                    ((Throwable) data).printStackTrace();
                    int resId = getStringRes(LoginThirdActivity.this, "smssdk_network_error");
                    Toast.makeText(LoginThirdActivity.this, "验证码错误", Toast.LENGTH_SHORT).show();
                    if (resId > 0) {
                        Toast.makeText(LoginThirdActivity.this, resId, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {case R.id.third_btn_back:
            LoginThirdActivity.this.finish();
            break;
            case R.id.third_btn_queren:
                if(!TextUtils.isEmpty(cord.getText().toString().trim())){
                    if(cord.getText().toString().trim().length()==4){
                        iCord = cord.getText().toString().trim();
                        SMSSDK.submitVerificationCode("86", iPhone, iCord);
                        flag=false;
                    }
                    else{
                        Toast.makeText(LoginThirdActivity.this, "请输入完整验证码", Toast.LENGTH_LONG).show();
                        cord.requestFocus();
                    }
                }else{
                    Toast.makeText(LoginThirdActivity.this, "请输入验证码", Toast.LENGTH_LONG).show();
                    cord.requestFocus();
                }
                break;
            case R.id.third_btn_getcord:
                iPhone = phonenumber.getText().toString().trim();
                iPassword=password.getText().toString().trim();
                if(!iPhone.isEmpty()&&!iPassword.isEmpty())
                {
                    if(iPhone.length()==11&&iPassword.length()>=6&&iPassword.length()<=20){
                        SMSSDK.getVerificationCode("86",iPhone);
                        cord.requestFocus();
                        time.start();
                    }
                    else if (iPhone.length()<11){
                        Toast.makeText(LoginThirdActivity.this, "请输入完整电话号码", Toast.LENGTH_LONG).show();
                        phonenumber.requestFocus();
                    }
                    else if (iPassword.length()<6||iPassword.length()>20)
                    {Toast.makeText(getApplicationContext(),"密码必须在6~20位之间",Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(LoginThirdActivity.this, "请输入您的电话号码或密码", Toast.LENGTH_LONG).show();
                    phonenumber.requestFocus();
                }
                break;
            default:break;
        }
    }
}
