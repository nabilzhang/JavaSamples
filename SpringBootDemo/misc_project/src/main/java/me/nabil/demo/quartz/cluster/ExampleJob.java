package me.nabil.demo.quartz.cluster;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

/**
 * 示例job
 *
 * @author zhangbi
 */
public class ExampleJob extends QuartzJobBean {

    private Logger logger = LoggerFactory.getLogger(ExampleJob.class);

    private int timeout;

    /**
     * Setter called after the ExampleJob is instantiated
     * with the value from the JobDetailFactoryBean (5)
     */
    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    protected void executeInternal(JobExecutionContext ctx) throws JobExecutionException {
        // do the actual work
        logger.info(String.format("class:%s,%s,%s",
                this.getClass().getName(),
                this.toString(),
                new Date()
        ));
    }

    @Override
    public String toString() {
        return "ExampleJob{" + "timeout=" + timeout + '}';
    }
}
