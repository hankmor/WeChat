package dendy.domain.msg.other;

import dendy.domain.msg.WeChatBaseMsg;
import dendy.exception.MsgParseException;
import dendy.util.wechat.WeChatStatusCode;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 该Bean 只用于消息回复，回复类型为NEWS 图文信息
 */
public class NewsMsg extends WeChatBaseMsg {
    /**
     * 图文消息个数，限制为10条以内
     * 必须项
     */
    private Integer ArticleCount;
    /**
     * 多条图文消息信息，默认第一个item为大图
     * 注意，如果图文数超过10，则将会无响应
     * 必须项
     */
    private List<Articles> articles;


    /**
     * 无参构造函数，用户消息回复
     */
    public NewsMsg() {
        this.setMsgType(WeChatStatusCode.MessageType.NEWS);
    }

    /**
     * 有参构造函数
     */
    public NewsMsg(String msg) {
        this.parseBean(msg);
    }

    public Integer getArticleCount() {
        return ArticleCount;
    }

    public void setArticleCount(Integer articleCount) {
        ArticleCount = articleCount;
    }

    public List<Articles> getArticles() {
        return articles;
    }

    public void setArticles(List<Articles> articles) {
        this.articles = articles;
    }

    public class Articles {
        /**
         * 图文消息标题
         */
        private String Title;
        /**
         * 图文消息描述
         */
        private String Description;
        /**
         * 图片链接，支持JPG、PNG格式
         * 较好的效果为大图360*200，小图200*200
         */
        private String PicUrl;
        /**
         * 点击图文消息跳转链接
         */
        private String Url;

        public Articles() {
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String title) {
            Title = title;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String description) {
            Description = description;
        }

        public String getPicUrl() {
            return PicUrl;
        }

        public void setPicUrl(String picUrl) {
            PicUrl = picUrl;
        }

        public String getUrl() {
            return Url;
        }

        public void setUrl(String url) {
            Url = url;
        }
    }

    @Override
    public void parseEspecial(Element element) {
        if (this.getMsgType().equals(WeChatStatusCode.MessageType.NEWS)) {
            if (element != null) {
                //ArticleCount
                //articles
                this.setArticleCount(Integer.parseInt(element.elementText("ArticleCount")));
                Element artics = element.element("Articles");
                Iterator<Element> it = artics.elementIterator("item");
                List<Articles> lst = new ArrayList<Articles>();
                while (it.hasNext()) {
                    Element e = it.next();
                    Articles a = new Articles();
                    a.setTitle(e.elementText("Title"));
                    a.setDescription(e.elementText("Description"));
                    a.setPicUrl(e.elementText("PicUrl"));
                    a.setUrl(e.elementText("Url"));
                    lst.add(a);
                }
                this.setArticles(lst);

                if (!this.getArticleCount().equals(lst.size())) {
                    this.setArticleCount(lst.size());
                }
            }
        } else {
            throw new MsgParseException("微信信息格式解析错误，不能由" + this.getMsgType().labelOf() + "解析成  " + WeChatStatusCode.MessageType.NEWS.labelOf() + " 类型");
        }
    }

    @Override
    public String replyXmlData() {
        StringBuilder tmp = new StringBuilder("");
        tmp.append("<xml>");

        tmp.append("<ToUserName><![CDATA[").append(this.getToUserName()).append("]]></ToUserName>");
        tmp.append("<FromUserName><![CDATA[").append(this.getFromUserName()).append("]]></FromUserName>");
        tmp.append("<CreateTime>").append(System.currentTimeMillis()).append("</CreateTime>");
        tmp.append("<MsgType><![CDATA[").append(this.getMsgType().labelOf()).append("]]></MsgType>");
        tmp.append("<ArticleCount>").append(this.getArticleCount()).append("</ArticleCount>");

        tmp.append("<Articles>");
        if (this.getArticles() != null && this.getArticles().size() > 0) {
            for (Articles atc : this.getArticles()) {
                tmp.append("<item>");
                tmp.append("<Title><![CDATA[").append(atc.getTitle()).append("]]></Title> ");
                tmp.append("<Description><![CDATA[").append(atc.getDescription()).append("]]></Description>");
                tmp.append("<PicUrl><![CDATA[").append(StringUtils.trimToEmpty(atc.getPicUrl())).append("]]></PicUrl>");
                tmp.append("<Url><![CDATA[").append(atc.getUrl()).append("]]></Url>");
                tmp.append("</item>");
            }
        }
        tmp.append("</Articles>");
        tmp.append("</xml> ");
        return tmp.toString();
    }

}
