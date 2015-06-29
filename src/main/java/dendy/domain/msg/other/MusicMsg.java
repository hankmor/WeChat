package dendy.domain.msg.other;

import dendy.domain.msg.WeChatBaseMsg;
import dendy.util.wechat.WeChatStatusCode;

public class MusicMsg extends WeChatBaseMsg {
    public String Title;
    public String Description;
    /**
     * 音乐链接
     * （非必须，但是和高质量链接必须至少有一项不为空）
     */
    public String MusicURL;
    /**
     * 高质量音乐文件URL地址
     * （非必须，但是和音乐链接必须至少有一项不为空）
     */
    public String HQMusicUrl;
    /**
     * 必须项，不能为空，音乐缩略图ID，上传到微信服务器上的缩略图ID
     */
    public String ThumbMediaId;

    /**
     *
     */
    public MusicMsg() {
        this.setMsgType(WeChatStatusCode.MessageType.MUSIC);
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

    public String getMusicURL() {
        return MusicURL;
    }

    public void setMusicURL(String musicURL) {
        MusicURL = musicURL;
    }

    public String getHQMusicUrl() {
        return HQMusicUrl;
    }

    public void setHQMusicUrl(String hQMusicUrl) {
        HQMusicUrl = hQMusicUrl;
    }

    public String getThumbMediaId() {
        return ThumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        ThumbMediaId = thumbMediaId;
    }

    /**
     * 回复消息时使用xml数据进行回复
     */
    @Override
    public String replyXmlData() {
        StringBuilder tmp = new StringBuilder();
        tmp.append("<xml>");
        tmp.append("<ToUserName><![CDATA[").append(this.getToUserName()).append("]]></ToUserName>");
        tmp.append("<FromUserName><![CDATA[").append(this.getFromUserName()).append("]]></FromUserName>");
        tmp.append("<CreateTime>").append(System.currentTimeMillis()).append("</CreateTime>");
        tmp.append("<MsgType><![CDATA[").append(this.getMsgType().labelOf()).append("]]></MsgType>");

        tmp.append("<Music>");
        tmp.append("<Title><![CDATA[").append(this.getTitle()).append("]]></Title>");
        tmp.append("<Description><![CDATA[").append(this.getDescription()).append("]]></Description>");
        tmp.append("<MusicUrl><![CDATA[").append(this.getMusicURL()).append("<MusicUrl><![CDATA[");
        tmp.append("<HQMusicUrl><![CDATA[").append(this.getHQMusicUrl()).append("]]></HQMusicUrl>");
        tmp.append("<ThumbMediaId><![CDATA[").append(this.getThumbMediaId()).append("]]></ThumbMediaId>");
        tmp.append("</Music>");

        tmp.append("</xml>");

        return tmp.toString();
    }
}
