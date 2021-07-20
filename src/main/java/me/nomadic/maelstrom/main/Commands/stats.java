package me.nomadic.maelstrom.main.Commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class stats implements CommandExecutor {
    //Config Stuff
    Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("MaelstromCore");
    FileConfiguration config;

    public stats() {
        this.config = this.plugin.getConfig();
    }

    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        //Check if sender is player
        if (sender instanceof Player) {
            //variable assignments
            Player player = (Player)sender;
            String tUUID;
            if (args.length == 0) {
                //Shows the executor's stats
                String UUID = player.getUniqueId().toString();
                tUUID = "stats." + UUID;
                //this.plugin.reloadConfig();
                player.sendMessage(ChatColor.GOLD + "Your total kills are: " + ChatColor.AQUA + this.config.getInt(tUUID + ".kills"));
                player.sendMessage(ChatColor.GOLD + "Your level is: " + ChatColor.AQUA + this.config.getInt(tUUID + ".level"));
                player.sendMessage(ChatColor.GOLD + "Your experience: " + ChatColor.AQUA + this.config.getInt(tUUID + ".experience"));
                player.sendMessage(ChatColor.GOLD + "Your total deaths are: " + ChatColor.AQUA + this.config.getInt(tUUID + ".deaths"));
                player.sendMessage(ChatColor.GOLD + "Your Prestige is: " + ChatColor.AQUA + this.config.getInt(tUUID + ".prestige"));
                return true;
            } else if (args.length > 0) {

                if (Bukkit.getPlayer(args[0]) != null) {
                    //First checks to see if a player is online, and if so, tries to fetch their stats
                    Player target = Bukkit.getPlayer(args[0]);
                    tUUID = target.getUniqueId().toString();
                    String tStats = "stats." + tUUID;
                    if (config.contains(tStats)) {
                        player.sendMessage(ChatColor.GOLD + target.getName() + "'s total kills are " + ChatColor.AQUA + this.config.getInt(tStats + ".kills"));
                        player.sendMessage(ChatColor.GOLD + target.getName() + "'s level is " + ChatColor.AQUA + this.config.getInt(tStats + ".level"));
                        player.sendMessage(ChatColor.GOLD + target.getName() + "'s experience " + ChatColor.AQUA + this.config.getInt(tStats + ".experience"));
                        player.sendMessage(ChatColor.GOLD + target.getName() + "'s total deaths are " + ChatColor.AQUA + this.config.getInt(tStats + ".deaths"));
                        player.sendMessage(ChatColor.GOLD + target.getName() + "'s prestige is " + ChatColor.AQUA + this.config.getInt(tStats + ".prestige"));
                        return true;
                    }
                    return true;


                } else {
                    sender.sendMessage(ChatColor.RED + "You must specify an online player.");
                    return true;
                }
            } else {
                System.out.println("Only players can use this command");
                return true;
            }
        } else {
            return true;
        }
    }
}
