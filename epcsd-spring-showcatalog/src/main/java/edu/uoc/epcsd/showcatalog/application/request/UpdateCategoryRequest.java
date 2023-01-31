package edu.uoc.epcsd.showcatalog.application.request;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateCategoryRequest {
	@NotNull
	private Long id;

	@NotNull
	private String name;

	@NotNull
	private String description;

	@JsonCreator
	public UpdateCategoryRequest(@JsonProperty("id") @NotNull final Long id, @JsonProperty("name") @NotNull final String name, @JsonProperty("description") final String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}
}
