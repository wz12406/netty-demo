package cn.yesway.demo.io;

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
		ServerSocket server = null;
		try {
			server = new ServerSocket(port);
			System.out.println("the time server is start in port :"+port);
			Socket socket = null;
			TimeServerHandlerExecutePool singleExecute = new TimeServerHandlerExecutePool(50,10000);
			while(true){
				socket = server.accept();
				singleExecute.execute(new TimeServerHandler(socket));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
