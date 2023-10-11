package fr.kotlini.exercise.command;

import com.google.inject.Inject;
import fr.kotlini.exercise.model.PlayerInfos;
import fr.kotlini.exercise.service.PlayerService;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class PlayerCommand implements CommandExecutor, TabCompleter
{
    private final PlayerService service;

    @Inject
    public PlayerCommand(PlayerService service) {
        this.service = service;
    }

    @Override
    public boolean onCommand(@NonNull CommandSender sender, @NonNull Command command, @NonNull String label, @Nullable String[] args) {
        if (!(sender instanceof Player) || args == null || args.length == 0) return false;

        final UUID uuid = ((Player) sender).getUniqueId();
        if (service.hasCachePlayer(uuid)) {
            sendMessage(sender, service.getCachePlayer(uuid));
            return true;
        }

        service.getOrCreate(uuid, sender.getName()).thenAccept(playerInfos -> {
            sendMessage(sender, playerInfos);
            service.getPlayerCache().put(uuid, playerInfos);
        }).exceptionally(t -> {
            Logger.getLogger("minecraft").log(Level.SEVERE, "An internal error ;)", t);
            return null;
        });
        return true;
    }

    public void sendMessage(CommandSender sender, PlayerInfos playerInfos) {
        sender.sendMessage("§6Hey §a§l" + playerInfos.getName() + "!\n§fGrade: §c" + playerInfos.getGrade() + "\n§fMoney: §c" +
                playerInfos.getMoney() + "\n§fAge: §c" + playerInfos.getAge());
    }

    @Override
    @SuppressWarnings("NullableProblems")
    public List<String> onTabComplete(@NonNull CommandSender sender, @NonNull Command command, @NonNull String alias, String[] args) {
        if (!(sender instanceof Player) || args.length == 0) return null;

        return Bukkit.getOnlinePlayers().stream().map(HumanEntity::getName).filter(name -> name.startsWith(args[0])).
                collect(Collectors.toList());
    }
}