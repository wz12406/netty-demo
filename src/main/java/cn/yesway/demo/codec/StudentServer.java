package cn.yesway.demo.codec;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/** 
 * @author wangzhen 
 * @version 1.0  
 * @createDate：2015年11月26日 下午1:59:59 
 * 
 */
public class StudentServer {

	public static void main(String[] args) throws Exception{
		int port = 8888;
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
		    ServerBootstrap b = new ServerBootstrap();
		    b.group(bossGroup, workerGroup)
			    .channel(NioServerSocketChannel.class)
			    .option(ChannelOption.SO_BACKLOG, 1024)
			    .handler(new LoggingHandler(LogLevel.INFO))
			    .childHandler(new ChannelInitializer<Channel>() {

					@Override
					protected void initChannel(Channel ch) throws Exception {
						ch.pipeline()
						.addLast(new ObjectDecoder(1024*1024, 
								ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())))
								.addLast(new ObjectEncoder())
								.addLast(new StudentHandler());
						
					}
				});
		    // 绑定端口，同步等待成功
		    ChannelFuture f = b.bind(port).sync();

		    // 等待服务端监听端口关闭
		    f.channel().closeFuture().sync();
		} finally {
		    // 优雅退出，释放线程池资源
		    bossGroup.shutdownGracefully();
		    workerGroup.shutdownGracefully();
		}
	}
}
