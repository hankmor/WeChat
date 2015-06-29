package dendy.util.wechat;

import dendy.domain.event.MenuEventMsg;
import dendy.domain.msg.WeChatBaseMsg;
import dendy.service.IMenuHandleService;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;

import java.io.File;
import java.lang.reflect.Method;

public class WeChatConfig {
    private static final Logger LOG = LoggerFactory.getLogger(WeChatConfig.class);

    /**
     * 配置文件
     */
    private static final String CONFIG_FILE = "/WEB-INF/classes/config/wechat-config.xml";
    private static Document configDom;

    static {
        parseConfig();
    }

    public static Document getConfigDom() {
        return configDom;
    }

    public static String getConfigContext() {
        return CONFIG_FILE;
    }

    /**
     * 使用缓存，List的原始数据用于保持数据的顺序，这里额外添加一个UseBuffer保存使用过数据便于快速回调，读取信息
     */
    public static Document parseConfig() {
        if (configDom == null) {
            // 启动时加载数据。注意数据文件地址是可以替换的。
            File file = new File(ContextLoader.getCurrentWebApplicationContext().getServletContext().getRealPath(CONFIG_FILE));
            Document document = null;
            try {
                SAXReader saxReader = new SAXReader();
                document = saxReader.read(file);
            } catch (Exception e) {
                LOG.error("Parse WeChat config file cause exception : ", e);
            }
            configDom = document;
        }
        return configDom;
    }

    public static String executeDealKeyClick(MenuEventMsg msg) {
        String retStr = "";

        Element e = configDom.getRootElement()
                .elementByID(msg.getEventKey());

        if (e == null) {
            throw new RuntimeException("微信接口处理配置文件读取失败：ID：" + msg.getEventKey() + " 未定义!");
        }

        String _class = e.attributeValue("class");
        String _method = e.attributeValue("method");

        try {
            IMenuHandleService handle = (IMenuHandleService) Class.forName(_class).newInstance();
            Method mth = Class.forName(_class).getMethod(_method, WeChatBaseMsg.class);
            retStr = (String) mth.invoke(handle, (WeChatBaseMsg) msg);
        } catch (Exception e1) {
            LOG.error("Execute click cause exception : ", e1);
        }
        return retStr;
    }
}
