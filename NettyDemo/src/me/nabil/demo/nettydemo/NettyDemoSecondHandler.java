package me.nabil.demo.nettydemo;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

public class NettyDemoSecondHandler extends SimpleChannelHandler {
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e){
		
		
		ChannelBuffer buffer = (ChannelBuffer)e.getMessage();
		char c =  (char)buffer.readByte();
		System.out.println(buffer.readableBytes());
		System.out.println(this.getClass().toString() + c);
		e.getChannel().write(e.getMessage());
//		ctx.sendUpstream(e);
	}
}
