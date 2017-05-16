package org.ipvp.ingot;

import java.util.List;

public interface Hotbar {
    
    Slot getSlot(int index);
    
    List<Slot> getSlots();
}
