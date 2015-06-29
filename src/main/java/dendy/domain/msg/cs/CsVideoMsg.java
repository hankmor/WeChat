package dendy.domain.msg.cs;

import dendy.domain.msg.WeChatBaseCustomServiceMsg;

import static dendy.util.wechat.WeChatStatusCode.*;

public class CsVideoMsg extends WeChatBaseCustomServiceMsg {

    /**
     * 发送的视频的媒体ID
     */
    private String media_id;
    /**
     * 视频消息的标题
     */
    private String title;
    /**
     * 视频消息的描述
     */
    private String description;

    public CsVideoMsg() {
        this.setMsgtype(MessageType.VIDEO);
    }

    public String getMedia_id() {
        return media_id;
    }

    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toReplyJson() {
        StringBuilder tmp = new StringBuilder("");

        tmp.append("{");
        tmp.append("\"touser\":\"").append(this.getTouser()).append("\",");
        tmp.append("\"msgtype\":\"").append(this.getMsgtype().labelOf()).append("\",");
        tmp.append("\"video\":");
        tmp.append("{");
        tmp.append("\"media_id\":\"").append(this.getMedia_id()).append("\",");
        tmp.append("\"title\":\"").append(this.getTitle()).append("\",");
        tmp.append("\"description\":\"").append(this.getDescription()).append("\"");
        tmp.append("}");
        tmp.append("}");

        return tmp.toString();
    }

}
