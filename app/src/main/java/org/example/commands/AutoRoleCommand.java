package org.example.commands;

import dev.morphia.Datastore;
import dev.morphia.query.filters.Filters;
import java.util.ArrayList;
import java.util.List;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;
import org.example.database.MongoManager;
import org.example.database.models.GuildData;
import org.example.database.models.embedded.AutoRole;

public class AutoRoleCommand implements Command {
  private void updateAutoRole(
      Guild guild,
      boolean enable,
      List<String> roleIds,
      Datastore datastore,
      Runnable onSuccess,
      Runnable onFail) {
    GuildData guildData =
        datastore.find(GuildData.class).filter(Filters.eq("id", guild.getId())).first();
    if (guildData == null) {
      guildData = new GuildData(guild.getId(), new AutoRole(false, new ArrayList<>()));
    }

    AutoRole autoRole = guildData.getAutoRole();

    if (autoRole == null) autoRole = new AutoRole();
    autoRole.setActive(enable);
    if (enable && roleIds != null && !roleIds.isEmpty()) {
      autoRole.setRoleIds(roleIds);
    }

    guildData.setAutoRole(autoRole);
    datastore.save(guildData);
    if (onSuccess != null) onSuccess.run();
  }

  @Override
  public void onMessageCommand(MessageReceivedEvent event, String[] args) {
    if (args.length < 1) {
      event
          .getChannel()
          .sendMessage("Usage: !autorole <enabled|disabled> [@role @role2 ...]")
          .queue();
      return;
    }

    Guild guild = event.getGuild();
    Datastore datastore = MongoManager.getDatastore();

    String status = args[0].toLowerCase();
    boolean enable = "enabled".equalsIgnoreCase(status);

    List<String> roleIds = new ArrayList<>();
    if (enable && args.length > 1) {
      for (int i = 1; i < args.length; i++) {
        String mention = args[i];
        if (mention.matches("<@&\\d+>")) {
          String id = mention.replaceAll("[^\\d]", "");
          roleIds.add(id);
        } else {
          Role role = guild.getRolesByName(mention, true).stream().findFirst().orElse(null);
          if (role != null) roleIds.add(role.getId());
        }
      }
    }

    updateAutoRole(
        guild,
        enable,
        roleIds,
        datastore,
        () ->
            event
                .getChannel()
                .sendMessage(enable ? "Auto role enabled." : "Auto role has been disabled.")
                .queue(),
        () -> event.getChannel().sendMessage("Failed to update auto role.").queue());
  }

  @Override
  public void onSlashCommand(SlashCommandInteractionEvent event) {
    Guild guild = event.getGuild();
    Datastore datastore = MongoManager.getDatastore();
    if (guild == null) return;

    OptionMapping statusOption = event.getOption("status");
    OptionMapping rolesOption = event.getOption("roles");

    if (statusOption == null) {
      event
          .reply("Usage: /autorole status:<enabled|disabled> @role1,@role2 ...")
          .setEphemeral(true)
          .queue();
      return;
    }

    String status = statusOption.getAsString().toLowerCase();
    boolean enable = "enabled".equalsIgnoreCase(status);

    List<String> roleIds = new ArrayList<>();
    if (enable && rolesOption != null && !rolesOption.getAsString().isBlank()) {
      String[] roleMentions = rolesOption.getAsString().split(",");
      for (String mention : roleMentions) {
        mention = mention.trim();
        if (mention.matches("<@&\\d+>")) {
          String id = mention.replaceAll("[^\\d]", "");
          roleIds.add(id);
        } else {
          Role role = guild.getRolesByName(mention, true).stream().findFirst().orElse(null);
          if (role != null) roleIds.add(role.getId());
        }
      }
    }

    if (enable && roleIds.isEmpty()) {
      event
          .reply("No valid roles provided. Please mention roles like @role1, @role2, ...")
          .setEphemeral(true)
          .queue();
      return;
    }

    updateAutoRole(
        guild,
        enable,
        roleIds,
        datastore,
        () ->
            event
                .reply(enable ? "Auto role enabled." : "Auto role has been disabled.")
                .setEphemeral(true)
                .queue(),
        () -> event.reply("Failed to update auto role.").setEphemeral(true).queue());
  }

  @Override
  public String getName() {
    return "autorole";
  }

  @Override
  public String getDescription() {
    return "Turn on/off auto role system and set role";
  }

  @Override
  public Permission[] getRequiredPermissions() {
    return new Permission[] {Permission.MANAGE_ROLES};
  }

  @Override
  public boolean isServerOnly() {
    return true;
  }

  @Override
  public void setOptions(SlashCommandData commandData) {
    OptionData statusOption =
        new OptionData(OptionType.STRING, "status", "Set auto role status (enabled/disabled)", true)
            .addChoice("enabled", "enabled")
            .addChoice("disabled", "disabled");

    OptionData rolesOption =
        new OptionData(
            OptionType.STRING,
            "roles",
            "Comma separated role mentions (@role1, @role2, ...)",
            false);

    commandData.addOptions(statusOption, rolesOption);
  }
}
