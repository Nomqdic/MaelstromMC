package me.nomadic.maelstrom.main.Stats;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;

import java.util.Objects;

public class ChatStuff implements Listener {
    Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("MaelstromCore");
    FileConfiguration config;


    public ChatStuff() {
        assert this.plugin != null;
        this.config = this.plugin.getConfig();
    }



    @EventHandler
    public void ChatEvent(AsyncPlayerChatEvent event) {

        String UUID = event.getPlayer().getUniqueId().toString();

        String level = Objects.requireNonNull(config.get("stats." + UUID + ".level")).toString();
        String message = event.getMessage();

        event.setCancelled(true);

        Bukkit.broadcastMessage(ChatColor.GRAY + "[" + level + "] " + event.getPlayer().getDisplayName() + " " + ChatColor.WHITE + message);



    }

}
