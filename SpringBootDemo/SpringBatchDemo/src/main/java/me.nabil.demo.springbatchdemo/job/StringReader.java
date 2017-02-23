package me.nabil.demo.springbatchdemo.job;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

/**
 * @author zhangbi
 */
public class StringReader implements ItemReader<String> {

    private static final Logger LOGGER = LoggerFactory.getLogger(StringReader.class);

    private String message;

    @Override
    public String read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
        LOGGER.info(message);
        return message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
