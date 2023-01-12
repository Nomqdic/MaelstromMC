package me.nomadic.maelstrom.main.Commands;


import com.sun.org.apache.xerces.internal.xs.StringList;
import me.nomadic.maelstrom.main.Utils.ItemBuilder;
import me.nomadic.maelstrom.main.Utils.ItemTweaker;
import me.nomadic.maelstrom.main.Utils.Text;
import me.nomadic.maelstrom.main.Utils.canvas;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.ipvp.canvas.Menu;
import org.ipvp.canvas.mask.BinaryMask;
import org.ipvp.canvas.mask.Mask;
import org.ipvp.canvas.slot.Slot;
import org.ipvp.canvas.type.ChestMenu;

import java.util.ArrayList;
import java.util.List;

public class newStats implements CommandExecutor {

    Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("MaelstromCore");
    FileConfiguration config;

    public newStats() {
        this.config = this.plugin.getConfig();
    }

    canvas canvas;
    ItemBuilder itemBuilder;
    ItemTweaker itemTweaker;

    public int XPToNextLevel;


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        if(sender instanceof Player) {
            Player player = (Player) sender;

            String UUID = player.getUniqueId().toString();
            String tUUID = "stats." + UUID;
            int plevel = config.getInt(tUUID + ".level");


            if(config.getInt(tUUID + ".level") <= 10) {
                 XPToNextLevel = plevel^2 + 6* plevel ;
            }else if(config.getInt(tUUID + ".level") <= 30) {

                XPToNextLevel = 3 * plevel^2 - 20 * plevel + 360;
            }else if(config.getInt(tUUID+ ".level") >= 50) {

                 XPToNextLevel = 5 * plevel^2 - 80 * plevel + 2220;
            }

            Menu menu = ChestMenu.builder(5).title("Stats").build();
            Slot levelS = menu.getSlot(32);
            levelS.setItemTemplate(p -> {
                int level = this.config.getInt(tUUID + ".level");
                ItemStack item = new ItemStack(Material.NETHER_STAR);
                ItemMeta itemMeta = item.getItemMeta();
                itemMeta.setDisplayName(Text.color("&6Level:&b " + level));
                item.setItemMeta(itemMeta);
                return item;
            });
            Slot kills = menu.getSlot(30);
            kills.setItemTemplate(p -> {
                int level = this.config.getInt(tUUID + ".kills");
                ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
                ItemMeta itemMeta = item.getItemMeta();
                itemMeta.setDisplayName(Text.color("&6Kills:&b " + level));
                item.setItemMeta(itemMeta);
                item.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
                return item;
            });
            Slot playerS = menu.getSlot(13);
            playerS.setItemTemplate(p -> {

                ItemStack item = ItemTweaker.createPlayerHead(player.getName());
                ItemMeta itemMeta = item.getItemMeta();
                itemMeta.setDisplayName(Text.color("&6Player:&b " + player.getDisplayName()));

                List<String> lore = new ArrayList<>();
                lore.add(Text.color("&6Current Experience: &e" + this.config.getInt(tUUID + ".experience") + "&f/&b" + XPToNextLevel + " &9To Next Level"));
                lore.add(Text.color("&6Prestige: &b" + this.config.getInt(tUUID + ".prestige")));
                lore.add(Text.color("&6Prestige Icon: &b" + this.config.getString(tUUID + ".prestigeIcon")));
                itemMeta.setLore(lore);
                item.setItemMeta(itemMeta);
                return item;
            });

            /*Slot deaths = menu.getSlot(7);
            kills.setItemTemplate(p -> {
                int level = this.config.getInt(tUUID + ".deaths");
                ItemStack item = new ItemStack(Material.DIAMOND_SWORD);
                ItemMeta itemMeta = item.getItemMeta();
                itemMeta.setDisplayName(Text.color("&6Deaths:&b " + level));
                item.setItemMeta(itemMeta);
                return item;
            });*/
            Mask mask = BinaryMask.builder(menu)
                    .item(new ItemStack(Material.GRAY_STAINED_GLASS_PANE))
                    .pattern("111111111") // First row
                    .pattern("100000001") // Second row
                    .pattern("100000001") // Third row
                    .pattern("100000001") // Third row
                    .pattern("111111111").build(); // Fourth row
            mask.apply(menu);



            menu.open(player);


        }else {
            sender.sendMessage(Text.color("&c&lYou must be a player to use this command."));
        }


        return true;

    }
}
