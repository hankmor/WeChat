package dendy.domain.msg;

import org.dom4j.Element;

/**
 * 消息基本处理类。
 */
public abstract class BaseMsgBeanHandler {
    /**
     * 解析特殊属性方法，在具体实现类中进行重写
     *
     * @param element dom元素
     */
    public abstract void parseEspecial(Element element);

    /**
     * 解析接收消息父级 通用信息
     *
     * @param msg xml结构字符串
     */
    public abstract void parseBean(String msg);

    /**
     * 被动回复动作
     *
     * @return xml结构字符串
     */
    public abstract String replyXmlData();
}
