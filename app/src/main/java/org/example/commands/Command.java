package org.example.commands;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

public interface Command {
  void onMessageCommand(MessageReceivedEvent event, String[] args);

  void onSlashCommand(SlashCommandInteractionEvent event);

  String getName();

  String getDescription();

  default Permission[] getRequiredPermissions() {
    return new Permission[0];
  }

  default boolean isServerOnly() {
    return false;
  }

  default void setOptions(SlashCommandData commandData) {}
}
