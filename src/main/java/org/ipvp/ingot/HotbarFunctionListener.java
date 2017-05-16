package org.ipvp.ingot;

import java.util.Objects;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class HotbarFunctionListener implements Listener {
    
    private JavaPlugin plugin;
    
    public HotbarFunctionListener(JavaPlugin plugin) {
        Objects.requireNonNull(plugin);
        this.plugin = plugin;
    }
}
