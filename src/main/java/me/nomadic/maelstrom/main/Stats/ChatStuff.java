package me.nomadic.maelstrom.main.Stats;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.nomadic.maelstrom.main.Utils.Text;
import me.nomadic.maelstrom.main.Utils.ConfigurationFile;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;

import java.util.Objects;

import static net.kyori.adventure.text.format.NamedTextColor.DARK_RED;

public class ChatStuff implements Listener {
    Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("MaelstromCore");
    FileConfiguration config;
    public ChatStuff() {
        assert this.plugin != null;
        this.config = this.plugin.getConfig();
    }

    @EventHandler
    public void ChatEvent(AsyncChatEvent event) {

        String UUID = event.getPlayer().getUniqueId().toString();
        Player player = event.getPlayer();
        try {
            String level = Objects.requireNonNull(config.get("stats." + UUID + ".level")).toString();
            Integer prestige = config.getInt("stats." + UUID + ".prestige");
            String message = event.message().insertion();



            event.setCancelled(true);

            if (player.hasPermission("maelstrom.coloredchat") || player.isOp() || prestige >= 5) {
                Bukkit.broadcastMessage(ChatColor.GRAY + "[" + level + Text.color(config.getString("stats." + UUID + ".prestigeIcon")) + ChatColor.GRAY+ "] " + event.getPlayer().getDisplayName() + ": " + ChatColor.WHITE + Text.color(message));


            }else {
                Bukkit.broadcastMessage(ChatColor.GRAY + "[" + level + Text.color(config.getString("stats." + UUID + ".prestigeIcon"))  + ChatColor.GRAY+ "] " + event.getPlayer().getDisplayName() + ": " + ChatColor.WHITE + message);
            }
        }catch(Exception e) {

            for(Player operators : Bukkit.getOnlinePlayers()){
                if(operators.isOp()) {
                    Component exception = Component.text(Text.color("&b" + player.getName() + " did not have a valid stats save."));
                    Bukkit.broadcast(exception);
                }else{
                    return;
                }


            }

        }







    }

}
