package dendy.domain.msg;

import dendy.util.wechat.WeChatStatusCode;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 非客服信息bean。
 * <p/>
 * <p>用于发送和接收非客服的消息数据封装。</p>
 */
public abstract class WeChatBaseMsg extends BaseMsgBeanHandler {
    /**
     * 接收方openId/服务号微信号
     */
    protected String ToUserName;
    /**
     * 发送方微信号，如果发送方位普通用户则为openID
     */
    protected String FromUserName;
    /**
     * 消息创建时间 （整型）
     */
    protected Long CreateTime;
    /**
     * 消息类型
     */
    protected WeChatStatusCode.MessageType MsgType;
    /**
     * 当前消息ID,接收消息时用
     */
    protected String MsgId;

    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
    }

    public Long getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(Long createTime) {
        CreateTime = createTime;
    }

    public WeChatStatusCode.MessageType getMsgType() {
        return MsgType;
    }

    public void setMsgType(WeChatStatusCode.MessageType msgType) {
        MsgType = msgType;
    }

    public String getMsgId() {
        return MsgId;
    }

    public void setMsgId(String msgId) {
        MsgId = msgId;
    }

    /**
     * 解析xml 设置消息bean属性值
     *
     * @param msg
     */
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
                this.parseEspecial(root);
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void parseEspecial(Element element) {
    }

    /**
     * “被动”回复信息时转换成xml数据进行回复
     *
     * @return
     */
    @Override
    public String replyXmlData() {
        return "";
    }

    /**
     * 重写toString 方法
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("");
        Method[] methodes = this.getClass().getMethods();
        for (Method method : methodes) {
            if (method.getName().startsWith("get") && !method.getName().contains("Class")) {
                try {
                    Object obj = method.invoke(this);
                    String lb = "";
                    if (obj != null && obj.getClass().equals(WeChatStatusCode.MessageType.class)) {
                        lb = ((WeChatStatusCode.MessageType) obj).labelOf();
                    } else if (obj != null) {
                        lb = obj.toString();
                    }
                    sb.append("   ").append(method.getName().replaceAll("get", "")).append(" : ").append(lb).append("\n");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
}
