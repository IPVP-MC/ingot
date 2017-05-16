package org.ipvp.ingot;

import org.bukkit.entity.Player;

public interface Hotbar {
    
    void apply(Player player);
    
    void remove(Player player);
    
    Slot getSlot(int index);
}
