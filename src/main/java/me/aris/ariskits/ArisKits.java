package me.aris.ariskits;

import com.tcoc.folialib.FoliaLib;
import me.aris.ariskits.commands.KitCommand;
import me.aris.ariskits.kit.KitManager;
import me.aris.ariskits.listeners.ChatListener;
import me.aris.ariskits.listeners.InventoryListener;
import me.aris.ariskits.utils.MessageManager;
import org.bukkit.plugin.java.JavaPlugin;

public class ArisKits extends JavaPlugin {
    private static ArisKits instance;
    private KitManager kitManager;
    private MessageManager messageManager;
    private FoliaLib foliaLib;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        this.foliaLib = new FoliaLib(this);
        this.messageManager = new MessageManager(this);
        this.kitManager = new KitManager(this);
        messageManager.loadMessages();
        kitManager.loadKits();
        getCommand("kit").setExecutor(new KitCommand());
        getServer().getPluginManager().registerEvents(new InventoryListener(), this);
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
    }

    public static ArisKits getInstance() { return instance; }
    public KitManager getKitManager() { return kitManager; }
    public MessageManager getMessageManager() { return messageManager; }
    public FoliaLib getFoliaLib() { return foliaLib; }
          }
