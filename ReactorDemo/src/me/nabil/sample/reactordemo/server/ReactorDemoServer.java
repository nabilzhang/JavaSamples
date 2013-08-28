package me.nabil.sample.reactordemo.server;

import java.io.IOException;

public class ReactorDemoServer {
	
	
	public static void main(String args[]) throws IOException{
		new Thread(new Reactor(8090)).start();
	}
}
