package org.example.events;

import dev.morphia.Datastore;
import dev.morphia.query.filters.Filters;
import java.util.List;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import org.example.database.MongoManager;
import org.example.database.models.GuildData;
import org.example.database.models.embedded.AutoRole;

public class GuildMemberJoin implements TypedListener<GuildMemberJoinEvent> {
  @Override
  public void accept(GuildMemberJoinEvent event) {
    Guild guild = event.getGuild();
    Member member = event.getMember();
    Datastore datastore = MongoManager.getDatastore();

    if (guild == null || member == null || datastore == null) return;

    GuildData guildData =
        datastore.find(GuildData.class).filter(Filters.eq("id", guild.getId())).first();

    AutoRole autoRole = (guildData != null) ? guildData.getAutoRole() : null;

    if (autoRole != null && autoRole.isActive()) {
      List<String> roleIds = autoRole.getRoleIds();
      if (roleIds != null) {
        for (String roleId : roleIds) {
          if (!roleId.matches("\\d{10,}")) continue;

          Role role = guild.getRoleById(roleId);
          if (role != null && guild.getSelfMember().canInteract(role)) {
            guild.addRoleToMember(member, role).queue();
          }
        }
      }
    }
  }

  @Override
  public Class<GuildMemberJoinEvent> getEventType() {
    return GuildMemberJoinEvent.class;
  }
}
