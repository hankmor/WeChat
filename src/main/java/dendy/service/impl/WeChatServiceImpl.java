package dendy.service.impl;

import dendy.domain.OpenIdAcquire;
import dendy.domain.WechatUserBind;
import dendy.domain.menu.WeChatMenu;
import dendy.domain.msg.WeChatBaseMsg;
import dendy.mapper.WeChatMapper;
import dendy.service.IWeChatDialogService;
import dendy.service.IWeChatEventService;
import dendy.service.IWeChatService;
import dendy.util.wechat.WeChatSetting;
import dendy.util.http.HttpRequestUtil;
import dendy.util.json.JacksonUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static dendy.util.wechat.WeChatStatusCode.*;

/**
 * 微信 接口 信息判定
 */
@Service
public class WeChatServiceImpl implements IWeChatService {
    private static final Logger LOG = LoggerFactory.getLogger(IWeChatService.class);

    @Autowired
    private IWeChatEventService eventServices;
    @Autowired
    private IWeChatDialogService dialogServices;
    @Autowired
    private WeChatMapper weChatMapper;

    @Override
    public boolean validate(String signature, String timestamp, String nonce, String echostr) {
        signature = StringUtils.trimToEmpty(signature);
        nonce = StringUtils.trimToEmpty(nonce);
        timestamp = StringUtils.trimToEmpty(timestamp);
        echostr = StringUtils.trimToEmpty(echostr);
        String[] str = {WeChatSetting.TOKEN, timestamp, nonce};
        java.util.Arrays.sort(str);
        String pwd = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            md.update((str[0] + str[1] + str[2]).getBytes());
            pwd = new BigInteger(1, md.digest()).toString(16);
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        }

        if (signature.equalsIgnoreCase(pwd)) {
            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }

    @Override
    public String responseDispatcher(WeChatBaseMsg msg) throws IOException {
        String resStr = "";
        if (msg != null) {
            switch (msg.getMsgType()) {
                case EVENT:
                    resStr = eventServices.responseEvent(msg);
                    break;
                default:
                    resStr = dialogServices.reply(msg);
                    break;
            }
        }
        return resStr;
    }

    @Override
    public GlobalStatus defineMenu() throws IOException {
        StringBuilder menus = new StringBuilder("{");
        menus.append("\"button\":[");
        List<WeChatMenu> m = weChatMapper.findAllMenuEnable("Y");
        StringBuilder menusTmp = new StringBuilder("");
        for (WeChatMenu par : m) {
            if (par.getMenu_parent() == null) {
                if (StringUtils.isNotBlank(menusTmp.toString())) {
                    menusTmp.append(",");
                }
                menusTmp.append("{");
                menusTmp.append("\"name\":").append("\"").append(par.getMenu_name()).append("\",");
                switch (par.getType()) {
                    case CLICK://点击后与服务器交互
                        menusTmp.append("\"type\":").append("\"").append(par.getType().labelOf()).append("\",");
                        menusTmp.append("\"key\":").append("\"").append(par.getMenu_key()).append("\"");
                        break;
                    case VIEW://点击后直接访问网页
                        menusTmp.append("\"type\":").append("\"").append(par.getType().labelOf()).append("\",");
                        menusTmp.append("\"url\":").append("\"").append(par.getMenu_url()).append("\"");
                        break;
                    case NONE://表示是多级菜单的父级
                        menusTmp.append("\"sub_button\":[");
                        StringBuilder subTmp = new StringBuilder("");
                        for (WeChatMenu sub : m) {
                            if (par.getMenu_id().equals(sub.getMenu_parent())) {
                                if (StringUtils.isNotBlank(subTmp.toString())) {
                                    subTmp.append(",");
                                }
                                subTmp.append("{");
                                subTmp.append("\"name\":").append("\"").append(sub.getMenu_name()).append("\",");
                                subTmp.append("\"type\":").append("\"").append(sub.getType().labelOf()).append("\",");
                                switch (sub.getType()) {
                                    case CLICK:
                                        subTmp.append("\"key\":").append("\"").append(sub.getMenu_key()).append("\"");
                                        break;
                                    case VIEW:
                                        subTmp.append("\"url\":").append("\"").append(sub.getMenu_url()).append("\"");
                                        break;
                                    default:
                                        break;
                                }
                                subTmp.append("}");
                            }
                        }
                        menusTmp.append(subTmp);

                        menusTmp.append("]");
                        break;
                    default:
                        break;
                }
                menusTmp.append("}");
            }
        }
        menus.append(menusTmp);
        menus.append("]");
        menus.append("}");
        LOG.info(menus.toString());
        LOG.debug(menus.toString());
        String ret = "";
        GlobalStatus status;
        int i = 1;
        do {
            ret = HttpRequestUtil.executeRquest(WeChatSetting.DEFINE_MENU(), menus.toString(), false,
                    null, null, "gb2312", null, null, null);
            status = executeStatus(ret);
            ++i;
        } while (i < WeChatSetting.MAX_TRY_TIMES &&
                (GlobalStatus.TOKEN_ERROR.equals(status)
                        || GlobalStatus.PARAMETER_ACCESSTOKEN_TIMEOUT.equals(status)));

        HashMap map = JacksonUtil.fromObject(ret, HashMap.class);
        String errcode = (String) map.get("errcode");

        return GlobalStatus.valueOf(errcode == null ? null : Integer.parseInt(errcode));
    }

    @Override
    public String uploadMaterial(String filePath, WeChatMediaType type) throws IOException {
        String retStr = null;

        File f = new File(filePath);
        float size = 0;
        try {
            InputStream io = new FileInputStream(f);
            size = io.available() / 1024;
            io.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        float limit = 0;
        boolean isFormatMach = false;
        //1. 根据微信规则进行校验媒体大小
        switch (type) {
            case IMAGE://支持jpg/jpeg 后缀图片， 大小 128kb
                isFormatMach = Pattern.compile("(.*\\.(?i)(jpeg|jpg))$").matcher("").matches();
                limit = 128;
                break;
            case VOICE:
                isFormatMach = Pattern.compile("(.*\\.(?i)(mp3|amr))$").matcher("").matches();
                limit = 256;
                break;
            case VIDEO:
                isFormatMach = Pattern.compile("(.*\\.(?i)(mp4))$").matcher("").matches();
                limit = 1024;
                break;
            case THUMB:
                isFormatMach = Pattern.compile("(.*\\.(?i)(jpeg|jpg))$").matcher("").matches();
                limit = 64;
                break;
            default:
                break;
        }

        if (!isFormatMach || limit < size / 1024) {//表示格式或者大小不合法  则返回上传失败
            return null;
        }

        String boundary = "---------------------------" + System.currentTimeMillis();
        Map<String, String> ctMap = new HashMap<String, String>();
        ctMap.put("Content-Type", "multipart/form-data; boundary=" + boundary);

        String ret = HttpRequestUtil.executeRquest(WeChatSetting.UPLOAD_MATERIAL_URL(type.labelOf()), null, false, ctMap, null, "UTF-8", null, filePath, boundary);
        LOG.debug(ret);

        HashMap map = JacksonUtil.fromObject(ret, HashMap.class);
        retStr = map != null ? (String) map.get("media_id") : null;
        LOG.debug(retStr);

        return retStr;
    }

    @Override
    public boolean validateMaterialActivity(Long mediaId) {
        return false;
    }

    @Override
    public void flushToken() throws IOException {
        String ret = HttpRequestUtil.executeRquest(WeChatSetting.ACCESS_TOKEN_URL(), null, true,
                null, null, "gb2312", null, null, null);
        HashMap map = JacksonUtil.fromObject(ret, HashMap.class);
        String retStr = map != null ? (String) map.get("access_token") : null;
        LOG.debug("access_token:" + retStr);
        WeChatSetting.setACCESS_TOKEN(retStr);
    }

    @Override
    public WechatUserBind obtainUserInfo(String openId) throws IOException {
        // TODO test
        if (true) {
            WechatUserBind wechatUserBind = new WechatUserBind();
            wechatUserBind.setOpenid(openId);
            wechatUserBind.setSubscribe("1");
            return wechatUserBind;
        }
        WechatUserBind user = null;
        GlobalStatus status = null;
        int i = 1;
        //重复关注OpenId 不会改变
        do {
            String ret = HttpRequestUtil.executeRquest(WeChatSetting.USER_INFO_URL(openId), null, true,
                    null, null, "utf-8", null, null, null);
            LOG.debug("获得的用户信息：" + ret);
            user = JacksonUtil.fromObject(ret, WechatUserBind.class);
            status = executeStatus(ret);
            LOG.debug("重试次数：" + i);
            ++i;
        } while (i < WeChatSetting.MAX_TRY_TIMES &&
                (GlobalStatus.TOKEN_ERROR.equals(status) ||
                        GlobalStatus.PARAMETER_ACCESSTOKEN_TIMEOUT.equals(status)));
        if (user.getErrcode() != null) {
            GlobalStatus st = GlobalStatus.valueOf(Integer.parseInt(user.getErrcode()));
            LOG.error("Obtain user info failed, error is ：" + st.getKey() + " - " + st.desc());
        }
        return user;
    }

    @Override
    public WechatUserBind userExistedInDb(String openId) throws IOException {
        WechatUserBind user = weChatMapper.findBindByOpenId(openId);
        if (user == null) {
            user = obtainUserInfo(openId);
            if (user != null) {
                user.setWechat_url_token(DigestUtils.md5Hex(user.getOpenid()));
                weChatMapper.contactWechat(user);
            }
        }
        return user;
    }

    @Override
    public OpenIdAcquire acquireOpenIdLst(String openid) throws IOException {

        String ret = "";
        GlobalStatus status;
        int i = 1;
        do {
            ret = HttpRequestUtil.executeRquest(
                    WeChatSetting.ACQUIRE_OPENID_LST_URL(openid), null, true,
                    null, null, "gb2312", null, null, null);
            status = executeStatus(ret);
            ++i;
        } while (i < WeChatSetting.MAX_TRY_TIMES &&
                (GlobalStatus.TOKEN_ERROR.equals(status) ||
                        GlobalStatus.PARAMETER_ACCESSTOKEN_TIMEOUT.equals(status)));

        OpenIdAcquire obj = JacksonUtil.fromObject(ret, OpenIdAcquire.class);

        return obj;
    }

    @Override
    public void synchronizedUserInfo() throws IOException {
        //1. 从服务器上读取关注用户的openId 列表
        OpenIdAcquire openIdObj = null;
        do {
            openIdObj = acquireOpenIdLst(
                    (openIdObj != null ? openIdObj.getNext_openid() : null)
            );
            if (openIdObj != null
                    && openIdObj.getCount() > 0
                    && openIdObj.getData() != null
                    && openIdObj.getData().getOpenid() != null) {
//				int i = 1;
                for (String oid : openIdObj.getData().getOpenid()) {
                    /*if(i == 73){
                        LOG.debug("waiting...");
					}
					i++;*/
                    WechatUserBind user = weChatMapper.findBindByOpenId(oid);
                    if (user == null) {
                        user = obtainUserInfo(oid);
                        if (user != null) {
                            user.setWechat_url_token(DigestUtils.md5Hex(user.getOpenid()));
                            weChatMapper.contactWechat(user);
                        }
                    } else {
                        WechatUserBind onlineUser = obtainUserInfo(oid);

                        if (onlineUser != null && !user.compareToOnline(onlineUser)) {
                            onlineUser.setUser_id(user.getUser_id());
                            onlineUser.setWechat_bind_time(user.getWechat_bind_time());
                            onlineUser.setWechat_url_token(user.getWechat_url_token());
                            weChatMapper.updateBind(onlineUser);
                        }
                    }
                }
            }

        } while (openIdObj != null && StringUtils.isNotBlank(openIdObj.getNext_openid()));
    }

    @Override
    public GlobalStatus executeStatus(String msg) throws IOException {
        GlobalStatus status;

        HashMap map = JacksonUtil.fromObject(msg, HashMap.class);
        Object obj = map.get("errcode");
        Integer errcode = obj == null ? null : Integer.parseInt(obj.toString());

        if (errcode == null) {
            status = GlobalStatus.SUCCESS;
        } else {
            status = GlobalStatus.valueOf(errcode);
            if (GlobalStatus.TOKEN_ERROR.equals(status)
                    || GlobalStatus.PARAMETER_ACCESSTOKEN_TIMEOUT.equals(status)) {// token 超时，需要刷新token
                flushToken();
            }
        }
        return status;
    }

    @Override
    public WechatUserBind findBindByUrlToken(String token) {
        return weChatMapper.findBindByUrlToken(token);
    }

    @Override
    public WechatUserBind isVisitIllegal(HttpServletRequest request) {
        String token = request.getParameter("BindParam");
        if (StringUtils.isBlank(token)) {
            return null;
        } else {
            WechatUserBind bind = weChatMapper.findBindByUrlToken(token);
            return bind;
        }
    }

    @Override
    public void updateBind(WechatUserBind userBind) {
        weChatMapper.updateBind(userBind);
    }

    @Override
    public WechatUserBind findBindByUserId(Long user_id) {
        WechatUserBind wechatUserBind = new WechatUserBind();
        if (user_id != null) {
            wechatUserBind = weChatMapper.findBindByUserId(String.valueOf(user_id));
        }
        return wechatUserBind;
    }

    @Override
    public WechatUserBind findBindByOpenId(String openId) {
        return weChatMapper.findBindByOpenId(openId);
    }

    @Override
    public String obtainMenu() throws IOException {
        String ret = "";
        GlobalStatus status;
        int i = 0;
        do {
            ret = HttpRequestUtil.executeRquest(
                    WeChatSetting.OBTAIN_MENU(), null, true,
                    null, null, "utf8", null, null, null);
            status = executeStatus(ret);
            ++i;

        } while (i < WeChatSetting.MAX_TRY_TIMES &&
                (GlobalStatus.TOKEN_ERROR.equals(status) ||
                        GlobalStatus.PARAMETER_ACCESSTOKEN_TIMEOUT.equals(status)));

        return ret;
    }

    @Override
    public GlobalStatus deleteMenu() throws IOException {
        String ret = "";
        GlobalStatus status;
        int i = 1;
        do {
            ret = HttpRequestUtil.executeRquest(
                    WeChatSetting.DELETE_ALL_MENU(), null, true,
                    null, null, "utf8", null, null, null);
            status = executeStatus(ret);
            ++i;
        } while (i < WeChatSetting.MAX_TRY_TIMES &&
                (GlobalStatus.TOKEN_ERROR.equals(status) ||
                        GlobalStatus.PARAMETER_ACCESSTOKEN_TIMEOUT.equals(status)));

        return status;
    }
}
