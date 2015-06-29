package dendy.domain.msg.other;

import dendy.domain.msg.WeChatBaseMsg;
import dendy.exception.MsgParseException;
import dendy.util.wechat.WeChatStatusCode;
import org.dom4j.Element;

/**
 * @author <a href="mailto:royrxc@gmail.com">Ranxc</a>
 */
public class TextMsg extends WeChatBaseMsg {
    /**
     * 文本信息内容
     */
    private String Content;

    /**
     * 默认构造函数，解析数据
     *
     * @param msg
     */
    public TextMsg(String msg) {
        this.parseBean(msg);
    }

    /**
     * 无惨构造函数，提供给回复信息时使用
     */
    public TextMsg() {
        this.setMsgType(WeChatStatusCode.MessageType.TEXT);
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    @Override
    public void parseEspecial(Element element) {
        if (this.getMsgType().equals(WeChatStatusCode.MessageType.TEXT)) {
            if (element != null) {
                this.setContent(element.elementTextTrim("Content"));
            }
        } else {
            throw new MsgParseException("微信信息格式解析错误，不能由" + this.getMsgType().labelOf() + "解析成  " + WeChatStatusCode.MessageType.TEXT.labelOf() + " 类型");
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
        tmp.append("<Content><![CDATA[").append(this.getContent()).append("]]></Content>");

        tmp.append("</xml>");

        return tmp.toString();
    }

}
