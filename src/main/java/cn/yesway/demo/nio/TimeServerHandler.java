package cn.yesway.demo.nio;

import java.net.Socket;

/** 
 * @author wangzhen 
 * @version 1.0  
 * @createDate：2015年11月19日 下午5:48:38 
 * 
 */
public class TimeServerHandler implements Runnable {
	private Socket socket;
	public TimeServerHandler(Socket socket){
		this.socket = socket;
	}
	@Override
	public void run() {
			
	}

}
