package com.redhat.lightblue.client.request.metadata;

import com.redhat.lightblue.client.http.HttpMethod;
import com.redhat.lightblue.client.request.AbstractLightblueMetadataRequest;

public class MetadataGetEntityDependenciesRequest extends AbstractLightblueMetadataRequest {

    public MetadataGetEntityDependenciesRequest() {
        super();
    }

    public MetadataGetEntityDependenciesRequest(String entityName, String entityVersion) {
        super(entityName, entityVersion);
    }

    @Override
    public String getOperationPathParam() {
        return PATH_PARAM_GET_ENTITY_DEPENDENCIES;
    }

    @Override
    public HttpMethod getHttpMethod() {
        return HttpMethod.GET;
    }

}
