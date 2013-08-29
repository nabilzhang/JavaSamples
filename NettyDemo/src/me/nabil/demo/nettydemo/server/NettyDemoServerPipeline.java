package me.nabil.demo.nettydemo.server;

import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;

public class NettyDemoServerPipeline implements ChannelPipelineFactory {

	@Override
	public ChannelPipeline getPipeline() throws Exception {
		ChannelPipeline pipeline = Channels.pipeline();
		NettyDemoHandler demoHandler = new NettyDemoHandler();
		NettyDemoDecoder decoder = new NettyDemoDecoder();
		pipeline.addLast("decode", decoder);
		pipeline.addLast("handler", demoHandler);
		return pipeline;
	}

}
