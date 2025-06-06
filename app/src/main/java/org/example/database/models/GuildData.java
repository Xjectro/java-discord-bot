package org.example.database.models;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Id;
import dev.morphia.annotations.Property;
import org.example.database.models.embedded.AutoRole;

@Entity("guilds")
public class GuildData {
  @Id private String id;

  @Property("auto_role")
  private AutoRole autoRole;

  public GuildData() {}

  public GuildData(String id, AutoRole autoRole) {
    this.id = id;
    this.autoRole = autoRole;
  }

  public String getGuildId() {
    return id;
  }

  public void setGuildId(String id) {
    this.id = id;
  }

  public AutoRole getAutoRole() {
    return autoRole;
  }

  public void setAutoRole(AutoRole autoRole) {
    this.autoRole = autoRole;
  }
}
