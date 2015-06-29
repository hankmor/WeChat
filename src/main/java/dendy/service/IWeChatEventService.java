package dendy.service;

import dendy.domain.msg.WeChatBaseMsg;

import java.io.IOException;

public interface IWeChatEventService {
    public String responseEvent(WeChatBaseMsg msg) throws IOException;
}
