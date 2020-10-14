package www.jykj.com.jykj_zxyl.consultation;

import java.util.List;

import entity.DoctorInfo.InteractPatient;
import entity.yhhd.ProvideDoctorGoodFriendGroup;
import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenter;
import www.jykj.com.jykj_zxyl.app_base.mvp.BaseView;

/**
 * Description:
 *
 * @author: qiuxinhai
 * @date: 2020-10-10 17:56
 */
public class DoctorFriendsContract {

    public interface View extends BaseView {
        /**
         * 获取医生好友返回结果
         * @param list 医生好友列表
         */
        void getDoctorFriendsResult(List<ProvideDoctorGoodFriendGroup> list);

    }

    public interface Presenter extends BasePresenter<View> {

        /**
         * 发送医生好友请求
         * @param doctorId 医生id
         * @param doctorCode 医生code
         */
        void sendDoctorFriendsRequest(String doctorId, String doctorCode);

    }
}
