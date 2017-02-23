package me.nabil.demo.zookeeper;

import me.nabil.demo.zookeeper.zkserver.ClusterLaucher;
import org.apache.zookeeper.server.quorum.QuorumPeerConfig;

import java.io.IOException;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws IOException, QuorumPeerConfig.ConfigException {

        ClusterLaucher.startStandAloneServer();

    }
}
