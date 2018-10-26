package eu.europeana.set.mongo.model.internal;

import org.bson.types.ObjectId;

import eu.europeana.set.definitions.model.authentication.Client;
import eu.europeana.api.commons.nosql.entity.NoSqlEntity;

/**
 * This type is used as internal interface that binds the external interface (model.PersistentWhitelist) 
 * with the NoSql based implementation (NoSqlEntity) and provides additional methods used internally by 
 * the service implementation
 */
public interface PersistentClient extends Client, NoSqlEntity {

	public final static String FIELD_CLIENT_ID = "clientId";

	/**
	 * 
	 * @return the generated mongo id
	 */
	public ObjectId getId();
	
	/**
	 * This method is necessary for the update
	 * @param id
	 */
	public void setId(ObjectId id);

}
