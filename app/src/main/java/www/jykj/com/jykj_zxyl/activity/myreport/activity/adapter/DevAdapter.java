package www.jykj.com.jykj_zxyl.activity.myreport.activity.adapter;

import android.content.Context;

import java.util.List;

import entity.basicDate.ProvideBasicsRegion;
import www.jykj.com.jykj_zxyl.activity.myreport.activity.bean.DepartmentListBean;
import www.jykj.com.jykj_zxyl.util.widget.adapters.AbstractWheelTextAdapter;

public class DevAdapter<T> extends AbstractWheelTextAdapter {

    // items
    private List<DepartmentListBean> items;

    /**
     * Constructor
     * @param context the current context
     * @param items the items
     */
    public DevAdapter(Context context, List<DepartmentListBean> items) {
        super(context);

        //setEmptyItemResource(TEXT_VIEW_ITEM_RESOURCE);
        this.items = items;
    }

    @Override
    public CharSequence getItemText(int index) {
        if (index >= 0 && index < items.size()) {
            DepartmentListBean item = items.get(index);
            if (item instanceof DepartmentListBean) {
                return (CharSequence) item.getDepartmentName();
            }
            return item.toString();
        }
        return null;
    }

    @Override
    public int getItemsCount() {
        return items.size();
    }
}

