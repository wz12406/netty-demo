package cn.yesway.demo.privateprotocol.model;
/** 
 * @author wangzhen 
 * @version 1.0  
 * @createDate：2015年12月16日 上午11:24:40 
 * 
 */
public class NettyMessage {
	private Header header;//消息头
	
	private Object body;//消息体
	

	public Header getHeader() {
		return header;
	}

	public void setHeader(Header header) {
		this.header = header;
	}

	public Object getBody() {
		return body;
	}

	public void setBody(Object body) {
		this.body = body;
	}

	@Override
	public String toString() {
		return "NettyMessage [header="+header+"]";
	}
	
}
