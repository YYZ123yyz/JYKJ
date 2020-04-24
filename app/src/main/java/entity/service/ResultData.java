package entity.service;


/**
 * 返回消息类
 * 
 * @author JiaQ
 */
public class ResultData implements java.io.Serializable {
	
	/*
	 * 返回编码
	 * 
	 * 0		==> 失败
	 * 1		==> 成功
	 * 大于1		==> 异常(错误)
	 * */
	int resCode;
	/*
	 * 返回消息
	 * 
	 * 正常内容提醒；
	 * 异常内容提醒；
	 * */
	String resMsg;
	/*
	 * 返回JSON数据
	 * */
	String resJsonData;
	/*
	 * 返回Token数据
	 * 由登录时产生，后续所有接口请求调用时，都需要token的值。
	 * */
	String resTokenData;
	String resData;//返回数据


	public int getResCode() {
		return resCode;
	}

	public void setResCode(int resCode) {
		this.resCode = resCode;
	}

	public String getResMsg() {
		return resMsg;
	}

	public void setResMsg(String resMsg) {
		this.resMsg = resMsg;
	}

	public String getResJsonData() {
		return resJsonData;
	}

	public void setResJsonData(String resJsonData) {
		this.resJsonData = resJsonData;
	}

	public String getResTokenData() {
		return resTokenData;
	}

	public void setResTokenData(String resTokenData) {
		this.resTokenData = resTokenData;
	}

	public String getResData() {
		return resData;
	}

	public void setResData(String resData) {
		this.resData = resData;
	}
}
