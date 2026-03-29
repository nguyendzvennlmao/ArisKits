package me.aris.ariskits.inventory;

import me.aris.ariskits.kit.Kit;
import me.aris.ariskits.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.List;

public class KitEditInventory {
    public static void open(Player player, Kit kit) {
        Inventory inv = Bukkit.createInventory(null, 54, ColorUtils.format("&#facc15Edit Kit: " + kit.getId()));
        List<ItemStack> items = kit.getItems();
        for (int i = 0; i < items.size() && i < 45; i++) inv.setItem(i, items.get(i));
        
        inv.setItem(45, createItem(kit.isRequirePermission() ? Material.LIME_STAINED_GLASS_PANE : Material.RED_STAINED_GLASS_PANE, "&#facc15Quyền: " + (kit.isRequirePermission() ? "&aTRUE" : "&cFALSE")));
        inv.setItem(46, createItem(Material.CLOCK, "&#facc15Thời gian: &f" + kit.getCooldown() + "s"));
        inv.setItem(47, createItem(kit.getIcon().getType(), "&#facc15Thay đổi Icon (Cầm item click vào)"));
        inv.setItem(48, createItem(Material.COMPASS, "&#facc15Chỉnh Slot (Hiện tại: " + kit.getSlot() + ")"));
        inv.setItem(52, createItem(Material.LIME_WOOL, "&#1CFF47&lLƯU KIT"));
        inv.setItem(53, createItem(Material.RED_WOOL, "&#FF0812&lHỦY"));
        
        player.openInventory(inv);
    }

    private static ItemStack createItem(Material material, String name) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(ColorUtils.format(name));
            item.setItemMeta(meta);
        }
        return item;
    }
    }
