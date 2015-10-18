package com.andy.countdown;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by lxn on 2015/10/16.
 */
public class CountView extends RelativeLayout {

    private View bgView;
    private RoundView roundView;
    private TextView countText;

    private Timer countTimer;
    private TimerTask countTask;
    private Handler handler;

    private Animation countAnim;
    private Animation enterAnim;
    private Animation exitAnim;
    private int countNum;

    public CountView(Context context) {
        this(context,null);
    }

    public CountView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    /**
     * 开始显示倒计时
     * @param count 倒计时开始的时间
     * @param delay 延迟时间
     * @param interval 倒计时的时间间隔
     */
    public void show(final int count, int delay, int interval) {
        // 如果已有任务在执行则返回
        if (countTimer!=null || countTask!=null) return;
        this.countNum = count;
        enter();
        countTimer = new Timer();
        countTask = new TimerTask() {
            @Override
            public void run() {
                if (countNum<=1) {
                    if (countTask!=null) {
                        countTask.cancel();
                        countTask = null;
                    }
                    if (countTimer!=null) {
                        countTimer.cancel();
                        countTimer.purge();
                        countTimer = null;
                    }
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        countText.setText(String.valueOf(countNum--));
                        if (countNum==0) {
                            exit();
                        } else {
                            roundView.startAnimation(countAnim);
                        }
                    }
                });

            }
        };
        countTimer.schedule(countTask, delay, interval);
    }

    /**
     * 设置倒计时的动画
     * @param anim 倒计时动画
     */
    public void setCountAnimation(Animation anim) {
        this.countAnim = anim;
    }

    /**
     * 设置进入时的动画
     * @param anim 进入动画
     */
    public void setEnterAnimation(Animation anim) {
        this.enterAnim = anim;
    }

    /**
     * 设置退出时的动画
     * @param anim 退出动画
     */
    public void setExitAnimation(Animation anim) {
        this.exitAnim = anim;
    }

    private void enter() {
        countText.setText(String.valueOf(countNum));
        this.setVisibility(VISIBLE);
        this.clearAnimation();
        Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.fade_in);
        if (anim!=null) {
            bgView.startAnimation(anim);
        }

        if (enterAnim!=null) {
            roundView.startAnimation(enterAnim);
        }
    }

    private void exit() {
        Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.fade_out);
        if (anim!=null) {
            bgView.startAnimation(anim);
        }
        if (exitAnim!=null) {
            roundView.startAnimation(exitAnim);
            exitAnim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    countText.setText("");
                    CountView.this.setVisibility(INVISIBLE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.count_view, this, true);
        view.setVisibility(INVISIBLE);
        bgView = (View) view.findViewById(R.id.bg_view);
        roundView = (RoundView) view.findViewById(R.id.round_view);
        roundView.setColor(Color.GREEN);
        roundView.setRadius(80);
        countText = (TextView) view.findViewById(R.id.count_num);
        handler = new Handler();
    }

}
