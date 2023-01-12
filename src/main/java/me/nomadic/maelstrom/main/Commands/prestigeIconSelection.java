package me.nomadic.maelstrom.main.Commands;

import me.nomadic.maelstrom.main.Utils.ItemTweaker;
import me.nomadic.maelstrom.main.Utils.Text;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.ipvp.canvas.Menu;
import org.ipvp.canvas.mask.BinaryMask;
import org.ipvp.canvas.mask.Mask;
import org.ipvp.canvas.slot.ClickOptions;
import org.ipvp.canvas.slot.Slot;
import org.ipvp.canvas.type.ChestMenu;

import java.util.ArrayList;
import java.util.List;

public class prestigeIconSelection implements CommandExecutor {
    Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("MaelstromCore");
    FileConfiguration config;

    public prestigeIconSelection(){this.config = this.plugin.getConfig();}


    public Menu createMenu() {
        return ChestMenu.builder(5)
                .title("Prestige Icon Selector")
                .build();
    }
    public void displayMenu(Player player) {
        Menu menu = createMenu();
        menu.open(player);
    }

    public void addClickOptions(Slot slot){
        ClickOptions options = ClickOptions.DENY_ALL;
        slot.setClickOptions(options);
    }

    public void addClickHandler(Slot slot, String prestigeIcon, int requiredPrestige, Menu menu) {
        slot.setClickHandler((player, info) -> {
            String UUID = player.getUniqueId().toString();
            String PStats = "stats." + UUID;
            slot.setClickOptions(ClickOptions.DENY_ALL);
            if(config.getInt(PStats + ".prestige") >= requiredPrestige) {
                config.set(PStats + ".prestigeIcon", prestigeIcon);
                player.sendMessage(Text.color("&6You Prestige icon has been set to &b" + prestigeIcon));
                this.plugin.saveConfig();

            }else{
                player.sendMessage(Text.color("&cYou must be Prestige " + requiredPrestige + " to use this command"));
            }

            menu.close(player);

            // Additional functionality goes here
        });
    }
    public void addWhiteBorder(Inventory inventory) {

    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        // P1 ⚜️➀
        //p2 ★ ➁
        //p3 ♱ ➂
        //p4 ☺ ➃
        //p5 ☼ ➄
        //p6 ψ ➅
        //p7 ☹ ➆
        //p8 ➇
        //p9 ➈
        //p10 ♕ ➉

        /*■ □ ▢ ▲ △ ▼ ▽ ◆ ◇ ○ ◎ ● ◯ Δ ◕ ◔ʊ ϟ ღ ツ 回
        ₪ ™ © ® ¿ ¡ ½ ⅓ ⅔ ¼ ¾ ⅛ ⅜ ⅝ ⅞ ℅ № ⇨ ❝ ❞ #
        & ℃∃ ∧ ∠ ∨ ∩ ⊂ ⊃ ∪ ⊥ ∀ Ξ Γ  ɐ ə ɘ ε β ɟ ɥ ɯ
        ɔ и ๏ ɹ ʁ я ʌ ʍ λ ч ∞ Σ Π➀ ➁ ➂ ➃ ➄ ➅ ➆ ➇
        ➈ ➉Ⓐ Ⓑ Ⓒ Ⓓ Ⓔ Ⓕ Ⓖ Ⓗ Ⓘ Ⓙ Ⓚ Ⓛ Ⓜ Ⓝ Ⓞ Ⓟ Ⓠ Ⓡ
         Ⓢ Ⓣ Ⓤ Ⓥ Ⓦ Ⓧ Ⓨ Ⓩⓐ ⓑ ⓒ ⓓ ⓔ ⓕ ⓖ ⓗ ⓘ ⓙ ⓚ ⓛ
          ⓜ ⓝ ⓞ ⓟ ⓠ ⓡ ⓢ ⓣ ⓤ ⓥ ⓦ ⓧ ⓨ ⓩ{｡^◕‿◕^(◕^^◕)
          ✖✗✘♒♬✄ ✆✦✧♱♰♂♀☿❤❥❦❧ ™®©♡♦♢♔♕♚♛★ ☆✮ ✯
          ☄☾☽ ☼☀☁☂☃☻ ☺☹ ☮۞۩ εїз☎☏¢ ☚☛☜☝☞☟✍ ✌☢☣☠☮☯
           ♠♤♣♧♥ ♨๑❀✿ ψ☪☭♪ ♩♫℘ℑ ℜℵ♏ηα ʊϟღツ回 ₪™ ©®¿¡½⅓
            ⅔¼¾⅛⅜⅝⅞℅ №⇨❝❞ ◠◡╭╮╯╰ ★☆⊙¤㊣ ★☆♀◆◇ ▆▇██■ ▓回
            □〓≡ ╝╚╔╗╬ ═╓╩ ┠┨┯┷┏ ┓┗┛┳⊥ ﹃﹄┌ ┐└┘∟「 」↑↓→ ←↘↙
            ♀ ♂┇┅﹉﹊ ﹍﹎╭╮╰╯ *^_^* ^*^ ^-^ ^_^ ^︵^∵∴‖ ︱︳
            ︴﹏ ﹋﹌♂♀ ♥♡☜☞☎ ☏⊙◎☺☻ ►◄▧▨ ♨◐◑↔↕ ▪▫☼♦▀
            ▄█▌▐ ░▒▬♦◊ ◦☼♠♣▣ ▤▥▦▩ ぃ◘◙◈♫ ♬♪♩♭♪ の☆→あ
            ￡❤｡◕‿ ◕｡✎✟ஐ ≈๑۩ ۩.. ..۩۩๑ ๑۩۞۩๑ ✲❈➹ ~.~◕ ‿-
            ｡☀☂☁ 【】┱┲❣ ✚✪✣ ✤✥ ✦❉ ❥❦❧❃ ❂❁❀✄☪ ☣☢☠☭
            ♈ ✓✔✕ ✖㊚㊛ *.:｡ ✿*ﾟ‘ﾟ･ ⊙¤㊣★☆ ▁ ▂ ▃ ▄ ▅ ▆ ▇ █
            ⊮ ⊯ ⊰ ⊱ ⊲ ⊳ ⊴ ⊵ ⊶ ⊷ ⊸ ⊹ ⊺ ⊻ ⊼ ⊽ ⊾ ⊿ ⋀ ⋁ ⋂ ⋃
             ⋄ ⋅ ⋆ ⋇ ⋈ ⋉ ⋊ ⋋ ⋌ ⋍ ⋎ ⋏ ⋐ ⋑ ⋒ ⋓ ⋔ ⋕ ⋖ ⋗ ⋘ ⋙
              ⋚ ⋛ ⋜ ⋝ ⋞ ⋟ ⋠ ⋡ ⋢ ⋣ ⋤ ⋥ ⋦ ⋧ ⋨ ⋩ ⋪ ⋫ ⋬ ⋭ ⋮ ⋯
              ⋰ ⋱ ⋲ ⋳ ⋴ ⋵ ⋶ ⋷ ⋸ ⋹ ⋺ ⋻ ⋼ ⋽ ⋾ ⋿ ⌀ ⌁ ⌂ ⌃ ⌄ ⌅
              ⌆ ⌇ ⌈ ⌉ ⌊ ⌋
         */


        //(AchievementStuff)
        //♫ slayer
        //☠ ultra slayer
        //☮ genocidal
        //Ω hellwalker
        //☢ killionaire

        if(sender instanceof Player) {


            Player player = (Player) sender;
            String UUID = player.getUniqueId().toString();
            String PStats = "stats." + UUID;


            Menu menu = createMenu();

            Mask mask = BinaryMask.builder(menu)
                    .item(new ItemStack(Material.GRAY_STAINED_GLASS_PANE))
                    .pattern("111111111") // First row
                    .pattern("100000001") // Second row
                    .pattern("100000001") // Third row
                    .pattern("100000001") // Third row
                    .pattern("111111111").build(); // Fourth row
            mask.apply(menu);
            Slot p1CS = menu.getSlot(10);
            Slot p1NS = menu.getSlot(19);
            Slot p2CS = menu.getSlot(11);
            Slot p2NS = menu.getSlot(20);
            Slot p3CS = menu.getSlot(12);
            Slot p3NS = menu.getSlot(21);
            Slot p4CS = menu.getSlot(13);
            Slot p4NS = menu.getSlot(22);
            Slot p5CS = menu.getSlot(14);
            Slot p5NS = menu.getSlot(23);

            p1CS.setItemTemplate(p -> {
                ItemStack p1C = new ItemStack(Material.END_CRYSTAL);
                ItemMeta p1CM = p1C.getItemMeta();
                p1CM.setDisplayName(Text.color("&9&lPrestige 1"));
                List<String> p1Cl = new ArrayList<>();

                p1Cl.add(Text.color("&7Set your Prestige Icon to &b⚜"));
                p1CM.setLore(p1Cl);
                p1C.setItemMeta(p1CM);
                addClickOptions(p1CS);

                p1CS.setItem(p1C);

                return p1C;
            });

            p1NS.setItemTemplate(p -> {
                ItemStack p1N = new ItemStack(Material.END_CRYSTAL);
                ItemMeta p1NM = p1N.getItemMeta();
                p1NM.setDisplayName(Text.color("&9&lPrestige 1"));
                List<String> p1Nl = new ArrayList<>();
                p1Nl.add(Text.color("&7Set your Prestige Icon to &b➀"));
                p1NM.setLore(p1Nl);
                addClickOptions(p1NS);

                p1N.setItemMeta(p1NM);
                p1NS.setItem(p1N);

                return p1N;
            });
            p2CS.setItemTemplate(p -> {
                ItemStack p2C = new ItemStack(Material.END_CRYSTAL);
                ItemMeta p2CM = p2C.getItemMeta();
                p2CM.setDisplayName(Text.color("&9&lPrestige 2"));
                List<String> p2Cl = new ArrayList<>();

                p2Cl.add(Text.color("&7Set your Prestige Icon to &b★"));
                p2CM.setLore(p2Cl);
                p2C.setItemMeta(p2CM);
                addClickOptions(p2CS);
                p2CS.setItem(p2C);
                return p2C;
            });

            p2NS.setItemTemplate(p -> {
                ItemStack p2N = new ItemStack(Material.END_CRYSTAL);
                ItemMeta p2NM = p2N.getItemMeta();
                p2NM.setDisplayName(Text.color("&9&lPrestige 2"));
                List<String> p2Nl = new ArrayList<>();
                p2Nl.add(Text.color("&7Set your Prestige Icon to &b➁"));
                p2NM.setLore(p2Nl);
                p2N.setItemMeta(p2NM);
                addClickOptions(p2NS);

                p2NS.setItem(p2N);
                return p2N;
            });

            addClickHandler(p1CS, "&b⚜", 1, menu);
            addClickHandler(p1NS, "&b➀", 1, menu);
            addClickHandler(p2CS, "&9★", 2, menu);
            addClickHandler(p2NS, "&9➁", 2, menu);
             menu.open(player);

        }

        return true;
    }
}
