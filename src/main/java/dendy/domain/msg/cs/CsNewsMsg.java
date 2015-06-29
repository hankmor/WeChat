package dendy.domain.msg.cs;

import dendy.domain.msg.WeChatBaseCustomServiceMsg;
import org.apache.commons.lang.StringUtils;

import java.util.List;

import static dendy.util.wechat.WeChatStatusCode.*;

public class CsNewsMsg extends WeChatBaseCustomServiceMsg {
    /**
     * 图文消息列表
     */
    private List<Article> articles;

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public CsNewsMsg() {
        this.setMsgtype(MessageType.NEWS);
    }

    public class Article {
        /**
         * 标题
         */
        private String title;
        /**
         * 描述
         */
        private String description;
        /**
         * 点击后跳转的链接
         */
        private String url;
        /**
         * 图文消息的图片链接，支持JPG、PNG格式，较好的效果为大图640*320，小图80*80
         */
        private String picurl;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getPicurl() {
            return picurl;
        }

        public void setPicurl(String picurl) {
            this.picurl = picurl;
        }

    }

    @Override
    public String toReplyJson() {
        StringBuilder tmp = new StringBuilder("");
        tmp.append("");
        tmp.append("{");
        tmp.append("\"touser\":\"").append(this.getTouser()).append("\",");
        tmp.append("\"msgtype\":\"").append(this.getMsgtype().labelOf()).append("\",");
        tmp.append("\"news\":{");
        tmp.append("\"articles\": [");
        StringBuilder at = new StringBuilder("");

        for (Article article : this.getArticles()) {
            if (StringUtils.isBlank(at.toString())) {
                at.append(",");
            }
            at.append("{");
            at.append("\"title\":\"").append(article.getTitle()).append("\",");
            at.append("\"description\":\"").append(article.getDescription()).append("\",");
            at.append("\"url\":\"").append(article.getUrl()).append("\",");
            at.append("\"picurl\":\"").append(article.getPicurl()).append("\"");
            at.append("}");
        }
        tmp.append(at);
        tmp.append("]");
        tmp.append("}");
        tmp.append("}");
        return tmp.toString();
    }

}
