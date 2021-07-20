package me.nomadic.maelstrom.main;

import me.nomadic.maelstrom.main.Commands.stats;
import me.nomadic.maelstrom.main.Stats.ChatStuff;
import me.nomadic.maelstrom.main.Stats.StatsCore;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.List;
import java.util.Optional;

public class Main extends JavaPlugin {
    
    //Config stuff
    Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("MaelstromCore");
    FileConfiguration config = this.getConfig();

    public Main() {
    }

    public void onEnable() {
        



        //load the base game and Basic String Vars
        this.saveDefaultConfig();
        this.getConfig();
        //Experience
        this.config.addDefault("BaseLevel", 30);
        this.config.addDefault("XPMultip", 1);
        this.config.addDefault("Prefix", "&6&lMaelstrom");


        //Multipliers
        this.config.addDefault("HeadshotMultiplier", 1.5);
        this.config.addDefault("NoScopeMultiplier", 2.0);
        this.config.addDefault("MeleeMultiplier", 1.75);
        this.config.addDefault("SniperMultiplier", 2.5);
        this.config.addDefault("LowHealthMultiplier", 1.25);


        //Weapon Class Assignment
        this.config.addDefault("WeaponClasses","");
        this.config.getStringList("WeaponClasses.ASR");
        this.config.getStringList("WeaponClasses.SMG");
        this.config.getStringList("WeaponClasses.HVY");
        this.config.getStringList("WeaponClasses.SNR");
        this.config.getStringList("WeaponClasses.STG");
        this.config.getStringList("WeaponClasses.MEL");
        this.config.getStringList("WeaponClasses.PIS");



        this.config.options().copyDefaults(true);
        this.saveConfig();
        
        //Register Commands
        this.getCommand("stats").setExecutor(new stats());




        //register event listeners
        Bukkit.getServer().getPluginManager().registerEvents(new StatsCore(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new ChatStuff(), this);
        




        






    }

    public void onDisable() {

    }
}
