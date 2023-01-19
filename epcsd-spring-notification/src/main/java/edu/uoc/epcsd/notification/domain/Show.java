package edu.uoc.epcsd.notification.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@ToString(exclude = {"performances"})
@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Show {

    private Long id;

    private String name;

    private String description;

    private String image;

    private Double price;

    private Double duration;

    private Long capacity;

    private LocalDate onSaleDate;

    @NotNull
    @Builder.Default
    private Status status = Status.CREATED;

    @NotNull
    private Category category;

    @Builder.Default
    @JsonIgnore
    private Set<Performance> performances = new HashSet<>();

    public void cancel() {
        if (this.status.isCancelled()) {
            throw new DomainException("Can't cancel an already cancelled show");
        }

        this.performances.forEach(Performance::cancel);

        this.status = Status.CANCELLED;
    }
}
