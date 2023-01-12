package me.nomadic.maelstrom.main.Commands;

import me.nomadic.maelstrom.main.Utils.Text;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Objects;

public class maelstrom implements CommandExecutor {
    Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("MaelstromCore");
    FileConfiguration config;



    public maelstrom() {
        this.config = this.plugin.getConfig();
    }





    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        String prefix = ChatColor.translateAlternateColorCodes('&', (Objects.requireNonNull(config.getString("Prefix"))));
        if (args.length > 0) {
            if(args[0].equalsIgnoreCase("reload") ) {

                sender.sendMessage(prefix + Text.color(" &7Reloaded Config"));
                plugin.reloadConfig();
                return true;
            }else if(args[0].equalsIgnoreCase("info")) {
                sender.sendMessage(prefix + ChatColor.GRAY + " running version 1.0");
                sender.sendMessage(ChatColor.GOLD + "Created by " + ChatColor.AQUA + "Nomqdic_");
            }
        }

        return true;
    }
}
