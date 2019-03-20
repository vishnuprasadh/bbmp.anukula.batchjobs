package bbmp.anukula.com.cognitive.bbmp.anukula.batch;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class BatchScheduler {

	private static final Logger logger = LoggerFactory.getLogger(BatchScheduler.class);
	
	//@Value("${fixedDelay}") long fixedDelayValue;
	
	@Autowired
	private JobLauncher launcher;
	
	@Autowired
	private Job processJob;
	
	@Scheduled(fixedDelayString="${fixedDelay}")
	public void runJob() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException
	{
		logger.info("Into job execution initialization");
		launcher.run(processJob, new JobParametersBuilder().addDate("launchDate", new Date() ).toJobParameters());
		logger.info("Completed job execution initialization");
	}
	
}
