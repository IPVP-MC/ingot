package org.ipvp.ingot.impl;

import java.util.Arrays;
import java.util.List;

import org.ipvp.ingot.Hotbar;
import org.ipvp.ingot.Slot;

/**
 * A simple Hotbar implementation that can be given to players.
 */
public class VanillaHotbar implements Hotbar {

    private Slot[] slots = new Slot[9];
    
    public VanillaHotbar() {
        for (int i = 0 ; i < slots.length ; i++) {
            slots[i] = new SlotImpl(i);
        }
    }

    @Override
    public Slot getSlot(int index) {
        if (index < 0 || index > 8) {
            throw new IllegalArgumentException("index must be between 0-8");
        }
        return slots[index];
    }

    @Override
    public List<Slot> getSlots() {
        return Arrays.asList(slots);
    }
}
