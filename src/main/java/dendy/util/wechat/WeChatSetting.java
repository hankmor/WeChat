package dendy.util.wechat;

import org.apache.commons.lang.StringUtils;

public class WeChatSetting {
	/**
	 * 尝试操作，最多尝试次数
	 */
	public static final int MAX_TRY_TIMES = 3;
	/**
	 * 微网站接入主机地址
	 */
	public static final String HOST_URL = "http://dendy.pagekite.me/wechat";
	/**
	 * 邦融的关键信息KEY
	 */
	public static final String BR_ACCESS_TOKEN ="abcdefghigklmnopqrstuvwxyz";
	/**
	 * 网站接入token 验证使用，微信接入使用
	 */
	public static final String TOKEN = "DendyWeChatToken1233210CoLtd";
	/**
	 * APP ID
	 */
	public static final String APPID = "wx76cfae22a5c25a40";
	/**
	 * APPSECRET 平台验证
	 */
	public static final String APPSECRET = "4520cf093b88522f361a1264452162d2";
	
	/**
	 * 语言国家:国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语 
	 */
	public static final String LANG = "zh_CN";

	/*基础支持*******************************************************/
	/**
	 * 访问access token 
	 * 更新时间为7200 秒进行更新
	 */
	private static String ACCESS_TOKEN ="hLIacNToLXQDrQs3HygeHr4xct1JFOZ7pz3hWmadhi8CoL-5vibqkk6_rUB5h37KPgVmuybz4amC6aPk9bbOxhkBHxc36jcxJNpMJhJWYFhSN7uCsmXIazud2g9cxM6Kznu7Re2K9Urzaboph0g2cw";
	/**
	 * access_token 获取地址
	 */
	public static String ACCESS_TOKEN_URL(){
		return "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+APPID+"&secret="+APPSECRET;
	}
	/**
	 * 同步操作时使用
	 */
	public synchronized static String getACCESS_TOKEN() {
		return ACCESS_TOKEN;
	}
	/**
	 * 同步操作更新ACCESS_TOKEN
	 * @param ACCESS_TOKEN
	 */
	public synchronized static void setACCESS_TOKEN(String ACCESS_TOKEN) {
		WeChatSetting.ACCESS_TOKEN = ACCESS_TOKEN;
	}
	/**
	 * 上传媒体文件
	 */
	public static String UPLOAD_MATERIAL_URL(String type){
		return "http://file.api.weixin.qq.com/cgi-bin/media/upload?access_token="+WeChatSetting.getACCESS_TOKEN()+"&type="+type;
	}
	/**
	 * 下载媒体文件
	 */
	public static String DOWNLOAD_METERIAL_URL(String media_id){
		return "http://file.api.weixin.qq.com/cgi-bin/media/get?access_token="+WeChatSetting.getACCESS_TOKEN()+"&media_id="+media_id;
	}
	
	/*发送消息**********************************************************/
	/**
	 * 发送客服消息
	 */
	public static String CUSTOM_MSG_SEND(){
		return "https://api.weixin.qq.com/cgi-bin/message/custom/other?access_token="+WeChatSetting.getACCESS_TOKEN();
	}
	/*用户管理**********************************************************/
	/**
	 * 创建分组
	 * http请求方式: POST（请使用https协议）
	 * https://api.weixin.qq.com/cgi-bin/groups/create?access_token=ACCESS_TOKEN
	 * POST数据格式：json
	 * POST数据例子：{"group":{"name":"test"}}
	 * return ::::{
	 *     "group": {
	 *         "id": 107, 
	 *         "name": "test"
	 *     }
	 * }
	 * error: {"errcode":40013,"errmsg":"invalid appid"}
	 */
	public static String CREATE_GROUP(){
		return "https://api.weixin.qq.com/cgi-bin/groups/create?access_token="+WeChatSetting.getACCESS_TOKEN();
	}
	
	/**
	 * 查询所有分组
	 * GET（请使用https协议）
	 * RETURN : {
	    "groups": [
	        {
	            "id": 0, 
	            "name": "未分组", 
	            "count": 72596
	        }, 
	        {
	            "id": 1, 
	            "name": "黑名单", 
	            "count": 36
	        }]
	     }
	 * ERROR : {"errcode":40013,"errmsg":"invalid appid"}
	 */
	public static String OBTAIN_GROUP(){
		return "https://api.weixin.qq.com/cgi-bin/groups/get?access_token="+WeChatSetting.getACCESS_TOKEN();
	}
	/**
	 * 获取用户所在分组
	 * http请求方式: POST（请使用https协议）
	 * POST数据格式：json
	 * POST数据例子：{"openid":"od8XIjsmk6QdVTETa9jLtGWA6KBc"}
	 * return : {"groupid": 102}
	 * error  : {"errcode":40003,"errmsg":"invalid openid"}
	 */
	public static String WECHAT_USER_GROUP_IN(){
		return "https://api.weixin.qq.com/cgi-bin/groups/getid?access_token="+WeChatSetting.getACCESS_TOKEN();
	}
	/**
	 * 修改分组名
	 * http请求方式: POST（请使用https协议）
	 * POST数据格式：json
	 * POST数据例子：{"group":{"id":108,"name":"test2_modify2"}}
	 */
	public static String MODIFY_GROUP_NAME(){
		return "https://api.weixin.qq.com/cgi-bin/groups/update?access_token="+WeChatSetting.getACCESS_TOKEN();
	}
	/**
	 * 移动用户分组
	 * http请求方式: POST（请使用https协议）
	 * POST数据格式：json
	 * POST数据例子：{"openid":"oDF3iYx0ro3_7jD4HFRDfrjdCM58","to_groupid":108}
	 * return : {"errcode": 0, "errmsg": "ok"}
	 */
	public static String ARRANGE_USER_TO_GROUP(){
		return "https://api.weixin.qq.com/cgi-bin/groups/members/update?access_token="+WeChatSetting.getACCESS_TOKEN();
	}
	/**
	 * 获取微信用户信息
	 */
	public static String USER_INFO_URL(String openId){
		return "https://api.weixin.qq.com/cgi-bin/user/info?access_token="+WeChatSetting.getACCESS_TOKEN()+"&openid="+openId+"&lang="+WeChatSetting.LANG;
	}
	/**
	 * 获取关注着openId 列表
	 * next_openid 从哪一个openId 开始进行下载  Null 时 将从头开始获取
	 */
	public static String ACQUIRE_OPENID_LST_URL(String next_openid){
		return "https://api.weixin.qq.com/cgi-bin/user/get?access_token="+getACCESS_TOKEN()+"&next_openid="+(StringUtils.isBlank(next_openid)?"":next_openid);
	}
	
	/*自定义菜单**********************************************************/
	/**
	 * 自定义菜单URL接口
	 */
	public static String DEFINE_MENU(){
		return "https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+WeChatSetting.getACCESS_TOKEN();
	}
	/**
	 * 查询菜单
	 */
	public static String OBTAIN_MENU(){
		return "https://api.weixin.qq.com/cgi-bin/menu/get?access_token="+WeChatSetting.getACCESS_TOKEN();
	}
	/**
	 * 删除自定义的菜单
	 */
	public static String DELETE_ALL_MENU(){
		return "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token="+WeChatSetting.getACCESS_TOKEN();
	}
	
	/*推广支持**********************************************************/
	/**
	 * 二维码生成
	 * 短暂的二维码：{"expire_seconds": 1800, "action_name": "QR_SCENE", "action_info": {"scene": {"scene_id": 123}}}
	 * 永久二维码：{"action_name": "QR_LIMIT_SCENE", "action_info": {"scene": {"scene_id": 123}}}
	 * expire_seconds 	该二维码有效时间，以秒为单位。 最大不超过1800。
	 * action_name 	二维码类型，QR_SCENE为临时,QR_LIMIT_SCENE为永久
	 * action_info 	二维码详细信息
	 * scene_id 	场景值ID，临时二维码时为32位非0整型，永久二维码时最大值为100000（目前参数只支持1--100000） 
	 * return : {"ticket":"gQG28DoAAAAAAAAAASxodHRwOi8vd2VpeGluLnFxLmNvbS9xL0FuWC1DNmZuVEhvMVp4NDNMRnNRAAIEesLvUQMECAcAAA==","expire_seconds":1800}
	 */
	public static String QRCODE_CREATE(){
		return "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token="+getACCESS_TOKEN();
	}
	
	/**
	 * 换取二维码
	 * ticket 为 二维码提取码
	 * 提醒：TICKET记得进行UrlEncode
	 * ticket正确情况下，http 返回码是200，是一张图片，可以直接展示或者下载。
	 * HTTP头（示例）如下：
	 * Accept-Ranges:bytes
	 * Cache-control:max-age=604800
	 * Connection:keep-alive
	 * Content-Length:28026
	 * Content-Type:image/jpg
	 * Date:Wed, 16 Oct 2013 06:37:10 GMT
	 * Expires:Wed, 23 Oct 2013 14:37:10 +0800
	 * Server:nginx/1.4.1

	 */
	public static String QRCODE_EXCHANGE(String ticket){
		return "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket="+ticket;
	}
	
}
