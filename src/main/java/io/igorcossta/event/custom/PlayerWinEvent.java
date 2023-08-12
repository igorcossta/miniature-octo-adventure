package io.igorcossta.event.custom;

import io.igorcossta.event.WarEvent;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

@Getter
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class PlayerWinEvent extends WarEvent {
    private final Player player;
}
