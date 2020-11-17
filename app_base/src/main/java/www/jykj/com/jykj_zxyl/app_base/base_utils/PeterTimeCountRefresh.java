package www.jykj.com.jykj_zxyl.app_base.base_utils;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.widget.TextView;

import java.text.DecimalFormat;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-11-11 16:06
 */
public class PeterTimeCountRefresh extends CountDownTimer {
    private TextView button;
    private long millisUntilFinished;
    private OnTimerListener onTimerListener;

    public PeterTimeCountRefresh(long millisInFuture, long countDownInterval
            , final TextView button,final OnTimerListener onTimerListener) {
        super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔,要显示的按钮
        this.button = button;
        this.onTimerListener=onTimerListener;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onTick(long millisUntilFinished) {//计时过程显示
        this.millisUntilFinished = millisUntilFinished;
        button.setTextColor(Color.parseColor("#C9544F"));
        //button.setBackgroundResource(R.drawable.send_code_wait);
        button.setClickable(false);
        //button.setTextSize((float) 11.5);
        DecimalFormat dec = new DecimalFormat("##.##");
        button.setText(String.format("0%d:%s",
                (int) Math.floor(millisUntilFinished / 60000),
                dec.format((millisUntilFinished % 60000) / 1000)));
        if (onTimerListener!=null) {
            onTimerListener.onTickTime(millisUntilFinished);
        }
    }

    @Override
    public void onFinish() {//计时完毕时触发
        //button.setText("刷新");
        button.setTextColor(Color.parseColor("#C9544F"));
        // button.setBackgroundResource(R.drawable.send_code);
        button.setClickable(true);
        if (onTimerListener!=null) {
            onTimerListener.onFinish();
        }
    }

    public interface OnTimerListener{
        void onTickTime(long millisUntilFinished);

        void onFinish();
    }


}
