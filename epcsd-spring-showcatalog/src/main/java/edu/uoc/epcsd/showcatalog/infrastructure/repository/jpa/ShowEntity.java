package edu.uoc.epcsd.showcatalog.infrastructure.repository.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.uoc.epcsd.showcatalog.domain.Show;
import edu.uoc.epcsd.showcatalog.domain.Status;
import lombok.*;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Entity
@ToString
@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "show")
public class ShowEntity implements DomainTranslatable<Show> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "image", nullable = false, length = 1024)
    private String image;

    @Column(name = "price")
    private Double price;

    @Column(name = "duration", nullable = false)
    private Double duration;

    @Column(name = "capacity", nullable = false)
    private Long capacity;

    @Column(name = "on_sale_date", nullable = false)
    private LocalDate onSaleDate;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Status status;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "id_category", referencedColumnName = "id")
    private CategoryEntity category;

    @Builder.Default
    @ElementCollection(targetClass = PerformanceEntity.class)
    @CollectionTable(name = "performance", joinColumns = @JoinColumn(name = "id_show", referencedColumnName = "id"), uniqueConstraints = {@UniqueConstraint(columnNames = {"date", "time"})})
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JsonIgnore
    private Set<PerformanceEntity> performances = new HashSet<>();

    @Builder.Default
    @ElementCollection(targetClass = ShowCommentEntity.class)
    @CollectionTable(name = "show_comments", joinColumns = @JoinColumn(name = "id_show", referencedColumnName = "id"))
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    @JsonIgnore
    private Set<ShowCommentEntity> comments = new HashSet<>();

    public static ShowEntity fromDomain(Show show) {
        if (show == null) {
            return null;
        }

        return ShowEntity.builder()
                .id(show.getId())
                .status(show.getStatus())
                .price(show.getPrice())
                .image(show.getImage())
                .onSaleDate(show.getOnSaleDate())
                .duration(show.getDuration())
                .capacity(show.getCapacity())
                .name(show.getName())
                .description(show.getDescription())
                .category(CategoryEntity.fromDomain(show.getCategory()))
                .performances(show.getPerformances().stream().map(PerformanceEntity::fromDomain).collect(Collectors.toSet()))
                .comments(show.getComments().stream().map(ShowCommentEntity::fromDomain).collect(Collectors.toSet()))
                .build();
    }

    @Override
    public Show toDomain() {
        return Show.builder()
                .id(this.getId())
                .status(this.getStatus())
                .price(this.getPrice())
                .image(this.getImage())
                .onSaleDate(this.getOnSaleDate())
                .duration(this.getDuration())
                .capacity(this.getCapacity())
                .name(this.getName())
                .description(this.getDescription())
                .category(this.getCategory().toDomain())
                .performances((this.getPerformances().stream().map(PerformanceEntity::toDomain).collect(Collectors.toSet())))
                .comments((this.getComments().stream().map(ShowCommentEntity::toDomain).collect(Collectors.toSet())))
                .build();
    }
}
