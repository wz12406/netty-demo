package cn.yesway.demo.privateprotocol.client;

import cn.yesway.demo.privateprotocol.MessageType;
import cn.yesway.demo.privateprotocol.model.Header;
import cn.yesway.demo.privateprotocol.model.NettyMessage;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/** 
 * @author wangzhen 
 * @version 1.0  
 * @createDate：2015年12月16日 下午2:38:35 
 * 
 */
public class LoginAuthReqHandler extends ChannelHandlerAdapter{

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
			ctx.writeAndFlush(buildLoginReq());
	}

	
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		NettyMessage message = (NettyMessage) msg;
		System.out.println("--------------------------------");
		//如果是握手应答消息，需要判断是否认证成功
		if(message.getHeader()!=null&&message.getHeader().getType()==MessageType.LOGIN_RESP.value()){
			byte loginResult = (Byte) message.getBody();
			if(loginResult!=(byte)0){
				ctx.close();
			}else{
				System.out.println("Login is OK:"+message);
				ctx.fireChannelRead(message);
			}
		}else{
			ctx.fireChannelRead(message);
		}
	}

	

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)throws Exception {
		System.out.println("--------------------------");
		ctx.fireExceptionCaught(cause);
	}
	
	private NettyMessage buildLoginReq() {
		NettyMessage message = new NettyMessage();
		Header header = new Header();
		header.setType(MessageType.LOGIN_REQ.value());
		message.setHeader(header);
		return message;
	}
	
}
