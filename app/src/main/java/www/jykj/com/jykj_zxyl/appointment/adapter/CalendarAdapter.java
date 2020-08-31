package www.jykj.com.jykj_zxyl.appointment.adapter;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.List;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_bean.CalendarItemBean;
import www.jykj.com.jykj_zxyl.app_base.base_utils.ScreenSizeUtils;
import www.jykj.com.jykj_zxyl.util.DateUtils;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-08-29 11:07
 */
public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.ViewHolder> {
    private Context mContext;
    private List<CalendarItemBean> list;
    private OnClickItemListener onClickItemListener;

    public void setOnClickItemListener(OnClickItemListener onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
    }

    public CalendarAdapter(Context context, List<CalendarItemBean>list){
        this.mContext=context;
        this.list=list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_calendar_frame, parent, false);
        ViewHolder vh = new ViewHolder(view);
        int screenWidth = ScreenSizeUtils.getInstance(mContext).getScreenWidth();
        vh.llRoot.setLayoutParams(new LinearLayout.LayoutParams(screenWidth / 7-16, WRAP_CONTENT));
        return vh;
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBindViewHolder(@NonNull CalendarAdapter.ViewHolder holder, int position) {
        CalendarItemBean calendarItemBean = list.get(position);
        holder.tvDateTime.setText(DateUtils.getDateToStringMMDD(calendarItemBean.getTimes()));
        holder.tvWeek.setText(getWeekStr(calendarItemBean.getWeek()));
        holder.tvSignalNum.setText(String.format("%d", calendarItemBean.getReserveTotal()));
        if (calendarItemBean.isChoosed()) {
            holder.llRoot.setBackgroundColor(ContextCompat.getColor(mContext,R.color.color_799dfe));
            holder.tvDateTime.setTextColor(Color.WHITE);
            holder.tvWeek.setTextColor(Color.WHITE);
            holder.tvSignalNum.setTextColor(Color.WHITE);
        }else{
            holder.llRoot.setBackgroundResource(R.drawable.round_frame_corner);
            holder.tvDateTime.setTextColor(ContextCompat.getColor(mContext,R.color.color_333333));
            holder.tvWeek.setTextColor(ContextCompat.getColor(mContext,R.color.color_333333));
            holder.tvSignalNum.setTextColor(ContextCompat.getColor(mContext,R.color.color_333333));
        }
        holder.llRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickItemListener!=null) {
                    onClickItemListener.onClickItem(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout llRoot;
        private TextView tvDateTime;
        private TextView tvWeek;
        private TextView tvSignalNum;
        public ViewHolder(View view) {
            super(view);
            llRoot=view.findViewById(R.id.ll_root_view);
             tvDateTime = view.findViewById(R.id.tv_date_time);
             tvWeek = view.findViewById(R.id.tv_week);
             tvSignalNum = view.findViewById(R.id.tv_signal_num);
        }
    }

    private String getWeekStr(int weekNum){
        String weekName="";
        switch (weekNum){
            case 1:
                weekName="日";
                break;
            case 2:
                weekName="一";
                break;
            case 3:
                weekName="二";
                break;
            case 4:
                weekName="三";
                break;
            case 5:
                weekName="四";
                break;
            case 6:
                weekName="五";
                break;
            case 7:
                weekName="六";
                break;
                default:
        }
       return weekName;
    }

    public interface OnClickItemListener{

        void onClickItem(int pos);
    }
}
