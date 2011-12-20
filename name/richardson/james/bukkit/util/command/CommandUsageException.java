/*******************************************************************************
 * Copyright (c) 2011 James Richardson.
 * 
 * CommandUsageException.java is part of BukkitUtilities.
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

public class CommandUsageException extends Exception {

  private static final long serialVersionUID = 8443259919961526754L;

  private final String message;
  private final String usage;

  public CommandUsageException(final String message, final String usage) {
    this.message = message;
    this.usage = usage;
  }

  @Override
  public String getMessage() {
    return this.message;
  }

  public String getUsage() {
    return this.usage;
  }

}
