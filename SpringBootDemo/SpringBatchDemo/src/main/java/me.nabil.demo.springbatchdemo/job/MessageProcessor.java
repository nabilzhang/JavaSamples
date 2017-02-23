package me.nabil.demo.springbatchdemo.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;


/**
 * @author zhangbi
 */
public class MessageProcessor implements ItemProcessor<String, String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(MessageProcessor.class);

    @Override
    public String process(String o) throws Exception {
        LOGGER.info(o + "" + System.currentTimeMillis());
        return o + "" + System.currentTimeMillis();
    }
}
