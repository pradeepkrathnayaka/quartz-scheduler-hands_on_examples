package com.rmpksoft.qtz;

import org.junit.jupiter.api.Test;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

import com.rmpksoft.qtz.job.SimpleJob;

public class QuartzTest {

	@Test
	public void helloJob() throws SchedulerException, InterruptedException {
		// Create JobDetail with SimpleJob containing Job implementation
		JobDetail jobDetail = JobBuilder.newJob(SimpleJob.class).build();

		// create a trigger that determines when to run
		Trigger trigger = TriggerBuilder.newTrigger().build();
		
		 // scheduler execution and scheduling with JobDetail and Trigger information
        Scheduler defaultScheduler = StdSchedulerFactory.getDefaultScheduler (); 
	       defaultScheduler.start (); 
	       defaultScheduler.scheduleJob (jobDetail, trigger); 
	       Thread.sleep ( 3 * 1000 );  // Allow time for Job to run // Shut down scheduler         
	       defaultScheduler.shutdown ( true );    
	 }
       
       



}
