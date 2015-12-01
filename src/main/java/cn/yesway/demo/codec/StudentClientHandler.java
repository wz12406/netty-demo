package cn.yesway.demo.codec;

import java.net.SocketAddress;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

/** 
 * @author wangzhen 
 * @version 1.0  
 * @createDate：2015年11月26日 下午2:17:59 
 * 
 */
public class StudentClientHandler extends ChannelHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.write(createStudent(1));
		ctx.flush();
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		System.out.println("发送成功---"+msg);
		
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelReadComplete(ctx);
	}
	
	
	private Object createStudent(int i) {
		Student student = new Student();
		student.setId(String.valueOf(i));
		student.setAdress("光山县");
		student.setAge(5);
		student.setHobby("篮球");
		return student;
	}

}
