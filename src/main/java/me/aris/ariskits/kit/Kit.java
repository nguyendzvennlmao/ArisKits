package me.aris.ariskits.kit;

import org.bukkit.inventory.ItemStack;
import java.util.List;

public class Kit {
    private final String id;
    private String displayName;
    private List<ItemStack> items;
    private long cooldown;
    private boolean requirePermission;
    private ItemStack icon;
    private int slot;

    public Kit(String id, String displayName, List<ItemStack> items, long cooldown, boolean requirePermission, ItemStack icon, int slot) {
        this.id = id;
        this.displayName = displayName;
        this.items = items;
        this.cooldown = cooldown;
        this.requirePermission = requirePermission;
        this.icon = icon;
        this.slot = slot;
    }

    public String getId() { return id; }
    public String getDisplayName() { return displayName; }
    public List<ItemStack> getItems() { return items; }
    public long getCooldown() { return cooldown; }
    public boolean isRequirePermission() { return requirePermission; }
    public String getPermission() { return "ariskits.kit." + id; }
    public ItemStack getIcon() { return icon; }
    public int getSlot() { return slot; }

    public void setDisplayName(String displayName) { this.displayName = displayName; }
    public void setItems(List<ItemStack> items) { this.items = items; }
    public void setCooldown(long cooldown) { this.cooldown = cooldown; }
    public void setRequirePermission(boolean requirePermission) { this.requirePermission = requirePermission; }
    public void setIcon(ItemStack icon) { this.icon = icon; }
    public void setSlot(int slot) { this.slot = slot; }
}
