package cn.z.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 九宫格拖拽控件
 * Created by byhook on 15-10-16.
 */
public class NineSlide extends ViewGroup implements View.OnLongClickListener {

    /**
     * 是否调试
     */
    private static final boolean DEBUG = true;

    /**
     * 滚动时间
     */
    private static final int DURATION = 200;
    /**
     * 阻尼系数
     */
    private static final float ELASTICITY_COEFFICIENT = 0.5f;

    /**
     * 调试标签
     */
    private static final String TAG = "NineSlide";

    /**
     * 滚动器
     */
    private Scroller mScroller;

    /**
     * 滚动宽度
     */
    private int mScrollWidth;

    /**
     * X坐标
     */
    private float mXPoint;

    /**
     * 容器宽和高
     */
    private int mWidth;
    private int mHeight;

    private NineDragController mController;

    public NineSlide(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    public NineSlide(Context context) {
        super(context);
        initView();
    }

    public NineSlide(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView(){
        mScroller = new Scroller(getContext(),new LinearInterpolator());
    }

    public void setController(NineDragController mController){
        this.mController = mController;
    }

    /**
     * {@inheritDoc}
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
         int count = getChildCount();

         for(int i=0;i<count;i++){
             View child = getChildAt(i);
             int screen = i/9;
             int screenCount = i%9;
             int screenX = screenCount%3;
             int screenY = screenCount/3;

             int childWidth = mWidth/3;
             int childHeight = mHeight/3;

             int left = screen * mWidth + l + screenX * childWidth;
             int right = screen * mWidth + l + (screenX + 1) * childWidth;
             int top = t + screenY * childHeight;
             int bottom = t + (screenY + 1) * childHeight;

             child.layout(left, top, right, bottom);

             if(DEBUG) Log.d(TAG,"Screen="+screen+"/left="+left+"/rigth="+right+"/top="+top+"/buttom="+bottom);
         }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        this.mWidth = MeasureSpec.getSize(widthMeasureSpec);
        this.mHeight = MeasureSpec.getSize(heightMeasureSpec);

        int childCount = getChildCount();
        int screen = childCount/9;

        setMeasuredDimension(widthMeasureSpec*(screen+1), heightMeasureSpec);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mXPoint = ev.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                if(Math.abs(ev.getX() - mXPoint) > 5){
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:
                final float fx = ev.getX();
                // 滚动距离
                float deltaX = fx - mXPoint;
                mXPoint = fx;
                deltaX *= ELASTICITY_COEFFICIENT;
                smoothScrollBy(-(int) (deltaX));

                break;
            case MotionEvent.ACTION_UP:
                //smoothScrollTo(0);
                break;
        }
        return super.onTouchEvent(ev);
    }

    private void smoothScrollTo(int fx) {
        int dx = fx - mScroller.getFinalX()+mScrollWidth;
        mScrollWidth += fx;
        smoothScrollBy(dx);
    }

    private void smoothScrollBy(int dx) {
        mScroller.startScroll(mScroller.getFinalX(), 0, dx, 0,DURATION);
        invalidate();
    }

    public void setOnItemLongClickListener(){
        int count = getChildCount();
        for(int i=0;i<count;i++){
            View child = getChildAt(i);
            child.setOnLongClickListener(this);
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setOnItemLongClickListener();
    }

    /**
     * Called when a view has been clicked and held.
     *
     * @param v The view that was clicked and held.
     * @return true if the callback consumed the long click, false otherwise.
     */
    @Override
    public boolean onLongClick(View v) {
        Toast.makeText(getContext(),"LongClick...",Toast.LENGTH_SHORT).show();
        ViewParent vp = getParent();
        if(vp!=null && vp instanceof NineDragLayout){
            TextView tv = new TextView(getContext());
            tv.setText("999");
            ((NineDragLayout) vp).addView(tv);
        }
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if(mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(), 0);
            postInvalidate();
        }
    }
}
