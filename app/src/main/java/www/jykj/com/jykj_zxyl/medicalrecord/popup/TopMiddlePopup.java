package www.jykj.com.jykj_zxyl.medicalrecord.popup;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import java.util.ArrayList;

import www.jykj.com.jykj_zxyl.R;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-09-10 16:59
 */
public class TopMiddlePopup extends PopupWindow {
    private Context myContext;
    private RecyclerView myLv;

    private LayoutInflater inflater = null;

    private View myMenuView;

    public TopMiddlePopup(Context context,
                          ArrayList<String> items) {

        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        myMenuView = inflater.inflate(R.layout.popup_inspection, null);

        this.myContext = context;

        initWidget();
        setPopup();
    }

    /**
     * 初始化控件
     */
    private void initWidget() {
        myLv =  myMenuView.findViewById(R.id.rv_list);

    }

    /**
     * 设置popup的样式
     */
    private void setPopup() {
        // 设置AccessoryPopup的view
        this.setContentView(myMenuView);
        // 设置AccessoryPopup弹出窗体的宽度
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        // 设置AccessoryPopup弹出窗体的高度
        this.setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        // 设置AccessoryPopup弹出窗体可点击
        this.setFocusable(true);
        // 设置AccessoryPopup弹出窗体的动画效果
        this.setAnimationStyle(R.style.AnimTopMiddle);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x33000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);


    }

    /**
     * 显示弹窗界面
     *
     * @param anchor
     */
    public void show(View anchor) {
        if (Build.VERSION.SDK_INT >= 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(anchor);
    }


}
