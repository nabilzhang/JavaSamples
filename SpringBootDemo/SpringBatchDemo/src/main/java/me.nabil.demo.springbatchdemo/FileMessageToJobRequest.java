package me.nabil.demo.springbatchdemo;

import org.apache.commons.io.FileUtils;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.integration.launch.JobLaunchRequest;
import org.springframework.integration.annotation.Transformer;
import org.springframework.messaging.Message;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileMessageToJobRequest {
    private Job job;
    private String fileParameterName;

    public void setFileParameterName(String fileParameterName) {
        this.fileParameterName = fileParameterName;
    }

    public void setJob(Job job) {
        this.job = job;
    }

    @Transformer
    public JobLaunchRequest toRequest(Message<File> message) {
        JobParametersBuilder jobParametersBuilder =
                new JobParametersBuilder();
        try {
            List<String> result = FileUtils.readLines(message.getPayload(), "utf-8");
            jobParametersBuilder.addString(fileParameterName,
                    result.get(0));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new JobLaunchRequest(job, jobParametersBuilder.toJobParameters());
    }
}
