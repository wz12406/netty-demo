package cn.yesway.demo.privateprotocol.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.ReadTimeoutHandler;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import cn.yesway.demo.privateprotocol.NettyConstant;
import cn.yesway.demo.privateprotocol.codec.NettyMessageDecoder;
import cn.yesway.demo.privateprotocol.codec.NettyMessageEncoder;

/** 
 * @author wangzhen 
 * @version 1.0  
 * @createDate：2015年12月16日 下午4:14:47 
 * 
 */
public class NettyClient {
	private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
	EventLoopGroup group = new NioEventLoopGroup();
	
	public static void main(String[] args) {
		new NettyClient().connect(NettyConstant.PORT,NettyConstant.REMOTEIP);
	}

	private void connect(int port, String host)  {
		
		try {
		Bootstrap b =new Bootstrap();
		b.group(group).channel(NioSocketChannel.class)
			.option(ChannelOption.TCP_NODELAY, true)
			.handler(new ChannelInitializer<Channel>() {

				@Override
				protected void initChannel(Channel ch) throws Exception {
					ch.pipeline().addLast(new NettyMessageDecoder(1024*1024, 4, 4));
					ch.pipeline().addLast("MessageEncoder",new NettyMessageEncoder());
					ch.pipeline().addLast("readTimeoutHandler",new ReadTimeoutHandler(50));
					ch.pipeline().addLast("LoginAuthHandler",new LoginAuthReqHandler());
					ch.pipeline().addLast("HeartBeatHandler",new HeartBeatReqHandler());

				}
			});
		
		//发起异步连接操作
		ChannelFuture future = b.connect(new InetSocketAddress(host, port),
					new InetSocketAddress(NettyConstant.LOCALIP,NettyConstant.LOCAL_PORT)).sync();
			future.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
			executorService.execute(new Runnable() {
				@Override
				public void run() {
						try {
							TimeUnit.SECONDS.sleep(5);
							connect(NettyConstant.PORT, NettyConstant.REMOTEIP);
						} catch (Exception e) {
							e.printStackTrace();
						}
				}
			});
		}
		
	}
}
