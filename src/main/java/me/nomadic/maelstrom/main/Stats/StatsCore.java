package me.nomadic.maelstrom.main.Stats;

import com.shampaggon.crackshot.events.WeaponDamageEntityEvent;
import me.nomadic.maelstrom.main.Utils.Text;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentBuilder;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
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
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.List;
import java.util.Random;

public class StatsCore implements Listener {
    Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("MaelstromCore");
    FileConfiguration config;

    public int L1XP;
    public int XPToNextLevel;

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
        String PStats = "stats." + UUID;
        System.out.println("Player has joined");
        if (!this.config.contains("stats." + UUID)) {


            this.config.addDefault(PStats, player.getDisplayName());
            this.config.addDefault(PStats + ".kills", 0);
            this.config.addDefault(PStats + ".level", 0);
            this.config.addDefault(PStats + ".experience", 0);
            this.config.addDefault(PStats + ".deaths", 0);
            this.config.addDefault(PStats + ".prestige", 0);
            this.config.getStringList(PStats + ".achievements" );
            this.config.addDefault(PStats + ".prestigeIcon", "✦");
            this.config.addDefault(PStats + ".coins", 100.0);



            this.config.options().copyDefaults(true);
            this.plugin.saveConfig();
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!player.isOnline()) {
                    cancel(); // this cancels it when they leave
                }

                Component component = Component.text(Text.color("&6★: &b " + config.getInt(PStats + ".level") + "  &c❤:&2&l " + Math.round(player.getHealth())));
                player.sendActionBar(component);
            }
        }.runTaskTimer(this.plugin /*<-- your plugin instance*/, 5L, 5L);


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



        Random random = new Random();




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
            int plevel = config.getInt("stats." + KUID + ".level");

            if(config.getInt("stats." + KUID + ".level") <= 10) {
                 XPToNextLevel = config.getInt("stats." + KUID + ".level")^2 + 6 * config.getInt("stats." + KUID + ".level");
            }else if(config.getInt("stats." + KUID + ".level") <= 30) {

                 XPToNextLevel = 3 * plevel^2 - 20 * plevel + 360;
            }else if(config.getInt("stats." + KUID + ".level") >= 50) {

                 XPToNextLevel = 5 * plevel^2 - 80 * plevel + 2220;
            }



                if (((Player)k).getPlayer().getHealth() < 4.0D) {
                    //Low health bonus
                    this.config.set("stats." + KUID + ".experience", xpcount + (random.nextInt(10) * LHP));

                }if (SNR.contains(event.getWeaponTitle())) {
                    //sniper bonus
                    this.config.set("stats." + KUID + ".experience", xpcount + (random.nextInt(10) * SM));


                }if (MEL.contains(event.getWeaponTitle())) {
                    //melee bonus
                this.config.set("stats." + KUID + ".experience", xpcount + (random.nextInt(10) * MLM));

            }if(event.isHeadshot()) {
                    //headshot bonus
                    this.config.set("stats." + KUID + ".experience", xpcount + (random.nextInt(10) * HS));

                } else{
                    //normal bonus
                    this.config.set("stats." + KUID + ".experience", xpcount + (random.nextInt(10) * this.config.getInt("XPMultip")));

                }
            xpcount = this.config.getInt("stats." + KUID + ".experience");




            if (xpcount >= XPToNextLevel && lvlcount != 10) {
                //regular level up
                this.config.set("stats." + KUID + ".experience", xpcount - XPToNextLevel);
                this.config.set("stats." + KUID + ".level", lvlcount + 1);

                event.getPlayer().playSound(k.getLocation(), "entity.player.levelup", 1.0F, 1.0F);
                event.getPlayer().sendActionBar(ChatColor.GOLD + "You have leveled up to" + ChatColor.AQUA + this.config.get("stats." + KUID + ".level"));
                event.getPlayer().setLevel(this.config.getInt("stats." + KUID + ".level"));
            }

            if (xpcount >= XPToNextLevel && lvlcount == 10) {
                //prestige
                this.config.set("stats." + KUID + ".prestige", PTcount + 1);
                this.config.set("stats." + KUID + ".experience", xpcount - XPToNextLevel );
                this.config.set("stats." + KUID + ".level", lvlcount + 1);

                event.getPlayer().playSound(k.getLocation(), "block.end_portal.spawn", 1.0F, 1.0F);
            }

            p.sendMessage(ChatColor.GOLD + "You have died, you now have " + ChatColor.AQUA + this.config.get("stats." + PUID + ".deaths") + ChatColor.GOLD + " deaths");
            k.sendMessage(ChatColor.GOLD + "You have killed " + ((Player)p).getDisplayName() + ChatColor.GOLD + ", you now have " + ChatColor.AQUA + this.config.get("stats." + KUID + ".kills") + ChatColor.GOLD + " kills");
            //this.config.options().copyDefaults(true);
            this.plugin.saveConfig();
        }

    }
}
