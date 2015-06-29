package dendy.domain.msg.cs;

import dendy.domain.msg.WeChatBaseCustomServiceMsg;

import static dendy.util.wechat.WeChatStatusCode.*;

public class CsImgMsg extends WeChatBaseCustomServiceMsg {

    /**
     * 发送的图片的媒体ID
     */
    private String media_id;

    public CsImgMsg() {
        this.setMsgtype(MessageType.IMAGE);
    }

    public String getMedia_id() {
        return media_id;
    }

    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }

    @Override
    public String toReplyJson() {
        StringBuilder tmp = new StringBuilder("");
        tmp.append("");
        tmp.append("{");
        tmp.append("\"touser\":\"").append(this.getTouser()).append("\",");
        tmp.append("\"msgtype\":\"").append(this.getMsgtype().labelOf()).append("\",");
        tmp.append("\"image\":");
        tmp.append("{");
        tmp.append("\"media_id\":\"").append(this.getMedia_id()).append("\"");
        tmp.append("}");
        tmp.append("}");
        return tmp.toString();
    }

}
