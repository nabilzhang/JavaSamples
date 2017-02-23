package me.nabil.demo.click.storm.demo;

import me.nabil.demo.click.storm.demo.utils.GeoCountry;
import me.nabil.demo.click.storm.demo.utils.HttpIpResolver;
import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;

import java.util.Map;

/**
 * GeographyBolt
 *
 * @author zhangbi
 */
public class GeographyBolt extends BaseRichBolt {

    private OutputCollector collector;

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void execute(Tuple input) {
        String ip = input.getStringByField(me.nabil.demo.click.storm.demo.constants.Fields.IP);

        GeoCountry geoCountry = HttpIpResolver.resolveIp(ip);


        collector.emit(new Values(geoCountry.getCountry(), geoCountry.getCity()));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new
                Fields(me.nabil.demo.click.storm.demo.constants.Fields.COUNTRY,
                me.nabil.demo.click.storm.demo.constants.Fields.CITY));
    }
}
