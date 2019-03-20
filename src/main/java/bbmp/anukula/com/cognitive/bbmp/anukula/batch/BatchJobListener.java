package bbmp.anukula.com.cognitive.bbmp.anukula.batch;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class BatchJobListener extends JobExecutionListenerSupport {

	@Autowired
	private Environment env;
	
	private static final Logger logger = LoggerFactory.getLogger(JobExecutionListenerSupport.class);
	
	@Override
	public void afterJob(JobExecution jobExecution) {
		// TODO Auto-generated method stub
		super.afterJob(jobExecution);
		if (jobExecution.getExitStatus() == ExitStatus.COMPLETED || 
				jobExecution.getStatus() == BatchStatus.COMPLETED)
		{
			
			logger.info(":JobId:{}  is complete with status of {}" ,jobExecution.getJobId(), jobExecution.getStatus().toString());
			
			//Now lets rename the file as the processing has been completed.
			File file =null;
			String fileName = System.getProperty("user.dir") + env.getProperty("filePath");
			
			logger.info("File path is {}", fileName);
			try
				{
					if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
						
						//Get the file and rename the same.
						file = new File(fileName);
						if (file!=null && file.exists())
						{
							String renameFile = System.getProperty("user.dir") + "/wardIssue_"+ new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss").format(new Date().getTime()).toString() +"_completed";
							logger.info("File being renamed to {}", renameFile);
							file.renameTo(new File(renameFile));
							
						}
						logger.info("Batch job completed successfully");;
					}	
				}
			catch(Exception ex)
			{
				logger.error("FileNotFound exception occurred for {}" + fileName);
				logger.error(ex.getMessage());
			}
		
		}
		
		
			
		
		
	}

	
}
