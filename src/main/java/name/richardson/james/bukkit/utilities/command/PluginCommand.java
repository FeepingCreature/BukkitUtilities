package name.richardson.james.bukkit.utilities.command;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.permissions.Permission;

import name.richardson.james.bukkit.utilities.permissions.PermissionsHolder;
import name.richardson.james.bukkit.utilities.plugin.SimplePlugin;

// TODO: Auto-generated Javadoc
/**
 * The Class PluginCommand.
 */
public abstract class PluginCommand implements Command, PermissionsHolder {

  public static final ChatColor REQUIRED_ARGUMENT_COLOUR = ChatColor.RED;
  public static final ChatColor OPTIONAL_ARGUMENT_COLOUR = ChatColor.GREEN;

  /**
   * The resource bundle for generic messages which will be used across plugins.
   */
  private static final ResourceBundle messages = ResourceBundle.getBundle("BukkitUtilities");

  /** The plugin. */
  protected SimplePlugin plugin;

  /** The description of what this command does */
  private final String description;

  /** The name of this command */
  private final String name;

  /** The usage message for this command */
  private final String usage;

  /** The permissions associated with this command */
  private final List<Permission> permissions = new LinkedList<Permission>();

  private final Map<String, Object> arguments = new HashMap<String, Object>();

  public PluginCommand(final SimplePlugin plugin) {
    final String pathPrefix = this.getClass().getSimpleName().toLowerCase();
    this.name = plugin.getMessage(pathPrefix + "-name");
    this.description = plugin.getMessage(pathPrefix + "-description");
    this.usage = plugin.getMessage(pathPrefix + "-usage");
    this.plugin = plugin;
  }

  public void addPermission(final Permission permission) {
    this.plugin.addPermission(permission);
    this.permissions.add(permission);
  }

  /*
   * (non-Javadoc)
   * @see name.richardson.james.bukkit.utilities.command.Command#getArguments()
   */
  public Map<String, Object> getArguments() {
    return Collections.unmodifiableMap(this.arguments);
  }

  /*
   * (non-Javadoc)
   * @see
   * name.richardson.james.bukkit.util.plugin.Localisable#getMessage(java.lang
   * .String)
   */

  public String getColouredUsage() {
    String message = this.usage;
    message = this.usage.replaceAll("<", REQUIRED_ARGUMENT_COLOUR + "<");
    message = this.usage.replaceAll("[", OPTIONAL_ARGUMENT_COLOUR + "[");
    return message;
  }

  /*
   * (non-Javadoc)
   * @see
   * name.richardson.james.bukkit.utilities.command.Command#getDescription()
   */
  public String getDescription() {
    return this.description;
  }

  /*
   * (non-Javadoc)
   * @see name.richardson.james.bukkit.utilities.command.Command#getName()
   */
  public String getName() {
    return this.name;
  }

  public Permission getPermission(final int index) {
    return this.permissions.get(index);
  }

  public Permission getPermission(final String path) {
    for (final Permission permission : this.permissions) {
      if (permission.getName().equalsIgnoreCase(path)) {
        return permission;
      }
    }
    return null;
  }

  public List<Permission> getPermissions() {
    return Collections.unmodifiableList(this.permissions);
  }

  /*
   * (non-Javadoc)
   * @see name.richardson.james.bukkit.utilities.command.Command#getUsage()
   */
  public String getUsage() {
    return this.usage;
  }

  /*
   * (non-Javadoc)
   * @see
   * org.bukkit.command.CommandExecutor#onCommand(org.bukkit.command.CommandSender
   * , org.bukkit.command.Command, java.lang.String, java.lang.String[])
   */
  public boolean onCommand(final CommandSender sender, final org.bukkit.command.Command command, final String label, final String[] args) {

    // create the argument list and remove the command name from the list
    final List<String> arguments = new LinkedList<String>();
    for (final String argument : args) {
      if (!argument.equalsIgnoreCase(this.getName())) {
        arguments.add(argument);
      }
    }

    if (!this.getClass().isAnnotationPresent(ConsoleCommand.class) && (sender instanceof ConsoleCommandSender)) {
      // check if this command is available to the console.
      // if it isn't and they try to use it give them an error message.
      sender.sendMessage(ChatColor.RED + messages.getString("command-not-available-to-console"));
      return true;
    }

    for (final Permission permission : this.getPermissions()) {
      // check to see if the user has one of the permissions required to use
      // this command.
      // if they do execute the command
      if (sender.hasPermission(permission)) {
        try {
          this.parseArguments(arguments, sender);
          this.execute(sender);
        } catch (final CommandArgumentException exception) {
          sender.sendMessage(ChatColor.RED + exception.getMessage());
          sender.sendMessage(ChatColor.YELLOW + exception.getHelp());
        } catch (final CommandPermissionException exception) {
          sender.sendMessage(ChatColor.RED + messages.getString("command-not-permitted"));
          if (exception.getMessage() != null) {
            sender.sendMessage(ChatColor.YELLOW + exception.getMessage());
          }
          if (this.plugin.isDebugging()) {
            // if debugging is enabled output the permission that is required.
            sender.sendMessage(ChatColor.DARK_PURPLE + messages.getString(String.format("permission-required", exception.getPermission().getName())));
          }
        } catch (final CommandUsageException exception) {
          sender.sendMessage(ChatColor.RED + exception.getMessage());
        }
        break;
      }
    }

    return true;

  }

  public String getMessage(final String key) {
    return this.plugin.getMessage(key);
  }

  private String getSimpleFormattedMessage(final String key, final Object[] arguments) {
    return this.plugin.getSimpleFormattedMessage(key, arguments);
  }

  public String getSimpleFormattedMessage(final String key, final String argument) {
    final String[] arguments = { argument };
    return this.getSimpleFormattedMessage(key, arguments);
  }

  protected void setArguments(final Map<String, Object> arguments) {
    this.arguments.clear();
    this.arguments.putAll(arguments);
  }

}
