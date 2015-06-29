package dendy.domain.msg.cs;

import dendy.domain.msg.WeChatBaseCustomServiceMsg;

import static dendy.util.wechat.WeChatStatusCode.*;

public class CsMusicMsg extends WeChatBaseCustomServiceMsg {

    /**
     * 音乐标题
     */
    private String title;
    /**
     * 音乐描述
     */
    private String description;
    /**
     * 音乐链接
     */
    private String musicurl;
    /**
     * 高品质音乐链接，wifi环境优先使用该链接播放音乐
     */
    private String hqmusicurl;
    /**
     * 缩略图的媒体ID
     */
    private String thumb_media_id;

    public CsMusicMsg() {
        this.setMsgtype(MessageType.MUSIC);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMusicurl() {
        return musicurl;
    }

    public void setMusicurl(String musicurl) {
        this.musicurl = musicurl;
    }

    public String getHqmusicurl() {
        return hqmusicurl;
    }

    public void setHqmusicurl(String hqmusicurl) {
        this.hqmusicurl = hqmusicurl;
    }

    public String getThumb_media_id() {
        return thumb_media_id;
    }

    public void setThumb_media_id(String thumb_media_id) {
        this.thumb_media_id = thumb_media_id;
    }

    @Override
    public String toReplyJson() {
        StringBuilder tmp = new StringBuilder("");
        tmp.append("{");
        tmp.append("\"touser\":\"").append(this.getTouser()).append("\",");
        tmp.append("\"msgtype\":\"").append(this.getMsgtype().labelOf()).append("\",");
        tmp.append("\"music\":");
        tmp.append("{");
        tmp.append("\"title\":\"").append(this.getTitle()).append("\",");
        tmp.append("\"description\":\"").append(this.getDescription()).append("\",");
        tmp.append("\"musicurl\":\"").append(this.getMusicurl()).append("\",");
        tmp.append("\"hqmusicurl\":\"").append(this.getHqmusicurl()).append("\",");
        tmp.append("\"thumb_media_id\":\"").append(this.getThumb_media_id()).append("\"");
        tmp.append("}");

        return tmp.toString();
    }

}
