package www.jykj.com.jykj_zxyl.appointment.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.allen.library.utils.ToastUtils;
import com.allin.refreshandload.loadmore.HeaderAndFooterRecyclerViewAdapter;
import com.allin.refreshandload.loadmore.RecyclerViewFinal;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import www.jykj.com.jykj_zxyl.R;
import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseReasonBean;
import www.jykj.com.jykj_zxyl.appointment.adapter.AppointTypeAdapter;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-08-29 18:26
 */
public class AppointTypeDialog  extends Dialog {
    private View mRootView;
    private Context mContext;
    private TextView mTvCancelBtn;
    private TextView tvEnsureBtn;
    private RecyclerViewFinal mRvList;
    private AppointTypeAdapter appointTypeAdapter;
    private List<BaseReasonBean> list;
    private OnChoosedItemListener onChoosedItemListener;
    private BaseReasonBean currentReasonBean;
    public void setOnChoosedItemListener(OnChoosedItemListener onChoosedItemListener) {
        this.onChoosedItemListener = onChoosedItemListener;
    }

    public AppointTypeDialog(@NonNull Context context) {
        super(context, R.style.DialogTheme);
        list=new ArrayList<>();
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
        mRootView = LayoutInflater.from(context).inflate(R.layout.dialog_appoint_type, null);
        setContentView(mRootView);
        initView(mRootView);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height=WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);
        window.setGravity(Gravity.BOTTOM);
    }


    private void initView(View view) {
        mTvCancelBtn = view.findViewById(R.id.tv_cancel_btn);
        tvEnsureBtn = view.findViewById(R.id.tv_ensure_btn);
        mRvList=view.findViewById(R.id.rv_list);

    }
    private void addListener(){
        mTvCancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppointTypeDialog.this.dismiss();
            }
        });
        tvEnsureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppointTypeDialog.this.dismiss();
                if (onChoosedItemListener!=null) {
                    if (currentReasonBean==null) {
                        ToastUtils.showToast("请选择号源类型");
                        return;
                    }
                    onChoosedItemListener.onChoosedSignalTypeItem(currentReasonBean);
                }
            }
        });
        mRvList.setOnItemClickListener(new HeaderAndFooterRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, int i) {
                setChoosedSignalType(i);
                currentReasonBean = list.get(i);
            }
        });
    }

    public void setData(List<BaseReasonBean> list){
        this.list=list;
        appointTypeAdapter=new AppointTypeAdapter(mContext,this.list);
        mRvList.setLayoutManager(new LinearLayoutManager(mContext));
        mRvList.setAdapter(appointTypeAdapter);
    }

    /**
     * 设置选中号状态
     * @param pos 位置
     */
    private void setChoosedSignalType(int pos){
        for (int i = 0; i < list.size(); i++) {
            if (i==pos) {
                list.get(i).setChoosed(true);
            }else{
                list.get(i).setChoosed(false);
            }
        }
        appointTypeAdapter.notifyDataSetChanged();
    }

    public interface OnChoosedItemListener{

        void onChoosedSignalTypeItem(BaseReasonBean baseReasonBean);
    }
}
