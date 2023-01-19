package edu.uoc.epcsd.showcatalog.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalTime;


@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Performance {

    private LocalDate date;

    @Schema(pattern = "HH:mm:ss", example = "21:30")
    private LocalTime time;

    private String streamingURL;

    private Long remainingSeats;

    @NotNull
    private Status status;

    public void cancel() {
        this.setStatus(Status.CANCELLED);
    }
}
