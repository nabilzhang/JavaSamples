package me.nabil.demo.nettydemo.server;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import me.nabil.demo.nettydemo.UnixTime;

/**
 * Handles a server-side channel.
 *
 * @author nabil
 * @date 2019/1/3
 */
public class TimeServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(final ChannelHandlerContext ctx) {
        UnixTime unixTime = new UnixTime();
        ChannelFuture f = ctx.writeAndFlush(new UnixTime());
        System.out.println(unixTime);
        f.addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
