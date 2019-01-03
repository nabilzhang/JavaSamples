package me.nabil.demo.nettydemo.server;

import com.google.gson.Gson;
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

    Gson gson = new Gson();

    @Override
    protected void encode(ChannelHandlerContext ctx, UnixTime msg, ByteBuf out) {
        String json = gson.toJson(msg);
        System.out.println(json);
        out.writeInt(json.length());
        out.writeBytes(json.getBytes());
    }
}
