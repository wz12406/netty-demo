package cn.yesway.demo.privateprotocol;
/** 
 * @author wangzhen 
 * @version 1.0  
 * @createDate：2015年12月16日 下午2:42:20 
 * 
 */
public enum MessageType {
	SERVICE_REQ((byte) 0), SERVICE_RESP((byte) 1), ONE_WAY((byte) 2), LOGIN_REQ(
		    (byte) 3), LOGIN_RESP((byte) 4), HEARTBEAT_REQ((byte) 5), HEARTBEAT_RESP(
		    (byte) 6);
	
	 private byte value;
	
	 private MessageType(byte value) {
			this.value = value;
	 }
	 
	 public byte value() {
			return this.value;
	 }
}
