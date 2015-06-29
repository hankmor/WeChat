package dendy.service;

import static dendy.util.wechat.WeChatStatusCode.*;

import dendy.domain.OpenIdAcquire;
import dendy.domain.WechatUserBind;
import dendy.domain.msg.WeChatBaseMsg;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 微信 接口 信息判定
 */
public interface IWeChatService {
    /**
     * @param signature 微信官方验证签名
     * @param timestamp 微信官方时间戳
     * @param nonce     微信官方返回随机数
     * @param echostr   微信官方返回随机字符串
     * @return 返回是否校验成功，成功表示当前请求是由微信服务器的合法信息，否则表示其他接口则直接返回false
     */
    public boolean validate(String signature, String timestamp, String nonce, String echostr);

    /**
     * response services
     */
    public String responseDispatcher(WeChatBaseMsg msg) throws IOException;

    /**
     * 菜单定义-通过https 定义微信菜单
     *
     * @return
     */
    public GlobalStatus defineMenu() throws IOException;

    /**
     * 查询菜单
     */
    public String obtainMenu() throws IOException;

    /**
     * 删除菜单
     */

    public GlobalStatus deleteMenu() throws IOException;

    /**
     * 上传素材
     *
     * @return 返回mediaID
     */
    public String uploadMaterial(String filePath, WeChatMediaType type) throws IOException;

    /**
     * 获取素材
     */
    public boolean validateMaterialActivity(Long mediaId);

    /**
     * 刷新access_token
     */
    public void flushToken() throws IOException;

    /**
     * 获取用户的微信信息
     *
     * @param openId
     */
    public WechatUserBind obtainUserInfo(String openId) throws IOException;

    /**
     * 查询微信用户是否存在，不存在则从微信官方抓取数据并保存到数据库中
     *
     * @return 返回WechatUserBind 对象
     */
    public WechatUserBind userExistedInDb(String openId) throws IOException;

    /**
     * 获取微信关注者的openId 列表
     *
     * @param open_id : 从哪一个openId开始
     */
    public OpenIdAcquire acquireOpenIdLst(String open_id) throws IOException;

    /**
     * 同步微信关注用户信息
     * 与微信服务器同步数据时使用
     */
    public void synchronizedUserInfo() throws IOException;

    /**
     * 执行状态判定
     * 用于判定http请求是否成功，不成功做一些特殊处理
     * errcode
     * 根据请求返回的errcode判定请求是否成功
     * 对失败的请求进行处理
     *
     * @param msg,返回的信息
     * @return 如果返回信息为失败则返回对应的信息，否则返回成功
     */
    public GlobalStatus executeStatus(String msg) throws IOException;

    public WechatUserBind findBindByUrlToken(String token);

    public WechatUserBind isVisitIllegal(HttpServletRequest request);

    public void updateBind(WechatUserBind bind);


    /**
     * 通过user_id 查询绑定关系
     *
     * @param user_id 用户系统ID
     * @return
     */
    public WechatUserBind findBindByUserId(Long user_id);

    /**
     * 通过OpenId 查询该用户是否绑定
     *
     * @param openId
     * @return WechatUserBind 或者null
     * null 表示未关注微信
     */
    public WechatUserBind findBindByOpenId(String openId);
}
