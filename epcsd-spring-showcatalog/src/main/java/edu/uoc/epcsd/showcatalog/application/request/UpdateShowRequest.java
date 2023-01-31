package edu.uoc.epcsd.showcatalog.application.request;

import java.time.LocalDate;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateShowRequest {
	@NotNull
	private Long id;

	@NotNull
	private String name;

	@NotNull
	private String description;

	@NotNull
	private String image;

	private Double price;

	@NotNull
	private Double duration;

	@NotNull
	private Long capacity;

	@NotNull
	private LocalDate onSaleDate;

	@NotNull
	private Long categoryId;

	@JsonCreator
	public UpdateShowRequest(@JsonProperty("id") @NotNull final Long id, @JsonProperty("name") @NotNull final String name, @JsonProperty("description") @NotNull final String description,
			@JsonProperty("image") @NotNull final String image, @JsonProperty("price") final Double price, @JsonProperty("duration") @NotNull final Double duration,
			@JsonProperty("capacity") @NotNull final Long capacity, @JsonProperty("onSaleDate") @NotNull final LocalDate onSaleDate, @JsonProperty("categoryId") @NotNull final Long categoryId) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.image = image;
		this.price = price;
		this.duration = duration;
		this.capacity = capacity;
		this.onSaleDate = onSaleDate;
		this.categoryId = categoryId;
	}
}
