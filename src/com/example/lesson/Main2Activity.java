package com.example.lesson;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lesson.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class Main2Activity extends Activity implements AdapterView.OnItemClickListener {

    private GridView gridview;
    private GridAdapter adapter;
    /** 发送 **/
    private TextView activity_selectimg_send;
    private ImageView back;
    /** EditText **/
    private EditText comment_content;
    private String temp;
    private Button selectimg_bt_content_type, selectimg_bt_search;
    private LinearLayout selectimg_relativeLayout_below;
    private ScrollView activity_selectimg_scrollView;
    /** 水平ScrollView **/
    private HorizontalScrollView selectimg_horizontalScrollView;

    private List<String> categoryList;

    private float dp;
    /** 设置TitleBar **/
    private RelativeLayout titlebar_ll_backBTN;
    private TextView activitymain2_tv_title;
    private TextView activitymain2_tv_editTitle;

    /** 图片list **/
    public List<Bitmap> bmp = new ArrayList<Bitmap>();
    public List<String> drr = new ArrayList<String>();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_pics_selecting);
        getIntent().getStringExtra("name");//获得打包的数据
        Init();
        RelativeLayout titlebar_ll_backBTN=(RelativeLayout) findViewById(R.id.titlebar_ll_backBTN2);
        TextView activitymain2_tv_title=(TextView)findViewById(R.id.activitymain2_tv_title2);
        TextView activitymain2_tv_editTitle=(TextView)findViewById(R.id.activitymain2_tv_editTitle2);

        //titlebar返回键监听
        titlebar_ll_backBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //编辑键不可见
        activitymain2_tv_editTitle.setVisibility(View.INVISIBLE);

        //改标题
        Intent intent=getIntent();
        String activityname=intent.getStringExtra("name");
        activitymain2_tv_title.setText(activityname);
    }

    List<String> urList = new ArrayList<String>();

    public void Init() {
        dp = getResources().getDimension(R.dimen.dp);//获取指定资源id对应的尺寸
        comment_content = (EditText) findViewById(R.id.activityPicsSelecting_et_commentContent);
        comment_content.setFocusable(true);//接收键盘事件
        comment_content.setFocusableInTouchMode(true);//接收触摸事件

        back = (ImageView) findViewById(R.id.activityPicsSelecting_iv_backIcon);
        back.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Main2Activity.this.finish();//点击退出
            }
        });

        selectimg_horizontalScrollView = (HorizontalScrollView) findViewById(R.id.activityPicsSelecting_hs_horizontalScrollView);
        gridview = (GridView) findViewById(R.id.activityPicsSelecting_gv_noScrollgridview);
        gridview.setSelector(new ColorDrawable(Color.TRANSPARENT));//设置背景色
        gridviewInit();//初始化gridview

        //给EditText设置监听器
        comment_content.addTextChangedListener(new TextWatcher() {

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

                temp = s.toString();
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void afterTextChanged(Editable s) {

            }
        });
        activity_selectimg_send = (TextView) findViewById(R.id.activityPicsSelecting_tv_selectimgSend);
        //监听
        activity_selectimg_send.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                if (bmp.size() < 1) {
                    Toast.makeText(getApplicationContext(), "图片为空",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                // 收起软键盘
                ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                        .hideSoftInputFromWindow(Main2Activity.this
                                        .getCurrentFocus().getWindowToken(),
                                InputMethodManager.HIDE_NOT_ALWAYS);

                String content = comment_content.getText().toString().trim();//去空格
                if (content.equals("")) {
                    Toast.makeText(getApplicationContext(), "内容为空",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                for (int i = 0; i < drr.size(); i++) {
                    urList.add(drr.get(i));
                }
                activity_selectimg_send.setEnabled(false);//设置不可用
            }
        });

        selectimg_relativeLayout_below = (LinearLayout) findViewById(R.id.activityPicsSelecting_ll_relativeLayoutBelow);
        activity_selectimg_scrollView = (ScrollView) findViewById(R.id.activityPicsSelecting_sv_selectimg);
        activity_selectimg_scrollView.setVerticalScrollBarEnabled(false);//隐藏滚动条

        final View decorView = getWindow().getDecorView();//屏幕截图
        final WindowManager wm = this.getWindowManager();

        decorView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {

                    public void onGlobalLayout() {
                        @SuppressWarnings("deprecation")
                        int displayheight = wm.getDefaultDisplay().getHeight();
                        Rect rect = new Rect();
                        decorView.getWindowVisibleDisplayFrame(rect);
                        int dynamicHight = rect.bottom - rect.top;
                        float ratio = (float) dynamicHight
                                / (float) displayheight;

                        if (ratio > 0.2f && ratio < 0.6f) {
                            selectimg_relativeLayout_below
                                    .setVisibility(View.VISIBLE);
                            activity_selectimg_scrollView.scrollBy(0,
                                    activity_selectimg_scrollView.getHeight());
                        } else {
                            selectimg_relativeLayout_below
                                    .setVisibility(View.GONE);
                        }
                    }
                });

    }

    /***
     * 初始化GridView
     */
    public void gridviewInit() {
        adapter = new GridAdapter(this);
        adapter.setSelectedPosition(0);
        int size = 0;
        if (bmp.size() < 6) {
            size = bmp.size() + 1;
        } else {
            size = bmp.size();
        }
        ViewGroup.LayoutParams params = gridview.getLayoutParams();
        final int width = size * (int) (dp * 9.4f);
        params.width = width;
        gridview.setLayoutParams(params);
        gridview.setColumnWidth((int) (dp * 9.4f));
        gridview.setStretchMode(GridView.NO_STRETCH);//设置gridview中的条目以什么缩放模式去填充空间
        gridview.setNumColumns(size);
        gridview.setAdapter(adapter);
        gridview.setOnItemClickListener(this);

        selectimg_horizontalScrollView.getViewTreeObserver()
                .addOnPreDrawListener(
                        new ViewTreeObserver.OnPreDrawListener() {
                            public boolean onPreDraw() {
                                selectimg_horizontalScrollView.scrollTo(width,
                                        0);
                                selectimg_horizontalScrollView
                                        .getViewTreeObserver()
                                        .removeOnPreDrawListener(this);
                                return false;
                            }
                        });
    }

    protected void onPause() {
        super.onPause();
    }

    public class GridAdapter extends BaseAdapter {
        private LayoutInflater listContainer;
        private int selectedPosition = -1;
        private boolean shape;

        public boolean isShape() {
            return shape;
        }

        public void setShape(boolean shape) {
            this.shape = shape;
        }

        public class ViewHolder {
            public ImageView image;
            public Button bt;
        }

        public GridAdapter(Context context) {
            listContainer = LayoutInflater.from(context);//讲xml布局转换为view对象
        }

        public int getCount() {
            if (bmp.size() < 6) {
                return bmp.size() + 1;
            } else {
                return bmp.size();
            }
        }

        public Object getItem(int arg0) {

            return null;
        }

        public long getItemId(int arg0) {

            return 0;
        }

        public void setSelectedPosition(int position) {
            selectedPosition = position;
        }

        public int getSelectedPosition() {
            return selectedPosition;
        }

        /**
         * ListView Item
         */
        public View getView(int position, View convertView, ViewGroup parent) {
            final int sign = position;
            ViewHolder holder = null;

            if (convertView == null) {
                holder = new ViewHolder();
                convertView = listContainer.inflate(
                        R.layout.item_published_grida, null);
                //将子view放到holder中
                holder.image = (ImageView) convertView
                        .findViewById(R.id.itemPublishedGrida_iv_gridaImage);
                holder.bt = (Button) convertView
                        .findViewById(R.id.itemPublishedGrida_btn_grida);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            if (position == bmp.size()) {
                //设置图片显示
                holder.image.setImageBitmap(BitmapFactory.decodeResource(
                        getResources(), R.drawable.icon_addpic_unfocused));
                holder.bt.setVisibility(View.GONE);
                if (position == 6) {
                    holder.image.setVisibility(View.GONE);
                }
            } else {
                holder.image.setImageBitmap(bmp.get(position));
                holder.bt.setOnClickListener(new View.OnClickListener() {

                    public void onClick(View v) {
                        PhotoActivity.bitmap.remove(sign);
                        bmp.get(sign).recycle();
                        bmp.remove(sign);
                        drr.remove(sign);

                        gridviewInit();
                    }
                });
            }

            return convertView;
        }
    }

    public class PopupWindows extends PopupWindow {

        public PopupWindows(Context mContext, View parent) {

            View view = View
                    .inflate(mContext, R.layout.item_popup_windows, null);
            view.startAnimation(AnimationUtils.loadAnimation(mContext,
                    R.anim.fade_ins));
            LinearLayout ll_popup = (LinearLayout) view
                    .findViewById(R.id.itemPopupWindows_ll_popup);
            // ll_popup.startAnimation(AnimationUtils.loadAnimation(mContext,
            // R.anim.push_bottom_in_2));

            setWidth(ViewGroup.LayoutParams.FILL_PARENT);
            setHeight(ViewGroup.LayoutParams.FILL_PARENT);
            setBackgroundDrawable(new BitmapDrawable());
            setFocusable(true);
            setOutsideTouchable(true);
            setContentView(view);
            //显示弹窗
            showAtLocation(parent, Gravity.BOTTOM, 0, 0);
            update();

            Button bt1 = (Button) view
                    .findViewById(R.id.itemPopupWindows_btn_CallCamera);
            Button bt2 = (Button) view
                    .findViewById(R.id.itemPopupWindows_btn_CallPhoto);
            Button bt3 = (Button) view
                    .findViewById(R.id.itemPopupWindows_btn_cancel);
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
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    //得到新打开Activity关闭后返回的数据
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

    private static final int TAKE_PICTURE = 0;
    private static final int RESULT_LOAD_IMAGE = 1;
    private static final int CUT_PHOTO_REQUEST_CODE = 2;
    private static final int SELECTIMG_SEARCH = 3;
    private String path = "";
    private Uri photoUri;

    public void photo() {
        try {

            Intent openCameraIntent = new Intent(
                    MediaStore.ACTION_IMAGE_CAPTURE);

            //获取sd卡的状态
            String sdcardState = Environment.getExternalStorageState();
            //获取文件目录
            String sdcardPathDir = android.os.Environment
                    .getExternalStorageDirectory().getPath() + "/tempImage/";
            File file = null;
            if (Environment.MEDIA_MOUNTED.equals(sdcardState)) {
                File fileDir = new File(sdcardPathDir);
                if (!fileDir.exists()) {
                    //建立文件夹
                    fileDir.mkdirs();
                }
                file = new File(sdcardPathDir + System.currentTimeMillis()
                        + ".JPEG");
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PICTURE:
                if (drr.size() < 6 && resultCode == -1) {//拍照
                    startPhotoZoom(photoUri);
                }
                break;
            case RESULT_LOAD_IMAGE:
                if (drr.size() < 6 && resultCode == RESULT_OK && null != data) {//相册返回
                    Uri uri = data.getData();
                    if (uri != null) {
                        startPhotoZoom(uri);
                    }
                }
                break;
            case CUT_PHOTO_REQUEST_CODE:
                if (resultCode == RESULT_OK && null != data) {//剪裁返回
                    Bitmap bitmap = Bimp.getLoacalBitmap(drr.get(drr.size() - 1));
                    PhotoActivity.bitmap.add(bitmap);
                    bitmap = Bimp.createFramedPhoto(480, 480, bitmap,
                            (int) (dp * 1.6f));
                    bmp.add(bitmap);
                    gridviewInit();
                }
                break;
            case SELECTIMG_SEARCH:
                if (resultCode == RESULT_OK && null != data) {
                    String text = "#" + data.getStringExtra("topic") + "#";
                    text = comment_content.getText().toString() + text;
                    comment_content.setText(text);

                    comment_content.getViewTreeObserver().addOnPreDrawListener(
                            new ViewTreeObserver.OnPreDrawListener() {
                                public boolean onPreDraw() {
                                    comment_content.setEnabled(true);
                                    // 设置光标为末尾
                                    CharSequence cs = comment_content.getText();
                                    if (cs instanceof Spannable) {
                                        Spannable spanText = (Spannable) cs;
                                        Selection.setSelection(spanText,
                                                cs.length());
                                    }
                                    comment_content.getViewTreeObserver()
                                            .removeOnPreDrawListener(this);
                                    return false;
                                }
                            });

                }

                break;
        }
    }
    private void startPhotoZoom(Uri uri) {
        try {
            //获取系统时间
            SimpleDateFormat sDateFormat = new SimpleDateFormat(
                    "yyyyMMddhhmmss");
            String address = sDateFormat.format(new java.util.Date());
            if (!FileUtils.isFileExist("")) {
                FileUtils.createSDDir("");
            }
            drr.add(FileUtils.SDPATH + address + ".JPEG");
            Uri imageUri = Uri.parse("file:///sdcard/formats/" + address
                    + ".JPEG");
            final Intent intent = new Intent("com.android.camera.action.CROP");
            //uri地址
            intent.setDataAndType(uri, "image/*");
            intent.putExtra("crop", "true");
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("outputX", 480);
            intent.putExtra("outputY", 480);
            //路径
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
            //格式
            intent.putExtra("outputFormat",
                    Bitmap.CompressFormat.JPEG.toString());
            intent.putExtra("noFaceDetection", false);
            intent.putExtra("return-data", false);
            startActivityForResult(intent, CUT_PHOTO_REQUEST_CODE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void onDestroy() {

        FileUtils.deleteDir(FileUtils.SDPATH);
        FileUtils.deleteDir(FileUtils.SDPATH1);
        //清理缓存
        for (int i = 0; i < bmp.size(); i++) {
            bmp.get(i).recycle();
        }
        for (int i = 0; i < PhotoActivity.bitmap.size(); i++) {
            PhotoActivity.bitmap.get(i).recycle();
        }
        PhotoActivity.bitmap.clear();
        bmp.clear();
        drr.clear();
        super.onDestroy();
    }

    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(Main2Activity.this.getCurrentFocus().getWindowToken()
                        , InputMethodManager.HIDE_NOT_ALWAYS);
        if (arg2 == bmp.size()) {
            String sdcardState = Environment.getExternalStorageState();
            if (Environment.MEDIA_MOUNTED.equals(sdcardState)) {
                new PopupWindows(Main2Activity.this, gridview);
            } else {
                Toast.makeText(getApplicationContext(),
                        "sdcard已拔出，不能选择照片", Toast.LENGTH_SHORT)
                        .show();
            }
        } else {
            Intent intent = new Intent(Main2Activity.this, PhotoActivity.class);

            intent.putExtra("ID", arg2);
            startActivity(intent);
        }
    }

}
