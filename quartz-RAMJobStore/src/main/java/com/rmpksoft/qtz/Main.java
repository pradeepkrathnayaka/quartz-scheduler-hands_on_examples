package com.rmpksoft.qtz;

import org.quartz.SchedulerException;

public class Main {

	public static void main(String[] args) {

		try {
			JobService j = new JobService();
			j.task();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

}
