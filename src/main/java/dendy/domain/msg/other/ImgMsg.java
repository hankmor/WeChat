package dendy.domain.msg.other;

import dendy.domain.msg.WeChatBaseMsg;
import dendy.exception.MsgParseException;
import dendy.util.wechat.WeChatStatusCode;
import org.dom4j.Element;

/**
 */
public class ImgMsg extends WeChatBaseMsg {
    /**
     * 图片地址（接受信息用）
     * 发送信息时不使用
     */
    private String PicUrl;
    /**
     * 图片系统ID
     */
    private String MediaId;

    /**
     * 默认构造函数
     */
    public ImgMsg(String msg) {
        this.parseBean(msg);
    }

    public ImgMsg() {
        this.setMsgType(WeChatStatusCode.MessageType.IMAGE);
    }

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }

    @Override
    public void parseEspecial(Element element) {
        if (this.getMsgType().equals(WeChatStatusCode.MessageType.IMAGE)) {
            if (element != null) {
                this.setPicUrl(element.elementTextTrim("PicUrl"));
                this.setMediaId(element.elementTextTrim("MediaId"));
            }
        } else {
            throw new MsgParseException("微信信息格式解析错误，不能由" + this.getMsgType().labelOf() + "解析成  " + WeChatStatusCode.MessageType.IMAGE.labelOf() + " 类型");
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
        tmp.append("<Image>");
        tmp.append("<MediaId><![CDATA[").append(this.getMediaId()).append("]]></MediaId>");
        tmp.append("</Image>");

        tmp.append("</xml>");
        return tmp.toString();
    }

}
