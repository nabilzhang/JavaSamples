package me.nabil.demo.springbatchdemo.job;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

/**
 * @author zhangbi
 */
public class DecisionTasklet implements Tasklet {
    private String message;

    public DecisionTasklet() {

    }

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        System.out.println("message is: " + message);
        return RepeatStatus.FINISHED;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
}
