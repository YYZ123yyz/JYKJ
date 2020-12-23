package www.jykj.com.jykj_zxyl.app_base.base_enumtype;


import android.support.annotation.StringDef;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Title: 搜索跳转类型
 * Description:
 *
 * @author qiuxinhai
 * @version 2019-05-13
 */

@Retention(RetentionPolicy.SOURCE)
@StringDef({JumpTypeEnum.JUMP_BALANCE_ACTIVITY})
public @interface JumpTypeEnum {
    /**
     * 跳转余额
     */
    String JUMP_BALANCE_ACTIVITY = "jump_balance_activity";


}
