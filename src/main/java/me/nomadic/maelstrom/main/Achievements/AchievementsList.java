package me.nomadic.maelstrom.main.Achievements;

import me.nomadic.maelstrom.main.Utils.Text;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.Map;

public class AchievementsList {
    Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("MaelstromCore");
    FileConfiguration config;
    private Object ListOfAchievements;
    private AchievementsList() {}


    public enum ListOfAchievements{
        Slayer(Text.color("&3&lSlayer")), //50 kills
        UltraSlayer(Text.color("&c&lUltra-Slayer")), //150 kills
        Genocidal(Text.color("&0&lGenocide Syndrome")), //250 kills
        DoomSlayer(Text.color("&4&lHellwalker")), //1993 kills Easter-egg
        Killionaire(Text.color("&3&kkill &6&l&oKillionaire &3&kkill")), //500 kills top tier
        Headhunter(Text.color("&3&lHeadhunter")), //50 headshot kills
        Crosshair(Text.color("&4&lCrosshair")), //99 headshot kills
        Helmetbane(Text.color("&c&lHelmetbane")), //150 headshot kills
        Aimbot(Text.color("&0&lAimbot?")), // 250 headshot kills
        BrainSurgery(Text.color("&3&khead &6&l&oBrain Surgeon &3&khead"));



        private final String desc;

        ListOfAchievements(String desc) {
            this.desc =  desc;

        }
    }

    Map<Player, ListOfAchievements> a = new HashMap<>();





    public void addAward(Player player, ListOfAchievements achievement) {

        a.put(player, achievement);
    }
    public void saveAchievements (Player player) {

        config.set("stats." + player.getUniqueId() + "achievements", a.get(ListOfAchievements));
        
    }













}
