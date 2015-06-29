package dendy.controller;

import dendy.domain.msg.WeChatBaseMsg;
import dendy.service.IWeChatService;
import dendy.util.Assert;
import dendy.util.wechat.WeChatFactory;
import dendy.util.wechat.WeChatSetting;
import dendy.util.msg.BaseMessage;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import static dendy.util.wechat.WeChatStatusCode.*;

/**
 * 微信官方接口对接
 */
@Controller
@RequestMapping("/wx")
public class WeChatController {
    private static final Logger LOG = LoggerFactory.getLogger(WeChatController.class);

    @Autowired
    private IWeChatService wechatService;
    public static String TEST_MSG = null;

    /**
     * 校验是否来自微信官方网站
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public void validate(HttpServletRequest request, HttpServletResponse response) {
        String signature = request.getParameter("signature");
        String timestamp = request.getParameter("timestamp");
        String nonce = request.getParameter("nonce");
        String echostr = request.getParameter("echostr");
        try {
            PrintWriter out;
            boolean bol = wechatService.validate(signature, timestamp, nonce, echostr);
            if (bol) {
                out = response.getWriter();
                out.write(echostr);
                out.flush();
                out.close();
            }
        } catch (IOException e) {
            LOG.error("Validate wechat cause exception : ", e);
        }
    }

    /**
     * 接收消息，判定消息内容 进行智能回复
     *
     * @param request
     * @param response
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public void autoReply(HttpServletRequest request, HttpServletResponse response) {
        try {
            request.setCharacterEncoding("UTF-8");
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=utf-8");
            String signature = request.getParameter("signature");
            String timestamp = request.getParameter("timestamp");
            String nonce = request.getParameter("nonce");
            String echostr = request.getParameter("echostr");

		    /* 访问 合法性校验,判定是否是微信官方push 的信息，否则不做任何处理*/
            boolean isIllegal = wechatService.validate(signature, timestamp, nonce, echostr);
            if (isIllegal) {
                String retXml = "";
                BufferedReader reader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
                StringBuffer sbf = new StringBuffer();
                String line;
                while ((line = reader.readLine()) != null) {
                    sbf.append(line);
                }
                reader.close();
                LOG.info("Request info : " + sbf.toString());

                //解析xml消息
                if (StringUtils.isNotBlank(sbf.toString())) {
                    WeChatBaseMsg msg = WeChatFactory.parseToWeChatMsg(sbf.toString());
                    if (StringUtils.isNotBlank(WeChatController.TEST_MSG)) {
                        retXml = WeChatController.TEST_MSG;
                    } else {
                        retXml = wechatService.responseDispatcher(msg);
                    }

                    LOG.info("Response info : " + retXml);
                    if (StringUtils.isNotBlank(retXml)) {
                        response.getWriter().write(retXml);
                    }
                }
            }
        } catch (Exception e) {
            LOG.error("AutoReply cause exception : ", e);
        }
    }

    /**
     * 自定义菜单提交接口
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/dm")
    @ResponseBody
    public BaseMessage defineMenu(HttpServletRequest request) {
        String token = request.getParameter("token");
        BaseMessage msg = null;
        if (WeChatSetting.BR_ACCESS_TOKEN.equals(token)) {//验证成功
            GlobalStatus status = null;
            try {
                status = wechatService.defineMenu();
            } catch (IOException e) {
                LOG.error("Query menu cause exception : ", e);
            }
            if (GlobalStatus.SUCCESS.equals(status)) {
                msg = BaseMessage.successMsg("菜单创建成功!");
            } else {
                Assert.notNull(status);
                msg = BaseMessage.errorMsg(status.desc());
            }
        } else {
            msg = BaseMessage.errorMsg("token 验证失败！");
        }
        return msg;
    }

    @RequestMapping(value = "/syncUser")
    @ResponseBody
    public BaseMessage synchronizedDb(HttpServletRequest request, HttpServletResponse response) {
        String token = request.getParameter("token");
        if (WeChatSetting.BR_ACCESS_TOKEN.equals(token)) {//验证成功
            try {
                response.getOutputStream().write("正在同步.......".getBytes());
                response.getOutputStream().flush();
                wechatService.synchronizedUserInfo();
            } catch (IOException e) {
                LOG.error("Sync user cause exception : ", e);
            }
            return BaseMessage.successMsg("******************同步完成******************");
        } else {
            return BaseMessage.errorMsg("...................未授权访问..................");
        }
    }
}
