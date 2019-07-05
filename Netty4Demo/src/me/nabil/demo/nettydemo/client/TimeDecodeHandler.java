package me.nabil.demo.nettydemo.client;

import com.google.gson.Gson;
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
public class TimeDecodeHandler extends ReplayingDecoder<TimeDecodeHandler.DecodeState> {

    private int contentLength;
    private Gson gson = new Gson();

    enum DecodeState {
        LEN, CONTENT
    }

    TimeDecodeHandler() {
        super(DecodeState.LEN);
    }

    @Override
    protected void decode(
            ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
        switch (state()) {
            case LEN:
                contentLength = in.readInt();
                checkpoint(DecodeState.CONTENT);
                break;
            case CONTENT:
                byte[] contentBytes = new byte[contentLength];
                in.readBytes(contentBytes);
                String content = new String(contentBytes);
                System.out.println("content:" + content);
                UnixTime unix = gson.fromJson(content, UnixTime.class);
                out.add(unix);
            default:

        }
    }
}
