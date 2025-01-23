package dev.irxfatal.integrity;

import dev.irxfatal.integrity.commands.IFreezeCommand;
import dev.irxfatal.integrity.commands.IUnfreezeCommand;
import dev.irxfatal.integrity.listeners.ChatListener;
import dev.irxfatal.integrity.listeners.PlayerQuitListener;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class IntegrityPlugin extends JavaPlugin {

    private Location freezeLocation;
    private List<String> bannedWords;
    private String behaviorBanCommand;
    private String leftBanCommand;
    private String freezeMessage;
    private String unfreezeMessage;
    private HashMap<UUID, Location> frozenPlayers = new HashMap<>();
    private String language;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        loadConfigValues();

        getCommand("ifreeze").setExecutor(new IFreezeCommand(this));
        getCommand("iunfreeze").setExecutor(new IUnfreezeCommand(this));
        Bukkit.getPluginManager().registerEvents(new ChatListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerQuitListener(this), this);

    }

    private void loadConfigValues() {
        freezeLocation = new Location(
                Bukkit.getWorld(getConfig().getString("freeze-location.world")),
                getConfig().getDouble("freeze-location.x"),
                getConfig().getDouble("freeze-location.y"),
                getConfig().getDouble("freeze-location.z")
        );
        bannedWords = getConfig().getStringList("banned-words");
        behaviorBanCommand = getConfig().getString("behavior-ban-command");
        leftBanCommand = getConfig().getString("left-ban-command");
        freezeMessage = getConfig().getString("freeze");
        language = getConfig().getString("language");
        unfreezeMessage = getConfig().getString("unfreeze");
    }

    public Location getFreezeLocation() {
        return freezeLocation;
    }

    public List<String> getBannedWords() {
        return bannedWords;
    }

    public String getBehaviorBanCommand() {
        return behaviorBanCommand;
    }

    public String getLeftBanCommand() {
        return leftBanCommand;
    }


    public String getUnfreezeMessage() {
        return unfreezeMessage;
    }

    public String getFreezeMessage() {
        return freezeMessage;
    }

    public HashMap<UUID, Location> getFrozenPlayers() {
        return frozenPlayers;
    }

    public String translate(String key) {
        return getConfig().getString("messages." + language + "." + key);
    }
}
