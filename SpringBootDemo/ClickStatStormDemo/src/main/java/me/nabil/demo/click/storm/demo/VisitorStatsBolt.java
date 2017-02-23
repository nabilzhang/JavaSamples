package me.nabil.demo.click.storm.demo;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.topology.base.BaseRichBolt;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Tuple;
import org.apache.storm.tuple.Values;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author zhangbi
 */
public class VisitorStatsBolt extends BaseRichBolt {

    private static final Logger LOGGER = LoggerFactory.getLogger(GeoStatsBolt.class);

    private int total;
    private int uniqueCount;

    private OutputCollector collector;

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void execute(Tuple input) {
        boolean unique = Boolean.parseBoolean(
                input.getStringByField(me.nabil.demo.click.storm.demo.constants.Fields.UNIQUE));

        if (unique) {
            uniqueCount++;
        }
        total++;

        LOGGER.info("\n\n=======================================================\n" +
                        "total:{}, uniqueCount:{} \n" +
                        "=======================================================\n",
                new Object[]{total, uniqueCount});
        collector.emit(new Values(total, uniqueCount));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new
                Fields(me.nabil.demo.click.storm.demo.constants.Fields.TOTAL,
                me.nabil.demo.click.storm.demo.constants.Fields.UNIQUE_COUNT));
    }
}
