package dendy.domain.event;

import dendy.domain.msg.WeChatBaseMsg;
import dendy.util.wechat.WeChatStatusCode;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public abstract class WeChatEventMsg extends WeChatBaseMsg {
    /**
     * 事件类型，subscribe(订阅)、unsubscribe(取消订阅)
     */
    private WeChatStatusCode.WechatEvent Event;

    public WeChatStatusCode.WechatEvent getEvent() {
        return Event;
    }

    public void setEvent(WeChatStatusCode.WechatEvent event) {
        Event = event;
    }

    @Override
    public void parseBean(String msg) {

        if (StringUtils.isNotBlank(msg)) {
            Document doc;
            try {
                doc = DocumentHelper.parseText(msg);
                Element root = doc.getRootElement();
                this.setToUserName(root.elementText("ToUserName"));
                this.setFromUserName(root.elementText("FromUserName"));
                this.setMsgType(WeChatStatusCode.MessageType.valueOf(StringUtils.upperCase(root.elementText("MsgType"))));
                this.setMsgId(root.elementText("MsgId"));
                String ct = root.elementText("CreateTime");
                this.setCreateTime(StringUtils.isNotBlank(ct) ? Long.parseLong(ct) : null);
                this.setEvent(WeChatStatusCode.WechatEvent.valueOf(StringUtils.upperCase(root.elementText("Event"))));
                this.parseEspecial(root);
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }
    }

}
