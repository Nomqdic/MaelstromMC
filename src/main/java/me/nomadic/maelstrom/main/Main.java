package me.nomadic.maelstrom.main;

import me.nomadic.maelstrom.main.Achievements.AchievementHandler;
import me.nomadic.maelstrom.main.Commands.maelstrom;
import me.nomadic.maelstrom.main.Commands.newStats;
import me.nomadic.maelstrom.main.Commands.prestigeIconSelection;
import me.nomadic.maelstrom.main.MobSpawningStuff.Montagne;
import me.nomadic.maelstrom.main.MobSpawningStuff.Pengorbanan;
import me.nomadic.maelstrom.main.Stats.ChatStuff;
import me.nomadic.maelstrom.main.Stats.StatsCore;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import com.shampaggon.crackshot.CSUtility;

import java.io.File;

public class Main extends JavaPlugin {
    
    //Config stuff
    Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("MaelstromCore");
    FileConfiguration config = this.getConfig();

    public Main() {
    }

    private CSUtility csUtility;







    public void onEnable() {


        csUtility = new CSUtility();

        //load the base game and Basic String Vars
        this.saveDefaultConfig();
        this.getConfig();
        //Experience
        config.addDefault("BaseLevel", 30);
        config.addDefault("XPMultip", 1);
        config.addDefault("Prefix", "&6&lMaelstrom");


        //Multipliers
        config.addDefault("HeadshotMultiplier", 1.5);
        config.addDefault("NoScopeMultiplier", 2.0);
        config.addDefault("MeleeMultiplier", 1.75);
        config.addDefault("SniperMultiplier", 2.5);
        config.addDefault("LowHealthMultiplier", 1.25);


        //Weapon Class Assignment
        config.addDefault("WeaponClasses","");
        config.getStringList("WeaponClasses.ASR");
        config.getStringList("WeaponClasses.SMG");
        config.getStringList("WeaponClasses.HVY");
        config.getStringList("WeaponClasses.SNR");
        config.getStringList("WeaponClasses.STG");
        config.getStringList("WeaponClasses.MEL");
        config.getStringList("WeaponClasses.PIS");

        //MySQL Stuff
        config.addDefault("MySQLEnabled", false);
        config.addDefault("SQL.host", "localhost");
        config.addDefault("SQL.database", "exampleDatabase");
        config.addDefault("SQL.username", "root");
        config.addDefault("SQL.password", "examplePassword");
        config.addDefault("SQL.port", 3306);

        //Achievements... yaaaay
        FileConfiguration data = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "achievements.yml"));

        data.getStringList("Achievements");

        config.options().copyDefaults(true);
        this.saveConfig();
        
        //Register Commands
        this.getCommand("stats").setExecutor(new newStats());
        this.getCommand("maelstrom").setExecutor(new maelstrom());
        this.getCommand("prestigeIcon").setExecutor(new prestigeIconSelection());

        if(config.getBoolean("MySQLEnabled")) {

            return;
        }

        //register event listeners
        Bukkit.getServer().getPluginManager().registerEvents(new StatsCore(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new ChatStuff(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new AchievementHandler(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new Pengorbanan(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new Montagne(), this);


    }
    public CSUtility getCrackshot() { return csUtility; }

    public void onDisable() {

    }
}
