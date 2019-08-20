package com.rmpksoft.qrtz.jobs;

import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractBaseJob implements Job {

	private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		log.info("Parent JOB! - " + new Date());
	}

}
