package me.nomadic.maelstrom.main.Utils;

import org.bukkit.entity.Player;
import org.ipvp.canvas.Menu;
import org.ipvp.canvas.type.ChestMenu;

public class canvas {

    public Menu createMenu() {
        return ChestMenu.builder(5)
                .title("Stats")
                .build();
    }
    public void displayMenu(Player player) {
        Menu menu = createMenu();
        menu.open(player);
    }



}
