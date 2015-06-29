package dendy.domain.msg.other;

import dendy.domain.msg.WeChatBaseMsg;
import dendy.exception.MsgParseException;
import dendy.util.wechat.WeChatStatusCode;
import org.dom4j.Element;

public class LinkMsg extends WeChatBaseMsg {
    /**
     * 消息标题
     */
    private String Title;
    /**
     * 消息描述
     */
    private String Description;
    /**
     * 消息链接
     */
    private String Url;

    /**
     * 默认构造函数，进行数据分配
     *
     * @param msg
     */
    public LinkMsg(String msg) {
        this.parseBean(msg);
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

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }

    @Override
    public void parseEspecial(Element element) {
        if (this.getMsgType().equals(WeChatStatusCode.MessageType.LINK)) {
            if (element != null) {
                this.setTitle(element.elementTextTrim("Title"));
                this.setDescription(element.elementTextTrim("Description"));
                this.setUrl(element.elementTextTrim("Url"));
            }
        } else {
            throw new MsgParseException("微信信息格式解析错误，不能由" + this.getMsgType().labelOf() + "解析成  " +
                    WeChatStatusCode.MessageType.LINK.labelOf() + " 类型");
        }
    }
}
