package entity.mySelf;

import java.util.Date;


/**
 * 用户(医生)基本信息-医生资质
 *
 * @author JiaQ
 */

public class ProvideDoctorQualification implements java.io.Serializable {
    private Integer doctorQualificationId;//医生资质编号
    private String doctorCode;//医生编码
    private String imgId;//图片ID
    private Integer idNumberPositive;//[医生]身份证正面.0:未上传;1:已上传;
    private Integer idNumberSide;//[医生]身份证反面.0:未上传;1:已上传;
    private Integer practising;//[医生]医师执业证.0:未上传;1:已上传;
    private Integer qualification;//[医生]医师资格证.0:未上传;1:已上传;
    private Integer professional;//[医生]医师职称证.0:未上传;1:已上传;

    private Integer flagAuthenticationType;//认证类型.1:医生线上自行认证;2:线下认证;3:医院统一认证;4:;5:;
    private Integer flagSubmitState;//提交状态.0:未提交;1:已提交;
    private Date submitDate;//提交日期
    private String submitReason;//[预留]提交描述
    private Integer flagApplyState;//申请状态.0:待处理;1:未通过;2:已过期;3:通过;
    private String refuseReason;//拒绝(未通过)原因描述
    private Date approvalDate;//审批日期

    private Integer flagDel;
    private String remark;
    private Date createDate;
    private String createMan;
    private Date updateDate;
    private String updateMan;

    private String idNumberPositiveImgUrl;//[医生]身份证正面图片地址
    private String idNumberSideImgUrl;//[医生]身份证反面图片地址
    private String practisingImgUrl;//[医生]医师执业证图片地址
    private String qualificationImgUrl;//[医生]医师资格证图片地址
    private String professionalImgUrl;//[医生]医师职称证图片地址

}
