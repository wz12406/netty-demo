package cn.yesway.demo.nio;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/** 
 * @author wangzhen 
 * @version 1.0  
 * @createDate：2015年11月19日 下午5:03:33 
 * 
 */
public class TimeService {
	public static void main(String[] args) {
		int port = 8080;
		MultiplexerTimeServer timeServer  = new MultiplexerTimeServer(port);
		new Thread(timeServer,"NIO-MultiplexerTimeServer-001").start();
	}
}
