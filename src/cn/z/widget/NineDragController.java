package cn.z.widget;

import android.view.MotionEvent;

/**
 * Created by byhook on 15-10-16.
 */
public class NineDragController {

    /**
     * X坐标
     */
    private float mXPoint;

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
        return true;
    }

    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:
                final float fx = ev.getX();
                // 滚动距离
                float deltaX = fx - mXPoint;
                mXPoint = fx;

                break;
            case MotionEvent.ACTION_UP:
                //smoothScrollTo(0);
                break;
        }
        return true;
    }

}
