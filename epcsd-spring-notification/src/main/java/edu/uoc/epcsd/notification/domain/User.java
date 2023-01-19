package edu.uoc.epcsd.notification.domain;

import lombok.*;

@ToString
@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Long id;

    private String fullName;

    private String password;

    private String email;

    private String mobileNumber;
}
