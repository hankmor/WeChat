package dendy.domain.msg;

import dendy.util.wechat.WeChatStatusCode;

/**
 * 客服基本消息bean。
 */
public abstract class WeChatBaseCustomServiceMsg {
    /**
     * 调用接口凭证
     */
    private String access_token;
    /**
     * 普通用户openid
     */
    private String touser;
    /**
     * 消息类型
     */
    private WeChatStatusCode.MessageType msgtype;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getTouser() {
        return touser;
    }

    public void setTouser(String touser) {
        this.touser = touser;
    }

    public WeChatStatusCode.MessageType getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(WeChatStatusCode.MessageType msgtype) {
        this.msgtype = msgtype;
    }

    public abstract String toReplyJson();
}
