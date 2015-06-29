package dendy.domain.msg.cs;

import dendy.domain.msg.WeChatBaseCustomServiceMsg;

public class CsVoiceMsg extends WeChatBaseCustomServiceMsg {

	/**
	 * 发送的语音的媒体ID 
	 */
	private String media_id;
	
	public String getMedia_id() {
		return media_id;
	}

	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}

	@Override
	public String toReplyJson() {
		/*
		    "touser":"OPENID",
		    "msgtype":"voice",
		    "voice":
		    {
		      "media_id":"MEDIA_ID"
		    }
		*/
		StringBuilder tmp = new StringBuilder("");
		tmp.append("{");
		tmp.append("\"touser\":").append("\"").append(this.getTouser()).append("\"");
		tmp.append("\"msgtype\":").append("\"").append(this.getMsgtype().labelOf()).append("\"");
		
		tmp.append("\"voice\":{");
		tmp.append("\"media_id\":").append("\"").append(this.getMedia_id()).append("\"");
		tmp.append("}");
		
		tmp.append("}");
		return tmp.toString();
	}

}
