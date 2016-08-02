package com.example.lesson;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.lesson.R;
import com.example.lesson.CustomImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * Created by lenovo on 2016/7/26.
 */
public class SecondActivity extends Activity implements View.OnClickListener {
    private ImageView btn_goback;
    private RelativeLayout sec_tobackone;
    private Button btn_sign;
    private TextView sec_sign;
    private  TextView sec_signone;
    private RelativeLayout sec_tobacktwo;
    private  TextView sec_signtwo;
    private RelativeLayout sec_tobackthree;
    private RelativeLayout sec_tobackfour;
    private TextView sec_sex,sec_job;
    View layout,layout1;
    Dialog dialog,dialog1;
    private ImageView layoutsex,layoutjob;
    private RadioButton rbt1, rbt2,rbt3,rbt4;
    private RelativeLayout sec_tobackfive;
    private  TextView sec_signthree;

    private ImageView imghead;
    private View content;
    private static final int TAKE_PICTURE = 0;
    private static final int RESULT_LOAD_IMAGE = 5;
    private static final int CUT_PHOTO_REQUEST_CODE = 6;
    private String path = "";
    private Uri photoUri;
    private  TextView titlename;




    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.shezhi_activity_second);

        content = LayoutInflater.from(this).inflate(
                R.layout.activity_main, null);
        //从本地读取
        SharedPreferences sharedPreferences = getSharedPreferences("jqr",
                Context.MODE_PRIVATE);
        btn_goback = (ImageView) findViewById(R.id.shenfenxinxi_btn_goback);
        btn_goback.setOnClickListener(this);

        titlename=(TextView)findViewById(R.id.shenfenxinxi_tv_name) ;
        String sign6 = sharedPreferences.getString("sign1", "");
        titlename.setText(sign6);

        imghead=(ImageView)findViewById(R.id.shenfenxinxi_iv_head);
        //从本地读取图片
        Bitmap bm=  getLoacalBitmap("/sdcard/formats/Image.JPEG");//FilePath为图片路径及名字
        imghead.setImageBitmap(CustomImageView.makeRoundCorner(bm));
        imghead.setOnClickListener(this);

        btn_sign = (Button) findViewById(R.id.shenfenxinxi_btn_sign);
        sec_sign = (TextView) findViewById(R.id.shenfenxinxi_tv_sign);
        String sign = sharedPreferences.getString("sign", "");
        sec_sign.setText(sign);
        btn_sign.setOnClickListener(this);

        sec_tobackone=(RelativeLayout)findViewById(R.id.shenfenxinxi_rl_tobackname);
        sec_signone=(TextView) findViewById(R.id.shenfenxinxi_tv_tobackname);
        String sign1 = sharedPreferences.getString("sign1", "");
        sec_signone.setText(sign1);
        sec_tobackone.setOnClickListener(this);

        sec_tobacktwo=(RelativeLayout)findViewById(R.id.shenfenxinxi_rl_tobacknumber);
        sec_signtwo=(TextView) findViewById(R.id.shenfenxinxi_tv_tobacknumber);
        String sign2 = sharedPreferences.getString("sign2", "");
        sec_signtwo.setText(sign2);
        sec_tobacktwo.setOnClickListener(this);

        sec_tobackthree=(RelativeLayout)findViewById(R.id.shenfenxinxi_rl_tobacksex);
        sec_sex=(TextView)findViewById(R.id.shenfenxinxi_tv_tobacksex);
        String sign4 = sharedPreferences.getString("sign4", "");
        sec_sex.setText(sign4);
        sec_tobackthree.setOnClickListener(this);


        sec_tobackfour = (RelativeLayout) findViewById(R.id.shenfenxinxi_rl_tobackjob);
        sec_job=(TextView)findViewById(R.id.shenfenxinxi_tv_tobackjob);
        String sign5 = sharedPreferences.getString("sign5", "");
        sec_job.setText(sign5);
        sec_tobackfour.setOnClickListener(this);

        sec_tobackfive=(RelativeLayout) findViewById(R.id.shenfenxinxi_rl_tobackgrjj);
        sec_signthree=(TextView) findViewById(R.id.shenfenxinxi_tv_tobackgrjj);
        String sign3 = sharedPreferences.getString("sign3", "");
        sec_signthree.setText(sign3);
        sec_tobackfive.setOnClickListener(this);

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //   可以根据多个请求代码来作相应的操作

        //照片
        if (resultCode == RESULT_OK) {

            switch (requestCode) { // resultCode为回传的标记，我在B中回传的是RESULT_OK

                case TAKE_PICTURE:

                    startPhotoZoom(photoUri);

                    break;

                case CUT_PHOTO_REQUEST_CODE:
                    if (resultCode == RESULT_OK) {
                        Bitmap bitmap = Bimp.getLoacalBitmap(path);

                        imghead.setImageBitmap(CustomImageView.makeRoundCorner(bitmap));

                    }
                    break;
                case RESULT_LOAD_IMAGE:
                    if (resultCode == RESULT_OK && null != data) {
                        Uri uri = data.getData();
                        if (uri != null) {
                            startPhotoZoom(uri);
                        }
                    }
                    break;

            }
        }
        else{
            Bundle b = data.getExtras(); // data为B中回传的Intent

            if (1 == resultCode) {
                String str1 = b.getString("sign");
                sec_sign.setText(str1);

            }
            if (2 == resultCode) {
                String str2 = b.getString("sign1");
                sec_signone.setText(str2);
                titlename.setText(str2);

            }
            if (3 == resultCode) {
                String str3 = b.getString("sign2");
                sec_signtwo.setText(str3);

            }
            if (4 == resultCode) {
                String str4 = b.getString("sign3");
                sec_signthree.setText(str4);

            }

        }

        super.onActivityResult(requestCode,resultCode,data);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shenfenxinxi_btn_goback:
                finish();


                break;
            case R.id.shenfenxinxi_iv_head:
                new PopupWindows(SecondActivity.this, content);
                break;
            case R.id.shenfenxinxi_btn_sign:
                Intent intent4 = new Intent(SecondActivity.this, SecondOneActivity.class);
                //关键点来了，使用startActivityForResult来启动
                Bundle bundle = new Bundle();
                bundle.putString("sign", sec_sign.getText().toString());
                intent4.putExtras(bundle);
                startActivityForResult(intent4, 100);
                break;
            case R.id.shenfenxinxi_rl_tobackname:
                Intent intent1 = new Intent(SecondActivity.this, SecondTwoActivity.class);
                //关键点来了，使用startActivityForResult来启动
                startActivityForResult(intent1, 100);
                break;
            case R.id.shenfenxinxi_rl_tobacknumber:
                Intent intent2 = new Intent(SecondActivity.this, SecondThreeAcytivity.class);
                //关键点来了，使用startActivityForResult来启动
                startActivityForResult(intent2, 100);
                break;
            case R.id.shenfenxinxi_rl_tobacksex:
                LayoutInflater inflater = LayoutInflater.from(SecondActivity.this);
                //xml转换成视图
                layout = inflater.inflate(R.layout.dialog_sex, null);
                AlertDialog.Builder builder = new AlertDialog.Builder(SecondActivity.this);
                builder.setView(layout);
                dialog = builder.show();
                dialog.show();

                rbt1=(RadioButton)layout.findViewById(R.id.sex_rbt01);
                rbt1.setOnClickListener(new RadioButton.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        sec_sex.setText("男");
                        SharedPreferences preferences=getSharedPreferences("jqr", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor=preferences.edit();
                        editor.putString("sign4","男");
                        editor.commit();
                    }
                });
                rbt2=(RadioButton)layout.findViewById(R.id.sex_rbt02);
                rbt2.setOnClickListener(new RadioButton.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        sec_sex.setText("女");
                        SharedPreferences preferences=getSharedPreferences("jqr", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor=preferences.edit();
                        editor.putString("sign4","女");
                        editor.commit();
                    }
                });
                break;
            case R.id.shenfenxinxi_rl_tobackjob:
                LayoutInflater inflater1 = LayoutInflater.from(SecondActivity.this);
                //xml转换成视图
                layout1 = inflater1.inflate(R.layout.dialog_job, null);
                AlertDialog.Builder builder1 = new AlertDialog.Builder(SecondActivity.this);
                builder1.setView(layout1);
                dialog1 = builder1.show();
                dialog1.show();
                rbt3=(RadioButton)layout1.findViewById(R.id.job_rbt03);
                rbt3.setOnClickListener(new RadioButton.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        dialog1.dismiss();
                        sec_job.setText("老师");
                        SharedPreferences preferences=getSharedPreferences("jqr", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor=preferences.edit();
                        editor.putString("sign5","老师");
                        editor.commit();
                    }
                });
                rbt4=(RadioButton)layout1.findViewById(R.id.job_rbt04);
                rbt4.setOnClickListener(new RadioButton.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        dialog1.dismiss();
                        sec_job.setText("学生");
                        SharedPreferences preferences=getSharedPreferences("jqr", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor=preferences.edit();
                        editor.putString("sign5","学生");
                        editor.commit();
                    }
                });
                break;
            case R.id.shenfenxinxi_rl_tobackgrjj:
                Intent intent3 = new Intent(SecondActivity.this, SecondFourActivity.class);
                //关键点来了，使用startActivityForResult来启动
                startActivityForResult(intent3, 100);

                break;
        }
    }
    Uri imageUri;
    //照片
    private void startPhotoZoom(Uri uri) {
        try {
            SimpleDateFormat sDateFormat = new SimpleDateFormat(
                    "yyyyMMddhhmmss");
            String address = sDateFormat.format(new java.util.Date());
            if (!FileUtils.isFileExist("")) {
                FileUtils.createSDDir("");
            }
            path = FileUtils.SDPATH +"Image.JPEG";
            imageUri = Uri.parse("file:///sdcard/formats/Image.JPEG");
            final Intent intent = new Intent("com.android.camera.action.CROP");
            intent.setDataAndType(uri, "image/*");
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("outputX", 480);
            intent.putExtra("outputY", 480);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            intent.putExtra("outputFormat",
                    Bitmap.CompressFormat.JPEG.toString());
            intent.putExtra("noFaceDetection", false);
            intent.putExtra("return-data", false);
            startActivityForResult(intent, CUT_PHOTO_REQUEST_CODE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public class PopupWindows extends PopupWindow {

        public PopupWindows(Context mContext, View parent) {

            View view = View
                    .inflate(mContext, R.layout.item_popupwindows, null);
            view.startAnimation(AnimationUtils.loadAnimation(mContext,
                    R.anim.fade_ins));
            LinearLayout ll_popup = (LinearLayout) view
                    .findViewById(R.id.ll_popup);
            // ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
            // R.anim.push_bottom_in_2));

            setWidth(ViewGroup.LayoutParams.FILL_PARENT);
            setHeight(ViewGroup.LayoutParams.FILL_PARENT);
            setBackgroundDrawable(new BitmapDrawable());
            setFocusable(true);
            setOutsideTouchable(true);
            setContentView(view);
            showAtLocation(parent, Gravity.BOTTOM, 0, 0);
            update();

            Button bt1 = (Button) view
                    .findViewById(R.id.item_popupwindows_camera);
            Button bt2 = (Button) view
                    .findViewById(R.id.item_popupwindows_Photo);
            Button bt3 = (Button) view
                    .findViewById(R.id.item_popupwindows_cancel);
            bt1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    photo();
                    dismiss();
                }
            });
            bt2.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent i = new Intent(
                            Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, RESULT_LOAD_IMAGE);
                    dismiss();
                }
            });
            bt3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dismiss();
                }
            });

        }
    }

    public void photo() {
        try {
            Intent openCameraIntent = new Intent(
                    MediaStore.ACTION_IMAGE_CAPTURE);

            String sdcardState = Environment.getExternalStorageState();
            String sdcardPathDir = Environment
                    .getExternalStorageDirectory().getPath() + "/tempImage/";
            File file = null;
            if (Environment.MEDIA_MOUNTED.equals(sdcardState)) {
                File fileDir = new File(sdcardPathDir);
                if (!fileDir.exists()) {
                    fileDir.mkdirs();
                }
                file = new File(sdcardPathDir + System.currentTimeMillis()
                        + "Image.JPEG");
            }
            if (file != null) {
                path = file.getPath();
                photoUri = Uri.fromFile(file);
                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);

                startActivityForResult(openCameraIntent, TAKE_PICTURE);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //从本地读取图片
    public static Bitmap getLoacalBitmap(String url) {
        try {
            FileInputStream fis = new FileInputStream(url);
            return BitmapFactory.decodeStream(fis);  ///把流转化为Bitmap图片

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

}



