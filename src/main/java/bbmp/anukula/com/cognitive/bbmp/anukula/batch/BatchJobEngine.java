package bbmp.anukula.com.cognitive.bbmp.anukula.batch;

import java.io.File;
import java.net.MalformedURLException;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.batch.api.chunk.ItemProcessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;

@Component
public class BatchJobEngine  {
	
	private static final Logger logger = LoggerFactory.getLogger(BatchJobEngine.class);
	
	@Autowired
	private JobBuilderFactory jobBuilder;
	
	@Autowired
	private StepBuilderFactory stepBuilder;
	
	@Autowired
	private BatchJobListener batchJobListener ;
	
	private String executionPath;
	
	@Value("${filePath}") private String filePath;
	
	@Value("${wardIssueUpdate}")private String baseIssueUpdateURL;
	@Value("${userAgent}") private String userAgent;
	
	
	@Bean
	public BatchJobItemWriter<WardIssue> itemWriter()
	{
		logger.info ("Initialized Bean WardIssueItemWriter Step for {}", this.getClass().getSimpleName());		
		return new BatchJobItemWriter<WardIssue>(baseIssueUpdateURL,userAgent,null);
	}
	
	@Bean
	public WardIssueProcessor itemProcessor()
	{
		return new WardIssueProcessor();
	}
	
	private AtomicBoolean canExecute = new AtomicBoolean(false);
	
	@Bean
	public FlatFileItemReader<WardIssue> itemReader() throws MalformedURLException
	{
		String localfile;
		this.executionPath = System.getProperty("user.dir");
		localfile =  executionPath + filePath ;
		
		File file = new File(filePath);
		Boolean canExecute = file.canExecute();
		
		FlatFileItemReader<WardIssue> reader = new FlatFileItemReader<WardIssue>();
		reader.setStrict(false);
		
			UrlResource urlResource = new UrlResource("file:"+localfile);
			reader.setResource(urlResource);
			
		
		DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
		tokenizer.setDelimiter(",");
		tokenizer.setNames( new  String[] {"slno","issueId","location","category","complaintDate","complaintSource","priority","status",
        		"typeOfComplaint","wardCode"});
        tokenizer.setStrict(false);
		
        
		BeanWrapperFieldSetMapper< WardIssue> fieldSetter = new BeanWrapperFieldSetMapper<WardIssue>();
		fieldSetter.setTargetType(WardIssue.class);
		
		DefaultLineMapper<WardIssue> lineMapper = new DefaultLineMapper<WardIssue>();
		lineMapper.setFieldSetMapper(fieldSetter);
		lineMapper.setLineTokenizer(tokenizer);
		
		reader.setLineMapper(lineMapper);
		reader.setLinesToSkip(1);
		
		logger.info("Execution completed for Item reader");
		return reader;
		
	}
	
	@Bean
	public Step stepChunk() throws MalformedURLException
	{
		logger.info("Chunk step is being set");
		return this.stepBuilder.get("postWardIssue")
					.<WardIssue,WardIssue> chunk(10)
					.reader(itemReader())
					.processor(itemProcessor())
					.writer(itemWriter())
					.build();
	}
	
	@Bean
	public Job wardJob() throws MalformedURLException
	{
		logger.info("Job step is being set");
		return this.jobBuilder.get("wardIssueprocessor")
			.incrementer(new RunIdIncrementer())
			.listener(batchJobListener)
			.flow(stepChunk())
			.end()
			.build();
	}
	
	
}
