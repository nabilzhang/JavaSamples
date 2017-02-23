package me.nabil.demo.zookeeper.zkserver;

import org.apache.zookeeper.server.ServerConfig;
import org.apache.zookeeper.server.ZooKeeperServerMain;
import org.apache.zookeeper.server.quorum.QuorumPeerConfig;

import java.io.IOException;

/**
 * 启动内嵌ZK Server
 *
 * @author nabilzhang
 */
public class ClusterLaucher {
    /**
     * 启动zookeeper服务
     */
    public static void startStandAloneServer() throws QuorumPeerConfig.ConfigException, IOException {
        ServerConfig serverConfig = new ServerConfig();
        serverConfig.parse(ClusterLaucher.class.getResource("/").getPath() + "zoo.cfg");

        new ZooKeeperServerMain().runFromConfig(serverConfig);
    }
}
