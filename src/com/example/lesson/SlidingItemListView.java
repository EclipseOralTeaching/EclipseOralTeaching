package com.example.lesson;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

import com.example.lesson.R;

public class SlidingItemListView extends ListView {

    private View mPreItemView;

    private View mCurrentItemView;

    private float mFirstX;
    private float mFirstY;

    private int mRightViewWidth;

    private boolean mIsShown;

    private Boolean mIsHorizontal;

    public SlidingItemListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs,
                R.styleable.slidingitemlistview);
        mRightViewWidth = (int) typedArray.getDimension(
                0, 200);
        typedArray.recycle();

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        float lastX = ev.getX();
        float lastY = ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mIsHorizontal = null;
                mFirstX = lastX;
                mFirstY = lastY;
                int position = pointToPosition((int) mFirstX, (int) mFirstY);
                if (position >= 0) {
                    View view = getChildAt(position - getFirstVisiblePosition());
                    mPreItemView = mCurrentItemView;
                    mCurrentItemView = view;

                }
                Log.i("TAG", "onInterceptTouchEvent----->ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:
                Log.i("TAG", "onInterceptTouchEvent----->ACTION_UP");
                if (mIsShown) {
                    hideRightView(mCurrentItemView);
                }
                break;

            default:
                break;
        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        float lastX = ev.getX();
        float lastY = ev.getY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i("TAG", "onTouchEvent---->ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = lastX - mFirstX;
                float dy = lastY - mFirstY;
                Log.i("TAG", "onTouchEvent---->ACTION_MOVE");

                if (mIsHorizontal == null) {
                    if (!judgeScrollDirection(dx, dy)) {
                       
                        break;
                    }
                }

                if (mIsHorizontal) {
                    if (mIsShown&&mPreItemView!=mCurrentItemView) {
                        hideRightView(mPreItemView);
                    }

                    if (dx < 0 && dx > -mRightViewWidth) {
                        Log.i("TAG", "onTouchEvent---->MOVE   -dx=" + -dx);
                        mCurrentItemView.scrollTo((int) (-dx), 0);
                    }
//				 return true;
                } else {
                    if (mIsShown) {
                        hideRightView(mPreItemView);
                    }
                }

                break;
            case MotionEvent.ACTION_UP:
                if (mIsShown) {
                    Log.i("TAG", "MotionEvent.ACTION_UP ");
                    hideRightView(mCurrentItemView);
                    hideRightView(mPreItemView);
                }

                if (mIsHorizontal != null && mIsHorizontal) {
                    if (mFirstX - lastX > mRightViewWidth / 2) {
                        showRight(mCurrentItemView);
                    } else {
                     
                        hideRightView(mCurrentItemView);
                    }
                    Log.i("TAG", "OnTouchEvent  CANCLE return TRUE");
                    return true;
                }
                break;

            default:
                break;
        }

        return super.onTouchEvent(ev);
    }

    
    private void showRight(View mCurrentItemView2) {
        mCurrentItemView2.scrollTo(mRightViewWidth, 0);
        mIsShown = true;
    }

   
    private void hideRightView(View mCurrentItemView2) {

        mCurrentItemView2.scrollTo(0, 0);

        mIsShown = false;

    }

    
    private boolean judgeScrollDirection(float dx, float dy) {

        if (Math.abs(dx) > 30 && Math.abs(dx) > Math.abs(dy) * 2) {
            mIsHorizontal = true;
            return true;
        }
        if (Math.abs(dy) > 30 && Math.abs(dy) > Math.abs(dx) * 2) {
            mIsHorizontal = false;
            return true;
        }

        return false;
    }

    public int getRightViewWidth() {
        return mRightViewWidth;
    }

    public void setRightViewWidth(int mRightViewWidth) {
        this.mRightViewWidth = mRightViewWidth;
    }

}
