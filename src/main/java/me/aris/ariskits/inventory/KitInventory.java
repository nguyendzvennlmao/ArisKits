package me.aris.ariskits.inventory;

import me.aris.ariskits.ArisKits;
import me.aris.ariskits.kit.Kit;
import me.aris.ariskits.utils.ColorUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class KitInventory {
    public static void open(Player player) {
        String title = ArisKits.getInstance().getMessageManager().getRaw("messages.gui-title");
        Inventory inv = Bukkit.createInventory(null, 54, ColorUtils.format(title));
        List<Kit> kits = ArisKits.getInstance().getKitManager().getAllKits().stream().sorted(Comparator.comparingInt(Kit::getSlot)).collect(Collectors.toList());
        for (Kit kit : kits) {
            boolean hasPerm = !kit.isRequirePermission() || player.hasPermission(kit.getPermission());
            ItemStack icon = hasPerm ? (kit.getIcon() != null ? kit.getIcon().clone() : new ItemStack(Material.CHEST)) : new ItemStack(Material.BARRIER);
            ItemMeta meta = icon.getItemMeta();
            if (meta != null) {
                meta.setDisplayName(ColorUtils.format(kit.getDisplayName()));
                List<String> lore = new ArrayList<>();
                lore.add("");
                lore.add(ColorUtils.format(hasPerm ? "&#1CFF47▶ Click để nhận bộ kit này" : "&#FF0812✕ Bạn không có quyền sở hữu"));
                meta.setLore(lore);
                icon.setItemMeta(meta);
            }
            if (kit.getSlot() >= 0 && kit.getSlot() < 54) inv.setItem(kit.getSlot(), icon);
            else inv.addItem(icon);
        }
        player.openInventory(inv);
    }
              }
