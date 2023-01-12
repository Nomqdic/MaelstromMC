package me.nomadic.maelstrom.main.MobSpawningStuff;

import com.shampaggon.crackshot.CSUtility;
import me.nomadic.maelstrom.main.Utils.Text;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collection;
import java.util.Random;

public class Pengorbanan implements Listener {

    Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("MaelstromCore");
    FileConfiguration config;

    public Pengorbanan() {this.config = this.plugin.getConfig();}


    private CSUtility csUtility;


    public LivingEntity closestEntity;
    @EventHandler
    public void entitySpawn(EntitySpawnEvent event) {
        Entity Eventity = event.getEntity();
        csUtility = new CSUtility();
        Random random = new Random();
        int randInt = random.nextInt(10);

        if(Eventity.getType() == EntityType.SPIDER && randInt == 3 ) {
            Spider spider = (Spider)Eventity;

            World world = spider.getWorld();

            PotionEffect effect = new PotionEffect(PotionEffectType.SPEED, 30000, 1);

            spider.setHealth(1.0);
            spider.setSilent(true);
            spider.addPotionEffect(effect);
            spider.setCustomName(Text.color("&6Pengorbanan"));
            spider.setCustomNameVisible(true);


            double lowestDistanceSoFar = Double.MAX_VALUE;
            LivingEntity closestEntity = null;
            Collection<LivingEntity> nearest = world.getNearbyLivingEntities(spider.getLocation(), 40, 40, 40, entity -> entity instanceof Player);
            for (LivingEntity entity : nearest) { // This loops through all the entities, setting the variable "entity" to each element.
                double distance = entity.getLocation().distance(spider.getLocation());
                if (distance < lowestDistanceSoFar) {
                    lowestDistanceSoFar = distance;
                    closestEntity = entity;
                }
            }
            if(closestEntity instanceof Player) {
                LivingEntity finalClosestEntity = closestEntity;
                new BukkitRunnable() {

                    @Override
                    public void run() {
                        if (spider.isDead()) {
                            cancel(); // this cancels it when they leave
                        }
                        if(finalClosestEntity != null && ((Player) finalClosestEntity).getGameMode() != GameMode.CREATIVE) {
                            spider.setTarget(finalClosestEntity);
                        }
                    }
                }.runTaskTimer(this.plugin /*<-- your plugin instance*/, 5L, 10L);




            }

        }

    }

    @EventHandler
    public void playerDamage(EntityDamageByEntityEvent event) {

        if(event.getEntity() instanceof Player) {

            Player player = (Player) event.getEntity();

            if(event.getDamager().getType() == EntityType.SPIDER && event.getDamager().getCustomName().equalsIgnoreCase(Text.color("&6Pengorbanan")))  {

                player.getWorld().createExplosion(event.getDamager().getLocation(), 1, false, false);


            }
            if(event.getDamager().getType() == EntityType.HUSK && event.getDamager().getCustomName().equalsIgnoreCase(Text.color("&7&lMontagne"))) {
                event.setDamage(event.getDamage() / 2);

            }



        }



    }



}
