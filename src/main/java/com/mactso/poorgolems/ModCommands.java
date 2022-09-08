package com.mactso.poorgolems;

import com.mactso.poorgolems.config.MyConfig;
import com.mactso.poorgolems.util.Utility;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;

import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.server.level.ServerPlayer;

public class ModCommands {
	String subcommand = "";
	String value = "";

	public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
		dispatcher.register(Commands.literal("poorgolems").requires((source) -> {
			return source.hasPermission(2);
		}).then(Commands.literal("debugLevel")
				.then(Commands.argument("debugLevel", IntegerArgumentType.integer(0, 2)).executes(ctx -> {
					ServerPlayer serverPlayerEntity = (ServerPlayer) ctx.getSource().getEntity();
					Utility.sendChat(serverPlayerEntity, "Poor Golems Debug level set.");
					return setDebugLevel(IntegerArgumentType.getInteger(ctx, "debugLevel"));
					// return 1;
				}))).then(Commands.literal("info").executes(ctx -> {
					ServerPlayer serverPlayerEntity = (ServerPlayer) ctx.getSource().getEntity();
					String chatMessage = "Poor Golems Info" + "\n Debug Level....:" + MyConfig.debugLevel  
							+ "\n Drop Mode....: "+ MyConfig.getIronGolemDropMode() + " (" + MyConfig.getIronGolemDropModeAsString() + ")"
							+ "\n Seconds Between Drops ... :" + MyConfig.getSecondsBetweenIronDrops()	;
					Utility.sendChat(serverPlayerEntity, chatMessage);
					return 1;
				})));
	}

	public static int setDebugLevel(int newDebugLevel) {
		MyConfig.debugLevel = newDebugLevel;
		MyConfig.pushDebugValue();
		return 1;
	}
	
}
