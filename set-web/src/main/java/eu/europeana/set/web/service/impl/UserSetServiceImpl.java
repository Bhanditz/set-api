package eu.europeana.set.web.service.impl;


import java.io.IOException;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.ObjectMapper;

import eu.europeana.api.common.config.I18nConstants;
import eu.europeana.api.commons.web.exception.HttpException;
import eu.europeana.set.definitions.exception.UserSetAttributeInstantiationException;
import eu.europeana.set.definitions.exception.UserSetInstantiationException;
//import eu.europeana.api.commons.config.i18n.I18nService;
import eu.europeana.set.definitions.model.UserSet;
import eu.europeana.set.definitions.model.utils.UserSetUtils;
import eu.europeana.set.mongo.model.internal.PersistentUserSet;
import eu.europeana.set.web.exception.request.RequestBodyValidationException;
import eu.europeana.set.web.model.WebUserSetImpl;
import eu.europeana.set.web.service.UserSetService;

public class UserSetServiceImpl extends BaseUserSetServiceImpl implements UserSetService {

    UserSetUtils userSetUtils = new UserSetUtils();

    public UserSetUtils getUserSetUtils() {
      return userSetUtils;
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.europeana.UserSet.web.service.UserSetService#storeUserSet(eu.
	 * europeana.UserSet.definitions.model.UserSet)
	 */
	@Override
	public UserSet storeUserSet(UserSet newUserSet) {
		
		UserSet extUserSet = getUserSetUtils().analysePagination(newUserSet);

		// store in mongo database
		UserSet res = getMongoPersistence().store(extUserSet);
		return res;
	}
	
	@Override
	public UserSet getUserSetById(String userSetId) {
		// store in mongo database
		UserSet res = getMongoPersistence().getByIdentifier(userSetId);
		return res; 
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.europeana.UserSet.web.service.UserSetService#storeUserSet(eu.
	 * europeana.UserSet.definitions.model.UserSet, boolean)
	 */
	@Override
	public UserSet updateUserSet(PersistentUserSet persistentUserSet, UserSet webUserSet) {
		mergeUserSetProperties(persistentUserSet, webUserSet);
		UserSet res = updateAndReindex(persistentUserSet);

		return res;
	}

	/**
	 * @deprecated check if the update test must merge the properties or if it simply overwrites it
	 * @param UserSet
	 * @param updatedWebUserSet
	 */
	private void mergeUserSetProperties(PersistentUserSet UserSet, UserSet updatedWebUserSet) {
		if (updatedWebUserSet.getType() != null)
			UserSet.setType(updatedWebUserSet.getType());

		if (updatedWebUserSet.getTitle() != null)
			UserSet.setTitle(updatedWebUserSet.getTitle());
		
		if (updatedWebUserSet.getCreator() != null)
			UserSet.setCreator(updatedWebUserSet.getCreator());
			
		if (updatedWebUserSet.getCreated() != null)
			UserSet.setCreated(updatedWebUserSet.getCreated());
	}

	@Override
	public UserSet parseUserSetLd(String userSetJsonLdStr)
			throws JsonParseException, HttpException {

		JsonParser parser;
	    ObjectMapper mapper = new ObjectMapper();  
	    mapper.configure(Feature.AUTO_CLOSE_SOURCE, true);
	    JsonFactory jsonFactory = mapper.getFactory();
		
		/**
		 * parse JsonLd string using JsonLdParser. JsonLd string -> JsonLdParser
		 * -> JsonLd object
		 */
		try {
			
			parser = jsonFactory.createParser(userSetJsonLdStr);
			UserSet userSet = mapper.readValue(parser, WebUserSetImpl.class); 
            return userSet;
		} catch (UserSetAttributeInstantiationException e) {
			throw new RequestBodyValidationException(userSetJsonLdStr, I18nConstants.USERSET_CANT_PARSE_BODY, e);
		} catch (JsonParseException e) {
			throw new UserSetInstantiationException("Json formating exception!", e);
		} catch (IOException e) {
			throw new UserSetInstantiationException("Json reading exception!", e);
		}
	}
	
}
