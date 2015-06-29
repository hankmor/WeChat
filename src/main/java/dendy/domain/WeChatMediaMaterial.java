package dendy.domain;

import org.apache.commons.lang.StringUtils;

import static dendy.util.wechat.WeChatStatusCode.*;

/**
 * 微信媒体文件素材-本地化
 */
public class WeChatMediaMaterial {
    /**
     * 系统ID
     */
    private Long mt_id;
    /**
     * 媒体类型
     */
    private String mt_type;
    /**
     * 枚举类型，在代码中使用
     */
    private WeChatMediaType type;
    /**
     * 本地全路径 对应关系
     */
    private String mt_url_local;
    /**
     * 微信官方的72小时的有效期 ID
     */
    private String mt_media_id;
    /**
     * 上传时间，用于判定该资源是否在官方有效
     */
    private Long mt_upload_time;
    /**
     * 视频文件的title
     */
    private String mt_video_title;
    /**
     * 视频文件的描述性内容
     */
    private String mt_video_desc;

    public Long getMt_id() {
        return mt_id;
    }

    public void setMt_id(Long mt_id) {
        this.mt_id = mt_id;
    }

    public String getMt_type() {
        return mt_type;
    }

    public void setMt_type(String mt_type) {
        if (StringUtils.isNotBlank(mt_type)) {
            if (WeChatMediaType.containsKeyIgnorecase(mt_type)) {
                this.type = WeChatMediaType.valueOf(StringUtils.upperCase(mt_type));
            }
        }
        this.mt_type = mt_type;
    }

    public WeChatMediaType getType() {
        return type;
    }

    public void setType(WeChatMediaType type) {
        this.setMt_type(StringUtils.upperCase(type.labelOf()));
        this.type = type;
    }

    public String getMt_url_local() {
        return mt_url_local;
    }

    public void setMt_url_local(String mt_url_local) {
        this.mt_url_local = mt_url_local;
    }

    public String getMt_media_id() {
        return mt_media_id;
    }

    public void setMt_media_id(String mt_media_id) {
        this.mt_media_id = mt_media_id;
    }

    public Long getMt_upload_time() {
        return mt_upload_time;
    }

    public void setMt_upload_time(Long mt_upload_time) {
        this.mt_upload_time = mt_upload_time;
    }

    public String getMt_video_title() {
        return mt_video_title;
    }

    public void setMt_video_title(String mt_video_title) {
        this.mt_video_title = mt_video_title;
    }

    public String getMt_video_desc() {
        return mt_video_desc;
    }

    public void setMt_video_desc(String mt_video_desc) {
        this.mt_video_desc = mt_video_desc;
    }

}
