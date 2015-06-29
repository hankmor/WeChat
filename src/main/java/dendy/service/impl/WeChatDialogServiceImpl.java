package dendy.service.impl;

import dendy.domain.WeChatKeyWords;
import dendy.domain.msg.WeChatBaseMsg;
import dendy.domain.msg.other.TextMsg;
import dendy.mapper.WeChatMapper;
import dendy.service.IWeChatDialogService;
import dendy.util.Assert;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static dendy.util.wechat.WeChatStatusCode.*;

@Service
public class WeChatDialogServiceImpl implements IWeChatDialogService {
    @Autowired
    private WeChatMapper weChatMapper;

    @Override
    public String reply(WeChatBaseMsg msg) {
        Assert.notNull(msg);
        TextMsg txt = new TextMsg();
        txt.setToUserName(msg.getFromUserName());
        txt.setFromUserName(msg.getToUserName());
        txt.setCreateTime(System.currentTimeMillis() / 1000);
        txt.setMsgType(MessageType.TEXT);
        if (msg.getClass().equals(TextMsg.class)) {
            TextMsg txtmsg = (TextMsg) msg;
            // 回复的数字
            if (NumberUtils.isDigits(StringUtils.trimToEmpty(txtmsg.getContent()).trim())) {
                WeChatKeyWords kw = weChatMapper.findKeyWordsByKey(Long.parseLong(txtmsg.getContent().trim()));
                if (kw != null) {
                    txt.setContent(StringUtils.trimToEmpty(kw.getWc_key_content()));
                } else {
                    String ct = weChatMapper.findSpecItemVal(WeChatCustomMsg.MSG_NO_MATCH.keyOf());
                    txt.setContent(StringUtils.trimToEmpty(ct));
                }
            } else {
                WeChatKeyWords wk = weChatMapper.findMatching(txtmsg.getContent());
                if (wk != null) {
                    txt.setContent(StringUtils.trimToEmpty(wk.getWc_key_content()));
                } else {
                    String ct = weChatMapper.findSpecItemVal(WeChatCustomMsg.MSG_NO_MATCH.keyOf());
                    txt.setContent(StringUtils.trimToEmpty(ct));
                }
            }
        } else {
            //回复非关键字信息时，反馈给用户对应的错误信息
            String msg_no_match = weChatMapper.findSpecItemVal(WeChatCustomMsg.MSG_NO_MATCH.keyOf());
            txt.setContent(StringUtils.isNotBlank(msg_no_match) ? msg_no_match : "亲，请按操作提示进行回复！");
        }
        return txt.replyXmlData();
    }
}
