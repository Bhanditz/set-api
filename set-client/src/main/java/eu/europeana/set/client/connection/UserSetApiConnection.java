package eu.europeana.set.client.connection;

import java.io.IOException;

import org.springframework.http.ResponseEntity;

import eu.europeana.set.client.config.ClientConfiguration;
import eu.europeana.set.definitions.model.vocabulary.WebUserSetFields;

/**
 * @author GrafR
 *
 */
public class UserSetApiConnection extends BaseApiConnection {

	/**
	 * Create a new connection to the UserSet Service (REST API).
	 * 
	 * @param apiKey
	 *            API Key required to access the API
	 */
	public UserSetApiConnection(String setServiceUri, String apiKey) {
		super(setServiceUri, apiKey);
	}

	public UserSetApiConnection() {
		this(ClientConfiguration.getInstance().getServiceUri(),
				ClientConfiguration.getInstance().getApiKey());
	}
	
	/**
	 * This method creates UserSet object from Json string.
	 * Example HTTP request for tag object: 
	 *     http://localhost:8080/set/?wskey=<key>&userToken=<token>
	 * @param wskey
	 * @param set The UserSet body
	 * @param userToken
	 * @return response entity that comprises response body, headers and status code.
	 * @throws IOException
	 */
	public ResponseEntity<String> createUserSet(
			String wskey, String set, String userToken) throws IOException {
		
		StringBuilder urlBuilder = getUserSetServiceUri();		
		urlBuilder.append(WebUserSetFields.PAR_CHAR);
		urlBuilder.append(WebUserSetFields.PARAM_WSKEY).append(WebUserSetFields.EQUALS)
			.append(wskey).append(WebUserSetFields.AND);
		urlBuilder.append(WebUserSetFields.USER_TOKEN).append(WebUserSetFields.EQUALS)
			.append(userToken).append(WebUserSetFields.AND);
		
		String resUrl = urlBuilder.toString();
		
		logger.trace("Ivoking create set: " + resUrl);
		
		/**
		 * Execute Europeana API request
		 */
		return postURL(resUrl, set);		
	}

	/**
	 * This method retrieves UserSet object.
	 * Example HTTP request for tag object: 
	 *      http://localhost:8080/set/{identifier}.jsonld?wskey=<key>&userToken=<token>
	 * where identifier is:
	 *     496
	 * @param wskey
	 * @param identifier
	 * @param userToken
	 * @return response entity that comprises response body, headers and status code.
	 * @throws IOException
	 */
	public ResponseEntity<String> getUserSet(
			String wskey, String identifier, String userToken) throws IOException {

		StringBuilder urlBuilder = getUserSetServiceUri();
		urlBuilder.append(identifier).append(WebUserSetFields.JSON_LD_REST);
		urlBuilder.append(WebUserSetFields.PAR_CHAR);
		urlBuilder.append(WebUserSetFields.PARAM_WSKEY).append(WebUserSetFields.EQUALS)
	    	.append(wskey).append(WebUserSetFields.AND);
		urlBuilder.append(WebUserSetFields.USER_TOKEN).append(WebUserSetFields.EQUALS)
	    	.append(userToken);
		
		/**
		 * Execute Europeana API request
		 */
		return getURL(urlBuilder.toString());		
	}

	/**
	 * This method updates UserSet object by the passed Json update string.
	 * Example HTTP request: 
	 *      http://localhost:8080/set/{identifier}.jsonld?wskey=<key>&userToken=<token>
	 * where identifier is:
	 *     496
	 * and the update JSON string is:
	 *     { "title": {"en":"Sport"},"description": {"en":"Best sport"} }
	 * @param wskey
	 * @param identifier The identifier that comprise set ID
	 * @param updateUserSet The update UserSet body in JSON format
	 * @param userToken
	 * @return response entity that comprises response body, headers and status code.
	 * @throws IOException
	 */
	public ResponseEntity<String> updateUserSet(
			String wskey, String identifier, String updateUserSet, String userToken) throws IOException {

		StringBuilder urlBuilder = getUserSetServiceUri();
		urlBuilder.append(identifier).append(WebUserSetFields.JSON_LD_REST);
		urlBuilder.append(WebUserSetFields.PAR_CHAR);
		urlBuilder.append(WebUserSetFields.PARAM_WSKEY).append(WebUserSetFields.EQUALS)
		    .append(wskey).append(WebUserSetFields.AND);
		
		/**
		 * Execute Europeana API request
		 */
		return putURL(urlBuilder.toString(), updateUserSet);		
	}

	/**
	 * This method deletes UserSet object by the passed identifier.
	 * Example HTTP request: 
	 *      http://localhost:8080/set/{identifier}.jsonld?wskey=<key>&userToken=<token>
	 * where identifier is:
	 *     494
	 * @param wskey
	 * @param identifier The identifier that comprise set ID
	 * @param userToken
	 * @return response entity that comprises response headers and status code.
	 * @throws IOException
	 */
	public ResponseEntity<String> deleteUserSet(
			String wskey, String identifier, String userToken) throws IOException {
		
		StringBuilder urlBuilder = getUserSetServiceUri();
		urlBuilder.append(identifier).append(WebUserSetFields.JSON_LD_REST);
		urlBuilder.append(WebUserSetFields.PAR_CHAR);
		urlBuilder.append(WebUserSetFields.PARAM_WSKEY).append(WebUserSetFields.EQUALS)
		    .append(wskey).append(WebUserSetFields.AND);
		
		/**
		 * Execute Europeana API request
		 */
		return deleteURL(urlBuilder.toString());		
	}

}
