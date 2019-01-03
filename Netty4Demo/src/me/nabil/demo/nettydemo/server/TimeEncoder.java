package me.nabil.demo.nettydemo.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import me.nabil.demo.nettydemo.UnixTime;

/**
 * TimeEncoder
 *
 * @author nabil
 * @date 2019/1/3
 */
public class TimeEncoder extends MessageToByteEncoder<UnixTime> {
    @Override
    protected void encode(ChannelHandlerContext ctx, UnixTime msg, ByteBuf out) {
        out.writeInt((int) msg.value());
    }
}
