package com.rmpksoft.intro.config;

import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.support.JobRegistryBeanPostProcessor;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.SmartLifecycle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import com.rmpksoft.intro.properties.QuartzProperties;

@Configuration
@EnableConfigurationProperties(QuartzProperties.class)
@EnableBatchProcessing
public class BatchConfiguration {

	@Bean
	public JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor(JobRegistry jobRegistry) {
		JobRegistryBeanPostProcessor jobRegistryBeanPostProcessor = new JobRegistryBeanPostProcessor();
		jobRegistryBeanPostProcessor.setJobRegistry(jobRegistry);
		return jobRegistryBeanPostProcessor;
	}

	@Bean
	public JobFactory jobFactory(AutowireCapableBeanFactory beanFactory) {
		return new SpringBeanJobFactory() {
			@Override
			protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
				Object job = super.createJobInstance(bundle);
				beanFactory.autowireBean(job);
				return job;
			}
		};
	}

	@Bean
	public SchedulerFactoryBean schedulerFactoryBean(DataSource datasource, QuartzProperties quartzProperties,
			JobFactory jobFactory, Trigger[] registryTrigger) throws Exception {

		SchedulerFactoryBean factory = new SchedulerFactoryBean();

		factory.setSchedulerName("SampleProject-0.0.1");
		factory.setJobFactory(jobFactory);
		factory.setWaitForJobsToCompleteOnShutdown(true);
		factory.setOverwriteExistingJobs(true);
		factory.setQuartzProperties(quartzProperties.toProperties());
		factory.setDataSource(datasource);
		factory.setTriggers(registryTrigger);

		return factory;
	}

	@Bean
	public Trigger[] registryTrigger(List<CronTriggerFactoryBean> cronTriggerFactoryBeanList) {
		return cronTriggerFactoryBeanList.stream().map(CronTriggerFactoryBean::getObject).toArray(Trigger[]::new);
	}

	@Bean
	public SmartLifecycle gracefulShutdownHookForQuartz(SchedulerFactoryBean schedulerFactoryBean) {
		return new SmartLifecycle() {
			private boolean isRunning = false;
			private final Logger logger = LoggerFactory.getLogger(this.getClass());

			@Override
			public boolean isAutoStartup() {
				return true;
			}

			@Override
			public void stop(Runnable callback) {
				stop();
				logger.info("Spring container is shutting down.");
				callback.run();
			}

			@Override
			public void start() {
				logger.info("Quartz Graceful Shutdown Hook started.");
				isRunning = true;
			}

			@Override
			public void stop() {
				isRunning = false;
				try {
					logger.info("Quartz Graceful Shutdown... ");
					schedulerFactoryBean.destroy();
				} catch (SchedulerException e) {
					try {
						logger.info("Error shutting down Quartz: " + e.getMessage(), e);
						schedulerFactoryBean.getScheduler().shutdown(false);
					} catch (SchedulerException ex) {
						logger.error("Unable to shutdown the Quartz scheduler.", ex);
					}
				}
			}

			@Override
			public boolean isRunning() {
				return isRunning;
			}

			@Override
			public int getPhase() {
				return Integer.MAX_VALUE;
			}
		};
	}

}
