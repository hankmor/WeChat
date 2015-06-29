package dendy.domain.msg.cs;

import dendy.domain.msg.WeChatBaseCustomServiceMsg;

import static dendy.util.wechat.WeChatStatusCode.*;

public class CsTextMsg extends WeChatBaseCustomServiceMsg {

    /**
     * 文本消息内容
     */
    private String content;

    public CsTextMsg() {
        this.setMsgtype(MessageType.TEXT);
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toReplyJson() {
        StringBuilder tmp = new StringBuilder("");
        tmp.append("{");
        tmp.append("\"touser\":\"OPENID\",");
        tmp.append("\"msgtype\":\"").append(this.getMsgtype().labelOf()).append("\",");
        tmp.append("\"text\":");
        tmp.append("{");
        tmp.append("\"content\":\"").append(this.getContent()).append("\"");
        tmp.append("}");
        tmp.append("}");
        return tmp.toString();
    }

}
