package com.cognitive.bbmp.batch;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.naming.directory.InvalidAttributesException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


@Component
public class BatchJobItemWriter<WardIssue> implements ItemWriter<WardIssue> {

	
	Properties initProperties ;
	private static final Logger logger = LoggerFactory.getLogger(BatchJobItemWriter.class);
	
	private final String DEFAULT_USERAGENT ="Mozilla/5.0";
	
	String baseIssueUpdateURL;
	String userAgent;
	HashMap<String, String> keyValueProps ;
	
	public  BatchJobItemWriter() {
		// TODO Auto-generated constructor stub
	}
	
	
	public BatchJobItemWriter(String baseUrl, String userAgent, Properties initProperties)
	{
		this.baseIssueUpdateURL = baseUrl;
		this.userAgent = userAgent;
		this.initProperties = initProperties;
		
	}
	
	
	@Override
	public void write(List<? extends WardIssue> items) throws Exception {
		// TODO Auto-generated method stub
		logger.info ("Working in {} Process", this.getClass().getSimpleName());		
		
		if (baseIssueUpdateURL==null) 
			throw new InvalidAttributesException("Ward update URL is empty, it has to be a wellformed url");
		
		if (userAgent==null) 
		{
			logger.warn("UsereAgent cant be empty, it has to be a wellformed url");
			userAgent = DEFAULT_USERAGENT;
		}
			
		URL url = new URL(baseIssueUpdateURL);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();		
		
		conn.setRequestMethod("POST");
		conn.setRequestProperty("User-Agent", userAgent);
		conn.setRequestProperty("content-type", "application/json");
		
		conn.setDoOutput(true);
		OutputStream output = conn.getOutputStream();
		OutputStreamWriter writer = new OutputStreamWriter(output,"UTF-8" );
		
		String json = JSonString(items);
		
		logger.info("Url:{} will be used",conn.getURL().toString());
		logger.info ("Info of Json Posted is /n {}",json);		
		
		writer.write(json);
		writer.flush();
		writer.close();
		
		conn.connect();
		
		StringBuilder sb = new StringBuilder();
		
		BufferedReader br = new BufferedReader(
					new InputStreamReader(conn.getInputStream(),"utf-8"));	
		String response =null;
		while ( (response = br.readLine())!=null)
		{
			sb.append(response +"\n");
		}
		
		logger.info ("Printing the output from Service Update", this.getClass().getSimpleName());		
		
		logger.info(sb.toString());
		
		logger.info ("Completed the output from WardIssueProcessor Process for {}", this.getClass().getSimpleName());	
	}
	
	private String JSonString(List<? extends WardIssue> items) throws JsonProcessingException
	{
		return new ObjectMapper().writeValueAsString(items);
	}

}
