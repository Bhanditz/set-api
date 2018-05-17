package eu.europeana.set.mongo.dao;

import java.io.Serializable;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import eu.europeana.api.commons.nosql.dao.impl.NosqlDaoImpl;
import eu.europeana.set.definitions.config.UserSetConfiguration;
import eu.europeana.set.mongo.model.internal.GeneratedUserSetIdImpl;
import eu.europeana.set.mongo.model.internal.PersistentUserSet;

public class PersistentUserSetDaoImpl <E extends PersistentUserSet, T extends Serializable>
		extends NosqlDaoImpl<E, T> implements PersistentUserSetDao<E, T>{

	@Resource
	private UserSetConfiguration configuration;
	
	protected final Logger logger = Logger.getLogger(this.getClass());
	
	public UserSetConfiguration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(UserSetConfiguration configuration) {
		this.configuration = configuration;
	}
	
	public PersistentUserSetDaoImpl(Class<E> clazz, Datastore datastore) {
		super(datastore, clazz);
	}

	@SuppressWarnings("deprecation")
	public long generateNextUserSetId(String provider) {

		GeneratedUserSetIdImpl nextUserSetId = null;

		synchronized (provider) {

			Query<GeneratedUserSetIdImpl> q = getDatastore().createQuery(GeneratedUserSetIdImpl.class);
			q.filter("_id", provider);
			
			UpdateOperations<GeneratedUserSetIdImpl> uOps = getDatastore()
					.createUpdateOperations(GeneratedUserSetIdImpl.class)
					.inc(GeneratedUserSetIdImpl.SEQUENCE_COLUMN_NAME);
			// search UserSetId and get incremented UserSet number 
			nextUserSetId = getDatastore().findAndModify(q, uOps);
			
			if (nextUserSetId == null) {
				nextUserSetId = new GeneratedUserSetIdImpl( 
						provider, ""+1L);
				ds.save(nextUserSetId);
			}
		}

		return nextUserSetId.getUserSetId();
	}
	
	
}
