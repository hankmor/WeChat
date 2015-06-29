package dendy.domain;

public class WeChatKeyWords {
	/**
	 * 系统ID
	 */
	private Long wc_id;
	/**
	 * 数字KEY
	 */
	private Long wc_key_num;
	/**
	 * key 中文
	 */
	private String wc_key_cn;
	/**
	 * 关键字回复内容
	 */
	private String wc_key_content;
	/**
	 * 该关键字是否可用 “Y” 可用  “N” 不可用
	 */
	private String wc_key_enable;
	
	public Long getWc_id() {
		return wc_id;
	}
	public void setWc_id(Long wc_id) {
		this.wc_id = wc_id;
	}
	public Long getWc_key_num() {
		return wc_key_num;
	}
	public void setWc_key_num(Long wc_key_num) {
		this.wc_key_num = wc_key_num;
	}
	public String getWc_key_cn() {
		return wc_key_cn;
	}
	public void setWc_key_cn(String wc_key_cn) {
		this.wc_key_cn = wc_key_cn;
	}
	public String getWc_key_content() {
		return wc_key_content;
	}
	public void setWc_key_content(String wc_key_content) {
		this.wc_key_content = wc_key_content;
	}
	public String getWc_key_enable() {
		return wc_key_enable;
	}
	public void setWc_key_enable(String wc_key_enable) {
		this.wc_key_enable = wc_key_enable;
	}
}
