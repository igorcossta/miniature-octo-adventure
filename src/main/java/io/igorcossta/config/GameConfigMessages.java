package io.igorcossta.config;

import de.exlll.configlib.Comment;
import de.exlll.configlib.Configuration;
import io.igorcossta.util.MessageUtils;
import net.kyori.adventure.text.Component;

@Configuration
public class GameConfigMessages {
    @Comment("This prefix is added before all event-related messages")
    private String prefix = "<gold>[Event]</gold>";

    // Messages for player commands
    @Comment("Displayed when the Color War event is not open")
    private String warNotOpenMessage = "<yellow>The <rainbow:!2><b>Color War</b></rainbow> event is currently unavailable. Please try again later.";

    @Comment("Displayed when the Color War event has not started yet")
    private String warNotStartedMessage = "<red>The war has not yet begun.";

    @Comment("Displayed when a player is already participating in the event")
    private String alreadyInWarMessage = "<yellow>You are already participating in the event.";

    @Comment("Displayed when it's too late for a player to join the event")
    private String tooLateToJoinMessage = "<yellow>You can no longer join the event.";

    @Comment("Displayed when a player must empty their inventory before joining the event")
    private String mustEmptyInventoryMessage = "<red>Please empty your inventory before joining the event.";

    // Messages for admin commands
    @Comment("Displayed when an attempt to start the Color War event fails due to it already being in progress")
    private String cantStartBecauseRunningMessage = "<yellow>The <rainbow:!2><b>Color War</b></rainbow> is already in progress.";

    @Comment("Displayed when an attempt to stop the Color War event fails because it is not currently active")
    private String cantStopBecauseNotRunningMessage = "<yellow>The <rainbow:!2><b>Color War</b></rainbow> is not currently active.";

    @Comment("")
    private String newLocationSet = "<green>You define a new location for {location}";

    @Comment("")
    private String locationNotFound = "<red>Location {location} not found";

    // Messages for server messages
    @Comment("Displayed when the Color War event is open for participation")
    private String warIsOpenMessage = "<yellow>The <rainbow:!2><b>Color War</b></rainbow> event is currently open for participation.";

    @Comment("Displayed when the Color War event has started")
    private String warIsStartedMessage = "<yellow>The <rainbow:!2><b>Color War</b></rainbow> event is now in progress!";

    @Comment("Displayed when the Color War event has ended, with a cause")
    private String warIsClosedMessage = "<yellow>The <rainbow:!2><b>Color War</b></rainbow> event has ended. ({cause})";

    @Comment("Displayed when a player joins the game")
    private String joinMessage = "<yellow>{player} has joined the game.";

    @Comment("Displayed when a player leaves the game")
    private String quitMessage = "<yellow>{player} has left the game.";

    @Comment("Displayed when a player dies")
    private String deathMessage = "<yellow>{player} has died.";

    @Comment("Displayed when a player wins the event")
    private String victoryMessage = "<yellow>{player} is the <rainbow:!2><b>champion</b></rainbow>! Congratulations!";


    // player messages builder
    public Component sendWarNotOpenMessage() {
        return buildMessage(warNotOpenMessage);
    }

    public Component sendWarNotStartedMessage() {
        return buildMessage(warNotStartedMessage);
    }

    public Component sendAlreadyInWarMessage() {
        return buildMessage(alreadyInWarMessage);
    }

    public Component sendTooLateToJoinMessage() {
        return buildMessage(tooLateToJoinMessage);
    }

    public Component sendMustEmptyInventoryMessage() {
        return buildMessage(mustEmptyInventoryMessage);
    }

    // admin messages builder
    public Component sendCantStartBecauseRunningMessage() {
        return buildMessage(cantStartBecauseRunningMessage);
    }

    public Component sendCantStopBecauseNotRunningMessage() {
        return buildMessage(cantStopBecauseNotRunningMessage);
    }

    public Component sendNewLocationSet(String location) {
        String messageTemplate = newLocationSet.replace("{location}", location);
        return buildMessage(messageTemplate);
    }

    public Component sendLocationNotFound(String location) {
        String messageTemplate = locationNotFound.replace("{location}", location);
        return buildMessage(messageTemplate);
    }

    // server messages builder
    public Component sendWarIsOpenMessage() {
        return buildMessage(warIsOpenMessage);
    }

    public Component sendWarIsStartedMessage() {
        return buildMessage(warIsStartedMessage);
    }

    public Component sendWarIsClosedMessage(String cause) {
        String messageTemplate = warIsClosedMessage.replace("{cause}", cause);
        return buildMessage(messageTemplate);
    }

    public Component sendJoinMessage(String whoJoin) {
        String messageTemplate = joinMessage.replace("{player}", whoJoin);
        return buildMessage(messageTemplate);
    }

    public Component sendQuitMessage(String whoQuit) {
        String messageTemplate = quitMessage.replace("{player}", whoQuit);
        return buildMessage(messageTemplate);
    }

    public Component sendDeathMessage(String whoDie) {
        String messageTemplate = deathMessage.replace("{player}", whoDie);
        return buildMessage(messageTemplate);
    }

    public Component sendVictoryMessage(String whoWon) {
        String messageTemplate = victoryMessage.replace("{player}", whoWon);
        return buildMessage(messageTemplate);
    }

    private Component buildMessage(String messageTemplate) {
        return MessageUtils.parse(messageTemplate, prefix);
    }

}
