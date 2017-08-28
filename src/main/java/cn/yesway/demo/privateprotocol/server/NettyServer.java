package cn.yesway.demo.privateprotocol.server;

import cn.yesway.demo.privateprotocol.NettyConstant;
import cn.yesway.demo.privateprotocol.codec.NettyMessageDecoder;
import cn.yesway.demo.privateprotocol.codec.NettyMessageEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;

/** 
 * @author wangzhen 
 * @version 1.0  
 * @createDate：2015年12月16日 下午4:37:42 
 * 
 */
public class NettyServer {
	
	public static void main(String[] args) throws Exception{
		new NettyServer().bind();
	}

	private void bind() throws Exception{
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		ServerBootstrap b = new ServerBootstrap();
		b.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class)
		.handler(new LoggingHandler(LogLevel.INFO))
				.option(ChannelOption.SO_BACKLOG, 100)
				.childHandler(new ChannelInitializer<Channel>() {

			@Override
			protected void initChannel(Channel ch) throws Exception {
				ch.pipeline().addLast(new NettyMessageDecoder(1024*1024,4,4));
				ch.pipeline().addLast("MessageEncoder",new NettyMessageEncoder());
				ch.pipeline().addLast("readTimeoutHandler",new ReadTimeoutHandler(50));
				ch.pipeline().addLast(new LoginAuthRespHandler());
				ch.pipeline().addLast("HeartBeatHandler",new HeartBeatRespHandler());
			}
			
		});
		
		//绑定端口，同步等待成功
		b.bind(NettyConstant.REMOTEIP,NettyConstant.PORT).sync();
		System.out.println("netty server start ok:"+(NettyConstant.REMOTEIP+":"+NettyConstant.PORT));
	}
}
