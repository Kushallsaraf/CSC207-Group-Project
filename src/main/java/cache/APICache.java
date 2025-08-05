package cache;

import kong.unirest.JsonNode;

/**Caches API calls by mapping a type of request to a unique request which maps to its unique response
 *
 */

//TODO: Expand this interface for new return types (if needed). For example add <cacheString>
// to the interface and implement it where applicable

public interface APICache {

    /** Checks to see if this request is already stored
     *
     * @param requestType : the type of request. Must be in
     * @param requestKey : the request URL
     * @return
     */
    boolean hasRequest(String requestType, String requestKey);
    void cacheJsonNode(String requestType, String requestKey, JsonNode response);
    void cacheInt(String requestType, String requestKey, int value);
    String getResponse(String requestType, String requestKey);
}
