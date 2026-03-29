package me.aris.ariskits.listeners;

import me.aris.ariskits.ArisKits;
import me.aris.ariskits.inventory.KitEditInventory;
import me.aris.ariskits.kit.Kit;
import me.aris.ariskits.utils.TimeUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import java.util.UUID;

public class ChatListener implements Listener {
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();
        if (!ArisKits.getInstance().getKitManager().awaitingTime.containsKey(uuid)) return;
        event.setCancelled(true);
        Kit kit = ArisKits.getInstance().getKitManager().awaitingTime.remove(uuid);
        long seconds = TimeUtils.parseTime(event.getMessage());
        kit.setCooldown(seconds);
        ArisKits.getInstance().getFoliaLib().getImpl().runAtEntity(player, (task) -> {
            KitEditInventory.open(player, kit);
            ArisKits.getInstance().getMessageManager().sendMessage(player, "messages.time-set", "%time%", event.getMessage());
        });
    }
                                                                   }
