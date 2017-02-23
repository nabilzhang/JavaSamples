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

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author zhangbi
 */
public class GeoStatsBolt extends BaseRichBolt {

    private static final Logger LOGGER = LoggerFactory.getLogger(GeoStatsBolt.class);

    private OutputCollector collector;

    private Map<String, CountryStats> stats = new HashMap<>();

    @Override
    public void prepare(Map stormConf, TopologyContext context, OutputCollector collector) {
        this.collector = collector;
    }

    @Override
    public void execute(Tuple input) {
        String country = input.getStringByField(me.nabil.demo.click.storm.demo.constants.Fields.COUNTRY);
        String city = input.getStringByField(me.nabil.demo.click.storm.demo.constants.Fields.CITY);
        if (!stats.containsKey(country)) {
            stats.put(country, new CountryStats(country));
        }

        stats.get(country).cityFound(city);

        LOGGER.info("\n\n=======================================================\n" +
                        "country:{}, countryTotal:{}, city:{}, cityTotal:{} \n" +
                        "=======================================================\n",
                new Object[]{country, stats.get(country).getCountryTotal(),
                        city, stats.get(country).getCityTotal(city)});

        collector.emit(new Values(country, stats.get(country).getCountryTotal(),
                city, stats.get(country).getCityTotal(city)));
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields(me.nabil.demo.click.storm.demo.constants.Fields.COUNTRY,
                me.nabil.demo.click.storm.demo.constants.Fields.COUNTRY_TOTAL,
                me.nabil.demo.click.storm.demo.constants.Fields.CITY,
                me.nabil.demo.click.storm.demo.constants.Fields.CITY_TOTAL));
    }

    /**
     * countryStats
     */
    private class CountryStats {
        private int countryTotal = 0;
        private static final int COUNT_INDEX = 0;
        private static final int PERCENTAGE_INDEX = 1;
        private String countryName;

        public CountryStats(String countryName) {
            this.countryName = countryName;
        }

        private Map<String, List<Integer>> cityStats = new HashMap<>();


        public void cityFound(String cityName) {
            countryTotal++;
            if (cityStats.containsKey(cityName)) {
                cityStats.get(cityName).set(COUNT_INDEX, cityStats.get(cityName).get(COUNT_INDEX).intValue() + 1);
            } else {
                List<Integer> list = new LinkedList<>();
                list.add(1);
                list.add(0);

                cityStats.put(cityName, list);
            }

            double percent = (double) cityStats.get(cityName).get(COUNT_INDEX) / (double) countryTotal;
            cityStats.get(cityName).set(PERCENTAGE_INDEX, (int) percent);
        }

        public int getCountryTotal() {
            return countryTotal;
        }

        public int getCityTotal(String cityName) {
            return cityStats.get(cityName).get(COUNT_INDEX).intValue();
        }

    }
}
