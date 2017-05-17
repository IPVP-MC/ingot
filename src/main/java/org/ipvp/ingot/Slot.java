package org.ipvp.ingot;

import java.util.Optional;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * A slot inside a players Hotbar.
 */
public interface Slot {

    /**
     * Returns the index of the slot.
     * 
     * @return The slots index, bound between 0 and 8 inclusive.
     */
    int getIndex();

    /**
     * Returns the current item defined to be in the slot.
     * 
     * @return The slots current item
     */
    ItemStack getItem();

    /**
     * Sets the item defined for this slot.
     * <p>
     * Players which already have a Hotbar with the slot must have 
     * their Hotbar and inventory updated through the 
     * {@link HotbarApi#setCurrentHotbar(Player, Hotbar)} method.
     * 
     * @param item The new item, or null
     */
    void setItem(ItemStack item);

    /**
     * Returns the slots ActionHandler.
     * 
     * @return The action to perform when this slot is interacted with
     */
    Optional<ActionHandler> getActionHandler();

    /**
     * Sets the slots ActionHandler.
     * 
     * @param handler The new action handler for the slot
     */
    void setActionHandler(ActionHandler handler);
}
