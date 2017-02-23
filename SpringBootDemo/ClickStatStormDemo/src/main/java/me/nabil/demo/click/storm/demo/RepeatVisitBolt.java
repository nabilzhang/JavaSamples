package me.nabil.demo.click.storm.demo;

import me.nabil.demo.click.storm.demo.constants.Conf;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import redis.clients.jedis.Jedis;

import java.util.Map;

/**
 * @author zhangbi
 */
public class RepeatVisitBolt extends BaseRichBolt {

    private OutputCollector collector;
    private Jedis jedis;
    private String host;
    private int port;

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
        host = stormConf.get(Conf.REDIS_HOST_KEY).toString();
        port = Integer.valueOf(stormConf.get(Conf.REDIS_PORT_KEY).toString());
        connectToRedis();
    }

    private void connectToRedis() {
        jedis = new Jedis(host, port);
        jedis.connect();
    }

    @Override
    public void execute(Tuple tuple) {
        String ip = tuple.getStringByField(me.nabil.demo.click.storm.demo.constants.Fields.IP);
        String clientKey = tuple.getStringByField(me.nabil.demo.click.storm.demo.constants.Fields.CLIENT_KEY);
        String url = tuple.getStringByField(me.nabil.demo.click.storm.demo.constants.Fields.URL);
        String key = url + ":" + clientKey;
        String value = jedis.get(key);
        if (value == null) {
            jedis.set(key, "visited");
            collector.emit(new Values(clientKey, url,
                    Boolean.TRUE.toString()));
        } else {
            collector.emit(new Values(clientKey, url,
                    Boolean.FALSE.toString()));
        }
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new
                Fields(me.nabil.demo.click.storm.demo.constants.Fields.CLIENT_KEY,
                me.nabil.demo.click.storm.demo.constants.Fields.URL,
                me.nabil.demo.click.storm.demo.constants.Fields.UNIQUE));
    }
}
