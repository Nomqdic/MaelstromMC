package me.nomadic.maelstrom.main.MobSpawningStuff;

import com.shampaggon.crackshot.CSUtility;
import me.nomadic.maelstrom.main.Utils.ItemBuilder;
import me.nomadic.maelstrom.main.Utils.ItemTweaker;
import me.nomadic.maelstrom.main.Utils.Text;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Husk;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class Montagne implements Listener {

    Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("MaelstromCore");
    FileConfiguration config;

    public Montagne() {this.config = this.plugin.getConfig();}


    private CSUtility csUtility;


    @EventHandler
    public void Montagne(EntitySpawnEvent event) {

        Entity entity = event.getEntity();


        if(entity.getType() == EntityType.HUSK) {

            Husk husk = (Husk) event.getEntity();
            EntityEquipment equip = husk.getEquipment();
            if(!husk.isAdult()){
                husk.setAdult();
            }


            PotionEffect slowness = new PotionEffect(PotionEffectType.SLOW, 30000, 1);
            PotionEffect health = new PotionEffect(PotionEffectType.HEALTH_BOOST, 30000, 9);
            PotionEffect instahealth = new PotionEffect(PotionEffectType.HEAL, 1, 127);

            husk.setCustomName(Text.color("&7&lMontagne"));
            husk.setCustomNameVisible(true);
            husk.damage(1.0);
            husk.addPotionEffect(slowness);
            husk.addPotionEffect(health);
            husk.addPotionEffect(instahealth);

            ItemStack leatherChest = ItemTweaker.dye(new ItemStack(Material.LEATHER_CHESTPLATE), 241, 162, 14) ;
            ItemStack leatherLeggings = ItemTweaker.dye(new ItemStack(Material.LEATHER_LEGGINGS), 241, 162, 14);
            ItemStack leatherBoots = ItemTweaker.dye(new ItemStack(Material.LEATHER_BOOTS), 241, 162, 14);



            assert equip != null;
            equip.setChestplate(leatherChest);
            equip.setLeggings(leatherLeggings);
            equip.setBoots(leatherBoots);


        }






    }



}
