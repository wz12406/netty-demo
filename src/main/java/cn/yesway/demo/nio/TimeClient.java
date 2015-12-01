package cn.yesway.demo.nio;
/** 
 * @author wangzhen 
 * @version 1.0  
 * @createDate：2015年11月20日 下午3:10:58 
 * 
 */
public class TimeClient {
	
	
	public static void main(String[] args) {
		int port = 8080;
		new Thread(new TimeClientHandle("127.0.0.1",port),"TimeClient-001").start();;

	}
}
