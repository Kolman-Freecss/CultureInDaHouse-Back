package edu.uoc.epcsd.showcatalog.application.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import edu.uoc.epcsd.showcatalog.domain.ShowComment;
import lombok.Getter;

import javax.validation.constraints.NotNull;

public class CreateShowCommentRequest {

    @Getter
    @NotNull
    private final ShowComment showComment;

    @JsonCreator
    public CreateShowCommentRequest(@JsonProperty("showComment") @NotNull final ShowComment showComment) {
        this.showComment = showComment;
    }
}
