package com.example.K8s.kubernetes.CR.hadoopcr;

import com.fasterxml.jackson.annotation.*;

import javax.annotation.Generated;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "clusterSize"
})
@Generated("jsonschema2pojo")
public class Spec {

    @JsonProperty("clusterSize")
    private Integer clusterSize;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("clusterSize")
    public Integer getClusterSize() {
        return clusterSize;
    }

    @JsonProperty("clusterSize")
    public void setClusterSize(Integer clusterSize) {
        this.clusterSize = clusterSize;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}