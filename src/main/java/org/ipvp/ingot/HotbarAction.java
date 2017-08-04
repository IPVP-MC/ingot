package org.ipvp.ingot;

import java.util.Optional;

import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

/**
 * Represents an action performed with a Hotbar item.
 */
public class HotbarAction {
    
    private final ActionHandler.ActionType type;
    private Entity clicked;
    private Block block;
    
    public HotbarAction(ActionHandler.ActionType type) {
        this.type = type;
    }
    
    public HotbarAction(ActionHandler.ActionType type, Entity clicked) {
        this(type);
        this.clicked = clicked;
    }
    
    public HotbarAction(ActionHandler.ActionType type, Block block) {
        this(type);
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
}
