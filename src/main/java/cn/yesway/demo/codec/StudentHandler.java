package cn.yesway.demo.codec;

import java.net.SocketAddress;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

/** 
 * @author wangzhen 
 * @version 1.0  
 * @createDate：2015年11月26日 下午2:09:18 
 * 
 */
public class StudentHandler extends ChannelHandlerAdapter {

	private int count = 0;
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		
		System.out.println("接受到student"+count+++"个对象"+msg);
		ctx.writeAndFlush(count);
	}
	
}
