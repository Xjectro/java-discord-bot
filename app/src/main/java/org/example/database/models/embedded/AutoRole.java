package org.example.database.models.embedded;

import dev.morphia.annotations.Entity;
import dev.morphia.annotations.Property;
import java.util.List;

@Entity
public class AutoRole {
  @Property("is_active")
  private boolean isActive;

  @Property("role_ids")
  private List<String> roleIds;

  public AutoRole() {}

  public AutoRole(boolean isActive, List<String> roleIds) {
    this.isActive = isActive;
    this.roleIds = roleIds;
  }

  public boolean isActive() {
    return isActive;
  }

  public void setActive(boolean active) {
    isActive = active;
  }

  public List<String> getRoleIds() {
    return roleIds;
  }

  public void setRoleIds(List<String> roleIds) {
    this.roleIds = roleIds;
  }
}
