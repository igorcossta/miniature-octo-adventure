package io.igorcossta.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Cause {
    NON_PARTICIPANTS("War has been canceled because there are no participants"),
    TIME_EXPIRED("War has been canceled because it takes too long to have a winner"),
    ONLY_ONE_PARTICIPANT("War has been canceled because have only 1 participant"),
    CLOSE_BY_OPERATOR("War has been canceled by an operator");

    @Getter
    private final String message;
}
