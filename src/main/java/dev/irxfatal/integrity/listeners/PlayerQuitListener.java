package dev.irxfatal.integrity.listeners;

import dev.irxfatal.integrity.IntegrityPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    private final IntegrityPlugin plugin;

    public PlayerQuitListener(IntegrityPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        if (plugin.getFrozenPlayers().containsKey(player.getUniqueId())) {
            String banCommand = plugin.getLeftBanCommand().replace("{player}", player.getName());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), banCommand);
            plugin.getFrozenPlayers().remove(player.getUniqueId());
        }
    }
}
