package me.nabil.demo.nettydemo.server;

import me.nabil.demo.nettydemo.MyEntity;

import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

public class NettyDemoHandler extends SimpleChannelHandler {
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
		if(!(e.getMessage() instanceof MyEntity)){
			System.out.println("e.message is not instanceof MyEnity");
			return ;
		}
		MyEntity entity = (MyEntity) e.getMessage();

		System.out.println(this.getClass().toString()
				+  entity.toString());
	}

	@Override
	public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e)
			throws Exception {
		super.handleUpstream(ctx, e);
	}
}
