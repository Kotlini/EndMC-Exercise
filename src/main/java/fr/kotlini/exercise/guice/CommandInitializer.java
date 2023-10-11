package fr.kotlini.exercise.guice;

import com.google.inject.Inject;
import fr.kotlini.exercise.command.PlayerCommand;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
@NoArgsConstructor
@SuppressWarnings("unused")
public class CommandInitializer
{
    @Inject
    private JavaPlugin plugin;

    @Inject
    private PlayerCommand command;

    public CommandInitializer(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void init() {
        final PluginCommand pluginCommand = plugin.getCommand("player");
        if (pluginCommand == null) {
            // error
            return;
        }

        pluginCommand.setExecutor(command);
        pluginCommand.setTabCompleter(command);
    }
}