package me.nabil.demo.nettydemo.server;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

public class NettyDemoServer {
	/**
	 * 
	 * @param args
	 */
	public static void main(String args[]) {
		ChannelFactory factory = new NioServerSocketChannelFactory(
				Executors.newCachedThreadPool(),
				Executors.newCachedThreadPool());

		ServerBootstrap bootstrap = new ServerBootstrap(factory);

		
		/**
		 * 采用默认ChannelPipeline管道 这意味着同一个Handler实例将被多个Channel通道共享
		 * 这种方式对于Handler中无有状态的成员变量是可以的，并且可以提高性能！
		 */
//		ChannelPipeline pipeline = bootstrap.getPipeline();
//		NettyDemoHandler demoHandler = new NettyDemoHandler();
//		NettyDemoDecoder decoder = new NettyDemoDecoder();
//		pipeline.addLast("decode", decoder);
//		pipeline.addLast("handler", demoHandler);
		
		/**
		 * 非默认channelPipeline,这样实例将不会被多个channel通道共享
		 */
		NettyDemoServerPipeline pipelineFactory = new NettyDemoServerPipeline();
		bootstrap.setPipelineFactory(pipelineFactory);
		bootstrap.bind(new InetSocketAddress(888));
	}
}
