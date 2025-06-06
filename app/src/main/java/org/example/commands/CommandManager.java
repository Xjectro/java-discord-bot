package org.example.commands;

import java.util.HashMap;
import java.util.Map;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

public class CommandManager {
  private final Map<String, Command> commands = new HashMap<>();

  public CommandManager() {
    registerCommand(new PingCommand());
    registerCommand(new AutoRoleCommand());
  }

  public void registerCommand(Command command) {
    commands.put(command.getName(), command);
  }

  public void handleMessageCommand(MessageReceivedEvent event) {
    String[] parts = event.getMessage().getContentRaw().split("\\s+");
    if (parts.length == 0) return;

    if (!parts[0].startsWith("!")) return;

    String cmdName = parts[0].substring(1).toLowerCase();

    Command command = commands.get(cmdName);
    Member member = event.getMember();
    MessageChannel channel = event.getChannel();

    if (command != null && member != null && channel != null) {
      if (event.isFromGuild()) {
        Permission[] perms = command.getRequiredPermissions();
        if (perms.length > 0 && (member == null || !hasAllPermissions(member, perms))) {
          channel.sendMessage("You do not have permission to use this command.").queue();
          return;
        }
      } else if (command.isServerOnly()) {
        channel.sendMessage("This command can only be used within the server.").queue();
        return;
      }

      String[] args = new String[parts.length - 1];
      System.arraycopy(parts, 1, args, 0, args.length);
      command.onMessageCommand(event, args);
    }
  }

  public void handleSlashCommand(SlashCommandInteractionEvent event) {
    String cmdName = event.getName().toLowerCase();
    Command command = commands.get(cmdName);
    Member member = event.getMember();
    MessageChannel channel = event.getChannel();

    if (command != null && member != null && channel != null) {
      if (event.isFromGuild()) {
        Permission[] perms = command.getRequiredPermissions();
        if (perms.length > 0 && (member == null || !hasAllPermissions(member, perms))) {
          event.reply("You do not have permission to use this command.").setEphemeral(true).queue();
          return;
        }
      } else if (command.isServerOnly()) {
        event.reply("This command can only be used within the server.").setEphemeral(true).queue();
        return;
      }

      command.onSlashCommand(event);
    }
  }

  private boolean hasAllPermissions(Member member, Permission[] perms) {
    if (member == null) return false;
    for (var perm : perms) {
      if (!member.hasPermission(perm)) {
        return false;
      }
    }
    return true;
  }

  public void registerSlashCommands(JDA jda) {
    for (Command command : commands.values()) {
      SlashCommandData commandData = Commands.slash(command.getName(), command.getDescription());
      command.setOptions(commandData);
      System.out.println(commandData);
      jda.upsertCommand(commandData).queue();
    }
  }
}
