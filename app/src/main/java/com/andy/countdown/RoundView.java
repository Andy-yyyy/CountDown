package com.andy.countdown;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by lxn on 2015/10/17.
 */
public class RoundView extends View {

    private Paint paint;
    private int width;
    private int height;
    private int radius;
    private int color;

    public RoundView(Context context) {
        this(context, null);
    }

    public RoundView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
    }



    /**
     * 设置要画的圆形的半径
     * @param radius 圆形半径
     */
    public void setRadius(int radius) {
        this.radius = radius;
    }

    /**
     * 设置要画的圆形的填充颜色
     * @param color 圆形的填充颜色
     */
    public void setColor(int color) {
        this.color = color;
    }




    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = getMeasuredWidth();
        height = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(color);
        canvas.drawCircle(width/2, height/2, radius, paint);
    }
}
