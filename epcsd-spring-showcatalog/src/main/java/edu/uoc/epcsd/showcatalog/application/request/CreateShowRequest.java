package edu.uoc.epcsd.showcatalog.application.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.uoc.epcsd.showcatalog.domain.Show;
import lombok.Getter;

import javax.validation.constraints.NotNull;

public class CreateShowRequest {

    @Getter
    @NotNull
    private final Show show;

    
    @JsonCreator
    public CreateShowRequest(@JsonProperty("show") @NotNull final Show show) {
        this.show = show;
    }
}
