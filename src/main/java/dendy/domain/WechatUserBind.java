package dendy.domain;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;

public class WechatUserBind {
    /**
     * 用户是否订阅该公众号标识
     * 值为0时，代表此用户没有关注该公众号，拉取不到其余信息。
     * 值为1时，表示该用户关注了该公众号
     */
    @JsonProperty("subscribe")
    private String subscribe;
    /**
     * OPenId
     * 用户的标识，对当前公众号唯一
     */
    @JsonProperty("openid")
    private String openid;
    /**
     * openId 加密串
     */
    @JsonProperty("wechat_url_token")
    private String wechat_url_token;

    /**
     * 微信昵称
     */
    @JsonProperty("nickname")
    private String nickname;
    /**
     * 用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
     */
    @JsonProperty("sex")
    private int sex;
    /**
     * 用户的语言，简体中文为zh_CN
     */
    @JsonProperty("language")
    private String language;
    /**
     * 用户所在城市
     */
    @JsonProperty("city")
    private String city;
    /**
     * 用户所在省份
     */
    @JsonProperty("province")
    private String province;
    /**
     * 用户所在国家
     */
    @JsonProperty("country")
    private String country;
    /**
     * 用户头像，
     * 最后一个数值代表正方形头像大小
     * （有0、46、64、96、132数值可选，0代表640*640正方形头像）
     * 用户没有头像时该项为空
     */
    @JsonProperty("headimgurl")
    private String headimgurl;
    /**
     * 用户关注时间，为时间戳。如果用户曾多次关注，则取最后关注时间
     */
    @JsonProperty("subscribe_time")
    private String subscribe_time;
    /**
     * 备注
     */
    @JsonProperty("remark")
    private String remark;

    /**
     * 错误代码
     */
    @JsonProperty("errcode")
    private String errcode;

    /**
     * 错误描述
     */
    @JsonProperty("errmsg")
    private String errmsg;

    /**
     * 邦融平台用户ID
     */
    @JsonProperty("user_id")
    private Long user_id;
    /**
     * 绑定时间
     */
    private Date wechat_bind_time;

    public String getWechat_url_token() {
        return wechat_url_token;
    }

    public void setWechat_url_token(String wechat_url_token) {
        this.wechat_url_token = wechat_url_token;
    }

    public String getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(String subscribe) {
        this.subscribe = subscribe;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getSubscribe_time() {
        return subscribe_time;
    }

    public void setSubscribe_time(String subscribe_time) {
        this.subscribe_time = subscribe_time;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Date getWechat_bind_time() {
        return wechat_bind_time;
    }

    public void setWechat_bind_time(Date wechat_bind_time) {
        this.wechat_bind_time = wechat_bind_time;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }


    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public boolean compareToOnline(Object online) {
        if (online == null) {
            return Boolean.FALSE;
        } else if (!this.getClass()
                .equals(online.getClass())) {
            return Boolean.FALSE;
        } else if (super.equals(online)) {
            return Boolean.TRUE;
        } else {
            WechatUserBind ol = (WechatUserBind) online;
            if (this.getSubscribe().equals(ol.getSubscribe())
                    && StringUtils.trimToEmpty(this.getCity()).equals(StringUtils.trimToEmpty(ol.getCity()))
                    && StringUtils.trimToEmpty(this.getCountry()).equals(StringUtils.trimToEmpty(ol.getCountry()))
                    && StringUtils.trimToEmpty(this.getHeadimgurl()).equals(StringUtils.trimToEmpty(ol.getHeadimgurl()))
                    && StringUtils.trimToEmpty(this.getLanguage()).equals(StringUtils.trimToEmpty(ol.getLanguage()))
                    && StringUtils.trimToEmpty(this.getNickname()).equals(StringUtils.trimToEmpty(ol.getNickname()))
                    && StringUtils.trimToEmpty(this.getOpenid()).equals(StringUtils.trimToEmpty(ol.getOpenid()))
                    && StringUtils.trimToEmpty(this.getProvince()).equals(StringUtils.trimToEmpty(ol.getProvince()))
                    && this.getSex() == ol.getSex()
                    && StringUtils.trimToEmpty(this.getSubscribe_time()).equals(StringUtils.trimToEmpty(ol.getSubscribe_time()))) {
                return Boolean.TRUE;
            } else {
                return Boolean.FALSE;
            }
        }
    }


}
