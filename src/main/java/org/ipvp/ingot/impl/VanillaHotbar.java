package org.ipvp.ingot.impl;

import org.bukkit.entity.Player;
import org.ipvp.ingot.Hotbar;
import org.ipvp.ingot.Slot;

public class VanillaHotbar implements Hotbar {

    private Slot[] slots = new Slot[9];
    
    public VanillaHotbar() {
        for (int i = 0 ; i < slots.length ; i++) {
            slots[i] = new SlotImpl(i);
        }
    }
    
    @Override
    public void apply(Player player) {
        for (int i = 0 ; i < 9 ; i++) {
            player.getInventory().setItem(i, getSlot(i).getItem());
        }
    }

    @Override
    public void remove(Player player) {
        for (int i = 0 ; i < 9 ; i++) {
            player.getInventory().setItem(i, null);
        }
    }

    @Override
    public Slot getSlot(int index) {
        if (index < 0 || index > 8) {
            throw new IllegalArgumentException("index must be between 0-8");
        }
        return slots[index];
    }
}
