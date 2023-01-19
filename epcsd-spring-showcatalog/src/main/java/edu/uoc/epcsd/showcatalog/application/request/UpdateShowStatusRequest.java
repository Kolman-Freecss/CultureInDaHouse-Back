package edu.uoc.epcsd.showcatalog.application.request;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import edu.uoc.epcsd.showcatalog.domain.Status;
import lombok.Getter;

public class UpdateShowStatusRequest {
    @Getter
    @NotNull
    private final Status status;

    @JsonCreator
    public UpdateShowStatusRequest(@JsonProperty("status") @NotNull final Status status) {
        this.status = status;
    }
}
