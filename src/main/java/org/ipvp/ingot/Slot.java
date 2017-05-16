package org.ipvp.ingot;

import java.util.Optional;

import org.bukkit.inventory.ItemStack;

public interface Slot {

    int getIndex();
    
    ItemStack getItem();
    
    void setItem(ItemStack item);

    Optional<ActionHandler> getActionHandler();

    void setActionHandler(ActionHandler handler);
}
