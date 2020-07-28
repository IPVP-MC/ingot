package org.ipvp.ingot;

import org.bukkit.entity.Player;

/**
 * An action handler defined for when a player interacts with a Hotbar item.
 * <p>
 * Interaction can occur in the following ways:
 * <ul>
 *     <li>A player drops an item by pressing Q (or bound key)
 *     <li>A player presses their drop key on an item in an inventory view
 *     <li>When switching items in the hotbar, the current item selection settles on the slot
 *     <li>A player clicks on the hotbar item while in an inventory view
 *     <li>A player left clicks with the item in their hand
 *     <li>A player right clicks with the item in their hand
 * </ul>
 */
@FunctionalInterface
public interface ActionHandler {

    /**
     * The type of action performed on a hotbar item
     */
    enum ActionType {
        /**
         * Holding the item in a players hand
         */
        HOVER,
        /**
         * Previous hovering item in a players hand
         */
        UNHOVER,
        /**
         * Clicking on the item inside an inventory
         */
        INVENTORY,
        /**
         * Left clicking with the item in their hand
         */
        LEFT_CLICK,
        /**
         * Left clicking an Entity with the item in their hand
         */
        LEFT_CLICK_ENTITY,
        /**
         * Right clicking with the item in their hand
         */
        RIGHT_CLICK,
        /**
         * Right clicking an Entity with the item in their hand
         */
        RIGHT_CLICK_ENTITY;

        /**
         * Returns whether the action is a right click.
         *
         * @return true if right click
         */
        public boolean isRightClick() {
            return this == RIGHT_CLICK || this == RIGHT_CLICK_ENTITY;
        }

        /**
         * Returns whether the action is a left click.
         *
         * @return true if left click
         */
        public boolean isLeftClick() {
            return this == LEFT_CLICK || this == LEFT_CLICK_ENTITY;
        }
    }

    /**
     * Called when a player interacts with a hotbar item.
     * 
     * @param player The player that clicked
     * @param action The action performed
     */
    void performAction(Player player, HotbarAction action);
}
