package org.ipvp.ingot;

import java.util.Optional;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class HotbarFunctionListener implements Listener {
    
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void handleInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Hotbar hotbar = HotbarApi.getCurrentHotbar(player);
        
        // If the player does not have a hotbar set for them we have
        // nothing to handle here.
        if (hotbar == null || event.getSlot() < 0) {
            return;
        }
        
        Inventory top = event.getView().getTopInventory();
        InventoryAction action = event.getAction();
        int raw = event.getRawSlot();
        int size = top.getSize();
        
        // Inventory size for a players inventory doesn't include the armor 
        // slots so we must adjust here
        if (top.getType() == InventoryType.CRAFTING) {
            size += 4;
        }
        
        int slot = raw - (27 + size); // Normalize the slot number

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
                event.setCancelled(true);
                break;
            
            // All other actions can be handled as a standard click on an item,
            // we double check to find which slot was clicked
            default:
                if (slot >= 0 && slot < 9) {
                    passAction(hotbar, slot, player, ActionHandler.ActionType.INVENTORY);
                    event.setResult(Event.Result.DENY);
                    event.setCancelled(true);
                }
                break;
        }
    }

    // Passes the action to a player
    private void passAction(Hotbar hotbar, int index, Player who, ActionHandler.ActionType action) {
        Slot slot = hotbar.getSlot(index);
        Optional<ActionHandler> actionHandlerOptional = slot.getActionHandler();
        if (actionHandlerOptional.isPresent()) {
            actionHandlerOptional.get().performAction(who, action);
        }
    }
}
