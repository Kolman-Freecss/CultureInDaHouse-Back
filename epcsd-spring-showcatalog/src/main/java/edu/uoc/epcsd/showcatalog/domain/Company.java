package edu.uoc.epcsd.showcatalog.domain;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Company {

	private Long id;

	@NotNull
	private String name;

	private String address;

	private String email;

	private String mobileNumber;
	
}
