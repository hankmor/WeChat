package dendy.domain;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.List;

/**
 * 获取 微信关注者的信息
 * @author Ranxc
 *
 */
public class OpenIdAcquire {
	/**
	 * 关注该公众账号的总用户数
	 */
	@JsonProperty("total")
	private Integer total;
	/**
	 * 拉取的OPENID个数
	 * 最大值为10000
	 */
	@JsonProperty("count")
	private Integer count;
	/**
	 * 列表数据，OPENID的列表
	 */
	@JsonProperty("Data")
	private Data Data;
	/**
	 * 拉取列表的后一个用户的OPENID 
	 */
	@JsonProperty("next_openid")
	private String next_openid;
	
	public Integer getTotal() {
		return total;
	}
	public void setTotal(Integer total) {
		this.total = total;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	
	public String getNext_openid() {
		return next_openid;
	}
	public void setNext_openid(String next_openid) {
		this.next_openid = next_openid;
	}
	
	public Data getData() {
		return Data;
	}
	public void setData(Data data) {
		Data = data;
	}

	public class Data{
		private List<String> openid ;

		public List<String> getOpenid() {
			return openid;
		}

		public void setOpenid(List<String> openid) {
			this.openid = openid;
		}
	}
}
