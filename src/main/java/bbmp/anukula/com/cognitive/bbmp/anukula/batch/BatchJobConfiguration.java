package bbmp.anukula.com.cognitive.bbmp.anukula.batch;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class BatchJobConfiguration {

	@Bean
	public ResourcelessTransactionManager txManager()
	{
		return new ResourcelessTransactionManager();
	}
	
	@Bean
	public JobRepository getJobRepository(ResourcelessTransactionManager txManager) throws Exception
	{
		MapJobRepositoryFactoryBean mJob= new MapJobRepositoryFactoryBean();
		mJob.setTransactionManager(txManager);
		return mJob.getObject();
	}
	
	@Bean
	@Primary
	public SimpleJobLauncher WardIssueJobLauncher(JobRepository jobRepository) throws Exception
	{
		SimpleJobLauncher joblauncher = new SimpleJobLauncher();
		joblauncher.setJobRepository(jobRepository);
		joblauncher.afterPropertiesSet();
		return joblauncher;
		
	}
	
}
