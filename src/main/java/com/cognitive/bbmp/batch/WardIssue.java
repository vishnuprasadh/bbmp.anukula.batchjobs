package com.cognitive.bbmp.batch;

import java.io.Serializable;
import java.sql.Timestamp;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public class WardIssue implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getIssueId() {
		return issueId;
	}

	public void setIssueId(String issueId) {
		this.issueId = issueId;
	}

	public String getComplaintSource() {
		return complaintSource;
	}

	public void setComplaintSource(String complaintSource) {
		this.complaintSource = complaintSource;
	}

	public String getComplaintDate() {
		return complaintDate;
	}

	public void setComplaintDate(String complaintDate) {
		this.complaintDate = complaintDate;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getTypeofComplaint() {
		return typeofComplaint;
	}

	public void setTypeofComplaint(String typeofComplaint) {
		this.typeofComplaint = typeofComplaint;
	}

	@NonNull
	private String wardCode;

	@NonNull
	private String issueId;

	@NonNull
	private String complaintSource;

	private String complaintDate;

	@NonNull
	private String location;

	@NonNull
	private String typeofComplaint;

	private String status;

	private String category;

	@NonNull
	private String priority;

	private String dateUpdated;

	public String getDateUpdated() {
		if (this.dateUpdated == null || this.dateUpdated == "null")
			this.dateUpdated = new Timestamp(new java.util.Date().getTime()).toString();
		return dateUpdated;
	}

	public void setDateUpdated(String dateUpdated) {
		if (dateUpdated == null || dateUpdated == "null")
			dateUpdated = new Timestamp(new java.util.Date().getTime()).toString();
		this.dateUpdated = dateUpdated;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getWardCode() {
		return wardCode;
	}

	public void setWardCode(String wardCode) {
		this.wardCode = wardCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	@Nullable
	private String _id;

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Category : " + getCategory() + ",");
		sb.append("ComplaintDate : " + getComplaintDate() + ",");
		sb.append("ComplaintSource : " + getComplaintSource() + ",");
		sb.append("DateUpdated : " + getDateUpdated() + ",");
		sb.append("IssueId : " + getIssueId() + ",");
		sb.append("Location : " + getLocation() + ",");
		sb.append("Priority : " + getPriority() + ",");
		sb.append("Status : " + getStatus() + ",");
		sb.append("TypeofComplaint : " + getTypeofComplaint() + ",");
		sb.append("WardCode : " + getWardCode());
		sb.append("Id : " + get_id());

		return sb.toString();

	}

}
