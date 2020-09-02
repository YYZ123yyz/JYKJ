package www.jykj.com.jykj_zxyl.app_base.base_dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.allin.refreshandload.loadmore.HeaderAndFooterRecyclerViewAdapter;
import com.allin.refreshandload.loadmore.RecyclerViewFinal;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import www.jykj.com.jykj_zxyl.app_base.R;
import www.jykj.com.jykj_zxyl.app_base.base_adapter.CancelContractAdapter;
import www.jykj.com.jykj_zxyl.app_base.base_bean.BaseReasonBean;
import www.jykj.com.jykj_zxyl.app_base.base_utils.CollectionUtils;


/**
 * Description:解约dialog
 *
 * @author: qiuxinhai
 * @date: 2020-07-31 19:08
 */
public class BaseReasonDialog extends Dialog {
    private Context mContext;
    private View mRootView;
    private RecyclerViewFinal mRecyclerView;
    private ImageView ivCloseBtn;
    private TextView mTvDialogTitle;
    private CancelContractAdapter cancelContractAdapter;
    private List<BaseReasonBean>cancelContractBeans;
    private OnClickItemListener onClickItemListener;
    private String title="";


    public void setOnClickItemListener(OnClickItemListener onClickItemListener) {
        this.onClickItemListener = onClickItemListener;
    }

    public BaseReasonDialog(@NonNull Context context,String title) {
        super(context, R.style.DialogTheme);
        setCanceledOnTouchOutside(true);//禁止点击空白区域消失
        Objects.requireNonNull(this.getWindow()).setDimAmount(0f);//核心代码 解决了无法去除遮罩
        cancelContractBeans=new ArrayList<>();
        this.title=title;
        init(context);
        addListener();
    }


    private void init(Context context) {
        mContext = context;
        mRootView = LayoutInflater.from(context).inflate(R.layout.dialog_cancel_contract_1, null);
        setContentView(mRootView);
        initView(mRootView);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(params);
        window.setGravity(Gravity.BOTTOM);
    }

    private void initView(View mRootView) {
        mRecyclerView = mRootView.findViewById(R.id.rv_list);
        ivCloseBtn=mRootView.findViewById(R.id.iv_close_btn);
        mTvDialogTitle=mRootView.findViewById(R.id.tv_dialog_title);
        mTvDialogTitle.setText(title);
    }

    /**
     * 设置数据
     * @param datas 数据列表
     */
    public void setData(List<BaseReasonBean> datas){
        cancelContractBeans=datas;
        cancelContractAdapter=new CancelContractAdapter(cancelContractBeans,mContext);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRecyclerView.setAdapter(cancelContractAdapter);
    }

    /**
     * 添加监听
     */
    private void addListener(){
        ivCloseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BaseReasonDialog.this.dismiss();
            }
        });
        mRecyclerView.setOnItemClickListener(new HeaderAndFooterRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, int i) {
                if (!CollectionUtils.isEmpty(cancelContractBeans)) {
                    for (int i1 = 0; i1 < cancelContractBeans.size(); i1++) {
                        if(i==i1){
                            cancelContractBeans.get(i1).setChoosed(true);
                        }else{
                            cancelContractBeans.get(i1).setChoosed(false);
                        }
                    }
                }
                cancelContractAdapter.notifyDataSetChanged();
                if (onClickItemListener!=null) {
                    BaseReasonBean cancelContractBean = cancelContractBeans.get(i);
                    onClickItemListener.onClickItem(cancelContractBean);
                }
            }
        });
    }


    public interface OnClickItemListener{

        void onClickItem(BaseReasonBean cancelContractBean);
    }

}
