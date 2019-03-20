package bbmp.anukula.com.cognitive.bbmp.anukula.batch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

@Component
public class BatchJobListener extends JobExecutionListenerSupport {

	private static final Logger logger = LoggerFactory.getLogger(JobExecutionListenerSupport.class);
	
	@Override
	public void afterJob(JobExecution jobExecution) {
		// TODO Auto-generated method stub
		super.afterJob(jobExecution);
		if (jobExecution.getExitStatus() == ExitStatus.COMPLETED || 
				jobExecution.getStatus() == BatchStatus.COMPLETED)
		{
			logger.info(":JobId:{}  is complete with status of {}" ,jobExecution.getJobId(), jobExecution.getStatus().toString());
		}
	}

	
}
