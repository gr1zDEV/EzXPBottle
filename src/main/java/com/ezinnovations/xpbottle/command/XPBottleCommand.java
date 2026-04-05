package com.ezinnovations.xpbottle.command;

import com.ezinnovations.xpbottle.XPBottlePlugin;
import com.ezinnovations.xpbottle.config.MessageManager;
import com.ezinnovations.xpbottle.gui.XPBottleGuiManager;
import com.ezinnovations.xpbottle.item.XPBottleItemManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class XPBottleCommand implements CommandExecutor, TabCompleter {

    private final XPBottlePlugin plugin;
    private final MessageManager messageManager;
    private final XPBottleGuiManager guiManager;
    private final XPBottleItemManager itemManager;

    public XPBottleCommand(XPBottlePlugin plugin,
                           MessageManager messageManager,
                           XPBottleGuiManager guiManager,
                           XPBottleItemManager itemManager) {
        this.plugin = plugin;
        this.messageManager = messageManager;
        this.guiManager = guiManager;
        this.itemManager = itemManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            if (!(sender instanceof Player player)) {
                messageManager.send(sender, "messages.player-only");
                return true;
            }
            if (!player.hasPermission("xpbottle.use")) {
                messageManager.send(sender, "messages.no-permission");
                return true;
            }
            guiManager.openMainGui(player);
            return true;
        }

        switch (args[0].toLowerCase(Locale.ROOT)) {
            case "help" -> handleHelp(sender);
            case "reload" -> handleReload(sender);
            case "withdraw" -> handleWithdraw(sender, args);
            case "give" -> handleGive(sender, args);
            default -> messageManager.send(sender, "messages.unknown-subcommand");
        }
        return true;
    }

    private void handleHelp(CommandSender sender) {
        for (String line : messageManager.getMessageList("messages.help-lines")) {
            sender.sendMessage(line);
        }
    }

    private void handleReload(CommandSender sender) {
        if (!sender.hasPermission("xpbottle.reload")) {
            messageManager.send(sender, "messages.no-permission");
            return;
        }

        plugin.reloadPlugin();
        messageManager.send(sender, "messages.reload-success");
    }

    private void handleWithdraw(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            messageManager.send(sender, "messages.player-only");
            return;
        }
        if (!player.hasPermission("xpbottle.withdraw")) {
            messageManager.send(sender, "messages.no-permission");
            return;
        }
        if (args.length != 2) {
            messageManager.send(sender, "messages.withdraw-usage");
            return;
        }

        int amount;
        try {
            amount = Integer.parseInt(args[1]);
        } catch (NumberFormatException ex) {
            messageManager.send(sender, "messages.invalid-amount");
            return;
        }

        if (amount <= 0) {
            messageManager.send(sender, "messages.invalid-amount");
            return;
        }

        if (itemManager.getTotalXp(player) < amount) {
            messageManager.send(sender, "messages.not-enough-xp");
            return;
        }

        if (itemManager.withdrawToBottle(player, amount)) {
            messageManager.send(player, "messages.withdrew-xp", Map.of("%xp%", String.valueOf(amount)));
        }
    }

    private void handleGive(CommandSender sender, String[] args) {
        if (!sender.hasPermission("xpbottle.give")) {
            messageManager.send(sender, "messages.no-permission");
            return;
        }
        if (args.length != 3) {
            messageManager.send(sender, "messages.give-usage");
            return;
        }

        Player target = Bukkit.getPlayerExact(args[1]);
        if (target == null) {
            messageManager.send(sender, "messages.player-not-found", Map.of("%player%", args[1]));
            return;
        }

        int amount;
        try {
            amount = Integer.parseInt(args[2]);
        } catch (NumberFormatException ex) {
            messageManager.send(sender, "messages.invalid-amount");
            return;
        }

        if (amount <= 0) {
            messageManager.send(sender, "messages.invalid-amount");
            return;
        }

        itemManager.giveBottle(target, amount);
        messageManager.send(sender, "messages.gave-bottle",
            Map.of("%xp%", String.valueOf(amount), "%player%", target.getName()));
        messageManager.send(target, "messages.received-bottle", Map.of("%xp%", String.valueOf(amount)));
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            List<String> subcommands = new ArrayList<>();
            subcommands.add("help");
            if (sender.hasPermission("xpbottle.withdraw")) {
                subcommands.add("withdraw");
            }
            if (sender.hasPermission("xpbottle.reload")) {
                subcommands.add("reload");
            }
            if (sender.hasPermission("xpbottle.give")) {
                subcommands.add("give");
            }
            return subcommands.stream().filter(s -> s.startsWith(args[0].toLowerCase(Locale.ROOT))).collect(Collectors.toList());
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("give") && sender.hasPermission("xpbottle.give")) {
            return Bukkit.getOnlinePlayers().stream().map(Player::getName)
                .filter(name -> name.toLowerCase(Locale.ROOT).startsWith(args[1].toLowerCase(Locale.ROOT)))
                .collect(Collectors.toList());
        }

        if (args.length == 2 && args[0].equalsIgnoreCase("withdraw") && sender.hasPermission("xpbottle.withdraw")) {
            return List.of("10", "50", "100", "500").stream()
                .filter(s -> s.startsWith(args[1]))
                .collect(Collectors.toList());
        }

        if (args.length == 3 && args[0].equalsIgnoreCase("give") && sender.hasPermission("xpbottle.give")) {
            return List.of("10", "50", "100", "500").stream()
                .filter(s -> s.startsWith(args[2]))
                .collect(Collectors.toList());
        }

        return new ArrayList<>();
    }
}
