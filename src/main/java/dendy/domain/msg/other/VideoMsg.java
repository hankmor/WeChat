package dendy.domain.msg.other;

import dendy.domain.msg.WeChatBaseMsg;
import dendy.exception.MsgParseException;
import dendy.util.wechat.WeChatStatusCode;
import org.dom4j.Element;

/**
 */
public class VideoMsg extends WeChatBaseMsg {
    /**
     * 视频消息媒体id，可以调用多媒体文件下载接口拉取数据
     */
    private String MediaId;
    /**
     * 视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据 (接收数据时使用)
     */
    private String ThumbMediaId;
    /**
     * 视频消息 标题（发送信息时使用）
     */
    private String Title;
    /**
     * 视频描述（发送信息时使用）
     */
    private String Description;

    /**
     * 默认构造函数，构造是根据xml 字符串进行对应属性的设置
     */
    public VideoMsg(String msg) {
        this.parseBean(msg);
    }

    /**
     * 无参构造函数（发送信息时使用），不解析用户发送过来的数据
     */
    public VideoMsg() {
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }

    public String getThumbMediaId() {
        return ThumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        ThumbMediaId = thumbMediaId;
    }

    @Override
    public void parseEspecial(Element element) {
        if (this.getMsgType().equals(WeChatStatusCode.MessageType.VIDEO)) {
            if (element != null) {
                this.setThumbMediaId(element.elementTextTrim("ThumbMediaId"));
                this.setMediaId(element.elementTextTrim("MediaId"));
            }
        } else {
            throw new MsgParseException("微信信息格式解析错误，不能由" + this.getMsgType().labelOf() + "解析成  " + WeChatStatusCode.MessageType.VIDEO.labelOf() + " 类型");
        }
    }

    @Override
    public String replyXmlData() {
        StringBuilder tmp = new StringBuilder("");

        tmp.append("<xml>");

        tmp.append("<ToUserName><![CDATA[").append(this.getToUserName()).append("]]></ToUserName>");
        tmp.append("<FromUserName><![CDATA[").append(this.getFromUserName()).append("]]></FromUserName>");
        tmp.append("<CreateTime>").append(System.currentTimeMillis()).append("</CreateTime>");
        tmp.append("<MsgType><![CDATA[").append(this.getMsgType().labelOf()).append("]]></MsgType>");

        tmp.append("<Video>");
        tmp.append("<MediaId><![CDATA[").append(this.getMediaId()).append("]]></MediaId>");
        tmp.append("<Title><![CDATA[").append(this.getTitle()).append("]]></Title>");
        tmp.append("<Description><![CDATA[").append(this.getDescription()).append("]]></Description>");
        tmp.append("</Video>");

        tmp.append("<xml>");

        return tmp.toString();
    }

}
