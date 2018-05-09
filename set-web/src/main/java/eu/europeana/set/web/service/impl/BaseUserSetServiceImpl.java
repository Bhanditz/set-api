package eu.europeana.set.web.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import eu.europeana.set.definitions.model.UserSet;
import eu.europeana.set.mongo.model.internal.PersistentUserSet;
import eu.europeana.set.mongo.service.PersistentUserSetService;

public abstract class BaseUserSetServiceImpl{

	@Resource
	PersistentUserSetService mongoPersistance;

	Logger logger = Logger.getLogger(getClass());


//	public AuthenticationService getAuthenticationService() {
//		return authenticationService;
//	}
//
//	public void setAuthenticationService(AuthenticationService authenticationService) {
//		this.authenticationService = authenticationService;
//	}

//	public String getComponentName() {
//		return configuration.getComponentName();
//	}
//
//	protected AnnotationConfiguration getConfiguration() {
//		return configuration;
//	}
//
//	public void setConfiguration(AnnotationConfiguration configuration) {
//		this.configuration = configuration;
//	}

	protected PersistentUserSetService getMongoPersistence() {
		return mongoPersistance;
	}

	public void setMongoPersistance(PersistentUserSetService mongoPersistance) {
		this.mongoPersistance = mongoPersistance;
	}
	
//	public Annotation getAnnotationById(AnnotationId annoId) throws AnnotationNotFoundException, UserAuthorizationException {
//		Annotation annotation = getMongoPersistence().find(annoId);
//		if(annotation == null)
//			throw new AnnotationNotFoundException(null, I18nConstants.ANNOTATION_NOT_FOUND, new String[]{annoId.toHttpUrl()});
//		
//		String user = (String)null;
//		try {
//			// check visibility	
//			checkVisibility(annotation, user);
//		} catch (AnnotationStateException e) {
//			if (annotation.isDisabled())
//				throw new UserAuthorizationException(null, I18nConstants.ANNOTATION_NOT_ACCESSIBLE, 
//						new String[]{annotation.getStatus()}, HttpStatus.GONE, e);
//			else
//				// TODO: either change method parameters to accept wsKey or return different exception
//				throw new UserAuthorizationException(null, I18nConstants.USER_NOT_AUTHORIZED, new String[]{user}, e);
//		}		
//		
//		return annotation;
//	}

//	public void indexAnnotation(AnnotationId annoId) {
//		try {
//			Annotation res = getAnnotationById(annoId);
//			getSolrService().delete(res.getAnnotationId());
//			getSolrService().store(res);
//		} catch (Exception e) {
//			throw new RuntimeException(e);
//		}
//	}
	
//	/**
//	 * Returns true by successful reindexing.
//	 * @param res
//	 * @return reindexing success status
//	 * @throws AnnotationIndexingException 
//	 */
//	protected boolean reindexAnnotation(Annotation res, Date lastIndexing) throws AnnotationIndexingException {
//		boolean success = false;
//		
//		if (!getConfiguration().isIndexingEnabled()){
//			getLogger().warn("Annotation was not reindexed, indexing is disabled. See configuration properties!");
//			return false;
//		}
//			
//		try {
//			Summary summary = getMongoModerationRecordPersistence().getModerationSummaryByAnnotationId(res.getAnnotationId());			
//			getSolrService().update(res, summary);
//			updateLastIndexingTime(res, lastIndexing);
//			
//			success = true;
//		} catch (Exception e) {
////			Logger.getLogger(getClass().getName()).error("Error by reindexing of annotation: "
////					+ res.getAnnotationId().toString() + ". " + e.getMessage());
//			throw new AnnotationIndexingException("cannot reindex annotation with ID: " + res.getAnnotationId(), e);
//		}
//		
//		// check if the tag is already indexed
////		try {
////			getSolrTagService().update(res);
////		} catch (Exception e) {
////			Logger.getLogger(getClass().getName())
////					.warn("The annotation was updated correctly in the Mongo, but the Body tag was not updated yet. "
////							, e);
////		}
//
//		// save the time of the last SOLR indexing
//		return success;
//	}
//
	

//	/**
//	 * Returns true by successful reindexing.
//	 * @param res
//	 * @return reindexing success status
//	 */
//	public boolean reindexAnnotationById(AnnotationId annoId, Date lastIndexing) {
//		boolean success = false;
//		try {
//			Annotation res = getAnnotationById(annoId);
//			success = reindexAnnotation(res, lastIndexing);
//		} catch (Exception e) {
//			logger.error(e.getMessage());
//			return false;
//		}
//		return success;
//	}
//
//	
//	protected void updateLastIndexingTime(Annotation res, Date lastIndexing) {
//		try {
//			getMongoPersistence().updateIndexingTime(res.getAnnotationId(), lastIndexing);
//			((PersistentAnnotation)res).setLastIndexed(lastIndexing);
//		} catch (Exception e) {
//			Logger.getLogger(getClass().getName())
//					.warn("The time of the last SOLR indexing could not be saved. " , e);
//		}
//	}

	public Logger getLogger() {
		return logger;
	}

	public void setLogger(Logger logger) {
		this.logger = logger;
	}

	public PersistentUserSetService getMongoPersistance() {
		return mongoPersistance;
	}

	protected UserSet updateAndReindex(PersistentUserSet persistentUserSet) {
		UserSet res = getMongoPersistence().update(persistentUserSet);
	
		//reindex annotation
//		try {
//			reindexAnnotation(res, res.getLastUpdate());	
//		} catch (AnnotationIndexingException e) {
//		   getLogger().warn("The annotation could not be reindexed successfully: " + persistentAnnotation.getAnnotationId(), e);		
//		}
		return res;
	}
	
}
