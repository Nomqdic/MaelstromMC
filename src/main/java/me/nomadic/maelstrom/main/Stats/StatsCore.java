package me.nomadic.maelstrom.main.Stats;

import com.shampaggon.crackshot.events.WeaponDamageEntityEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.List;

public class StatsCore implements Listener {
    Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("MaelstromCore");
    FileConfiguration config;







    public int L1XP;



    public StatsCore(Plugin plugin) {
        this.config = this.plugin.getConfig();
        this.L1XP = this.config.getInt("BaseLevel");
        this.plugin = plugin;
    }

    public StatsCore() {
        this.config = this.plugin.getConfig();
        this.L1XP = this.config.getInt("BaseLevel");
    }



    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String UUID = player.getUniqueId().toString();
        System.out.println("Player has joined");
        if (!this.config.contains("stats." + UUID)) {
            String PStats = "stats." + UUID;
            this.config.addDefault(PStats, player.getDisplayName());
            this.config.addDefault(PStats + ".kills", 0);
            this.config.addDefault(PStats + ".level", 0);
            this.config.addDefault(PStats + ".experience", 0);
            this.config.addDefault(PStats + ".deaths", 0);
            this.config.addDefault(PStats + ".prestige", 0);



            this.config.options().copyDefaults(true);
            this.plugin.saveConfig();
        }

    }

    @EventHandler
    public void onPlayerDeathE(WeaponDamageEntityEvent event) {
        Entity p = event.getVictim();
        Entity k = event.getPlayer();
        String KUID = k.getUniqueId().toString();
        String PUID = p.getUniqueId().toString();
        int killcount = this.config.getInt("stats." + KUID + ".kills");
        int deathcount = this.config.getInt("stats." + PUID + ".deaths");
        int xpcount = this.config.getInt("stats." + KUID + ".experience");
        int lvlcount = this.config.getInt("stats." + KUID + ".level");
        int PTcount = this.config.getInt("stats." + KUID + ".prestige");

        int HS = this.config.getInt("HeadshotMultiplier");
        int LHP = this.config.getInt("LowHealthMultiplier");
        int SM = this.config.getInt("SniperMultiplier");
        int NSM = this.config.getInt("NSMMultiplier");
        int MLM = this.config.getInt("MELMultiplier");


        List<String> ASR = this.config.getStringList("WeaponClasses.ASR");
        List<String> SMG = this.config.getStringList("WeaponClasses.SMG");
        List<String> HVY = this.config.getStringList("WeaponClasses.HVY");
        List<String> SNR = this.config.getStringList("WeaponClasses.SNR");
        List<String> STG = this.config.getStringList("WeaponClasses.STG");
        List<String> MEL = this.config.getStringList("WeaponClasses.MEL");
        List<String> PIS = this.config.getStringList("WeaponClasses.PIS");


        if (p instanceof Player && ((Player)p).getPlayer().getHealth() - event.getDamage() <= 0.0D) {
            this.config.set("stats." + KUID + ".kills", killcount + 1);
            this.config.set("stats." + PUID + ".deaths", deathcount + 1);
            int XPToNextLevel = this.L1XP + (3 * lvlcount);
            if (xpcount < XPToNextLevel) {
                if (((Player)k).getPlayer().getHealth() < 4.0D) {
                    this.config.set("stats." + KUID + ".experience", xpcount + (7 * LHP));
                    System.out.println("Low HP Bonus");
                }if (SNR.contains(event.getWeaponTitle())) {
                    this.config.set("stats." + KUID + ".experience", xpcount + (7 * SM));
                    System.out.println("Sniper Bonus.");

                }if (MEL.contains(event.getWeaponTitle())) {
                this.config.set("stats." + KUID + ".experience", xpcount + (7 * MLM));

            }if(event.isHeadshot()) {
                    this.config.set("stats." + KUID + ".experience", xpcount + (7 * HS));

                } else{
                    this.config.set("stats." + KUID + ".experience", xpcount + (7 * this.config.getInt("XPMultip")));
                    System.out.println("Regular Bonus");
                }

            }

            if (xpcount >= XPToNextLevel && lvlcount != 70) {
                this.config.set("stats." + KUID + ".experience", xpcount - XPToNextLevel);
                this.config.set("stats." + KUID + ".level", lvlcount + 1);

                event.getPlayer().playSound(k.getLocation(), "block.end_portal.spawn", 1.0F, 1.0F);
                event.getPlayer().sendTitle(ChatColor.GOLD + "You have leveled up", ChatColor.GOLD + "You are now level " + ChatColor.AQUA + this.config.get("stats." + KUID + ".level"));
                event.getPlayer().setLevel(this.config.getInt("stats." + KUID + ".level"));
            }

            if (xpcount >= XPToNextLevel && lvlcount == 70) {
                this.config.set("stats." + KUID + ".prestige", PTcount + 1);
                this.config.set("stats." + KUID + ".level", 0);
                event.getPlayer().playSound(k.getLocation(), "entity.wither.spawn", 1.0F, 1.0F);
            }

            p.sendMessage(ChatColor.GOLD + "You have died, you now have " + ChatColor.AQUA + this.config.get("stats." + PUID + ".deaths") + ChatColor.GOLD + " deaths");
            k.sendMessage(ChatColor.GOLD + "You have killed " + ((Player)p).getDisplayName() + ChatColor.GOLD + ", you now have " + ChatColor.AQUA + this.config.get("stats." + KUID + ".kills") + ChatColor.GOLD + " kills");
            //this.config.options().copyDefaults(true);
            this.plugin.saveConfig();
        }

    }
}
