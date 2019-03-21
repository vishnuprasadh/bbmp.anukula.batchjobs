package com.cognitive.bbmp.batch;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Component
public class WardIssueProcessor implements ItemProcessor<WardIssue,WardIssue> {

		
		private static final Logger logger = LoggerFactory.getLogger(WardIssueProcessor.class);
		
		SimpleDateFormat format = new SimpleDateFormat("dd/mm/yyyy");

		List<String> priorities = new ArrayList<String>(Arrays.asList("critical","high","medium","low"));
		
		List<String> statuses = new ArrayList<String>(Arrays.asList("open","new","in progress","closed","reopen","on hold","resolved"));
		
		@Override
		public WardIssue process(WardIssue item) throws Exception {
			
			logger.info ("Started execution in WardIssueProcessor Process for {}", this.getClass().getSimpleName());		
			
			// TODO Auto-generated method stub
			if (item.getIssueId()==null)
				throw new IllegalArgumentException("IssueId cannot be null for" + item.toString());
			if (item.getWardCode()==null)
				throw new IllegalArgumentException("WardCode cannot be null for IssueId " + item.getIssueId());
			if (item.getComplaintSource()==null)
				throw new IllegalArgumentException("Complaintsource cannot be null for IssueId " + item.getIssueId());
			if (item.getComplaintSource()==null)
				throw new IllegalArgumentException("ComplaintDate cannot be null for IssueId " + item.getIssueId());
			
			if (item.getTypeofComplaint()==null)
				throw new IllegalArgumentException("Type of Complaint cannot be null for IssueId " + item.getIssueId());
			
			//Validate for date format
			try
			{
				//TODO
				//Date complaintDate = DateUtil.getJavaDate(Double.parseDouble(item.getComplaintDate()));
				//System.out.println(complaintDate);
			}
			catch(Exception ex)
			{
				throw new IllegalArgumentException("ComplaintDate should be in format of dd/mm/yyyy,which is missed in " + item.getIssueId());
			}
			
			if (item.getLocation()==null)
				throw new IllegalArgumentException("Location cannot be null for IssueId " + item.getIssueId());
			if (item.getPriority()==null)
				throw new IllegalArgumentException("Priority cannot be null for IssueId " + item.getIssueId());
			

			//Status is not null and if so, should have a valid set of values.
			if (item.getStatus()!=null && !statuses.contains(item.getStatus().toLowerCase()))	
				throw new IllegalArgumentException("Status should be \"open\",\"new\",\"in progress\",\"closed\",\"reopen\",\"on hold\",\"resolved\" which is missing for " + item.getIssueId());
			

			logger.info ("Completed working on WardIssueProcessor Process for {}", this.getClass().getSimpleName());		
			
			return item;
		}

	
}
