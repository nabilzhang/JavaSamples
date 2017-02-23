package me.nabil.demo.springbatchdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.integration.launch.JobLaunchRequest;
import org.springframework.integration.annotation.Transformer;
import org.springframework.messaging.Message;

/**
 * AMQP transformer
 *
 * @author zhangbi
 */
public class AmqpMessageCustomerTransformer {

    private static final Logger LOGGER = LoggerFactory.getLogger(AmqpMessageCustomerTransformer.class);

    private Job job;
    private String idParameterName;
    private String msgIdParameterName;

    public void setIdParameterName(String idParameterName) {
        this.idParameterName = idParameterName;
    }

    public void setMsgIdParameterName(String msgIdParameterName) {
        this.msgIdParameterName = msgIdParameterName;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    @Transformer
    public JobLaunchRequest toRequest(Message<byte[]> message) {
        JobParametersBuilder jobParametersBuilder =
                new JobParametersBuilder();
        String id = new String(message.getPayload());
        LOGGER.info(id);
        jobParametersBuilder.addString(msgIdParameterName,
                message.getHeaders().getId().toString());
        jobParametersBuilder.addString(idParameterName, id);
        return new JobLaunchRequest(job, jobParametersBuilder.toJobParameters());
    }
}
