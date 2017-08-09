package org.ipvp.ingot.type;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.ipvp.ingot.Hotbar;
import org.ipvp.ingot.Slot;

/**
 * A simple Hotbar implementation that can be given to players.
 */
public class VanillaHotbar implements Hotbar {

    private final Hotbar parent;
    private Slot[] slots = new Slot[9];
    
    public VanillaHotbar() {
        this(null);
    }
    
    public VanillaHotbar(Hotbar parent) {
        this.parent = parent;
        for (int i = 0 ; i < slots.length ; i++) {
            slots[i] = new SlotImpl(i);
        }
    }

    @Override
    public Optional<Hotbar> getParent() {
        return Optional.ofNullable(parent);
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
