package www.jykj.com.jykj_zxyl.mypatient.presenter;

import www.jykj.com.jykj_zxyl.app_base.mvp.BasePresenterImpl;
import www.jykj.com.jykj_zxyl.mypatient.contract.RedRiskContract;


public class RedRiskPresenter extends BasePresenterImpl<RedRiskContract.View>
        implements RedRiskContract.Presenter  {
    private static final String SEND_OPERUPD_DRUG_INFO_REQUEST_TAG="send_operupd_drug_info_request_tag";
    @Override
    protected Object[] getRequestTags() {
        return new Object[0];
    }


}

