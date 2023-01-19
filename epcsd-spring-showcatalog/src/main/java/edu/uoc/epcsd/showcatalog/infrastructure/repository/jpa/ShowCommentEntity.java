package edu.uoc.epcsd.showcatalog.infrastructure.repository.jpa;

import edu.uoc.epcsd.showcatalog.domain.ShowComment;
import lombok.*;

import javax.persistence.*;

@Builder
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@Table(name = "show_comments")
public class ShowCommentEntity implements DomainTranslatable<ShowComment> {
    @Column(name = "comment", nullable = false)
    private String comment;

    @Column(name = "rating", nullable = false)
    private int rating;

    public static ShowCommentEntity fromDomain(ShowComment showComment) {
        if (showComment == null) {
            return null;
        }

        return ShowCommentEntity.builder()
                .comment(showComment.getComment())
                .rating(showComment.getRating())
                .build();
    }

    @Override
    public ShowComment toDomain() {
        return ShowComment.builder()
                .comment(this.getComment())
                .rating(this.getRating())
                .build();
    }
}