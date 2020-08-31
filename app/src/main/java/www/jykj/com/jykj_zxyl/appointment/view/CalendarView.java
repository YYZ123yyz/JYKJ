package www.jykj.com.jykj_zxyl.appointment.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_bean.CalendarItemBean;
import www.jykj.com.jykj_zxyl.appointment.adapter.CalendarAdapter;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-08-29 10:29
 */
public class CalendarView extends LinearLayout {

    private Context mContext;
    private RecyclerView rvList;
    private CalendarAdapter mCalendarAdapter;
    private List<CalendarItemBean> itemBeans;
    private OnClickCalendarListener onClickCalendarListener;

    public void setOnClickCalendarListener(OnClickCalendarListener onClickCalendarListener) {
        this.onClickCalendarListener = onClickCalendarListener;
    }

    public CalendarView(@NonNull Context context) {
        super(context);
        init(context);
    }
    public CalendarView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public void init(Context context){
        View mView =  View.inflate(context, R.layout.calendar_view, this);
        this.mContext = context;
        rvList=mView.findViewById(R.id.rv_list);
        itemBeans=new ArrayList<>();

    }

    public void setData(List<CalendarItemBean> calendarItemBeans){
        itemBeans=calendarItemBeans;
        initAdapter(mContext,itemBeans);
    }

    private void initAdapter(Context mContext,List<CalendarItemBean> list){
        mCalendarAdapter=new CalendarAdapter(mContext,list);
        mCalendarAdapter.setOnClickItemListener(new CalendarAdapter.OnClickItemListener() {
            @Override
            public void onClickItem(int pos) {
                if (onClickCalendarListener!=null) {
                    onClickCalendarListener.onClickCalendarItem(list.get(pos));
                    setChoosedItem(pos);

                }
            }
        });
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rvList.setLayoutManager(linearLayoutManager);
        rvList.setAdapter(mCalendarAdapter);

    }

    private void setChoosedItem(int pos){
        for (int i = 0; i < itemBeans.size(); i++) {
            if (i==pos) {
                itemBeans.get(i).setChoosed(true);
            }else{
                itemBeans.get(i).setChoosed(false);
            }

        }
        mCalendarAdapter.notifyDataSetChanged();
    }

    public interface OnClickCalendarListener{

        void onClickCalendarItem(CalendarItemBean calendarItemBean);
    }


}
