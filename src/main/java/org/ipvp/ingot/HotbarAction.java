package org.ipvp.ingot;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.Cancellable;

import java.util.Optional;

/**
 * Represents an action performed with a Hotbar item.
 */
public class HotbarAction implements Cancellable {
    
    private final ActionHandler.ActionType type;
    private final Cancellable cancellable;
    private Entity clicked;
    private Block block;

    private boolean interactable = false;
    
    public HotbarAction(ActionHandler.ActionType type, Cancellable cancellable) {
        this.type = type;
        this.cancellable = cancellable;
    }
    
    public HotbarAction(ActionHandler.ActionType type, Cancellable cancellable, Entity clicked) {
        this(type, cancellable);
        this.clicked = clicked;
    }
    
    public HotbarAction(ActionHandler.ActionType type, Cancellable cancellable, Block block) {
        this(type, cancellable);
        this.block = block;
    }

    /**
     * Returns the type of action performed.
     * 
     * @return The action type
     */
    public ActionHandler.ActionType getType() {
        return type;
    }

    /**
     * Returns any entity involved in the action.
     * <p>
     * The returned optional will only be present when Action type 
     * is either LEFT_CLICK_ENTITY or RIGHT_CLICK_ENTITY.
     * 
     * @return The entity involved in the action
     */
    public Optional<Entity> getClickedEntity() {
        return Optional.ofNullable(clicked);
    }

    /**
     * Returns any block involved in the action.
     * <p>
     * The returned optional will be present when Action type 
     * is either LEFT_CLICK or RIGHT_CLICK.
     * 
     * @return The clicked block in the action
     */
    public Optional<Block> getClickedBlock() {
        return Optional.ofNullable(block);
    }

    /**
     * Returns whether an item is interactable
     *
     * @return true if interactable
     */
    public boolean isInteractable() {
        return interactable;
    }

    @Override
    public boolean isCancelled() {
        return cancellable.isCancelled();
    }

    @Override
    public void setCancelled(boolean cancel) {
        interactable = !cancel;
        cancellable.setCancelled(cancel);
    }
}
