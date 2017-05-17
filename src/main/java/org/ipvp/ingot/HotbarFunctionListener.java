package org.ipvp.ingot;

import java.util.Optional;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

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
            case CLONE_STACK:
            case UNKNOWN:
                break;

            case DROP_ALL_CURSOR:
            case DROP_ONE_CURSOR:
                event.setCancelled(true);
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
                    ActionHandler.ActionType type = (action == InventoryAction.DROP_ALL_SLOT || action == InventoryAction.DROP_ONE_SLOT)
                            ? ActionHandler.ActionType.DROP_ITEM : ActionHandler.ActionType.INVENTORY;
                    passAction(hotbar, slot, player, type);
                    event.setResult(Event.Result.DENY);
                    event.setCancelled(true);
                }
                break;
        }

        // Update the players inventory to reflect the cancellation of the event
        if (event.isCancelled() || event.getResult() == Event.Result.DENY) {
            player.updateInventory();
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
    
    @EventHandler
    public void handleHotbarUse(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Hotbar hotbar = HotbarApi.getCurrentHotbar(player);
        
        // Don't process the event if the player has no hotbar
        if (hotbar == null || event.getAction() == Action.PHYSICAL) {
            return;
        }
        
        Action action = event.getAction();
        int slot = player.getInventory().getHeldItemSlot();
        ActionHandler.ActionType type = (action == Action.LEFT_CLICK_BLOCK || action == Action.LEFT_CLICK_AIR) 
                ? ActionHandler.ActionType.LEFT_CLICK : ActionHandler.ActionType.RIGHT_CLICK;
        
        // Pass the action to the slot
        passAction(hotbar, slot, player, type);
        event.setUseItemInHand(Event.Result.DENY);
        event.setCancelled(true);
    }
    
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void hadleHotbarHover(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        Hotbar hotbar = HotbarApi.getCurrentHotbar(player);
        
        // Don't process if the player has no hotbar
        if (hotbar == null) {
            return;
        }
        
        int slot = event.getNewSlot();
        passAction(hotbar, slot, player, ActionHandler.ActionType.HOVER);
    }
    
    @EventHandler
    public void handleHotbarDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        Hotbar hotbar = HotbarApi.getCurrentHotbar(player);

        // Don't process if the player has no Hotbar
        if (hotbar == null) {
            return;
        }

        Slot slot = hotbar.getSlot(player.getInventory().getHeldItemSlot());
        ItemStack drop = event.getItemDrop().getItemStack();

        if (slot.getItem() != null && slot.getItem().isSimilar(drop)) {
            passAction(hotbar, slot.getIndex(), player, ActionHandler.ActionType.DROP_ITEM);
            event.setCancelled(true);
        }
    }
}
