package www.jykj.com.jykj_zxyl.appointment.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.allen.library.utils.ToastUtils;
import com.allin.refreshandload.loadmore.RecyclerViewFinal;
import com.hyphenate.easeui.jykj.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_bean.AppointTimeBean;
import www.jykj.com.jykj_zxyl.appointment.adapter.AppointTimeAdapter;
import www.jykj.com.jykj_zxyl.appointment.data.DataUtil;
import www.jykj.com.jykj_zxyl.util.StringUtils;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-08-29 16:06
 */
public class AppointTimeDialog extends Dialog {
    private Context mContext;
    private View mRootView;
    private TextView tvCancelBtn;
    private TextView tvEnsureBtn;
    private RecyclerViewFinal rvStartList;
    private RecyclerViewFinal rvEndList;
    private AppointTimeAdapter startAppointTimeAdapter;
    private AppointTimeAdapter endAppointTimeAdapter;
    private List<AppointTimeBean> startAppointTimeBeans;
    private  List<AppointTimeBean> endAppointTimeBeans;
    private OnClickChoosedTimeListener onClickChoosedTimeListener;
    private String startTime;
    private String endTime;
    public void setOnClickChoosedTimeListener(OnClickChoosedTimeListener onClickChoosedTimeListener) {
        this.onClickChoosedTimeListener = onClickChoosedTimeListener;
    }

    public AppointTimeDialog(@NonNull Context context) {
        super(context, R.style.DialogTheme);
        startAppointTimeBeans=new ArrayList<>();
        endAppointTimeBeans=new ArrayList<>();
        setCanceledOnTouchOutside(true);//禁止点击空白区域消失
        Objects.requireNonNull(this.getWindow()).setDimAmount(0f);//核心代码 解决了无法去除遮罩
        init(context);
        addListener();
    }

    /**
     * 初始化页面
     * @param context 上下文
     */
    private void init(Context context){
        mContext = context;
        mRootView = LayoutInflater.from(context).inflate(R.layout.dialog_appoint_time, null);
        setContentView(mRootView);
        initView(mRootView);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height=WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);
        window.setGravity(Gravity.BOTTOM);
        initAdapter();
    }


    /**
     * 初始化View
     * @param view 根布局
     */
    private void initView(View view){
        tvCancelBtn  = view.findViewById(R.id.tv_cancel_btn);
        tvEnsureBtn=view.findViewById(R.id.tv_ensure_btn);
        rvStartList=view.findViewById(R.id.rv_start_list);
        rvEndList=view.findViewById(R.id.rv_end_list);
    }

    private void initAdapter(){

        List<String> startTimes = DataUtil.getStartTimes();
        for (String startTime : startTimes) {
            AppointTimeBean appointTimeBean=new AppointTimeBean();
            appointTimeBean.setAppointTime(startTime);
            startAppointTimeBeans.add(appointTimeBean);
        }
        startAppointTimeAdapter=new AppointTimeAdapter(mContext, startAppointTimeBeans);
        rvStartList.setLayoutManager(new LinearLayoutManager(mContext));
        rvStartList.setAdapter(startAppointTimeAdapter);

        List<String> endTimes = DataUtil.getEndTimes();
        for (String endTime : endTimes) {

            AppointTimeBean appointTimeBean=new AppointTimeBean();
            appointTimeBean.setAppointTime(endTime);
            endAppointTimeBeans.add(appointTimeBean);
        }
        endAppointTimeAdapter=new AppointTimeAdapter(mContext, endAppointTimeBeans);
        rvEndList.setLayoutManager(new LinearLayoutManager(mContext));
        rvEndList.setAdapter(endAppointTimeAdapter);
    }

    /**
     * 添加监听
     */
    private void addListener(){
        tvCancelBtn.setOnClickListener(v -> AppointTimeDialog.this.dismiss());

        tvEnsureBtn.setOnClickListener(v -> {
            if (onClickChoosedTimeListener!=null) {
                if (!StringUtils.isNotEmpty(startTime)) {
                    ToastUtils.showToast("请选择开始时间");
                    return;
                }
                if(!StringUtils.isNotEmpty(endTime)){
                    ToastUtils.showToast("请选择结束时间");
                    return;
                }
                boolean lessThanEndDate = DateUtils.isLessThanEndDate(startTime, endTime);
                if(!lessThanEndDate){
                    ToastUtils.showToast("结束时间不能小于开始时间");
                    return;
                }
                onClickChoosedTimeListener.onChoosedTimeChange(startTime,endTime);
                AppointTimeDialog.this.dismiss();
            }
        });
        rvStartList.setOnItemClickListener((viewHolder, i) -> {
            startTime=startAppointTimeBeans.get(i).getAppointTime();
            setStarTimeChoosed(i);
        });
        rvEndList.setOnItemClickListener((viewHolder, i) -> {
            endTime=endAppointTimeBeans.get(i).getAppointTime();
            setEndTimeChoosed(i);
        });

    }

    private void setStarTimeChoosed(int pos){
        for (int i = 0; i < startAppointTimeBeans.size(); i++) {
            if(pos==i){
                startAppointTimeBeans.get(i).setChoosed(true);
            }else{
                startAppointTimeBeans.get(i).setChoosed(false);
            }
        }
        startAppointTimeAdapter.notifyDataSetChanged();
    }

    private void setEndTimeChoosed(int pos){
        for (int i = 0; i < endAppointTimeBeans.size(); i++) {
            if (pos==i) {
                endAppointTimeBeans.get(i).setChoosed(true);
            }else{
                endAppointTimeBeans.get(i).setChoosed(false);
            }
        }
        endAppointTimeAdapter.notifyDataSetChanged();
    }

    public interface OnClickChoosedTimeListener{

        void onChoosedTimeChange(String startTime,String endTime);
    }

}
