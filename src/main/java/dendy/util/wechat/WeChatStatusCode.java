package dendy.util.wechat;

import org.apache.commons.lang.StringUtils;

public class WeChatStatusCode {
    public enum GlobalStatus {
        SYS_BUSYING(-1, "系统繁忙"),
        SUCCESS(0, "请求成功"),
        TOKEN_ERROR(40001, "获取access_token时AppSecret错误，或者access_token无效"),
        ILLEGAL_TOKEN(40002, "不合法的凭证类型"),
        ILLEGAL(40003, "不合法的OpenID"),
        MEDIA_TYPE_ILLEGAL(40004, "不合法的媒体文件类型"),
        FILE_TYPE_ILLEGAL(40005, "不合法的文件类型"),
        FILE_SIZE_ILLEGAL(40006, "不合法的文件大小"),
        MEDIA_ID_ILLEGAL(40007, "不合法的媒体文件id"),
        MSG_TYPE_ILLEGAL(40008, "不合法的消息类型"),
        IMG_SIZE_ILLEGAL(40009, "不合法的图片文件大小"),
        AUDIO_SIZE_ILLEGAL(40010, "不合法的语音文件大小"),
        MEDIA_SIZE_ILLEGAL(40011, "不合法的视频文件大小"),
        THUMBNAIL_SIZE_ILLEGAL(40012, "不合法的缩略图文件大小"),
        APP_ID_ILLEGAL(40013, "不合法的APPID"),
        ACCESS_TOKEN_ILLEGAL(40014, "不合法的access_token"),
        MENU_TYPE_ILLEGAL(40015, "不合法的菜单类型"),
        BTN_NUM_ILLEGAL(40016, "不合法的按钮个数"),
        BTN_NUM_ERROR(40017, "不合法的按钮个数"),
        BTN_NAMELEN_ILLEGAL(40018, "不合法的按钮名字长度"),
        BTN_KEYLEN_ILLEGAL(40019, "不合法的按钮KEY长度"),
        BTN_URLLEN_ILLEGAL(40020, "不合法的按钮URL长度"),
        MENU_VER_ILLEGAL(40021, "不合法的菜单版本号"),
        SUBMENU_NUM_ILLEGAL(40022, "不合法的子菜单级数"),
        SUBMENU_BTNNUM_ILLEGAL(40023, "不合法的子菜单按钮个数"),
        SUBMENU_BTNTYPE_ILLEGAL(40024, "不合法的子菜单按钮类型"),
        SUBMENU_BTNNAMELEN_ILLEGAL(40025, "不合法的子菜单按钮名字长度"),
        SUBMENU_BTNKEYLEN_ILLEGAL(40026, "不合法的子菜单按钮KEY长度"),
        SUBMENU_BTNURLLEN_ILLEGAL(40027, "不合法的子菜单按钮URL长度"),
        MENU_ACT_ILLEGAL(40028, "不合法的自定义菜单使用用户"),
        OAUTH_CODE_ILLEGAL(40029, "不合法的oauth_code"),
        REFRESH_TOKEN_ILLEGAL(40030, "不合法的refresh_token"),
        OPENID_ILLEGAL(40031, "不合法的openid列表"),
        OPENID_LSTLEN_ILLEGAL(40032, "不合法的openid列表长度"),
        MSG_INCLUDE_UNICODE(40033, "不合法的请求字符，不能包含\\uxxxx格式的字符"),
        PARAMETER_ILLEGAL(40035, "不合法的参数"),
        REQUEST_FORMAT_ILLEGAL(40038, "不合法的请求格式"),
        URL_ELLEGAL(40039, "不合法的URL长度"),
        GROUP_ID_ILLEGAL(40050, "不合法的分组id"),
        GROUP_NAME_ILLEGAL(40051, "分组名字不合法"),
        PARAMETER_ACCESSTOKEN_MISSING(41001, "缺少access_token参数"),
        PARAMETER_APPID_MISSING(41002, "缺少appid参数"),
        PARAMETER_REFRESHTOKEN_MISSING(41003, "缺少refresh_token参数"),
        PARAMETER_SECRET_MISSING(41004, "缺少secret参数"),
        PARAMETER_MEDIADATA_MISSING(41005, "缺少多媒体文件数据"),
        PARAMETER_MEDIA_MISSING(41006, "缺少media_id参数"),
        PARAMETER_SUBMENUDATA_MISSING(41007, "缺少子菜单数据"),
        PARAMETER_OAUTHCODE_MISSING(41008, "缺少oauthcode"),
        PARAMETER_OPENID_MISSING(41009, "缺少openid"),
        PARAMETER_ACCESSTOKEN_TIMEOUT(42001, "access_token超时"),
        PARAMETER_REFRESHTOKEN_TIMEOUT(42002, "refresh_token超时"),
        TIMEOUT_OAUTH_CODE(42003, "oauth_code超时"),
        REQUEST_GET_ERR(43001, "需要GET请求"),
        REQUEST_POST_ERR(43002, "需要POST请求"),
        REQUEST_HTTPS_ERR(43003, "需要HTTPS请求"),
        REQUEST_USER_ATTENTION(43004, "需要接收者关注"),
        REQUEST_FRIEND_ERR(43005, "需要好友关系"),
        REQUEST_MEDIA_NULL(44001, "多媒体文件为空"),
        REQUEST_POSTDATA_NULL(44002, "POST的数据包为空"),
        MSG_IT_NULL(44003, "图文消息内容为空"),
        MSG_T_NUL(44004, "文本消息内容为空"),
        MSG_MEDIASIZE_LIMIT(45001, "多媒体文件大小超过限制"),
        MSG_CONTENT_LIMIT(45002, "消息内容超过限制"),
        MSG_TITLE_LIMIT(45003, "标题字段超过限制"),
        MSG_DESC_LIMIT(45004, "描述字段超过限制"),
        MSG_LINK_LIMIT(45005, "链接字段超过限制"),
        MSG_LKIMG_LIMIT(45006, "图片链接字段超过限制"),
        MSG_AUDIOTIME_LIMIT(45007, "语音播放时间超过限制"),
        MSG_IT_LIMIT(45008, "图文消息超过限制"),
        INTERFACE_LIMIT(45009, "接口调用超过限制"),
        MENU_NUM_LIMIT(45010, "创建菜单个数超过限制"),
        REPLAYTIME_LIMIT(45015, "回复时间超过限制"),
        GROUP_READONLY(45016, "系统分组，不允许修改"),
        GROUP_NMELEN_LIMIT(45017, "分组名字过长"),
        GROUP_NUM_LIMIT(45018, "分组数量超过上限"),
        MEDIA_NOT_EXISTED(46001, "不存在媒体数据"),
        MENU_VER_NOTEXISTED(46002, "不存在的菜单版本"),
        MENU_DATA_NOTEXISTED(46003, "不存在的菜单数据"),
        USER_NOT_EXISTED(46004, "不存在的用户"),
        JSON_XML_FORMAT_ERR(47001, "解析JSON/XML内容错误"),
        API_AUTHORIZATION_ERR(48001, "api功能未授权"),
        USER_API_AUTHORIZATION_ERR(50001, "用户未授权该api"),
        UNKNOWN(null, "未知返回类型");
        private Integer _key;
        private String _desc;

        private GlobalStatus(Integer _key, String _desc) {
            this._key = _key;
            this._desc = _desc;
        }

        public static GlobalStatus valueOf(Integer _key) {
            if (_key == null) {
                return null;
            } else {
                for (GlobalStatus item : values()) {
                    if (item.getKey().equals(_key)) {
                        return item;
                    }
                }
                return null;
            }
        }

        public Integer getKey() {
            return _key;
        }

        public String desc() {
            return _desc;
        }
    }

    /**
     * 接收微信的消息类型
     *
     * @author Roy
     */
    public enum MessageType {
        /**
         * 文本信息
         */
        TEXT("text"),
        /**
         * 图片信息
         */
        IMAGE("image"),
        /**
         * 语音信息
         */
        VOICE("voice"),
        /**
         * 视频信息
         */
        VIDEO("video"),
        /**
         * 地理位置信息
         */
        LOCATION("location"),
        /**
         * 链接信息
         */
        LINK("link"),
        /**
         * 音乐类型：回复消息使用类型
         */
        MUSIC("music"),
        /**
         * 图文消息
         */
        NEWS("news"),
        /**
         * 事件消息（关注，取消关注,按钮点击事件【URL类visit 类按钮转发网址则不触发此事件】）
         */
        EVENT("event");

        private String label;

        private MessageType(String label) {
            this.label = label;
        }

        public String labelOf() {
            return this.label;
        }
    }

    /**
     * 事件类型
     */
    public enum WechatEvent {
        /**
         * 订阅事件，包括无参数扫描二维码事件
         */
        SUBSCRIBE("subscribe"),
        /**
         * 取消订阅事件
         */
        UNSUBSCRIBE("unsubscribe"),
        /**
         * 二维码扫描时间，在已经关注了微信号时有参数返回，但不表示新参数
         */
        SCAN("scan"),
        /**
         * 微信开通位置服务后，用户同意以后每5秒钟上报一次用户位置事件
         */
        LOCATION("location"),
        /**
         * 自定义菜单点击事件，Visit 类型的按钮不触发该事件
         */
        CLICK("click"),
        /**
         * 菜单按钮view 类型点击事件
         */
        VIEW("view");
        private String _label;

        private WechatEvent(String _label) {
            this._label = _label;
        }

        public String labelOf() {
            return this._label;
        }
    }

    /**
     * 微信菜单类型
     */
    public enum WeChatMenuType {
        /**
         * 点击事件
         */
        CLICK("click"),
        /**
         * 超链接
         */
        VIEW("view"),
        /**
         * 一级菜单的key
         */
        NONE("none");

        private String label;

        private WeChatMenuType(String label) {
            this.label = label;
        }

        public String labelOf() {
            return label;
        }
    }

    /**
     * 菜单Key分类
     */
    public enum WeChatMenuKey {

    }


    /**
     * 媒体文件类型
     */
    public enum WeChatMediaType {
        /**
         * 图片
         */
        IMAGE("image"),
        /**
         * 语音
         */
        VOICE("voice"),
        /**
         * 视频
         */
        VIDEO("video"),
        /**
         * 缩略图
         */
        THUMB("thumb");

        private String label;

        private WeChatMediaType(String label) {
            this.label = label;
        }

        public String labelOf() {
            return this.label;
        }

        public static boolean containsKeyIgnorecase(String str) {
            if (!StringUtils.isBlank(str)) {
                for (WeChatMediaType em : values()) {
                    if (str.compareToIgnoreCase(em.labelOf()) == 0) {
                        return Boolean.TRUE;
                    }
                }
            }
            return Boolean.FALSE;
        }
    }

    /**
     * 自定义消息类型
     */
    public enum WeChatCustomMsg {
        /**
         * 没有匹配到输入的消息
         */
        MSG_NO_MATCH("msg_no_match");

        private String key;

        private WeChatCustomMsg(String key) {
            this.key = key;
        }

        public String keyOf() {
            return this.key;
        }
    }
}
