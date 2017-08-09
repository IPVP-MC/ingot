package org.ipvp.ingot.type;

import java.util.Optional;

import org.bukkit.inventory.ItemStack;
import org.ipvp.ingot.ActionHandler;
import org.ipvp.ingot.Slot;

public class SlotImpl implements Slot {
    
    private final int index;
    private ItemStack item;
    private ActionHandler handler;
    
    SlotImpl(int index) {
        this.index = index;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public ItemStack getItem() {
        return item;
    }

    @Override
    public void setItem(ItemStack item) {
        this.item = item;
    }

    @Override
    public Optional<ActionHandler> getActionHandler() {
        return Optional.ofNullable(handler);
    }

    @Override
    public void setActionHandler(ActionHandler handler) {
        this.handler = handler;
    }
}
