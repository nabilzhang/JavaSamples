package me.nabil.demo.nettydemo.server;

import me.nabil.demo.nettydemo.MyEntity;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.replay.ReplayingDecoder;

public class NettyDemoDecoder extends
		ReplayingDecoder<NettyDemoDecoder.DecodeState> {

	private int contentLength;

	public NettyDemoDecoder() {
		super(DecodeState.LEN);
	}

	enum DecodeState {
		LEN, CONTENT
	}

	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel,
			ChannelBuffer buffer, DecodeState state) throws Exception {

		switch (state) {
		case LEN:
			if (buffer.readableBytes() < 4) {
				System.out.println("data so short");
				return null;
			}
			byte[] lenBytes = new byte[3];
			buffer.readBytes(lenBytes);
			String len = new String(lenBytes);
			
			try{
				contentLength = Integer.valueOf(len);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return null;
			}
			
			System.out.println(contentLength);
			checkpoint(DecodeState.CONTENT);
			break;
		case CONTENT:
			byte[] contentBytes = new byte[contentLength];
			buffer.readBytes(contentBytes);
			String content = new String(contentBytes);
			
			MyEntity entity = new MyEntity();
			entity.setId(contentLength);
			entity.setContent(content);
			reset();
			return entity;

		default:
			
			System.out.println("why here, god!");
			
			reset();
			break;
		}

		return null;
	}

	private void reset() {
		checkpoint(DecodeState.LEN);
		this.contentLength = 0;
	}
}
