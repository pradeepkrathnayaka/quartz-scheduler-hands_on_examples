package com.rmpksoft.qtz.job;

import java.util.List;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

public abstract class BaseJob implements Job {

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		beforeExecute(context);
		doExecute(context);
		afterExecute(context);
		scheduleNextJob(context);
	}

	private void beforeExecute(JobExecutionContext context) {
		System.out.println("%%% Before executing job");
	}

	protected abstract void doExecute(JobExecutionContext context);

	private void afterExecute(JobExecutionContext context) {
		System.out.println("%%% After executing job");
		Object object = context.getJobDetail().getJobDataMap().get("JobDetailQueue");
		List<JobDetail> jobDetailQueue = (List<JobDetail>) object;

		if (jobDetailQueue.size() > 0) {
			jobDetailQueue.remove(0);
		}
	}

	private void scheduleNextJob(JobExecutionContext context) {
		System.out.println("$$$ Schedule Next Job");

		Object object = context.getJobDetail().getJobDataMap().get("JobDetailQueue");
		List<JobDetail> jobDetailQueue = (List<JobDetail>) object;

		if (jobDetailQueue.size() > 0) {
			JobDetail nextJobDetail = jobDetailQueue.get(0);
			nextJobDetail.getJobDataMap().put("JobDetailQueue", jobDetailQueue);
			
			Trigger nowTrigger = TriggerBuilder.newTrigger().startNow().build();

			try { // The factory method below will always return the same scheduler even if called
					// multiple times.
				Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
				scheduler.start();
				scheduler.scheduleJob(nextJobDetail, nowTrigger);
			} catch (SchedulerException e) {
				throw new RuntimeException(e);
			}
		}
	}

}
