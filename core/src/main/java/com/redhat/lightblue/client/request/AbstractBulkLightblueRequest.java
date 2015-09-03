/**
 * 
 */
package com.redhat.lightblue.client.request;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.JsonNode;
import com.redhat.lightblue.client.http.HttpMethod;

/**
 * @author bvulaj
 *
 */
public abstract class AbstractBulkLightblueRequest<E extends AbstractLightblueRequest> implements LightblueRequest {

	protected List<E> reqs = new ArrayList<E>();

	public AbstractBulkLightblueRequest() {

	}

	public AbstractBulkLightblueRequest(List<E> alrs) {
		this.reqs.addAll(alrs);
	}

	/**
	 * Adds a request to the end of the current request chain.
	 * 
	 * @param alr
	 */
	public void add(E alr) {
		reqs.add(alr);
	}

	/**
	 * Adds a collection of requests to the end of the current request chain.
	 * 
	 * @param alrs
	 */
	public void addAll(List<E> alrs) {
		reqs.addAll(alrs);
	}

	/**
	 * Inserts a request at the given index. This method should not be preferred over the before / after methods.
	 * 
	 * @param alr
	 * @param index
	 */
	public void insert(E alr, int index) {
		reqs.add(index, alr);
	}

	/**
	 * Inserts a request before another specified request. This guarantees that the first request parameter will be executed, sequentially, before the second request parameter. It
	 * does not guarantee consecutive execution.
	 * 
	 * @param alr
	 * @param before
	 */
	public void insertBefore(E alr, E before) {
		reqs.add(reqs.indexOf(before), alr);
	}

	/**
	 * Inserts a request after another specified request. This guarantees that the first request parameter will be executed, sequentially, after the second request parameter. It
	 * does not guarantee consecutive execution.
	 * 
	 * @param alr
	 * @param after
	 */
	public void insertAfter(E alr, E after) {
		reqs.add(reqs.indexOf(after) + 1, alr);
	}

	@Override
	public abstract JsonNode getBodyJson();

	@Override
	public String getBody() {
		return getBodyJson().toString();
	}

	@Override
	public HttpMethod getHttpMethod() {
		return HttpMethod.POST;
	}

	@Override
	public String getRestURI(String baseServiceURI) {
		StringBuilder requestURI = new StringBuilder();
		requestURI.append(baseServiceURI);
		requestURI.append("/bulk");
		return requestURI.toString();
	}

}