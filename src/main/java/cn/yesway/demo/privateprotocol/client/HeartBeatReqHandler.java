package cn.yesway.demo.privateprotocol.client;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.ScheduledFuture;

import java.util.concurrent.TimeUnit;

import cn.yesway.demo.privateprotocol.MessageType;
import cn.yesway.demo.privateprotocol.model.Header;
import cn.yesway.demo.privateprotocol.model.NettyMessage;

/** 
 * @author wangzhen 
 * @version 1.0  
 * @createDate：2015年12月16日 下午3:47:12 
 * 
 */
public class HeartBeatReqHandler extends ChannelHandlerAdapter {
	private volatile ScheduledFuture<?> heartBeat;
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg)
			throws Exception {
		NettyMessage message =(NettyMessage) msg;
		//如果握手成功，主动发送心跳消息
		if(message.getHeader()!=null&&message.getHeader().getType()==MessageType.LOGIN_RESP.value()){
			heartBeat = ctx.executor().scheduleAtFixedRate(new HeartBeatReqHandler.HeartBeatTask(ctx), 
					0, 5000, TimeUnit.MILLISECONDS);
		}else if(message.getHeader()!=null&&message.getHeader().getType()==MessageType.HEARTBEAT_RESP.value()){
			System.out.println("Client receive server heart beat message:--->"+message);
		}else{
			super.channelRead(ctx, msg);
		}
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
			throws Exception {
		if(heartBeat!=null){
			heartBeat.cancel(true);
			heartBeat=null;
		}
		ctx.fireExceptionCaught(cause);
	}

	private class HeartBeatTask implements Runnable{
		private final ChannelHandlerContext ctx;
		public HeartBeatTask(final ChannelHandlerContext ctx){
			this.ctx =ctx;
		}
		@Override
		public void run() {
			NettyMessage heatBeat  = buildHeatBeat();
			System.out.println("Client send heart beat message to server :-->"+heatBeat);
			ctx.writeAndFlush(heatBeat);
		}
		
		private NettyMessage buildHeatBeat() {
			NettyMessage message  = new NettyMessage();
			Header header = new Header();
			header.setType(MessageType.HEARTBEAT_REQ.value());
			message.setHeader(header);
			return message;
		}
		
	}
}
