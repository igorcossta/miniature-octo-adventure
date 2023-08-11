package io.igorcossta.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Cause {
    NON_PARTICIPANTS("No participants"),
    TIME_EXPIRED("Took too long to determine a winner"),
    ONLY_ONE_PARTICIPANT("Only one participant"),
    CLOSE_BY_OPERATOR("Canceled by an operator");

    @Getter
    private final String message;
}
