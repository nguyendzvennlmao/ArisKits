package me.aris.ariskits.kit;

import me.aris.ariskits.ArisKits;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class KitManager {
    private final ArisKits plugin;
    private final Map<String, Kit> kits = new HashMap<>();
    private final Map<UUID, Map<String, Long>> cooldowns = new HashMap<>();
    public final Map<UUID, Kit> awaitingTime = new HashMap<>();
    public final Map<UUID, Kit> awaitingSlot = new HashMap<>();

    public KitManager(ArisKits plugin) {
        this.plugin = plugin;
    }

    public void loadKits() {
        kits.clear();
        File folder = new File(plugin.getDataFolder(), "kits");
        if (!folder.exists()) folder.mkdirs();
        File[] files = folder.listFiles((dir, name) -> name.endsWith(".yml"));
        if (files == null) return;
        for (File file : files) {
            FileConfiguration config = YamlConfiguration.loadConfiguration(file);
            String id = file.getName().replace(".yml", "");
            kits.put(id.toLowerCase(), new Kit(id, config.getString("display-name"), (List<ItemStack>) config.getList("items"), config.getLong("cooldown"), config.getBoolean("require-permission"), config.getItemStack("icon"), config.getInt("slot")));
        }
    }

    public void saveKitToFile(Kit kit) {
        File file = new File(plugin.getDataFolder(), "kits/" + kit.getId() + ".yml");
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.set("display-name", kit.getDisplayName());
        config.set("cooldown", kit.getCooldown());
        config.set("require-permission", kit.isRequirePermission());
        config.set("slot", kit.getSlot());
        config.set("icon", kit.getIcon());
        config.set("items", kit.getItems());
        try { config.save(file); } catch (IOException e) { e.printStackTrace(); }
    }

    public void giveKit(Player player, Kit kit) {
        if (kit.isRequirePermission() && !player.hasPermission(kit.getPermission())) {
            plugin.getMessageManager().sendMessage(player, "messages.no-permission");
            plugin.getMessageManager().playSound(player, "error");
            return;
        }
        if (isOnCooldown(player, kit)) {
            plugin.getMessageManager().sendActionBar(player, "messages.cooldown", "%time%", String.valueOf(getRemainingTime(player, kit)));
            plugin.getMessageManager().playSound(player, "error");
            return;
        }
        if (player.getInventory().firstEmpty() == -1) {
            plugin.getMessageManager().sendMessage(player, "messages.inventory-full");
            plugin.getMessageManager().playSound(player, "error");
            return;
        }
        plugin.getFoliaLib().getImpl().runAtEntity(player, (task) -> {
            for (ItemStack item : kit.getItems()) if (item != null) player.getInventory().addItem(item.clone());
            setCooldown(player, kit);
            plugin.getMessageManager().sendMessage(player, "messages.receive-success", "%kit%", kit.getDisplayName());
            plugin.getMessageManager().playSound(player, "receive");
        });
    }

    private void setCooldown(Player player, Kit kit) {
        cooldowns.computeIfAbsent(player.getUniqueId(), k -> new HashMap<>()).put(kit.getId(), System.currentTimeMillis() + (kit.getCooldown() * 1000));
    }

    private boolean isOnCooldown(Player player, Kit kit) {
        if (!cooldowns.containsKey(player.getUniqueId())) return false;
        Long expire = cooldowns.get(player.getUniqueId()).get(kit.getId());
        return expire != null && expire > System.currentTimeMillis();
    }

    private long getRemainingTime(Player player, Kit kit) {
        Long expire = cooldowns.get(player.getUniqueId()).get(kit.getId());
        return (expire - System.currentTimeMillis()) / 1000;
    }

    public void addKit(Kit kit) { kits.put(kit.getId().toLowerCase(), kit); }
    public Collection<Kit> getAllKits() { return kits.values(); }
    public Kit getKit(String id) { return kits.get(id.toLowerCase()); }
                             }
