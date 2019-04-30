package com.test.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(Include.NON_NULL)
public class ApplicationResponse {

	
	public enum Status{
		CREATED(2001, "Record(s) inserted"),
		NO_CONTENT(2004, "No Data found for the input request"),
		SERVER_ERROR(5000,"Server Error"),
		SUCCESS(2000, "Successfully processed request"),
		PARAMETER_MISSING(2002, "The Request is missing one or more required parameters");
		
		private int value;
		
		private String reason;
		/**
		 * @return the value
		 */
		public int value() {
			return value;
		}
		/**
		 * @param value the value to set
		 */
		public void setValue(int value) {
			this.value = value;
		}
		/**
		 * @return the reason
		 */
		public String getReason() {
			return reason;
		}
		/**
		 * @param reason the reason to set
		 */
		public void setReason(String reason) {
			this.reason = reason;
		}
		private Status(int value, String reason) {
			this.value = value;
			this.reason = reason;
		}		
	}


	@JsonProperty
	private int responseCode;
	
	@JsonProperty
	private String responseMessage;	

	/**
	 * @return the responseCode
	 */
	public int getResponseCode() {
		return responseCode;
	}

	/**
	 * @param responseCode the responseCode to set
	 */
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	/**
	 * @return the responseMessage
	 */
	public String getResponseMessage() {
		return responseMessage;
	}

	/**
	 * @param responseMessage the responseMessage to set
	 */
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public ApplicationResponse(int responseCode, String responseMessage) {
		super();
		this.responseCode = responseCode;
		this.responseMessage = responseMessage;
	}



	
}
