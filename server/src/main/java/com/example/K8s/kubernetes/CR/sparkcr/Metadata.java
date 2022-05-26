package com.example.K8s.kubernetes.CR.sparkcr;

import com.fasterxml.jackson.annotation.*;

import javax.annotation.Generated;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "name",
        "resourceVersion"
})
@Generated("jsonschema2pojo")
public class Metadata {
    @JsonProperty("name")
    private String name;
    @JsonProperty("resourceVersion")
    private String resourceVersion;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();


    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("resourceVersion")
    public String getResourceVersion() {return resourceVersion;}

    @JsonProperty("resourceVersion")
    public void setResourceVersion(String resourceVersion) {this.resourceVersion = resourceVersion;}

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }
}
