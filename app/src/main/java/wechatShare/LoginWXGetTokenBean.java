package wechatShare;

public class LoginWXGetTokenBean {

    /**
     * access_token : ACCESS_TOKEN
     * expires_in : 7200
     * refresh_token : REFRESH_TOKEN
     * openid : OPENID
     * scope : SCOPE
     */

    public String access_token;
    public int expires_in;
    public String refresh_token;
    public String openid;
    public String scope;

    @Override
    public String toString() {
        return "LoginWXGetTokenBean{" +
                "access_token='" + access_token + '\'' +
                ", expires_in=" + expires_in +
                ", refresh_token='" + refresh_token + '\'' +
                ", openid='" + openid + '\'' +
                ", scope='" + scope + '\'' +
                '}';
    }
}
