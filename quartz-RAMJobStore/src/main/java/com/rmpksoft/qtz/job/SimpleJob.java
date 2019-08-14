package com.rmpksoft.qtz.job;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class SimpleJob extends BaseJob {

	@Override
	protected void doExecute(JobExecutionContext context) {
		System.out.println("In SimpleQuartzJob - executing its JOB at " + new Date() + " by "
				+ context.getTrigger().getCalendarName());

		context.getJobDetail().getJobDataMap().get("JobName").toString();

	}

}
