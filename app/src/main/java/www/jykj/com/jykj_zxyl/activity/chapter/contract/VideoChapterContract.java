package www.jykj.com.jykj_zxyl.activity.chapter.contract;

import android.app.Activity;

import www.jykj.com.jykj_zxyl.activity.chapter.bean.ChapterListBean;
import www.jykj.com.jykj_zxyl.activity.chapter.bean.ChapterPayBean;
import www.jykj.com.jykj_zxyl.activity.chapter.bean.ChapterPriceBean;
import www.jykj.com.jykj_zxyl.activity.chapter.bean.ChatperSourceBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.AccountBalanceBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.CheckImResultBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.PatientRecordDetBean;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenter;
import www.jykj.com.jykj_zxyl.app_base.mvp.BaseView;
import www.jykj.com.jykj_zxyl.medicalrecord.PatientRecordContract;

public class VideoChapterContract {

    public interface View extends BaseView {
        void getListSucess(ChapterListBean data);

        void getDataFail(String msg);

        void getChatperPriceSucess(ChapterPriceBean bean);

        void getChapterSourceSucess(ChatperSourceBean bean);

        void getPayInfoSucess(ChapterPayBean bean);

        void getAliPayInfoSucess(String bean);

        void paySucess(String msg);

        void getAccountBalanceResult(AccountBalanceBean accountBalanceBean);

        /**
         * 校验密码是否成功
         * @param isSucess true or false
         * @param msg 状态信息
         */
        void checkPasswordResult(boolean isSucess,String msg);

    }


    public interface Presenter extends BasePresenter<View> {
        void getVideoChapterList(String params);

        void getChapterPriceDet(String params);

        void go2Pay(String params ,int type);

        void getChapterSource(String params);

        void getAccountBalance(Activity activity);

        /**
         * 密码校验
         * @param params 校验参数
         */
        void checkPassword(String params);
    }

}
