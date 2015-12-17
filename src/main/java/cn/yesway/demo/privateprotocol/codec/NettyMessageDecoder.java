package cn.yesway.demo.privateprotocol.codec;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cn.yesway.demo.privateprotocol.model.Header;
import cn.yesway.demo.privateprotocol.model.NettyMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/** 
 * @author wangzhen 
 * @version 1.0  
 * @createDate：2015年12月16日 下午2:24:33 
 * 
 */
public class NettyMessageDecoder extends LengthFieldBasedFrameDecoder {
	
	MarshallingDecoder marshallingDecoder ;
	
	public NettyMessageDecoder(int maxFrameLength, int lengthFieldOffset,
			int lengthFieldLength) throws IOException {
		super(maxFrameLength, lengthFieldOffset, lengthFieldLength);
		this.marshallingDecoder = new MarshallingDecoder();
	}

	@Override
	protected Object decode(ChannelHandlerContext ctx, ByteBuf in)
			throws Exception {
		ByteBuf frame = (ByteBuf) super.decode(ctx, in);
		if(frame == null)return null;
		
		NettyMessage message = new NettyMessage();
		Header header = new Header();
		header.setCrcCode(frame.readInt());
		header.setLength(frame.readInt());
		header.setSessionID(frame.readLong());
		header.setType(frame.readByte());
		header.setPriority(frame.readByte());
		
		int size = frame.readInt();
		if (size > 0) {
		    Map<String, Object> attch = new HashMap<String, Object>(size);
		    int keySize = 0;
		    byte[] keyArray = null;
		    String key = null;
		    for (int i = 0; i < size; i++) {
				keySize = frame.readInt();
				keyArray = new byte[keySize];
				frame.readBytes(keyArray);
				key = new String(keyArray, "UTF-8");
				attch.put(key, marshallingDecoder.decode(frame));
		    }
		    keyArray = null;
		    key = null;
		    header.setAttachment(attch);
		}
		if (frame.readableBytes() > 4) {
		    message.setBody(marshallingDecoder.decode(frame));
		}
		message.setHeader(header);
		return message; 
	}
	
}
