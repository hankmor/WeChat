package dendy.service.impl;

import dendy.domain.WechatUserBind;
import dendy.domain.event.MenuEventMsg;
import dendy.domain.event.SubscribeEventMsg;
import dendy.domain.event.WeChatEventMsg;
import dendy.domain.msg.WeChatBaseMsg;
import dendy.domain.msg.other.NewsMsg;
import dendy.mapper.WeChatMapper;
import dendy.service.IWeChatEventService;
import dendy.service.IWeChatService;
import dendy.util.wechat.WeChatConfig;
import dendy.util.wechat.WeChatStatusCode;
import dendy.util.config.Setting;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

@Service
public class WeChatEventServiceImpl implements IWeChatEventService {
    private static final Logger LOG = LoggerFactory.getLogger(IWeChatEventService.class);
    @Autowired
    private WeChatMapper weChatMapper;
    @Autowired
    private IWeChatService weChatService;

    /**
     * 事件回复
     */
    @Override
    public String responseEvent(WeChatBaseMsg msg) throws IOException {
        String resStr = "";
        WeChatEventMsg emsg = (WeChatEventMsg) msg;

        if (emsg != null && emsg.getEvent() != null) {
            switch (emsg.getEvent()) {
                // 关注事件
                case SUBSCRIBE:
                    // 已经关注扫描二维码事件  带参数
                case SCAN:
                    // 取消关注
                case UNSUBSCRIBE:
                    resStr = subscribeMsg(emsg);
                    break;
                case LOCATION://上报地理位置
                    LOG.info("上报地理位置：" + msg.toString());
                    break;
                case CLICK://菜单点击事件
                    resStr = WeChatConfig.executeDealKeyClick((MenuEventMsg) emsg);
                    break;
                default:
                    break;
            }
        }
        return resStr;
    }

    private String subscribeMsg(WeChatEventMsg msg) throws IOException {
        String retStr = "";
        if (msg != null && msg.getClass().equals(SubscribeEventMsg.class)) {
            SubscribeEventMsg smsg = (SubscribeEventMsg) msg;
            NewsMsg newsMsg = new NewsMsg();
            StringBuffer sb = new StringBuffer("");
            sb.append(Setting.global("system.company") + "是一个娱乐平台，信息不断更新中！");
            sb.append("\n————————————————");
            sb.append("\n输入序号查看相关问题：");
            sb.append("\n  [1]访问百度");
            sb.append("\n  [2]访问新浪");
            sb.append("\n————————————————");
            sb.append("\n更多联系方式：");
            sb.append("\n全国服务热线：12345678");
            sb.append("\n" + Setting.global("system.company") + "将竭诚为您服务！【" + Setting.global("system.company") + "】");
            newsMsg.setCreateTime(System.currentTimeMillis());
            newsMsg.setFromUserName(msg.getToUserName());
            newsMsg.setToUserName(msg.getFromUserName());
            newsMsg.setMsgType(WeChatStatusCode.MessageType.NEWS);
            newsMsg.setArticleCount(1);
            List<NewsMsg.Articles> articleses = new LinkedList<NewsMsg.Articles>();
            NewsMsg.Articles articles = newsMsg.new Articles();
            articles.setTitle("欢迎您来到" + Setting.global("system.company") + "！");
            articles.setPicUrl("http://pic1.nipic.com/2008-09-08/200898163242920_2.jpg");
            articles.setDescription(sb.toString());
            articles.setUrl("http://www.baidu.com");
            articleses.add(articles);
            newsMsg.setArticles(articleses);

            switch (smsg.getEvent()) {
                case SUBSCRIBE://关注
                    WechatUserBind userTmp = weChatService.obtainUserInfo(msg.getFromUserName());//微信服务端数据库用户基本信息
                    WechatUserBind user = weChatMapper.findBindByOpenId(msg.getFromUserName());
                    if (user != null) {//以前关注过
                        WechatUserBind tmp = userTmp == null ? user : userTmp;

                        tmp.setWechat_bind_time(Calendar.getInstance().getTime());
                        tmp.setWechat_url_token(DigestUtils.md5Hex(tmp.getOpenid()));
                        weChatMapper.updateBind(tmp);
                    } else {//以前没有关注过
                        LOG.debug("userTemp:" + userTmp);
                        LOG.debug("user:" + user);
                        LOG.debug("msg:" + msg);
                        LOG.debug("msg.getFromUserName:" + msg.getFromUserName());
                        LOG.debug("userTmp.getOpenid:" + userTmp.getOpenid());
                        userTmp.setWechat_bind_time(Calendar.getInstance().getTime());
                        userTmp.setWechat_url_token(DigestUtils.md5Hex(userTmp.getOpenid()));
                        // TODO 设置为平台的userId
                        userTmp.setUser_id(null);
                        weChatMapper.contactWechat(userTmp);
                    }
                    retStr = newsMsg.replyXmlData();
                    /*WechatUserBind userBind = new WechatUserBind();
                    //获取用户信息，并将信息注入到数据库中...未完
					userBind.setOpenid(msg.getFromUserName());
					userBind.setWechat_bind_time(Calendar.getInstance().getTime());
					weChatMapper.contactWechat(userBind);*/
                    break;
                case UNSUBSCRIBE://取消关注时，取消关联
                    weChatMapper.unContactWechat(msg.getFromUserName());
                    break;
                case SCAN://扫描，已经关注
                    retStr = newsMsg.replyXmlData();
                    break;
                default:
                    break;
            }
            LOG.info("Response info : " + retStr);
        }
        return retStr;
    }
}
