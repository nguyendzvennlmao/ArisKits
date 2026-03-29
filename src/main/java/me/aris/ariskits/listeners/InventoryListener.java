package me.aris.ariskits.listeners;

import me.aris.ariskits.ArisKits;
import me.aris.ariskits.inventory.KitEditInventory;
import me.aris.ariskits.inventory.KitInventory;
import me.aris.ariskits.kit.Kit;
import me.aris.ariskits.utils.ColorUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;
import java.util.List;

public class InventoryListener implements Listener {
    @EventHandler
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        String title = event.getView().getTitle();
        String mainTitle = ColorUtils.format(ArisKits.getInstance().getMessageManager().getRaw("messages.gui-title"));

        if (ArisKits.getInstance().getKitManager().awaitingSlot.containsKey(player.getUniqueId())) {
            if (title.equals(mainTitle)) {
                event.setCancelled(true);
                Kit kit = ArisKits.getInstance().getKitManager().awaitingSlot.remove(player.getUniqueId());
                kit.setSlot(event.getRawSlot());
                ArisKits.getInstance().getKitManager().saveKitToFile(kit);
                KitEditInventory.open(player, kit);
                return;
            }
        }

        if (title.equals(mainTitle)) {
            event.setCancelled(true);
            ItemStack item = event.getCurrentItem();
            if (item == null) return;
            for (Kit kit : ArisKits.getInstance().getKitManager().getAllKits()) {
                if (item.getItemMeta().getDisplayName().equals(ColorUtils.format(kit.getDisplayName()))) {
                    ArisKits.getInstance().getKitManager().giveKit(player, kit);
                    player.closeInventory();
                    break;
                }
            }
        } else if (title.contains("Edit Kit:")) {
            Kit kit = ArisKits.getInstance().getKitManager().getKit(title.split(": ")[1]);
            if (event.getRawSlot() >= 45) {
                event.setCancelled(true);
                if (event.getRawSlot() == 45) { kit.setRequirePermission(!kit.isRequirePermission()); KitEditInventory.open(player, kit); }
                else if (event.getRawSlot() == 46) { player.closeInventory(); ArisKits.getInstance().getKitManager().awaitingTime.put(player.getUniqueId(), kit); ArisKits.getInstance().getMessageManager().sendMessage(player, "messages.enter-time"); }
                else if (event.getRawSlot() == 47) { if (event.getCursor().getType() != Material.AIR) kit.setIcon(event.getCursor().clone()); KitEditInventory.open(player, kit); }
                else if (event.getRawSlot() == 48) { ArisKits.getInstance().getKitManager().awaitingSlot.put(player.getUniqueId(), kit); KitInventory.open(player); }
                else if (event.getRawSlot() == 52) { List<ItemStack> items = new ArrayList<>(); for (int i = 0; i < 45; i++) { ItemStack it = event.getInventory().getItem(i); if (it != null) items.add(it); } kit.setItems(items); ArisKits.getInstance().getKitManager().saveKitToFile(kit); player.closeInventory(); }
                else if (event.getRawSlot() == 53) player.closeInventory();
            }
        }
    }
                  }
