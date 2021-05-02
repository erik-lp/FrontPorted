package me.erik.frontported.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import me.erik.frontported.features.ChatReplacements;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.Text;

import java.util.Map;

public class ChatMacroCommand {
    
    private static final String CMD = "chatmacro";
    
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static void register(CommandDispatcher dispatcher) {
        LiteralCommandNode literalCommandNode = dispatcher.register(
                CommandManager
                        .literal(CMD)
                        .then(CommandManager.literal("list").executes(ctx -> list()))
                        .then(CommandManager.literal("remove")
                                .then(CommandManager.argument("macro", StringArgumentType.string())
                                        .executes(ctx -> remove(StringArgumentType.getString(ctx, "macro")))))
                        .then(CommandManager.literal("add")
                                .then(CommandManager.argument("text", StringArgumentType.string())
                                        .then(CommandManager
                                                .argument("replacement", StringArgumentType.string())
                                                .executes(ctx -> add(StringArgumentType.getString(ctx, "text"), StringArgumentType.getString(ctx, "replacement"))))))
        );
        dispatcher.register(CommandManager.literal(CMD).redirect(literalCommandNode));
    }
    
    private static int list() {
        PlayerEntity player = MinecraftClient.getInstance().player;
        if (player != null) {
            player.sendMessage(Text.of("§5[§6FrontPorted§5] §6Chat Replacements"), false);
            for (Map.Entry<String, String> entry : ChatReplacements.chatReplacements.entrySet()) {
                player.sendMessage(Text.of(String.format("\"%-15s | \"%s\"", entry.getKey() + "\"", entry.getValue())), false);
            }
        }
        return 1;
    }
    
    private static int add(String text, String replacement) {
        ChatReplacements.add(text, replacement);
        if (MinecraftClient.getInstance().player != null)
            MinecraftClient.getInstance().player.sendMessage(Text.of(String.format("Added \"%-15s | \"%s\" to chat replacements.", text + "\"", replacement)), false);
        return 1;
    }
    
    private static int remove(String macro) {
        String removedValue = ChatReplacements.remove(macro);
        if (MinecraftClient.getInstance().player != null) {
            MinecraftClient.getInstance().player.sendMessage(
                    removedValue != null ?
                            Text.of(String.format("Removed \"%-15s | \"%s\" from chat replacements.", macro + "\"", removedValue)) :
                            Text.of(String.format("A chat replacement with key \"%s\" does not exist!", macro)), false);
        }
        return 1;
    }
    
}
