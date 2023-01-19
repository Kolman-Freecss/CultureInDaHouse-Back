package edu.uoc.epcsd.notification.domain;

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
