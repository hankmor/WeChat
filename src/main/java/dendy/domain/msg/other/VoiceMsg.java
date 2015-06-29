package dendy.domain.msg.other;

import dendy.domain.msg.WeChatBaseMsg;
import dendy.exception.MsgParseException;
import dendy.util.wechat.WeChatStatusCode;
import org.dom4j.Element;

/**
 */
public class VoiceMsg extends WeChatBaseMsg {
    /**
     * 音频文件ID
     */
    private String MediaId;
    /**
     * 音频格式（接收信息时使用）
     */
    private String Format;

    /**
     * 默认构造函数
     */
    public VoiceMsg(String msg) {
        this.parseBean(msg);
    }

    /**
     * 发送该类信息时的构造函数
     *
     * @return
     */
    public VoiceMsg() {
    }

    public String getMediaId() {
        return MediaId;
    }

    public void setMediaId(String mediaId) {
        MediaId = mediaId;
    }

    public String getFormat() {
        return Format;
    }

    public void setFormat(String format) {
        Format = format;
    }

    @Override
    public void parseEspecial(Element element) {
        if (this.getMsgType().equals(WeChatStatusCode.MessageType.VOICE)) {
            if (element != null) {
                this.setFormat(element.elementTextTrim("Format"));
                this.setMediaId(element.elementTextTrim("MediaId"));
            }
        } else {
            throw new MsgParseException("微信信息格式解析错误，不能由" + this.getMsgType().labelOf() + "解析成  " + WeChatStatusCode.MessageType.VOICE.labelOf() + " 类型");
        }
    }

    @Override
    public String replyXmlData() {

        StringBuilder tmp = new StringBuilder("");

        tmp.append("<xml>");

        tmp.append("<ToUserName><![CDATA[").append(this.getToUserName()).append("]]></ToUserName>");
        tmp.append("<FromUserName><![CDATA[").append(this.getFromUserName()).append("]]></FromUserName>");
        tmp.append("<CreateTime>").append(System.currentTimeMillis()).append("<CreateTime>");
        tmp.append("<MsgType><![CDATA[").append(this.getMsgType().labelOf()).append("]]></MsgType>");

        tmp.append("<Voice>");
        tmp.append("<MediaId><![CDATA[").append(this.getMediaId()).append("]]></MediaId>");
        tmp.append("</Voice>");

        tmp.append("</xml>");

        return tmp.toString();
    }

}
