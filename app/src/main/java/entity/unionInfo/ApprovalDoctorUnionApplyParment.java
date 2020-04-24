package entity.unionInfo;

public class ApprovalDoctorUnionApplyParment {

    private             String                  loginDoctorPosition;   //当前登录医生所处的位置(定位信息).【V1.0 版本暂时传递经纬度值。经度和纬度直接使用^分割】
    private             String                  memberApplyId;          //	联盟申请表编号
    private             String                  applyCode;              //申请编码
    private             String                  unionCode;              //联盟编码
    private             String                  approvalDoctorCode;     //审核医生编码
    private             String                  approvalDoctorName;     //审核医生姓名
    private             String                  flagApplyState;         //审核申请状态。1:未通过;3:通过;
    private             String                  refuseReason;           //拒绝(未通过)原因描述[可为空]

    public String getLoginDoctorPosition() {
        return loginDoctorPosition;
    }

    public void setLoginDoctorPosition(String loginDoctorPosition) {
        this.loginDoctorPosition = loginDoctorPosition;
    }

    public String getMemberApplyId() {
        return memberApplyId;
    }

    public void setMemberApplyId(String memberApplyId) {
        this.memberApplyId = memberApplyId;
    }

    public String getApplyCode() {
        return applyCode;
    }

    public void setApplyCode(String applyCode) {
        this.applyCode = applyCode;
    }

    public String getUnionCode() {
        return unionCode;
    }

    public void setUnionCode(String unionCode) {
        this.unionCode = unionCode;
    }

    public String getApprovalDoctorCode() {
        return approvalDoctorCode;
    }

    public void setApprovalDoctorCode(String approvalDoctorCode) {
        this.approvalDoctorCode = approvalDoctorCode;
    }

    public String getApprovalDoctorName() {
        return approvalDoctorName;
    }

    public void setApprovalDoctorName(String approvalDoctorName) {
        this.approvalDoctorName = approvalDoctorName;
    }

    public String getFlagApplyState() {
        return flagApplyState;
    }

    public void setFlagApplyState(String flagApplyState) {
        this.flagApplyState = flagApplyState;
    }

    public String getRefuseReason() {
        return refuseReason;
    }

    public void setRefuseReason(String refuseReason) {
        this.refuseReason = refuseReason;
    }
}
