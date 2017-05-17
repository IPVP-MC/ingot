package org.ipvp.ingot;

import java.util.Optional;

import org.bukkit.entity.Entity;

/**
 * Represents an action performed with a Hotbar item.
 */
public class HotbarAction {
    
    private final ActionHandler.ActionType type;
    private final Entity clicked;
    
    public HotbarAction(ActionHandler.ActionType type) {
        this(type, null);
    }
    
    public HotbarAction(ActionHandler.ActionType type, Entity clicked) {
        this.type = type;
        this.clicked = clicked;
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
}
