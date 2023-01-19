package edu.uoc.epcsd.showcatalog.application.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.uoc.epcsd.showcatalog.domain.Performance;
import lombok.Getter;

import javax.validation.constraints.NotNull;

public class CreatePerformanceRequest {

    @Getter
    @NotNull
    private final Performance performance;

    @JsonCreator
    public CreatePerformanceRequest(@JsonProperty("performance") @NotNull final Performance performance) {
        this.performance = performance;
    }
}
