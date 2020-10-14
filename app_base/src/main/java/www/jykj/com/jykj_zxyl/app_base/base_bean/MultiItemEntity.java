package www.jykj.com.jykj_zxyl.app_base.base_bean;

import java.io.Serializable;

/**
 * 多类型布局抽象类
 *
 * @author qiuxinhai
 * @date 2019/6/3
 */
public abstract class MultiItemEntity implements Serializable {

    protected String itemType;

    public MultiItemEntity() {
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }
}
