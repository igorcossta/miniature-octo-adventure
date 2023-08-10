package io.igorcossta.event.custom;

import io.igorcossta.event.Cause;
import io.igorcossta.event.WarEvent;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

import java.util.List;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class WarStopEvent extends WarEvent {
    private final List<Player> participants;
    private final Cause cause;
}
