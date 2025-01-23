package dev.irxfatal.integrity.commands;

import dev.irxfatal.integrity.IntegrityPlugin;
import dev.irxfatal.integrity.util.HexColorUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class IUnfreezeCommand implements CommandExecutor {

    private final IntegrityPlugin plugin;

    public IUnfreezeCommand(IntegrityPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(HexColorUtil.hex(plugin.translate("usage_sunfreeze")));
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(HexColorUtil.hex(plugin.translate("player_not_found")));
            return true;
        }

        if (plugin.getFrozenPlayers().get(target.getUniqueId()) != null) {
            Location initialLocation = plugin.getFrozenPlayers().get(target.getUniqueId());
            target.teleport(initialLocation);
            target.setWalkSpeed(0.2f);
            target.setFlySpeed(0.3f);
            sender.sendMessage(HexColorUtil.hex(plugin.translate("player_unfrozen").replace("{player}", target.getName())));
            target.sendMessage(HexColorUtil.hex(plugin.getUnfreezeMessage()));
            plugin.getFrozenPlayers().remove(target.getUniqueId());
        }

        return true;
    }
}
