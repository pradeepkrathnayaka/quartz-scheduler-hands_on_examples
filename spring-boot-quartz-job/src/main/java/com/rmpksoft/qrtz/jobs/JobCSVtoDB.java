package com.rmpksoft.qrtz.jobs;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.PersistJobDataAfterExecution;
import org.quartz.SchedulerContext;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.rmpksoft.qrtz.service.StudentService;

@Component
@Scope(value = BeanDefinition.SCOPE_PROTOTYPE)
@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class JobCSVtoDB extends AbstractBaseJob{
	
	private final Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());
	
	@Autowired
	private StudentService studentService;
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		studentService.getStudentNameList().forEach(e->{
			log.info("-->" + e);
		});
		
		JobDataMap mergedJobDataMap = context.getMergedJobDataMap();
        SchedulerContext schedCtxt = null;
        try {
            schedCtxt = context.getScheduler().getContext();
        } catch (SchedulerException e) {
            throw new JobExecutionException("Error obtaining scheduler context.", e, false);
        }
	}
	
}
