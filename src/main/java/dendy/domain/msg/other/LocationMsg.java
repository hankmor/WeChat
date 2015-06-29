package dendy.domain.msg.other;

import dendy.domain.msg.WeChatBaseMsg;
import dendy.exception.MsgParseException;
import dendy.util.wechat.WeChatStatusCode;
import org.dom4j.Element;

/**
 */
public class LocationMsg extends WeChatBaseMsg {
    /**
     * 地理位置维度
     */
    private String Location_X;
    /**
     * 地理位置精度
     */
    private String Location_Y;
    /**
     * 地图缩放大小
     */
    private String Scale;
    /**
     * 地理位置信息
     */
    private String Label;

    /**
     * 默认构造函数，进行数据分配
     *
     * @param msg
     */
    public LocationMsg(String msg) {
        this.parseBean(msg);
    }

    public String getLocation_X() {
        return Location_X;
    }

    public void setLocation_X(String location_X) {
        Location_X = location_X;
    }

    public String getLocation_Y() {
        return Location_Y;
    }

    public void setLocation_Y(String location_Y) {
        Location_Y = location_Y;
    }

    public String getScale() {
        return Scale;
    }

    public void setScale(String scale) {
        Scale = scale;
    }

    public String getLabel() {
        return Label;
    }

    public void setLabel(String label) {
        Label = label;
    }

    /**
     * 解析自身特殊数据项
     */
    @Override
    public void parseEspecial(Element element) {
        if (this.getMsgType().equals(WeChatStatusCode.MessageType.LOCATION)) {
            if (element != null) {
                this.setLocation_X(element.elementTextTrim("Location_X"));
                this.setLocation_Y(element.elementTextTrim("Location_Y"));
                this.setScale(element.elementTextTrim("Scale"));
                this.setLabel(element.elementTextTrim("Label"));
            }
        } else {
            throw new MsgParseException("微信信息格式解析错误，不能由" + this.getMsgType().labelOf() + "解析成  " + WeChatStatusCode.MessageType.LOCATION.labelOf() + " 类型");
        }
    }
}
