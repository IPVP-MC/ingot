package org.ipvp.ingot.impl;

import org.bukkit.entity.Player;
import org.ipvp.ingot.ActionHandler;
import org.ipvp.ingot.Hotbar;
import org.ipvp.ingot.HotbarAction;
import org.ipvp.ingot.HotbarApi;

/**
 * An action handler that switches the current Hotbar of a player.
 */
public class ChangeHotbarActionHandler implements ActionHandler {
    
    private Hotbar target;
    
    public ChangeHotbarActionHandler(Hotbar target) {
        this.target = target;
    }

    @Override
    public void performAction(Player player, HotbarAction action) {
        if (action.getType() != ActionType.HOVER) {
            HotbarApi.setCurrentHotbar(player, target);
        }
    }
}
