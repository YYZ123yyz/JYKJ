package www.jykj.com.jykj_zxyl.util.widget.adapters;

import android.content.Context;

import java.util.List;

import entity.basicDate.ProvideBasicsRegion;

public class BirthdayWheelAdapter <T> extends AbstractWheelTextAdapter {

    // items
    private List<String> items;

    /**
     * Constructor
     * @param context the current context
     * @param items the items
     */
    public BirthdayWheelAdapter(Context context, List<String> items) {
        super(context);

        //setEmptyItemResource(TEXT_VIEW_ITEM_RESOURCE);
        this.items = items;
    }

    @Override
    public CharSequence getItemText(int index) {
        if (index >= 0 && index < items.size()) {
            String item = items.get(index);
            return item.toString();
        }
        return null;
    }

    @Override
    public int getItemsCount() {
        return items.size();
    }
}

