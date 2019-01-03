package me.nabil.demo.nettydemo.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;
import me.nabil.demo.nettydemo.UnixTime;

import java.util.List;

/**
 * TimeDecodeHandler
 *
 * @author nabil
 * @date 2019/1/3
 */
public class TimeDecodeHandler extends ReplayingDecoder<Void> {
    @Override
    protected void decode(
            ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        if (in.readableBytes() < 4) {
            return;
        }

        out.add(new UnixTime(in.readUnsignedInt()));
    }
}
