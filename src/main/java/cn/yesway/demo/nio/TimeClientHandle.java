package cn.yesway.demo.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/** 
 * @author wangzhen 
 * @version 1.0  
 * @createDate：2015年11月20日 下午3:13:57 
 * 
 */
public class TimeClientHandle implements Runnable {

	private String host;
	private int port;
	private Selector selector;
	private SocketChannel socketChannel;
	private volatile boolean stop;
	
	public TimeClientHandle(String host, int port) {
		this.host = host==null?"127.0.0.1":host;
		this.port = port;
		try {
			selector = Selector.open();
			socketChannel = SocketChannel.open();
			socketChannel.configureBlocking(false);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		
				
	}

	@Override
	public void run() {
		try {
			doConnect();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		while(!stop){
			try {
				selector.select(1000);
				Iterator<SelectionKey> it = selector.selectedKeys().iterator();
				SelectionKey key = null;
				while(it.hasNext()){
					key = it.next();
					it.remove();
					try{
						handleInput(key);
					}catch(Exception e){
						if(key!=null){
							key.cancel();
							if(key.channel()!=null){
								key.channel().close();
							}
						}
					}
					
				}
			} catch (IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
			
		}
		// 多路复用器关闭后，所有注册在上面的Channel和Pipe等资源都会被自动去注册并关闭，所以不需要重复释放资源
		if (selector != null)
		    try {
			selector.close();
		    } catch (IOException e) {
			e.printStackTrace();
		    }
	}

	private void handleInput(SelectionKey key) throws ClosedChannelException, IOException {
		if(key.isValid()){
			//判断是否连接成功
			SocketChannel sc = (SocketChannel) key.channel();
			if(key.isConnectable()){
				if(sc.finishConnect()){
					sc.register(selector, SelectionKey.OP_READ);
					doWrite(sc);
					System.out.println("已建立连接");
				}else{
					System.exit(1);
				}
			}
			if(key.isReadable()){
				System.out.println("可读状态");
				ByteBuffer readBuffer = ByteBuffer.allocate(1024);
				int readBytes = sc.read(readBuffer);
				if(readBytes>0){
					readBuffer.flip();
					byte[] bytes = new byte[readBytes];
					readBuffer.get(bytes);
					String body = new String(bytes,"utf-8");
					System.out.println("Now is :"+body);
					this.stop  = true;
				}else if(readBytes <0){
					key.cancel();
					sc.close();
				}
				
			}
		}
	}

	private void doConnect() throws IOException {
		//如果直接连接成功，则注册到多路复用器上，发送请求消息，读应答
		if(socketChannel.connect(new InetSocketAddress(host, port))){
			socketChannel.register(selector, SelectionKey.OP_READ);
			doWrite(socketChannel);
		}else{
			socketChannel.register(selector, SelectionKey.OP_CONNECT);
		}
	}

	private void doWrite(SocketChannel sc) throws IOException {
		byte[] req = "QUERY TIME ORDER".getBytes();
		ByteBuffer writeBuffer = ByteBuffer.allocate(req.length);
		writeBuffer.put(req);
		writeBuffer.flip();
		sc.write(writeBuffer);
		if(!writeBuffer.hasRemaining()){
			System.out.println("Send order 2 server succeed.");
		}
		
	}
	
}
