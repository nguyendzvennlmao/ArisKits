package me.aris.ariskits.commands;

import me.aris.ariskits.ArisKits;
import me.aris.ariskits.inventory.KitEditInventory;
import me.aris.ariskits.inventory.KitInventory;
import me.aris.ariskits.kit.Kit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class KitCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;
        if (args.length == 0) {
            KitInventory.open(player);
            return true;
        }
        if (args[0].equalsIgnoreCase("create") && player.hasPermission("ariskits.admin")) {
            if (args.length < 2) return true;
            String name = args[1];
            Kit newKit = new Kit(name, name, new ArrayList<>(), 0, true, new ItemStack(Material.CHEST), 0);
            ArisKits.getInstance().getKitManager().addKit(newKit);
            KitEditInventory.open(player, newKit);
            ArisKits.getInstance().getMessageManager().sendMessage(player, "messages.create-success", "%kit%", name);
            return true;
        }
        if (args[0].equalsIgnoreCase("edit") && player.hasPermission("ariskits.admin")) {
            if (args.length < 2) return true;
            Kit kit = ArisKits.getInstance().getKitManager().getKit(args[1]);
            if (kit != null) KitEditInventory.open(player, kit);
            return true;
        }
        if (args[0].equalsIgnoreCase("reload") && player.hasPermission("ariskits.admin")) {
            ArisKits.getInstance().getMessageManager().loadMessages();
            ArisKits.getInstance().getKitManager().loadKits();
            ArisKits.getInstance().getMessageManager().sendMessage(player, "messages.reload");
            return true;
        }
        Kit kit = ArisKits.getInstance().getKitManager().getKit(args[0]);
        if (kit != null) ArisKits.getInstance().getKitManager().giveKit(player, kit);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) return Arrays.asList("create", "edit", "reload", "delete", "time", "slot").stream().filter(s -> s.startsWith(args[0].toLowerCase())).collect(Collectors.toList());
        if (args.length == 2 && (args[0].equalsIgnoreCase("edit") || args[0].equalsIgnoreCase("delete"))) {
            return ArisKits.getInstance().getKitManager().getAllKits().stream().map(Kit::getId).filter(s -> s.startsWith(args[1].toLowerCase())).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
    }
