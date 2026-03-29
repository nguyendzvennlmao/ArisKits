package me.aris.ariskits.utils;

import me.aris.ariskits.ArisKits;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import java.io.File;

public class MessageManager {
    private final ArisKits plugin;
    private FileConfiguration msgConfig;

    public MessageManager(ArisKits plugin) {
        this.plugin = plugin;
    }

    public void loadMessages() {
        File file = new File(plugin.getDataFolder(), "messages.yml");
        if (!file.exists()) plugin.saveResource("messages.yml", false);
        msgConfig = YamlConfiguration.loadConfiguration(file);
    }

    public void sendMessage(Player player, String path) {
        String msg = msgConfig.getString(path);
        if (msg == null || msg.isEmpty()) return;
        player.sendMessage(ColorUtils.format(msg));
    }

    public void sendMessage(Player player, String path, String placeholder, String value) {
        String msg = msgConfig.getString(path);
        if (msg == null || msg.isEmpty()) return;
        player.sendMessage(ColorUtils.format(msg.replace(placeholder, value)));
    }

    public void sendActionBar(Player player, String path, String placeholder, String value) {
        String msg = msgConfig.getString(path);
        if (msg == null || msg.isEmpty()) return;
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ColorUtils.format(msg.replace(placeholder, value))));
    }

    public void playSound(Player player, String path) {
        String soundName = plugin.getConfig().getString("sounds." + path);
        if (soundName == null) return;
        try {
            player.playSound(player.getLocation(), Sound.valueOf(soundName), 1.0f, 1.0f);
        } catch (Exception ignored) {}
    }

    public String getRaw(String path) {
        return msgConfig.getString(path, "");
    }
            }
