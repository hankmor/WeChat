package dendy.domain.event;

import dendy.exception.MsgParseException;
import dendy.util.wechat.WeChatStatusCode;
import org.dom4j.Element;

public class SubscribeEventMsg extends WeChatEventMsg {
    /**
     * 事件KEY值，qrscene_为前缀，后面为二维码的参数值
     */
    private String EventKey;
    /**
     * 二维码的ticket，可用来换取二维码图片
     */
    private String Ticket;

    public SubscribeEventMsg(String msg) {
        this.parseBean(msg);
    }

    public String getEventKey() {
        return EventKey;
    }

    public void setEventKey(String eventKey) {
        EventKey = eventKey;
    }

    public String getTicket() {
        return Ticket;
    }

    public void setTicket(String ticket) {
        Ticket = ticket;
    }

    @Override
    public void parseEspecial(Element element) {
        boolean bol = (this.getEvent().equals(WeChatStatusCode.WechatEvent.SUBSCRIBE)
                || this.getEvent().equals(WeChatStatusCode.WechatEvent.SCAN));
        boolean unsub = this.getEvent().equals(WeChatStatusCode.WechatEvent.UNSUBSCRIBE);

        if (bol || unsub) {
            if ((!unsub) && element != null) {
                this.setEventKey(element.elementTextTrim("EventKey"));
                this.setTicket(element.elementTextTrim("Ticket"));
            }
        } else {
            throw new MsgParseException("微信信息格式解析错误，不能由" + this.getEvent().labelOf() + "解析成  " +
                    WeChatStatusCode.WechatEvent.SUBSCRIBE.labelOf() + " | " +
                    WeChatStatusCode.WechatEvent.UNSUBSCRIBE.labelOf() + " | " +
                    WeChatStatusCode.WechatEvent.SCAN.labelOf() + " 类型");
        }
    }
}
