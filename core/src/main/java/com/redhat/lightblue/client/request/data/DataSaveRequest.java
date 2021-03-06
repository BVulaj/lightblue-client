package com.redhat.lightblue.client.request.data;

import java.util.Collection;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;

import com.redhat.lightblue.client.http.HttpMethod;
import com.redhat.lightblue.client.Operation;
import com.redhat.lightblue.client.Projection;
import com.redhat.lightblue.client.request.AbstractLightblueDataRequest;
import com.redhat.lightblue.client.util.JSON;

public class DataSaveRequest extends AbstractLightblueDataRequest {

    private Projection projection;
    private Object[] objects;
    private Boolean upsert;

    public DataSaveRequest() {
        super();
    }

    public DataSaveRequest(String entityName, String entityVersion) {
        super(entityName, entityVersion);
    }

    public DataSaveRequest(String entityName) {
        super(entityName);
    }

    public void returns(Projection... projection) {
        this.projection = Projection.project(projection);
    }

    @Deprecated
    public void returns(com.redhat.lightblue.client.projection.Projection... projection) {
        Projection[] p = new Projection[projection.length];
        for (int i = 0; i < p.length; i++)
            p[i] = top(projection[i]);
        returns(p);
    }

    @Deprecated
    public void returns(Collection<com.redhat.lightblue.client.projection.Projection> projections) {
        returns(projections.toArray(new com.redhat.lightblue.client.projection.Projection[projections.size()]));
    }

    public void create(Object... objects) {
        if (objects[0] instanceof java.util.Collection<?>) {
            this.objects = ((Collection<?>) objects[0]).toArray();
        } else {
            this.objects = objects;
        }
    }

    public Boolean isUpsert() {
        return upsert;
    }

    public void setUpsert(Boolean upsert) {
        this.upsert = upsert;
    }

    @Override
    public String getOperationPathParam() {
        return "save";
    }

    @Override
    public JsonNode getBodyJson() {
        ObjectNode node = JsonNodeFactory.instance.objectNode();
        if (projection != null)
            node.set("projection", projection.toJson());
        if (objects.length == 1) {
            node.set("data", JSON.toJsonNode(objects[0]));
        } else {
            ArrayNode arr = JsonNodeFactory.instance.arrayNode();
            for (int i = 0; i < objects.length; i++)
                arr.add(JSON.toJsonNode(objects[i]));
            node.set("data", arr);
        }
        if (upsert != null)
            node.set("upsert", JsonNodeFactory.instance.booleanNode(upsert));
        return node;
    }

    @Override
    public HttpMethod getHttpMethod() {
        return HttpMethod.POST;
    }

    @Override
    public Operation getOperation() {
        return Operation.SAVE;
    }

}
