package edu.uoc.epcsd.showcatalog.application.request;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import edu.uoc.epcsd.showcatalog.domain.Company;
import lombok.Getter;

public class CreateCompanyRequest {

    @Getter
    @NotNull
    private final Company company;

    @JsonCreator
    public CreateCompanyRequest(@JsonProperty("company") @NotNull final Company company) {
        this.company = company;
    }
}
