package dendy.domain.event;

import dendy.exception.MsgParseException;
import org.dom4j.Element;

import static dendy.util.wechat.WeChatStatusCode.*;

public class MenuEventMsg extends WeChatEventMsg {
    /**
     * 事件KEY值，与自定义菜单接口中KEY值对应
     */
    private String EventKey;

    public MenuEventMsg(String msg) {
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
        if (this.getEvent().equals(WechatEvent.CLICK)) {
            if (element != null) {
                this.setEventKey(element.elementTextTrim("EventKey"));
            }
        } else {
            throw new MsgParseException("微信信息格式解析错误，不能由" + this.getEvent().labelOf() + "解析成  " + WechatEvent.CLICK.labelOf() + " 类型");
        }
    }

}
