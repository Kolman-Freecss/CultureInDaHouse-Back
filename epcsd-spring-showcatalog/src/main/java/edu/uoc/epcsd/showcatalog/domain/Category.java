package edu.uoc.epcsd.showcatalog.domain;

import lombok.*;


@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    private Long id;

    private String name;

    private String description;

}
