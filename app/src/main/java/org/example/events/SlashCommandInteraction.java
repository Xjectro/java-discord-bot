package org.example.events;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.example.commands.CommandManager;

public class SlashCommandInteraction implements TypedListener<SlashCommandInteractionEvent> {
  private final CommandManager commandManager;

  public SlashCommandInteraction(CommandManager commandManager) {
    this.commandManager = commandManager;
  }

  @Override
  public void accept(SlashCommandInteractionEvent event) {
    commandManager.handleSlashCommand(event);
  }

  @Override
  public Class<SlashCommandInteractionEvent> getEventType() {
    return SlashCommandInteractionEvent.class;
  }
}
