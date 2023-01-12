package me.nomadic.maelstrom.main.Achievements;

import com.shampaggon.crackshot.events.WeaponDamageEntityEvent;
import me.nomadic.maelstrom.main.Utils.Text;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class AchievementHandler implements Listener {
    Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("MaelstromCore");
    FileConfiguration config;
    public AchievementHandler() {
        this.config = this.plugin.getConfig();
    }
     

    //List<String> achievements = this.config.getStringList("Achievements");
    /*public void giveAchievement(Player player, String Achievement) {





    }*/




    @EventHandler
    public void onPlayerDeathE(WeaponDamageEntityEvent event) {
        Entity p = event.getVictim();
        Player k = event.getPlayer();
        String KUID = k.getUniqueId().toString();
        String PUID = p.getUniqueId().toString();
        int killcount = this.config.getInt("stats." + KUID + ".kills");
        int deathcount = this.config.getInt("stats." + PUID + ".deaths");
        int xpcount = this.config.getInt("stats." + KUID + ".experience");
        int lvlcount = this.config.getInt("stats." + KUID + ".level");
        int PTcount = this.config.getInt("stats." + KUID + ".prestige");
        List<String> ACH = this.config.getStringList("stats." + KUID + ".achievements");


        if (p instanceof Player && ((Player)p).getPlayer().getHealth() - event.getDamage() <= 0.0D) {
            if(killcount == 50) {

                Text.applyTextWithHover(k, Text.color("&6You unlocked &3&lSlayer!"), Text.color("&7Hmm, a bit of killing never hurt anyone did it?"));
                ACH.add("Slayer"); //♫


            }else if(killcount == 150) {
                ACH.add("UltraSlayer"); //☠
                Text.applyTextWithHover(k, Text.color("&6You have unlocked &c&lUltra-Slayer!"), Text.color("&7Oh yeah, it's all coming together"));

            }else if(killcount == 250) {

                ACH.add("Genocidal"); //☮
                Text.applyTextWithHover(k, Text.color("&6You have unlocked &0&lGenocide Syndrome"), Text.color("&7You're just addicted to killing people aren't you?"));

            } else if (killcount == 1993) {

                ACH.add("Hellwalker"); //Ω
                Bukkit.broadcastMessage(Text.color("&4&l" + k.getName() + " &2&lhas achieved the status of the &4&lHellwalker"));

            }else if(killcount == 500) {
                ACH.add("Killionaire"); //☢
                Bukkit.broadcastMessage(Text.color("&6&l" + k.getName() + "&7&l has gotten &3&kkill &6&l&oKillionaire &3&kkill"));
            }

            }
        this.plugin.saveConfig();




        }


}
