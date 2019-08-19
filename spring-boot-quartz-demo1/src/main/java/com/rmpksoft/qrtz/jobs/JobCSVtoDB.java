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
import org.springframework.boot.web.servlet.context.AnnotationConfigServletWebServerApplicationContext;
import org.springframework.context.ApplicationContext;

import com.rmpksoft.qrtz.cfg.JobSchedulerConfig;
import com.rmpksoft.qrtz.service.StudentService;
import com.rmpksoft.qrtz.service.StudentServiceImpl;

@DisallowConcurrentExecution
@PersistJobDataAfterExecution
public class JobCSVtoDB extends AbstractBaseJob{
	
	@Autowired
	private StudentService studentService;
	
	private static final Logger log = LoggerFactory.getLogger(JobSchedulerConfig.class);
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		log.info("fffffffffffffffffffffffffffffffffffffffffffffffffff==" + studentService);
		
		try {
			ApplicationContext object = (ApplicationContext) context.getScheduler().getContext().get("applicationContext");
			String[] beanDefinitionNames = object.getBeanDefinitionNames();
			for(String n:beanDefinitionNames) {
				//System.out.println(n);
			}
			
		} catch (SchedulerException e1) {
			e1.printStackTrace();
		}
		
		JobDataMap mergedJobDataMap = context.getMergedJobDataMap();
        SchedulerContext schedCtxt = null;
        try {
            schedCtxt = context.getScheduler().getContext();
        } catch (SchedulerException e) {
            throw new JobExecutionException("Error obtaining scheduler context.", e, false);
        }
	}
	
}
