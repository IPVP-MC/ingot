package org.ipvp.ingot;

import org.bukkit.entity.Player;

public interface ActionHandler {
    
    enum ActionType {
        DROP_ITEM,
        HOVER,
        INVENTORY,
        LEFT_CLICK,
        RIGHT_CLICK
    }
    
    void click(Player player, ActionType click);
}
