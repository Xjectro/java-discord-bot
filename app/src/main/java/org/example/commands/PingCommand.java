package org.example.commands;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class PingCommand implements Command {

  @Override
  public void onMessageCommand(MessageReceivedEvent event, String[] args) {
    event.getChannel().sendMessage("Pong! 🏓").queue();
  }

  @Override
  public void onSlashCommand(SlashCommandInteractionEvent event) {
    event.reply("Pong! 🏓").queue();
  }

  @Override
  public String getName() {
    return "ping";
  }

  @Override
  public String getDescription() {
    return "Sends pong.";
  }
}
