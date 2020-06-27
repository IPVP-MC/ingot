package org.ipvp.ingot;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;

import java.util.Optional;

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
            case DROP_ALL_SLOT:
            case DROP_ONE_CURSOR:
            case DROP_ONE_SLOT:
                event.setCancelled(true);
                break;
            
            // Events that we cancel to prevent the addition of other 
            // items into the hotbar
            case COLLECT_TO_CURSOR:
            case HOTBAR_SWAP:
            case HOTBAR_MOVE_AND_READD:
                event.setCancelled(true);
                break;
            
            // All other actions can be handled as a standard click on an item,
            // we double check to find which slot was clicked
            default:
                if (slot >= 0 && slot < 9) {
                    passAction(hotbar, slot, player, ActionHandler.ActionType.INVENTORY, event);
                    event.setCancelled(true);
                }
                break;
        }
    }

    // Passes the action to a player
    private void passAction(Hotbar hotbar, int index, Player who, ActionHandler.ActionType action, Cancellable cancellable) {
        passAction(hotbar, index, who, new HotbarAction(action, cancellable));
    }

    // Passes the action to a player
    private void passAction(Hotbar hotbar, int index, Player who, ActionHandler.ActionType action, Cancellable cancellable, Entity entity) {
        passAction(hotbar, index, who, new HotbarAction(action, cancellable, entity));
    }

    // Passes the action to a player
    private void passAction(Hotbar hotbar, int index, Player who, ActionHandler.ActionType action, Cancellable cancellable,  Block block) {
        passAction(hotbar, index, who, new HotbarAction(action, cancellable, block));
    }
    
    private void passAction(Hotbar hotbar, int index, Player who, HotbarAction action) {
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
        passAction(hotbar, slot, player, type, event, event.getClickedBlock());
        event.setUseItemInHand(Event.Result.DENY);
        event.setCancelled(true);
    }
    
    @EventHandler
    public void handleHotbarUse(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Hotbar hotbar = HotbarApi.getCurrentHotbar(player);

        // Don't process the event if the player has no hotbar
        if (hotbar == null) {
            return;
        }

        Entity clicked = event.getRightClicked();
        passAction(hotbar, player.getInventory().getHeldItemSlot(), player, ActionHandler.ActionType.RIGHT_CLICK_ENTITY, event, clicked);
        event.setCancelled(true);
    }
    
    @EventHandler
    public void handleHotbarUse(EntityDamageByEntityEvent event) {
        // Since we are only looking for players with Hotbars, we don't 
        // want any other attacking entity.
        if (!(event.getDamager() instanceof Player)) {
            return;
        }
        
        Player player = (Player) event.getDamager();
        Hotbar hotbar = HotbarApi.getCurrentHotbar(player);
        
        // Don't process the event if the player has no hotbar
        if (hotbar == null) {
            return;
        }
        
        passAction(hotbar, player.getInventory().getHeldItemSlot(), player, ActionHandler.ActionType.LEFT_CLICK_ENTITY, event, event.getEntity());
        event.setCancelled(true);
    }
    
    @EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
    public void handleHotbarHover(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        Hotbar hotbar = HotbarApi.getCurrentHotbar(player);
        
        // Don't process if the player has no hotbar
        if (hotbar == null) {
            return;
        }
        
        int slot = event.getNewSlot();
        int previousSlot = event.getPreviousSlot();
        passAction(hotbar, previousSlot, player, ActionHandler.ActionType.UNHOVER, event);
        passAction(hotbar, slot, player, ActionHandler.ActionType.HOVER, event);
    }


    @EventHandler
    public void handleHotbarDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        Hotbar hotbar = HotbarApi.getCurrentHotbar(player);

        // Don't process if the player has no hotbar
        if (hotbar == null) {
            return;
        }
        
        event.setCancelled(true);
    }

    @EventHandler
    public void handleHotbarPickup(EntityPickupItemEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            Hotbar hotbar = HotbarApi.getCurrentHotbar(player);

            // Don't process if the player has no hotbar
            if (hotbar == null) {
                return;
            }

            event.setCancelled(true);
        }
    }

    @EventHandler
    public void handleHotbarSwap(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();
        Hotbar hotbar = HotbarApi.getCurrentHotbar(player);

        // Don't process if the player has no hotbar
        if (hotbar == null) {
            return;
        }

        event.setCancelled(true);
    }
}