package dendy.util.wechat;

import dendy.domain.event.LocationEventMsg;
import dendy.domain.event.MenuEventMsg;
import dendy.domain.event.SubscribeEventMsg;
import dendy.domain.event.ViewEventMsg;
import dendy.domain.msg.WeChatBaseMsg;
import dendy.domain.msg.other.*;
import dendy.util.Assert;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WeChatFactory {
    public static final Logger LOG = LoggerFactory.getLogger(WeChatFactory.class);

    /**
     * 接收微信发送过来的信息字符串，将字符串解析成相应的对象
     *
     * @param msg
     * @return
     */
    public static WeChatBaseMsg parseToWeChatMsg(String msg) {
        WeChatBaseMsg weChatBaseMsg = null;
        if (StringUtils.isNotBlank(msg)) {
            try {
                Document dom = DocumentHelper.parseText(msg);
                if (dom != null) {
                    Element root = dom.getRootElement();
                    String msgType = root.elementTextTrim("MsgType");
                    if (StringUtils.isNotBlank(msgType)) {
                        WeChatStatusCode.MessageType msgt = WeChatStatusCode.MessageType.valueOf(StringUtils.upperCase(msgType));
                        if (msgt != null) {
                            switch (msgt) {
                                case TEXT:
                                    weChatBaseMsg = new TextMsg(msg);
                                    break;
                                case LINK:
                                    weChatBaseMsg = new LinkMsg(msg);
                                    break;
                                case IMAGE:
                                    weChatBaseMsg = new ImgMsg(msg);
                                    break;
                                case LOCATION:
                                    weChatBaseMsg = new LocationMsg(msg);
                                    break;
                                case VIDEO:
                                    weChatBaseMsg = new VideoMsg(msg);
                                    break;
                                case VOICE:
                                    weChatBaseMsg = new VoiceMsg(msg);
                                    break;
                                case EVENT:
                                case NEWS:
                                    weChatBaseMsg = parseToWeChatEventMsg(msg);
                                default:
                                    break;
                            }
                        }
                    }
                }

            } catch (DocumentException e) {
                LOG.error("Parse msg cause exception : ", e);
            }
        }
        return weChatBaseMsg;
    }

    /**
     * 解析事件类型消息
     *
     * @param msg
     * @return
     */
    private static WeChatBaseMsg parseToWeChatEventMsg(String msg) {
        WeChatBaseMsg weChatBaseMsg = null;
        Assert.hasLength(msg);
        try {
            Document dom = DocumentHelper.parseText(msg);
            if (dom != null) {
                Element root = dom.getRootElement();
                String msgType = root.elementTextTrim("Event");
                if (StringUtils.isNotBlank(msgType)) {
                    WeChatStatusCode.WechatEvent msgt = WeChatStatusCode.WechatEvent.valueOf(StringUtils.upperCase(msgType));
                    if (msgt != null) {
                        switch (msgt) {
                            case UNSUBSCRIBE:
                            case SCAN:
                            case SUBSCRIBE:
                                weChatBaseMsg = new SubscribeEventMsg(msg);
                                break;
                            case LOCATION:
                                weChatBaseMsg = new LocationEventMsg(msg);
                                break;
                            case CLICK:
                                weChatBaseMsg = new MenuEventMsg(msg);
                                break;
                            case VIEW:
                                weChatBaseMsg = new ViewEventMsg(msg);
                            default:
                                break;
                        }
                    }
                }
            }
        } catch (DocumentException e) {
            LOG.error("Parse event message cause exception : ", e);
        }
        return weChatBaseMsg;
    }
}
