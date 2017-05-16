package org.ipvp.ingot;

import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import org.bukkit.entity.Player;
import org.ipvp.ingot.impl.VanillaHotbar;

public final class HotbarApi {
    
    private static final Hotbar EMPTY_HOTBAR = new VanillaHotbar();
    private static Map<Player, Hotbar> playerHotbarMap = new WeakHashMap<>();
    
    public static Hotbar getCurrentHotbar(Player player) {
        return playerHotbarMap.get(player);
    }
    
    public static void setCurrentHotbar(Player player, Hotbar hotbar) {
        removeCurrentHotbar(player);
        giveHotbarItems(player, hotbar);
        playerHotbarMap.put(player, hotbar);
    }
    
    private static void removeCurrentHotbar(Player player) {
        Hotbar hotbar = playerHotbarMap.remove(player);
        if (hotbar != null) {
            giveHotbarItems(player, EMPTY_HOTBAR);
        }
    }
    
    private static void giveHotbarItems(Player player, Hotbar hotbar) {
        List<Slot> slots = hotbar.getSlots();
        for (int slot = 0 ; slot < slots.size() ; slot++) {
            player.getInventory().setItem(slot, slots.get(slot).getItem());
        }
    }
    
    private HotbarApi() {
        throw new UnsupportedOperationException();
    }
}
