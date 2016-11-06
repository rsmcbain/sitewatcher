package com.mcbain.sitewatcher.rest.errors;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ErrorDetail {
	private String title;
	private int status;
	private String detail;
	private long timeStamp;
	private String developerMessage;
	private Map<String, List<ValidationError>> errors = new HashMap<>();
	
	public ErrorDetail(String title, int status, String detail, long timeStamp, String developerMessage) {
		this.title = title;
		this.status = status;
		this.detail = detail;
		this.timeStamp = timeStamp;
		this.developerMessage = developerMessage;
	}
	public String getTitle() {
		return title;
	}
	public int getStatus() {
		return status;
	}
	public String getDetail() {
		return detail;
	}
	public long getTimeStamp() {
		return timeStamp;
	}
	public String getDeveloperMessage() {
		return developerMessage;
	}
	public Map<String, List<ValidationError>> getErrors() {
		return errors;
	}
	public void setErrors(Map<String, List<ValidationError>> errors) {
		this.errors = errors;
	}
}
