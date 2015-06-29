package dendy.domain.event;

import dendy.exception.MsgParseException;
import org.dom4j.Element;

import static dendy.util.wechat.WeChatStatusCode.*;

public class LocationEventMsg extends WeChatEventMsg {
    /**
     * 地理位置纬度
     */
    private String Latitude;
    /**
     * 地理位置经度
     */
    private String Longitude;
    /**
     * 地理位置精度
     */
    private String Precision;

    public LocationEventMsg(String msg) {
        this.parseBean(msg);
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getPrecision() {
        return Precision;
    }

    public void setPrecision(String precision) {
        Precision = precision;
    }

    @Override
    public void parseEspecial(Element element) {
        if (this.getEvent().equals(WechatEvent.LOCATION)) {
            if (element != null) {
                this.setLatitude(element.elementTextTrim("Latitude"));
                this.setLongitude(element.elementTextTrim("Longitude"));
                this.setPrecision(element.elementTextTrim("Precision"));
            }
        } else {
            throw new MsgParseException("微信信息格式解析错误，不能由" + this.getEvent().labelOf() + "解析成  " + WechatEvent.LOCATION.labelOf() + " 类型");
        }
    }

}