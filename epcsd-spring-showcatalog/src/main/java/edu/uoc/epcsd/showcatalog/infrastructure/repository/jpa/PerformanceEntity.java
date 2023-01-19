package edu.uoc.epcsd.showcatalog.infrastructure.repository.jpa;

import edu.uoc.epcsd.showcatalog.domain.Performance;
import edu.uoc.epcsd.showcatalog.domain.Status;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@Table(name = "performance")
public class PerformanceEntity implements DomainTranslatable<Performance> {

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "time", nullable = false)
    private LocalTime time;

    @Column(name = "streaming_url")
    private String streamingURL;

    @Column(name = "remaining_seats", nullable = false)
    private Long remainingSeats;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Status status;

    public static PerformanceEntity fromDomain(Performance performance) {
        return PerformanceEntity.builder()
                .date(performance.getDate())
                .time(performance.getTime())
                .streamingURL(performance.getStreamingURL())
                .remainingSeats(performance.getRemainingSeats())
                .status(performance.getStatus())
                .build();
    }

    @Override
    public Performance toDomain() {
        return Performance.builder()
                .date(this.getDate())
                .remainingSeats(this.getRemainingSeats())
                .status(this.getStatus())
                .time(this.getTime())
                .streamingURL(this.getStreamingURL())
                .build();
    }
}
