package eu.europeana.set.web.model;

import java.util.List;
import java.util.Map;

import eu.europeana.set.mongo.model.PersistentUserSetImpl;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldId;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldProperty;
import ioinformarics.oss.jackson.module.jsonld.annotation.JsonldType;


@JsonldType("http://schema.org/UserSet")
public class WebUserSetImpl extends PersistentUserSetImpl {
		
	@JsonldId
	public void setIdentifier(String sequenceIdentifier) {
		super.setIdentifier(sequenceIdentifier);
	}
		
	@JsonldProperty("http://schema.org/type")
	public void setType(String type) {
		super.setType(type);
	}
	
	@JsonldProperty("http://schema.org/title")
	public void setTitle(Map<String, String> title) {
		super.setTitle(title);
	}

	@JsonldProperty("http://schema.org/description")
	public void setDescription(Map<String, String> description) {
		super.setDescription(description);
	}		
	
	@JsonldProperty("@context")
	public void setContext(String context) {
		super.setContext(context);
	}
	
	@JsonldProperty("http://schema.org/items")
	public void setItems(List<String> items) {
		super.setItems(items);
	}
	
	public String toString() {
		return "WebUserSet [Title:" + getTitle() + ", Identifier:" + getIdentifier() + "]";
	}
	
}
