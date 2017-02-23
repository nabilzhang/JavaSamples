package me.nabil.demo.springbatchdemo.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

/**
 * HelloTasklet
 *
 * @author zhangbi
 */
public class HelloTasklet implements Tasklet {

    private static final Logger LOGGER = LoggerFactory.getLogger(HelloTasklet.class);

    private String message;

    private String messageId;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

        LOGGER.info("message:{}", message);
        LOGGER.info("messageId:{}", messageId);

        return RepeatStatus.FINISHED;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
}
