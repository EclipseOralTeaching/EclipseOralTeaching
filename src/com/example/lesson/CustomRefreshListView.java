package com.example.lesson;


import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.lesson.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by TianYuan on 2016/7/12.
 */
public class CustomRefreshListView extends ListView implements AbsListView.OnScrollListener {

    // ͷ����
    private View headerView;

    // ͷ�����ֵĸ߶�
    private int headerViewHeight;

    // ͷ����ת��ͼƬ
    private ImageView iv_arrow;

    // ͷ������ˢ��ʱ״̬������
    private TextView tv_state;

    // ����ˢ��ʱ�����ʾ�ؼ�
    private TextView tv_time;


    // �ײ�����
    private View footerView;

    // �ײ���תprogressbar
    private ProgressBar pb_rotate;


    // �ײ����ֵĸ߶�
    private int footerViewHeight;


    // ����ʱ��Y����
    private int downY;

    private final int PULL_REFRESH = 0;//����ˢ�µ�״̬
    private final int RELEASE_REFRESH = 1;//�ɿ�ˢ�µ�״̬
    private final int REFRESHING = 2;//����ˢ�µ�״̬

    // ��ǰ����ˢ�´��ڵ�״̬
    private int currentState = PULL_REFRESH;

    // ͷ������������ˢ�¸ı�ʱ��ͼ��Ķ���
    private RotateAnimation upAnimation, downAnimation;

    // ��ǰ�Ƿ��ڼ�������
    private boolean isLoadingMore = false;

    public CustomRefreshListView(Context context) {
        this(context, null);
    }

    public CustomRefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();//��ʼ��
    }

    private void init() {
        //���û�������
        setOnScrollListener(this);
        //��ʼ��ͷ����
        initHeaderView();
        //��ʼ��ͷ������ͼ�����ת����
        initRotateAnimation();
        //��ʼ��Ϊβ����
        initFooterView();
    }


    // ��ʼ��headerView
    private void initHeaderView() {
        headerView = View.inflate(getContext(), R.layout.head_custom_listview, null);
        iv_arrow = (ImageView) headerView.findViewById(R.id.headCustomListView_iv_arrow);
        pb_rotate = (ProgressBar) headerView.findViewById(R.id.headCustomListView_pb_rotate);
        tv_state = (TextView) headerView.findViewById(R.id.headCustomListView_tv_state);
        tv_time = (TextView) headerView.findViewById(R.id.headCustomListView_tv_time);

        //����headView�ĸ߶�
        headerView.measure(0, 0);
        //��ȡ�߶ȣ�������
        headerViewHeight = headerView.getMeasuredHeight();
        //����paddingTop = -headerViewHeight;�������ÿؼ�������
        headerView.setPadding(0, -headerViewHeight, 0, 0);
        //���ͷ����
        addHeaderView(headerView);
    }

    // ��ʼ����ת����
    private void initRotateAnimation() {

        upAnimation = new RotateAnimation(0, -180,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        upAnimation.setDuration(300);//���ó���ʱ����ms��
        upAnimation.setFillAfter(true);

        downAnimation = new RotateAnimation(-180, -360,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        downAnimation.setDuration(300);
        downAnimation.setFillAfter(true);
    }

    //��ʼ���ײ��֣���ͷ����ͬ��
    private void initFooterView() {
        footerView = View.inflate(getContext(), R.layout.foot_custom_listview, null);
        footerView.measure(0, 0);
        footerViewHeight = footerView.getMeasuredHeight();
        footerView.setPadding(0, -footerViewHeight, 0, 0);
        addFooterView(footerView);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //��ȡ����ʱy����
                downY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:

                if (currentState == REFRESHING) {
                    //�����ǰ���ڻ���״̬����������
                    break;
                }
                //��ָ����ƫ����
                int deltaY = (int) (ev.getY() - downY);

                //��ȡ�µ�paddingֵ
                int paddingTop = -headerViewHeight + deltaY;
                if (paddingTop > -headerViewHeight && getFirstVisiblePosition() == 0) {
                    //���»����Ҵ��ڶ���������paddingֵ���÷���ʵ���˶�����������������
                    headerView.setPadding(0, paddingTop, 0, 0);

                    if (paddingTop >= 0 && currentState == PULL_REFRESH) {
                        //������ˢ�½����ɿ�ˢ��״̬
                        currentState = RELEASE_REFRESH;
                        //ˢ��ͷ����
                        refreshHeaderView();
                    } else if (paddingTop < 0 && currentState == RELEASE_REFRESH) {
                        //��������ˢ��״̬
                        currentState = PULL_REFRESH;
                        refreshHeaderView();
                    }


                    return true;//����TouchMove������listview����ô�move�¼�,�����listview�޷�����
                }


                break;
            case MotionEvent.ACTION_UP:
                if (currentState == PULL_REFRESH) {
                    //�Դ�������ˢ��״̬��δ����һ�����룬���������ݣ�����headView
                    headerView.setPadding(0, -headerViewHeight, 0, 0);
                } else if (currentState == RELEASE_REFRESH) {
                    //����һ�����룬��ʾ��paddingֵ��headcView
                    headerView.setPadding(0, 0, 0, 0);
                    //����״̬Ϊˢ��
                    currentState = REFRESHING;

                    //ˢ��ͷ������
                    refreshHeaderView();

                    if (listener != null) {
                        //�ӿڻص���������
                        listener.onPullRefresh();
                    }
                }
                break;
        }
        return super.onTouchEvent(ev);
    }

    // ����currentState������headerView
    private void refreshHeaderView() {
        switch (currentState) {
            case PULL_REFRESH:
                tv_state.setText("����ˢ��");
                iv_arrow.startAnimation(downAnimation);
                break;
            case RELEASE_REFRESH:
                tv_state.setText("�ɿ�ˢ��");
                iv_arrow.startAnimation(upAnimation);
                break;
            case REFRESHING:
                iv_arrow.clearAnimation();//��Ϊ���ϵ���ת�����п���û��ִ����
                iv_arrow.setVisibility(View.INVISIBLE);
                pb_rotate.setVisibility(View.VISIBLE);
                tv_state.setText("����ˢ��...");
//                new Thread(){
//                    public void run(){
//                        try {
//                            Thread.sleep(2000);
//                        } catch (InterruptedException e) {
//                            // TODO Auto-generated catch block
////                            e.printStackTrace();
//                            completeRefresh();
//                        }
//                    }
//                }.start();
                break;

        }
    }

    // ���ˢ�²���������״̬,�����ȡ�����ݲ�������adater֮��ȥ��UI�߳��е��ø÷���
    public void completeRefresh() {
        if (isLoadingMore) {
            //����footerView״̬
            footerView.setPadding(0, -footerViewHeight, 0, 0);
            isLoadingMore = false;
        } else {
            //����headerView״̬
            headerView.setPadding(0, -headerViewHeight, 0, 0);
            currentState = PULL_REFRESH;
            pb_rotate.setVisibility(View.INVISIBLE);
            iv_arrow.setVisibility(View.VISIBLE);
            tv_state.setText("����ˢ��");
            tv_time.setText("���ˢ�£�" + getCurrentTime());
        }
    }

    /**
     * ��ȡ��ǰϵͳʱ�䣬����ʽ��
     *
     * @return
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private String getCurrentTime() {
        SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        return format.format(new Date());
    }

    private OnRefreshListener listener;

    public void setOnRefreshListener(OnRefreshListener listener) {
        this.listener = listener;
    }

    public interface OnRefreshListener {
        void onPullRefresh();

        void onLoadingMore();
    }

    /**
     * SCROLL_STATE_IDLE:����״̬��������ָ�ɿ�
     * SCROLL_STATE_TOUCH_SCROLL����ָ�������������ǰ���������
     * SCROLL_STATE_FLING�����ٻ������ɿ�
     */
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == OnScrollListener.SCROLL_STATE_IDLE
                && getLastVisiblePosition() == (getCount() - 1) && !isLoadingMore) {
            isLoadingMore = true;

            footerView.setPadding(0, 0, 0, 0);//��ʾ��footerView
            setSelection(getCount());//��listview���һ����ʾ��������ҳ����ȫ��ʾ���ײ���

            if (listener != null) {
                listener.onLoadingMore();
            }
        }
    }


    @Override
    public void onScroll(AbsListView view, int firstVisibleItem,
                         int visibleItemCount, int totalItemCount) {
    }

}
