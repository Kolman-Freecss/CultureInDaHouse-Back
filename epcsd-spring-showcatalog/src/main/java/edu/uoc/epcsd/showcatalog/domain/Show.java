package edu.uoc.epcsd.showcatalog.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@ToString(exclude = {"performances", "comments"})
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

    @Builder.Default
    @JsonIgnore
    private Set<ShowComment> comments = new HashSet<>(); 

    private float averageRating;

    public void cancel() {
        if (this.status.isCancelled()) {
            throw new DomainException("Can't cancel an already cancelled show");
        }

        this.performances.forEach(Performance::cancel);

        this.status = Status.CANCELLED;
    }

    public Category getCategory() {
        return category;
    }

    public float getAverageRating() {
        float average = 0;

        if(comments.size() > 0) {
        	for (ShowComment comment: comments) {
                average += comment.getRating();
            }
            averageRating = average/comments.size();
        } else {
        	averageRating = 0F;
        }
        
        return averageRating;
    }
}
