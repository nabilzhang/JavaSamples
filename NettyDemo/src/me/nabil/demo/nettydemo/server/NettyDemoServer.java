package me.nabil.demo.nettydemo.server;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import me.nabil.demo.nettydemo.NettyDemoDecoder;
import me.nabil.demo.nettydemo.NettyDemoHandler;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

public class NettyDemoServer {
	public static void main(String args[]) {
		ChannelFactory factory = new NioServerSocketChannelFactory(
				Executors.newCachedThreadPool(),
				Executors.newCachedThreadPool());
		
		ServerBootstrap bootstrap = new ServerBootstrap(factory);
		
		NettyDemoHandler demoHandler = new NettyDemoHandler();
		NettyDemoDecoder decoder = new NettyDemoDecoder();
		
		ChannelPipeline pipeline = bootstrap.getPipeline();
		
		pipeline.addLast("decode", decoder);
		pipeline.addLast("handler", demoHandler);
		
		bootstrap.bind(new InetSocketAddress(888));
	}
}
