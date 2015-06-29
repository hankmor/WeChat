package dendy.domain.menu;

import dendy.util.wechat.WeChatStatusCode;
import org.apache.commons.lang.StringUtils;

public class WeChatMenu {
    /**
     * 系统ID
     */
    private Long menu_id;
    /**
     * 菜单KEY值，用于消息接口推送，不超过128字节
     */
    private String menu_key;

    private String menu_type;
    private WeChatStatusCode.WeChatMenuType type;
    /**
     * 父级菜单菜单KEY值
     */
    private Long menu_parent;
    /**
     * 菜单标题
     * 不超过16个字节，子菜单不超过40个字节
     */
    private String menu_name;
    /**
     * 网页链接
     * 用户点击菜单可打开链接，不超过256字节
     */
    private String menu_url;
    /**
     * 该菜单项是否可以使用 Y表示可以使用，N表示不可以使用
     */
    private String menu_enable;
    /**
     * 位置
     *
     * @return
     */
    private Integer menu_idx;

    public Integer getMenu_idx() {
        return menu_idx;
    }

    public void setMenu_idx(Integer menu_idx) {
        this.menu_idx = menu_idx;
    }

    public String getMenu_enable() {
        return menu_enable;
    }

    public void setMenu_enable(String menu_enable) {
        this.menu_enable = menu_enable;
    }

    public Long getMenu_id() {
        return menu_id;
    }

    public void setMenu_id(Long menu_id) {
        this.menu_id = menu_id;
    }

    public String getMenu_key() {
        return menu_key;
    }

    public void setMenu_key(String menu_key) {
        this.menu_key = menu_key;
    }

    public String getMenu_type() {
        return menu_type;
    }

    public void setMenu_type(String menu_type) {
        this.type = WeChatStatusCode.WeChatMenuType.valueOf(StringUtils.upperCase(menu_type));
        this.menu_type = menu_type;
    }

    public WeChatStatusCode.WeChatMenuType getType() {
        return type;
    }

    public void setType(WeChatStatusCode.WeChatMenuType type) {
        this.menu_type = type.labelOf();
        this.type = type;
    }

    public Long getMenu_parent() {
        return menu_parent;
    }

    public void setMenu_parent(Long menu_parent) {
        this.menu_parent = menu_parent;
    }

    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }

    public String getMenu_url() {
        return menu_url;
    }

    public void setMenu_url(String menu_url) {
        this.menu_url = menu_url;
    }
}
