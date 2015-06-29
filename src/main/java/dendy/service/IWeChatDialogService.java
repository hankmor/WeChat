package dendy.service;

import dendy.domain.msg.WeChatBaseMsg;

/**
 * 用户输入信息进行会话
 */
public interface IWeChatDialogService {
    public String reply(WeChatBaseMsg msg);
}
