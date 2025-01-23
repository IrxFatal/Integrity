package dev.irxfatal.integrity.commands;

import dev.irxfatal.integrity.IntegrityPlugin;
import dev.irxfatal.integrity.util.HexColorUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class IFreezeCommand implements CommandExecutor {

    private final IntegrityPlugin plugin;

    public IFreezeCommand(IntegrityPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player) || args.length != 1) {
            sender.sendMessage(HexColorUtil.hex(plugin.translate("usage_sfreeze")));
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(HexColorUtil.hex(plugin.translate("player_not_found")));
            return true;
        }

        plugin.getFrozenPlayers().put(target.getUniqueId(), target.getLocation());
        target.teleport(plugin.getFreezeLocation());
        target.setWalkSpeed(0f);
        target.setFlySpeed(0f);
        sender.sendMessage(HexColorUtil.hex(plugin.translate("player_frozen").replace("{player}", target.getName()).replace("{mod}", sender.getName())));
        target.sendMessage(HexColorUtil.hex(plugin.getFreezeMessage()));

        return true;
    }
}
