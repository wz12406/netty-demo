package cn.yesway.demo.codec;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

/** 
 * @author wangzhen 
 * @version 1.0  
 * @createDate：2015年11月26日 下午1:59:59 
 * 
 */
public class StudentClient {

	public static void main(String[] args) throws Exception{
		int port = 8888;
		 EventLoopGroup group = new NioEventLoopGroup();
		try {
		    Bootstrap b = new Bootstrap();
		    b.group(group)
			    .channel(NioSocketChannel.class)
			    .option(ChannelOption.TCP_NODELAY, true)
			    .handler(new ChannelInitializer<Channel>() {

					@Override
					protected void initChannel(Channel ch) throws Exception {
						ch.pipeline()
						.addLast(new ObjectDecoder(1024*1024, 
								ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())))
								.addLast(new ObjectEncoder())
								.addLast(new StudentClientHandler());
						
					}
				});
		    // 绑定端口，同步等待成功
		    ChannelFuture f = b.connect("127.0.0.1",port).sync();

		    // 等待服务端监听端口关闭
		    f.channel().closeFuture().sync();
		} finally {
		    // 优雅退出，释放线程池资源
			group.shutdownGracefully();
		}
	}
}
