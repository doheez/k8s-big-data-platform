package com.example.K8s.kubernetes.CR.sparkcr;

import com.fasterxml.jackson.annotation.*;

import javax.annotation.Generated;
import java.util.HashMap;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "worker",
        "master"
})
@Generated("jsonschema2pojo")
public class Spec {
    @JsonProperty("worker")
    private Worker worker;
    @JsonProperty("master")
    private Master master;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("worker")
    public Worker getWorker() {
        return worker;
    }

    @JsonProperty("worker")
    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    @JsonProperty("master")
    public Master getMaster() {
        return master;
    }

    @JsonProperty("master")
    public void setMaster(Master master) {
        this.master = master;
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
