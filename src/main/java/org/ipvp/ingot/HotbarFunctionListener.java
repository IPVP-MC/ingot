package org.ipvp.ingot;

import java.util.Optional;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;

public class HotbarFunctionListener implements Listener {
    
    @EventHandler
    public void handleInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Hotbar hotbar = HotbarApi.getCurrentHotbar(player);
        
        // If the player does not have a hotbar set for them we have
        // nothing to handle here.
        if (hotbar == null) {
            return;
        }
        
        InventoryAction action = event.getAction();
        int slot = event.getSlot();
        System.out.print(slot);
        
        switch (action) {
            // Unhandled events that have nothing to do with hotbar interaction
            case DROP_ALL_CURSOR:
            case DROP_ONE_CURSOR:
            case CLONE_STACK:
            case UNKNOWN:
                break;

            // Events that we cancel to prevent the addition of other 
            // items into the hotbar
            case COLLECT_TO_CURSOR:
            case HOTBAR_SWAP:
            case HOTBAR_MOVE_AND_READD:
                event.setResult(Event.Result.DENY);
                break;
            
            // All other actions can be handled as a standard click on an item,
            // we double check to find which slot was clicked
            default:
                if (slot < 8) {
                    passClick(hotbar, slot, player, ActionHandler.ActionType.INVENTORY);
                    event.setResult(Event.Result.DENY);
                }
                break;
        }
    }

    private void passClick(Hotbar hotbar, int index, Player who, ActionHandler.ActionType action) {
        Slot slot = hotbar.getSlot(index);
        Optional<ActionHandler> actionHandlerOptional = slot.getActionHandler();
        if (actionHandlerOptional.isPresent()) {
            actionHandlerOptional.get().click(who, action);
        }
    }
}
