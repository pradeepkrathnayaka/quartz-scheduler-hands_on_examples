package com.rmpksoft.qtz;

import java.util.Date;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.rmpksoft.qtz.job.SimpleJob;

public class JobService {

	public void task() throws SchedulerException {
		// Initiate a Schedule Factory
		SchedulerFactory schedulerFactory = new StdSchedulerFactory();
		// Retrieve a scheduler from schedule factory
		Scheduler scheduler = schedulerFactory.getScheduler();

		// current time
		long ctime = System.currentTimeMillis();

		// Initiate JobDetail with job name, job group, and executable job class
		JobDetail job = JobBuilder.newJob(SimpleJob.class).withIdentity("job1", "group1").build();

		// Initiate SimpleTrigger with its name and group name
		// compute a time that is on the next round minute
		Date runTime = new Date("01/01/2010");

		// Trigger the job to run on the next round minute
		Trigger trigger =TriggerBuilder.newTrigger().withIdentity("trigger1", "group1").startNow().build();

		scheduler.scheduleJob(job, trigger);

		// start the scheduler
		scheduler.start();
		try {
			Thread.sleep(1L * 1000L);
		} catch (Exception e) {
		}
		// stop scheduler
		scheduler.shutdown(true);

	}

}
