package eu.europeana.set.web.model;

import java.util.HashMap;

public interface BatchReportable {
	
	public int getFailureCount();
	
	public int getSuccessCount();
	
	public void incrementFailureCount();
	
	public void incrementSuccessCount();
	
	public HashMap<Integer, String> getErrors();
	
	public void addError(int pos, String msg);
	
	public boolean hasErrors();
	
	public String toString();
	
}

