package me.nabil.demo.click.storm.demo;

import me.nabil.demo.click.storm.demo.constants.Conf;
import org.apache.storm.shade.org.json.simple.JSONObject;
import org.apache.storm.shade.org.json.simple.JSONValue;
import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichSpout;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;

import java.util.Map;

/**
 * @author zhangbi
 */
public class ClickSpout extends BaseRichSpout {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClickSpout.class);

    private Jedis jedis;
    private String host;
    private int port;

    private SpoutOutputCollector collector;

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new
                Fields(me.nabil.demo.click.storm.demo.constants.Fields.IP,
                me.nabil.demo.click.storm.demo.constants.Fields.URL,
                me.nabil.demo.click.storm.demo.constants.Fields.CLIENT_KEY));
    }

    @Override
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        host = conf.get(Conf.REDIS_HOST_KEY).toString();
        port = Integer.valueOf(conf
                .get(Conf.REDIS_PORT_KEY).toString());
        this.collector = collector;
        connectToRedis();

    }

    private void connectToRedis() {
        jedis = new Jedis(host, port);
        jedis.connect();
    }

    @Override
    public void nextTuple() {
        String content = jedis.rpop("connt");
        if (content == null || "nil".equals(content)) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {

            }
        } else {
            try {
                JSONObject obj = (JSONObject) JSONValue.parse(content);
                String ip = obj.get(me.nabil.demo.click.storm.demo.constants.Fields.IP).toString();
                String url = obj.get(me.nabil.demo.click.storm.demo.constants.Fields.URL).toString();
                String clientKey = obj.get(me.nabil.demo.click.storm.demo.constants.Fields.CLIENT_KEY).toString();

                collector.emit(new Values(ip, url, clientKey));
            } catch (Exception e) {
                LOGGER.info(e.getMessage(), e);
            }
        }
    }
}
