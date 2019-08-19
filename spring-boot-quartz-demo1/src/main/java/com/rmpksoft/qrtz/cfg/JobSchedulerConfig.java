package com.rmpksoft.qrtz.cfg;

import java.util.Properties;

import javax.sql.DataSource;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.rmpksoft.qrtz.jobs.JobCSVtoDB;

@Configuration
public class JobSchedulerConfig {

	private static final String TRIGGER_NAME_A = "TRIGGER_NAME_A";

	private static final String JOB_NAME_A = "JOB_NAME_A";

	private static final String JOB_GROUP_A = "JOB_GROUP_A";

	private static final String TRIGGER_GROUP_A = "TRIGGER_GROUP_A";

	private static final String CRON_EXPRESSION = "0/40 * * * * ?";

	private static final Logger log = LoggerFactory.getLogger(JobSchedulerConfig.class);

	@Autowired
	ApplicationContext applicationContext;

	@Bean("qrtzProp")
	@ConfigurationProperties("qrtz")
	public Properties qrtzProperties() {
		return new Properties();
	}
	
	@Bean
    public org.quartz.spi.JobFactory jobFactory(ApplicationContext applicationContext) {
        QuartzJobFactory JobFactory = new QuartzJobFactory();
        JobFactory.setApplicationContext(applicationContext);
        return JobFactory;
    }

	@Bean
	@Autowired
	public SchedulerFactory buildSchedulerFactory(@Qualifier("qrtzProp") Properties props) 
			throws SchedulerException {
		try {
			return new StdSchedulerFactory(props);
		} catch (SchedulerException e) {
			e.printStackTrace();
			log.error("SchedulerException : ", e);
			throw e;
		}
	}
	
	@Bean
	public Scheduler buildScheduler(SchedulerFactory schedulerFactory) throws SchedulerException {
		try {
			Scheduler scheduler = schedulerFactory.getScheduler();
			scheduler.getContext().put("applicationContext", applicationContext);

			JobDetail jobDetail = buildJobDetail();
			CronTrigger cronTrigger = buildCronTrigger();

			if (scheduler.checkExists(jobDetail.getKey())) {
				removeJob(jobDetail.getKey(), scheduler);
			}
			scheduler.scheduleJob(jobDetail, cronTrigger);
			log.info("Before Start schedule!!!");
			scheduler.start();
			log.info("After Start schedule!!!");

			if (scheduler.isStarted()) {
				log.info("Scheduler has been started!!!");
			}
			return scheduler;
		} catch (SchedulerException e) {
			e.printStackTrace();
			log.error("SchedulerException : ", e);
			throw e;
		}

	}
	
	private JobDetail buildJobDetail() {
		JobKey jobKey = JobKey.jobKey(JOB_NAME_A, JOB_GROUP_A); 
		return JobBuilder.newJob(JobCSVtoDB.class)  
                 .withIdentity(jobKey)
                 .requestRecovery(true)
                 .storeDurably(true)
                 .build();  
	}
	
	private CronTrigger buildCronTrigger() {
		TriggerKey triggerKey = TriggerKey.triggerKey(TRIGGER_NAME_A, TRIGGER_GROUP_A);  
		return TriggerBuilder.newTrigger()
				.forJob(buildJobDetail().getKey())
				.withSchedule(CronScheduleBuilder.cronSchedule(CRON_EXPRESSION))
				.withIdentity(triggerKey)
				.startNow()
				.build();
	}
	
	public static void removeJob(JobKey jobKey, Scheduler scheduler) {   
        TriggerKey triggerKey = TriggerKey.triggerKey(TRIGGER_NAME_A, TRIGGER_GROUP_A);  
        try {    
            scheduler.pauseTrigger(triggerKey);
            scheduler.unscheduleJob(triggerKey);   
            scheduler.deleteJob(jobKey); 
        } catch (Exception e) {    
        	log.error("Exception : ", e);
            throw new RuntimeException(e);    
        }    
    }    

}
