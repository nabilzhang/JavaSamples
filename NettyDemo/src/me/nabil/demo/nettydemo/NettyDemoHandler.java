package me.nabil.demo.nettydemo;

import org.jboss.netty.channel.ChannelEvent;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

public class NettyDemoHandler extends SimpleChannelHandler {
	@Override
	public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
		MyEntity entity = (MyEntity) e.getMessage();

		System.out.println(this.getClass().toString()
				+  entity.toString());
//		ctx.sendUpstream(e);
		e.getFuture().cancel();
		e.getChannel().close();
	}

	@Override
	public void handleUpstream(ChannelHandlerContext ctx, ChannelEvent e)
			throws Exception {
		super.handleUpstream(ctx, e);
	}
}
