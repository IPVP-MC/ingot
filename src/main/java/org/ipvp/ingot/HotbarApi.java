package org.ipvp.ingot;

import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import org.bukkit.entity.Player;
import org.ipvp.ingot.impl.VanillaHotbar;

/**
 * Core functions for controlling Hotbar distribution for players.
 */
public final class HotbarApi {
    
    private static final Hotbar EMPTY_HOTBAR = new VanillaHotbar();
    private static Map<Player, Hotbar> playerHotbarMap = new WeakHashMap<>();

    /**
     * Returns the current hotbar a player is holding.
     * 
     * @param player The player to check
     * @return The players current hotbar 
     */
    public static Hotbar getCurrentHotbar(Player player) {
        return playerHotbarMap.get(player);
    }

    /**
     * Sets the players hotbar. Calling this method will remove any
     * items the player currently has in their hotbar inventory, replacing
     * them with the contents of the new Hotbar. 
     * 
     * @param player The player to set
     * @param hotbar The new hotbar for the player
     */
    public static void setCurrentHotbar(Player player, Hotbar hotbar) {
        removeCurrentHotbar(player);
        if (hotbar != null) {
            giveHotbarItems(player, hotbar);
            playerHotbarMap.put(player, hotbar);
        }
    }
    
    // Removes the current Hotbar of a player
    private static void removeCurrentHotbar(Player player) {
        Hotbar hotbar = playerHotbarMap.remove(player);
        if (hotbar != null) {
            giveHotbarItems(player, EMPTY_HOTBAR);
        }
    }
    
    // Sets the hotbar items of a player to the new Hotbar
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
