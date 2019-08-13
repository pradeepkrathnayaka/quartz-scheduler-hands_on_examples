package com.rmpksoft.intro.jobs;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SchedulerException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;

@DisallowConcurrentExecution
public class BatchJobExecutor implements org.quartz.Job {

	@Autowired
	private JobLocator jobLocator;

	@Autowired
	private JobLauncher jobLauncher;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		 try {
	            String jobName = BatchHelper.getJobName(context.getMergedJobDataMap());
	            System.out.printf("[{}] started.", jobName);
	            JobParameters jobParameters = BatchHelper.getJobParameters(context);
	            jobLauncher.run(jobLocator.getJob(jobName), jobParameters);
	            System.out.printf("[{}] completed.", jobName);
	        } catch (NoSuchJobException | JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException | SchedulerException e) {
	        	 System.out.printf("job execution exception! - {}", e.getCause());
	        	 e.printStackTrace();
	            throw new JobExecutionException();
	        }

	}

}
