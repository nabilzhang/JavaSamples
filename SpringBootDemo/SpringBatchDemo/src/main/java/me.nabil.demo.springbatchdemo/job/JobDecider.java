package me.nabil.demo.springbatchdemo.job;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;

/**
 * @author zhangbi
 */
public class JobDecider implements JobExecutionDecider {

    public JobDecider() {
    }

    @Override
    public FlowExecutionStatus decide(JobExecution jobExecution, StepExecution stepExecution) {
        if (stepExecution.getReadCount() < 1) {
            return FlowExecutionStatus.FAILED;
        }
        return FlowExecutionStatus.COMPLETED;
    }
}
