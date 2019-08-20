package com.rmpksoft.qrtz.cfg;

import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.quartz.Trigger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import com.rmpksoft.qrtz.jobs.JobCSVtoDB;

@Configuration
public class QrtzConfig {

	private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

	private static final String SCHEDULER_NAME = "SCHEDULER_NAME_A";

	private static final String TRIGGER_NAME_A = "TRIGGER_NAME_A";

	private static final String JOB_NAME_A = "JOB_NAME_A";

	private static final String JOB_GROUP_A = "JOB_GROUP_A";

	private static final String TRIGGER_GROUP_A = "TRIGGER_GROUP_A";

	private static final String CRON_EXPRESSION = "0/40 * * * * ?";

	@Autowired
	private DataSource dataSource;

	@Autowired
	private PlatformTransactionManager transactionManager;

	@Autowired
	private ApplicationContext applicationContext;

	@PostConstruct
	public void init() {
		log.debug("QuartzConfig initialized.");
	}

	@Bean("qrtzProp")
	@ConfigurationProperties("qrtz")
	public Properties quartzProperties() {
		return new Properties();
	}

	@Bean
	public SchedulerFactoryBean quartzScheduler() {
		SchedulerFactoryBean qrtzScheduler = new SchedulerFactoryBean();

		qrtzScheduler.setDataSource(dataSource);
		qrtzScheduler.setTransactionManager(transactionManager);
		qrtzScheduler.setOverwriteExistingJobs(true);
		qrtzScheduler.setSchedulerName(SCHEDULER_NAME);

		AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
		jobFactory.setApplicationContext(applicationContext);
		qrtzScheduler.setJobFactory(jobFactory);

		qrtzScheduler.setQuartzProperties(quartzProperties());

		Trigger[] triggers = { procesoMQTrigger().getObject() };
		qrtzScheduler.setTriggers(triggers);

		return qrtzScheduler;
	}

	@Bean
	public JobDetailFactoryBean procesoMQJob() {
		JobDetailFactoryBean jobDetailFactory = new JobDetailFactoryBean();
		jobDetailFactory.setJobClass(JobCSVtoDB.class);
		jobDetailFactory.setGroup(JOB_GROUP_A);
		jobDetailFactory.setName(JOB_NAME_A);
		return jobDetailFactory;
	}

	@Bean
	public CronTriggerFactoryBean procesoMQTrigger() {
		CronTriggerFactoryBean cronTriggerFactoryBean = new CronTriggerFactoryBean();
		cronTriggerFactoryBean.setJobDetail(procesoMQJob().getObject());
		cronTriggerFactoryBean.setCronExpression(CRON_EXPRESSION);
		cronTriggerFactoryBean.setGroup(TRIGGER_GROUP_A);
		cronTriggerFactoryBean.setName(TRIGGER_NAME_A);
		return cronTriggerFactoryBean;
	}
}
