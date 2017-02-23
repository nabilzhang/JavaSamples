package me.nabil.demo.springbatchdemo.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

/**
 * 写
 *
 * @author zhangbi
 */
public class StringWriter implements ItemWriter<String> {
    private static final Logger LOGGER = LoggerFactory.getLogger(StringWriter.class);

    @Override
    public void write(List<? extends String> list) throws Exception {
        LOGGER.info("{}", list);
    }
}
