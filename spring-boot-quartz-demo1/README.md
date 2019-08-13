@EnableBatchProcessing
bootstrap a number of common components (JobRepository, TransactionManager, etc). It's typically better to use these as common components instead of bootstrapping one per job.



--
-- In your Quartz properties file, you'll need to set 
-- org.quartz.jobStore.driverDelegateClass = org.quartz.impl.jdbcjobstore.HSQLDBDelegate
--

You must remember this basic flow- 1. Create a job. 2. Create a Trigger. 3. Scheduler(job, trigger) All the above tables are based on the above 3 steps.

qrtz_triggers is where general information of a trigger is saved.
qrtz_simple_triggers, qrtz_simprop_triggers, qrtz_crons_triggers, qrtz_blob_triggers have a foreign key relation to qrtz_triggers which save those specific details. Ex. Cron has cron expression which is unique to it.
qrtz_job_details is simply the task to be executed.
qrtz_fired_triggers is a log of all the triggers that were fired.
qrtz_paused trigger is to save the information about triggers which are not active.
Calendars are useful for excluding blocks of time from the the trigger’s firing schedule. For instance, you could create a trigger that fires a job every weekday at 9:30 am, but then add a Calendar that excludes all of the business’s holidays. (taken from website. I havent' worked on it)
I honestly haven't worked in qrtz_locks, qrtz_scheduler_sate tables.



References

https://github.com/demoiselle/scheduler/tree/master/impl/dashboard/src/main/java/br/gov/frameworkdemoiselle/scheduler/dashboard/domain