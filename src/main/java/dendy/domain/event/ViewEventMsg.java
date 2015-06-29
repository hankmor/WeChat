package dendy.domain.event;

import dendy.exception.MsgParseException;
import org.dom4j.Element;

import static dendy.util.wechat.WeChatStatusCode.*;

/**
 * 按钮类型为view的点击跳转后的服务器通知消息<BR>
 * 该消息用于统计该链接的访问次数或者用于给客户返回相关信息
 */
public class ViewEventMsg extends WeChatEventMsg {
    /**
     * 事件KEY值，设置的跳转URL
     */
    private String EventKey;

    public ViewEventMsg(String msg) {
        this.parseBean(msg);
    }

    public String getEventKey() {
        return EventKey;
    }

    public void setEventKey(String eventKey) {
        EventKey = eventKey;
    }

    @Override
    public void parseEspecial(Element element) {
        if (this.getEvent().equals(WechatEvent.VIEW)) {
            if (element != null) {
                this.setEventKey(element.elementTextTrim("EventKey"));
            }
        } else {
            throw new MsgParseException("微信信息格式解析错误，不能由" + this.getEvent().labelOf() + "解析成  " + WechatEvent.VIEW.labelOf() + " 类型");
        }
    }

}
