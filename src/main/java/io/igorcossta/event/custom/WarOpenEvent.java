package io.igorcossta.event.custom;

import io.igorcossta.event.WarEvent;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class WarOpenEvent extends WarEvent {

}
