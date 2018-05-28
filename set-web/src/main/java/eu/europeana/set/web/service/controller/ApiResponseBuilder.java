package eu.europeana.set.web.service.controller;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.log4j.Logger;

import eu.europeana.set.web.model.UserSetOperationResponse;

public class ApiResponseBuilder {

	Logger logger = Logger.getLogger(getClass());
	
	public Logger getLogger() {
		return logger;
	}

	public UserSetOperationResponse buildErrorResponse(String errorMessage,
			String action, String apiKey) {
		UserSetOperationResponse response;
		response = new UserSetOperationResponse(
				apiKey, action);
		
		response.success = false;
		response.error = errorMessage;
		return response;
	}

	/**
	 * This method generates validation error report.
	 * The validation report takes as input parameters from the request. This method must take as input the request object and extract parameters as needed.
	 * @param apiKey
	 * @param action
	 * @param errorMessage
	 * @return
	 */
	public UserSetOperationResponse getValidationReport(String apiKey, String action, String errorMessage, Throwable th, boolean includeErrorStack) {

		UserSetOperationResponse response = new UserSetOperationResponse(
				apiKey, action);
		
		final String blank = " ";
		StringBuilder messageBuilder = new StringBuilder();
		
		if (errorMessage != null)
			messageBuilder.append(blank).append(errorMessage).append(". ");
		if (th !=null){
			if(errorMessage!= null)
				messageBuilder.append("Caused by: ");
			messageBuilder.append(th.getMessage());
		}
		if(th != null && th.getCause() !=null && th != th.getCause())
			messageBuilder.append(blank).append(th.getCause().getMessage());
		
		response = buildErrorResponse(messageBuilder.toString(), response.action, response.apikey);
		
		if(includeErrorStack && th != null)
			response.setStackTrace(getStackTraceAsString(th));
		
		return response;
	}

	String getStackTraceAsString(Throwable th) {
		StringWriter out = new StringWriter();
		th.printStackTrace(new PrintWriter(out));
		return out.toString();
	}

	/**
	 * This method is employed for positive report - without errors.
	 * @param apiKey
	 * @param action
	 * @param message
	 * @return
	 */
	public UserSetOperationResponse buildResponse(String message,
			String action, String apiKey) {
		UserSetOperationResponse response;
		response = new UserSetOperationResponse(
				apiKey, action);
		
		response.success = true;
		response.setStatus(message);
		return response;
	}

}
