package www.jykj.com.jykj_zxyl.activity.chapter.contract;

import android.app.Activity;

import www.jykj.com.jykj_zxyl.activity.chapter.bean.ChapterListBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.CheckImResultBean;
import www.jykj.com.jykj_zxyl.app_base.base_bean.PatientRecordDetBean;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenter;
import www.jykj.com.jykj_zxyl.app_base.mvp.BaseView;
import www.jykj.com.jykj_zxyl.medicalrecord.PatientRecordContract;

public class VideoChapterContract {

    public interface View extends BaseView {
        void getListSucess(ChapterListBean data);

        void getDataFail(String msg);
    }


    public interface Presenter extends BasePresenter<View> {
        void getVideoChapterList(String params);

    }
}
