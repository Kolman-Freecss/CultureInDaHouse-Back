package edu.uoc.epcsd.showcatalog.domain;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShowComment {
    private String comment;
    private int rating;
}