package www.jykj.com.jykj_zxyl.app_base.calendar;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ldf.calendar.Utils;
import com.ldf.calendar.component.State;
import com.ldf.calendar.interf.IDayRenderer;
import com.ldf.calendar.model.CalendarDate;
import com.ldf.calendar.view.DayView;

import java.util.Calendar;

import www.jykj.com.jykj_zxyl.app_base.R;

/**
 * Created by ldf on 17/6/26.
 */

@SuppressLint("ViewConstructor")
public class CustomDayView extends DayView {

    private TextView dateTv;
    private TextView tvWeek;
    private TextView tvSignalNum;
    private ImageView marker;
    private View selectedBackground;
    private View todayBackground;
    private final CalendarDate today = new CalendarDate();

    /**
     * 构造器
     *
     * @param context        上下文
     * @param layoutResource 自定义DayView的layout资源
     */
    public CustomDayView(Context context, int layoutResource) {
        super(context, layoutResource);
        dateTv = findViewById(R.id.date);
        marker = findViewById(R.id.maker);
        tvWeek = findViewById(R.id.tv_week);
        tvSignalNum=findViewById(R.id.tv_signal_num);
        selectedBackground = findViewById(R.id.selected_background);
        todayBackground = findViewById(R.id.today_background);
    }

    @Override
    public void refreshContent() {
        renderToday(day.getDate());
        renderSelect(day.getState());
        renderMarker(day.getDate(), day.getState());
        super.refreshContent();
    }

    private void renderMarker(CalendarDate date, State state) {
        if (Utils.loadMarkData().containsKey(date.toString())) {
            if (state == State.SELECT || date.toString().equals(today.toString())) {
                marker.setVisibility(GONE);
            } else {
                marker.setVisibility(VISIBLE);
                if (Utils.loadMarkData().get(date.toString()).equals("0")) {
                    marker.setEnabled(true);
                } else {
                    marker.setEnabled(false);
                }
            }
        } else {
            marker.setVisibility(GONE);
        }
    }

    private void renderSelect(State state) {
        if (state == State.SELECT) {
            selectedBackground.setVisibility(VISIBLE);
            dateTv.setTextColor(Color.WHITE);
            tvWeek.setTextColor(Color.WHITE);
            tvSignalNum.setTextColor(Color.WHITE);
        } else if (state == State.NEXT_MONTH || state == State.PAST_MONTH) {
            selectedBackground.setVisibility(GONE);
            dateTv.setTextColor(ContextCompat.getColor(context,R.color.color_333333));
            tvWeek.setTextColor(ContextCompat.getColor(context,R.color.color_333333));
            tvSignalNum.setTextColor(ContextCompat.getColor(context,R.color.color_333333));
        } else {
            selectedBackground.setVisibility(GONE);
            dateTv.setTextColor(ContextCompat.getColor(context,R.color.color_333333));
            tvWeek.setTextColor(ContextCompat.getColor(context,R.color.color_333333));
            tvSignalNum.setTextColor(ContextCompat.getColor(context,R.color.color_333333));
        }
    }

    @SuppressLint("DefaultLocale")
    private void renderToday(CalendarDate date) {
        if (date != null) {
            if (date.equals(today)) {
                dateTv.setText("今");
                todayBackground.setVisibility(VISIBLE);
            } else {
                String week = getWeek(String.format("%d-%d-%d", date.getYear(), date.getMonth(), date.getDay()));
                tvWeek.setText(week);
                dateTv.setText(String.format("%d-%d", date.month, date.day));
                todayBackground.setVisibility(GONE);
            }
        }
    }

    @Override
    public IDayRenderer copy() {
        return new CustomDayView(context, layoutResource);
    }

    private String getWeek(String pTime) {


        String Week = "";

        SimpleDateFormat format = null;
        format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {

            c.setTime(format.parse(pTime));

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            Week += "日";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 2) {
            Week += "一";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 3) {
            Week += "二";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 4) {
            Week += "三";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 5) {
            Week += "四";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 6) {
            Week += "五";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 7) {
            Week += "六";
        }


        return Week;
    }

}
