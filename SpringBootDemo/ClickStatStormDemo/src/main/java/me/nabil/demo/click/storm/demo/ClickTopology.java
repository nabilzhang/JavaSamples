package me.nabil.demo.click.storm.demo;

import me.nabil.demo.click.storm.demo.constants.Conf;
import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.generated.StormTopology;
import org.apache.storm.topology.TopologyBuilder;
import org.apache.storm.tuple.Fields;
import org.apache.storm.utils.Utils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 点击统计
 *
 * @author zhangbi
 */
public class ClickTopology {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClickTopology.class);

    private static final int DEFAULT_JEDIS_PORT = 6379;

    private static final String NAME = "ClickTopology";

    private Config conf = new Config();


    protected StormTopology build() {
        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout("clickSpout", new ClickSpout(), 10);

        // 第一层实现
        builder.setBolt("repeatsBolt", new RepeatVisitBolt(), 10).shuffleGrouping("clickSpout");
        builder.setBolt("geographyBolt", new GeographyBolt(), 10).shuffleGrouping("clickSpout");

        // 第二层
        builder.setBolt("totalStats", new VisitorStatsBolt(), 1).globalGrouping("repeatsBolt");
        builder.setBolt("geoStats", new GeoStatsBolt(), 10).fieldsGrouping("geographyBolt",
                new Fields(me.nabil.demo.click.storm.demo.constants.Fields.COUNTRY));


        conf.put(Conf.REDIS_PORT_KEY, DEFAULT_JEDIS_PORT);

        return builder.createTopology();
    }

    public void runLocal(int runTime) {
        conf.setDebug(true);
        conf.put(Conf.REDIS_HOST_KEY, "localhost");

        LocalCluster cluster = new LocalCluster();
        cluster.submitTopology(NAME, conf, build());

        if (runTime > 0) {
            Utils.sleep(runTime);
            if (cluster != null) {
                LOGGER.info("killing topology");
                cluster.killTopology(NAME);
                cluster.shutdown();
            }
        }
    }

    public static void main(String[] args) {
        ClickTopology clickTopology = new ClickTopology();

        clickTopology.runLocal(1000 * 60 * 10);
    }


}
