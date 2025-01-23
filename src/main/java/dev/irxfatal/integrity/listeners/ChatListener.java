package dev.irxfatal.integrity.listeners;

import dev.irxfatal.integrity.IntegrityPlugin;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerCommandSendEvent;

public class ChatListener implements Listener {

    private final IntegrityPlugin plugin;

    public ChatListener(IntegrityPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (!plugin.getFrozenPlayers().containsKey(player.getUniqueId())) {
            return;
        }

        String message = event.getMessage().toLowerCase();
        for (String word : plugin.getBannedWords()) {
            if (message.contains(word.toLowerCase())) {
                Bukkit.getScheduler().runTask(plugin, () -> {
                    String banCommand = plugin.getBehaviorBanCommand().replace("{player}", player.getName());
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), banCommand);
                });
                event.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        if (!plugin.getFrozenPlayers().containsKey(player.getUniqueId())) {
            return;
        }

        event.setCancelled(true);
    }
}
