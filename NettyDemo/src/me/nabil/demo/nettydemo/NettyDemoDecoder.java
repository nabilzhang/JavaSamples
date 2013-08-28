package me.nabil.demo.nettydemo;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

public class NettyDemoDecoder extends FrameDecoder {

	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel,
			ChannelBuffer buffer) throws Exception {
		
		int length = buffer.readableBytes();
		
		if (length < 3){
			System.out.println("data so short");
			return null;
		}
		buffer.readByte();
		MyEntity myEntity = new MyEntity();
		myEntity.setId(length);
		return myEntity;
	}
	

}
