package com.example.K8s.kubernetes.CR.sparkcr;

import com.fasterxml.jackson.annotation.*;

import javax.annotation.Generated;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "instances"
})
@Generated("jsonschema2pojo")
public class Worker {
    @JsonProperty("instances")
    private String instances;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("instances")
    public String getInstances() {
        return instances;
    }

    @JsonProperty("instances")
    public void setInstances(String instances) {
        this.instances = instances;
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
