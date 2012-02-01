/*******************************************************************************
 * Copyright (c) 2011 James Richardson.
 * 
 * CommandPermissionException.java is part of BukkitUtilities.
 * 
 * BukkitUtilities is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * BukkitUtilities is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU General Public License along with
 * BukkitUtilities. If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package name.richardson.james.bukkit.util.command;

import org.bukkit.permissions.Permission;

public class CommandPermissionException extends Exception {

  private static final long serialVersionUID = 4498180605868829834L;

  private String message;
  private Permission permission;

  public CommandPermissionException(final String message, final Permission permission) {
    this.setMessage(message);
    this.setPermission(permission);
  }

  @Override
  public String getMessage() {
    return this.message;
  }

  public Permission getPermission() {
    return this.permission;
  }

  private void setMessage(final String message) {
    this.message = message;
  }

  private void setPermission(final Permission permission) {
    this.permission = permission;
  }

}